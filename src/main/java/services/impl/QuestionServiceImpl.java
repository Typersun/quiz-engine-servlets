package services.impl;

import dto.QuestionDto;
import exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import models.Question;
import models.Quiz;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import services.QuestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {    // ༼ つ ◕_◕ ༽つ QUESTION SERVICE ☜(ﾟヮﾟ☜)
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    @Override
    public void save(QuestionDto entity) {
        Question question = Question.builder()
                .id(entity.getId())
                .text(entity.getText())
                .quiz(quizRepository.findByName(entity.getQuizName()).get())
                .build();
        questionRepository.save(question);
    }

    @Override
    public QuestionDto findById(int id) throws NotFoundException {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            QuestionDto questionDto = QuestionDto.builder()
                    .id(question.getId())
                    .text(question.getText())
                    .quizName(question.getQuiz().getName())
                    .build();
            return questionDto;
        } else throw new NotFoundException("Ne naideno otec");
    }

    @Override
    public void update(QuestionDto entity) {
        Question question = Question.builder()
                .id(entity.getId())
                .text(entity.getText())
                .quiz(quizRepository.findByName(entity.getQuizName()).get())
                .build();
        questionRepository.update(question);
    }

    @Override
    public void deleteById(int id) {
        questionRepository.deleteById(id);
    }

    @Override
    public List<QuestionDto> findAll() throws NotFoundException {
        List<Question> questions = questionRepository.findAll();
        if (questions.isEmpty()) {
            throw new NotFoundException("Ne naideno otec");
        } else {
            List<QuestionDto> questionDtos = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++) {
                questionDtos.add(QuestionDto.builder()
                        .id(questions.get(i).getId())
                        .text(questions.get(i).getText())
                        .quizName(questions.get(i).getQuiz().getName())
                        .build());
            }
            return questionDtos;
        }
    }
}
