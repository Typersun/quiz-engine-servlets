package services.impl;

import dto.QuizDto;
import exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import models.Quiz;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import repositories.UserRepository;
import services.QuizService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Override
    public void save(QuizDto entity) {
        Quiz quiz = Quiz.builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(userRepository.findOneByUsername(entity.getUsername()).get())
                .build();
        quizRepository.save(quiz);
    }

    @Override
    public QuizDto findById(int id) throws NotFoundException {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quizModel = optionalQuiz.get();
            return QuizDto.builder()
                    .id(quizModel.getId())
                    .name(quizModel.getName())
                    .username(quizModel.getUser().getUsername())
                    .build();
        } else throw new NotFoundException();
    }

    @Override
    public void update(QuizDto entity) {
        Quiz quiz = Quiz.builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(userRepository.findOneByUsername(entity.getUsername()).get())
                .build();
        quizRepository.update(quiz);
    }

    @Override
    public void deleteById(int id) throws NotFoundException {
        quizRepository.deleteById(id);
    }

    @Override
    public List<QuizDto> findAll() throws NotFoundException {
        List<Quiz> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) {
            throw new NotFoundException("Ne naideno, otec");
        } else {
            List<QuizDto> quizDtos = new ArrayList<>();
            for (int i = 0; i < quizzes.size(); i++) {
                quizDtos.add(QuizDto.builder()
                        .id(quizzes.get(i).getId())
                        .name(quizzes.get(i).getName())
                        .username(quizzes.get(i).getUser().getUsername())
                        .build());
            }
            return quizDtos;
        }

    }
}
