package repositories.impl;

import models.Question;
import models.Quiz;
import repositories.QuestionRepository;
import repositories.QuizRepository;
import utils.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionRepositoryImpl implements QuestionRepository {

    @Override
    public void save(Question entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO question(text, quiz_id) VALUES (?, ?);");
            preparedStatement.setString(1, entity.getText());
            Quiz quiz = entity.getQuiz();
            preparedStatement.setInt(2, quiz.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Question> findById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question LEFT JOIN quiz qz on question.quiz_id = qz.id WHERE question.id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Quiz quiz = Quiz.builder()
                        .id(resultSet.getInt("qz.id"))
                        .name(resultSet.getString("name"))
                        .build();
                return Optional.of(Question.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .quiz(quiz)
                        .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Question entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE question SET text = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setInt(2, entity.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM question WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Question> findAll() {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question LEFT JOIN quiz qz on question.quiz_id = qz.id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Question> questions = new ArrayList<>();
            while (resultSet.next()) {
                Quiz quiz = Quiz.builder()
                        .id(resultSet.getInt("qz.id"))
                        .name(resultSet.getString("name"))
                        .build();
                questions.add(Question.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .quiz(quiz)
                        .build());
            }
            return questions;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}
