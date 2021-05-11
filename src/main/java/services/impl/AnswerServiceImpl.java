package services.impl;

import dto.AnswerDto;
import exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import models.Answer;
import repositories.*;
import services.AnswerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final UserRepository userRepository;
    @Override
    public void save(AnswerDto entity) {
        Answer answer = Answer.builder()
                .id(entity.getId())
                .question(questionRepository.findByText(entity.getQuestionText()).get())
                .questionOption(questionOptionRepository.findByText(entity.getQuestionOptionText()).get())
                .author(userRepository.findOneByUsername(entity.getUsername()).get())
                .build();
        answerRepository.save(answer);
    }

    @Override
    public AnswerDto findById(int id) throws NotFoundException {
        Optional<Answer> optionalAnswer = answerRepository.findById(id);
        if (optionalAnswer.isPresent()) {
            Answer answer = optionalAnswer.get();
            return AnswerDto.builder()
                    .id(answer.getId())
                    .questionText(answer.getQuestion().getText())
                    .questionOptionText(answer.getQuestionOption().getText())
                    .username(answer.getAuthor().getUsername())
                    .build();
        } else throw new NotFoundException("Ne naideno, otec");
    }

    @Override
    public void update(AnswerDto entity) {
        Answer answer = Answer.builder()
                .id(entity.getId())
                .question(questionRepository.findByText(entity.getQuestionText()).get())
                .questionOption(questionOptionRepository.findByText(entity.getQuestionOptionText()).get())
                .author(userRepository.findOneByUsername(entity.getUsername()).get())
                .build();
        answerRepository.update(answer);
    }

    @Override
    public void deleteById(int id) {
        answerRepository.deleteById(id);
    }

    @Override
    public List<AnswerDto> findAll() throws NotFoundException {
        List<Answer> answers = answerRepository.findAll();
        if (answers.isEmpty()) {
            throw new NotFoundException("Ne naideno, otec");
        } else {
            List<AnswerDto> answerDtos = new ArrayList<>();
            for (int i = 0; i < answers.size(); i++) {
                answerDtos.add(AnswerDto.builder()
                        .id(answers.get(i).getId())
                        .questionText(answers.get(i).getQuestion().getText())
                        .questionOptionText(answers.get(i).getQuestionOption().getText())
                        .username(answers.get(i).getAuthor().getUsername())
                        .build());
            }
            return answerDtos;
        }
    }
}
