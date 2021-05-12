package repositories.jdbc_impl;

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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.id as \"q.id\",\n" +
                    "       q.text as \"q.text\",\n" +
                    "       qo.text as \"text\", qo.id as \"qo.id\",\n" +
                    "       qo.is_correct as \"is_correct\"\n" +
                    "FROM question_option qo LEFT JOIN question q on qo.question_id = q.id WHERE qo.id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                return Optional.of(QuestionOption.builder()
                        .id(resultSet.getInt("qo.id"))
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
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE question_option SET text = ?, is_correct = ?, question_id = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setBoolean(2, entity.isCorrect());
            preparedStatement.setInt(3, entity.getQuestion().getId());
            preparedStatement.setInt(4, entity.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT qo.id as \"qo.id\",\n" +
                    "       qo.text as \"qo.text\",\n" +
                    "       qo.is_correct as \"is_correct\",\n" +
                    "       q.id as \"q.id\",\n" +
                    "       q.text as \"q.text\",\n" +
                    "       q.quiz_id as \"quiz_id\" \n" +
                    "       FROM question_option qo\n" +
                    "    LEFT JOIN question q\n" +
                    "        on qo.question_id = q.id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<QuestionOption> questionOptions = new ArrayList<>();
            while (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                questionOptions.add(QuestionOption.builder()
                        .id(resultSet.getInt("qo.id"))
                        .text(resultSet.getString("qo.text"))
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

    @Override
    public Optional<QuestionOption> findByText(String text) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.id as \"q.id\",\n" +
                    "       q.text as \"q.text\",\n" +
                    "       qo.text as \"text\", qo.id as \"qo.id\",\n" +
                    "       qo.is_correct as \"is_correct\"\n" +
                    "FROM question_option qo LEFT JOIN question q on qo.question_id = q.id WHERE qo.text = ?;");
            preparedStatement.setString(1, text);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                return Optional.of(QuestionOption.builder()
                        .id(resultSet.getInt("qo.id"))
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
}
