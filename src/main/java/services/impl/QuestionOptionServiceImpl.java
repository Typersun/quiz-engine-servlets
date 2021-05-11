package services.impl;

import dto.QuestionOptionDto;
import exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import models.QuestionOption;
import repositories.QuestionOptionRepository;
import repositories.QuestionRepository;
import services.QuestionOptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class QuestionOptionServiceImpl implements QuestionOptionService {
    private final QuestionOptionRepository questionOptionRepository;
    private final QuestionRepository questionRepository;
    @Override
    public void save(QuestionOptionDto entity) {
        QuestionOption questionOption = QuestionOption.builder()
                .id(entity.getId())
                .text(entity.getText())
                .isCorrect(entity.isCorrect())
                .question(questionRepository.findById(entity.getQuestionId()).get())
                .build();
        questionOptionRepository.save(questionOption);
    }

    @Override
    public QuestionOptionDto findById(int id) throws NotFoundException {
        Optional<QuestionOption> optionalQuestionOption = questionOptionRepository.findById(id);
        if (optionalQuestionOption.isPresent()) {
            QuestionOption questionOption = optionalQuestionOption.get();
            QuestionOptionDto questionOptionDto = QuestionOptionDto.builder()
                    .id(questionOption.getId())
                    .text(questionOption.getText())
                    .isCorrect(questionOption.isCorrect())
                    .questionId(questionOption.getQuestion().getId())
                    .build();
            return questionOptionDto;
        } else throw new NotFoundException("Ne naideno otec");
    }

    @Override
    public void update(QuestionOptionDto entity) {
        QuestionOption questionOption = QuestionOption.builder()
                .id(entity.getId())
                .text(entity.getText())
                .isCorrect(entity.isCorrect())
                .question(questionRepository.findById(entity.getQuestionId()).get())
                .build();
        questionOptionRepository.update(questionOption);
    }

    @Override
    public void deleteById(int id) {
        questionOptionRepository.deleteById(id);
    }

    @Override
    public List<QuestionOptionDto> findAll() throws NotFoundException {
        List<QuestionOption> questionOptions = questionOptionRepository.findAll();
        if (questionOptions.isEmpty()) {
            throw new NotFoundException("Ne naideno otec");
        } else {
            List<QuestionOptionDto> questionOptionDtos = new ArrayList<>();
            for (int i = 0; i < questionOptions.size(); i++) {
                questionOptionDtos.add(QuestionOptionDto.builder()
                        .id(questionOptions.get(i).getId())
                        .text(questionOptions.get(i).getText())
                        .isCorrect(questionOptions.get(i).isCorrect())
                        .questionId(questionOptions.get(i).getQuestion().getId())
                        .build());
            }
            return questionOptionDtos;
        }
    }
}
