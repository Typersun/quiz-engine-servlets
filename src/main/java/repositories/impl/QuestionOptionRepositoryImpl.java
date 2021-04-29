package repositories.impl;

import models.Question;
import models.QuestionOption;
import repositories.QuestionOptionRepository;
import utils.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionOptionRepositoryImpl implements QuestionOptionRepository {

    @Override
    public void save(QuestionOption entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO question_option(text, is_correct, question_id) VALUES (?, ?, ?);");
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setBoolean(2, entity.isCorrect());
            Question question = entity.getQuestion();
            preparedStatement.setInt(3, question.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<QuestionOption> findById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question_option LEFT JOIN question q on question_option.question_id = q.id WHERE question_option.id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                return Optional.of(QuestionOption.builder()
                        .text(resultSet.getString("text"))
                        .isCorrect(resultSet.getBoolean("is_correct"))
                        .question(question)
                        .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.empty();
    }
    // TODO: maybe we should add other "find" methods like "findbyquestionID" etc

    @Override
    public void update(QuestionOption entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE question_option SET text = ?, is_correct = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getText());
            Question question = entity.getQuestion();
            preparedStatement.setInt(2, question.getId());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void deleteById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM question_option WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<QuestionOption> findAll() {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM question_option LEFT JOIN question q on question_option.question_id = q.id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<QuestionOption> questionOptions = new ArrayList<>();
            while (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                questionOptions.add(QuestionOption.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .isCorrect(resultSet.getBoolean("is_correct"))
                        .question(question)
                        .build());
            }
            return questionOptions;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new ArrayList<>();
    }
}
