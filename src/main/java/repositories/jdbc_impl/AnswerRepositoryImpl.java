package repositories.jdbc_impl;

import models.Answer;
import models.Question;
import models.QuestionOption;
import models.User;
import repositories.AnswerRepository;
import utils.DbConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnswerRepositoryImpl implements AnswerRepository {

    @Override
    public void save(Answer entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO answer(question_id, question_option_id, user_id, time) VALUES (?, ?, ?, ?);");
            Question question = entity.getQuestion();
            QuestionOption questionOption = entity.getQuestionOption();
            User user = entity.getAuthor();
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setInt(2, questionOption.getId());
            preparedStatement.setInt(3, user.getId());
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            preparedStatement.setTimestamp(4, timestamp);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Answer> findById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT question.id as \"q.id\",\n" +
                    "       question.text as \"q.text\",\n" +
                    "       qo.id as \"qo.id\",\n" +
                    "       qo.text as \"qo.text\",\n" +
                    "       qo.is_correct as \"is_correct\",\n" +
                    "       u.id as \"u.id\",\n" +
                    "       u.username as \"username\",\n" +
                    "       u.password as \"password\",\n" +
                    "       answer.id as \"answer.id\"\n" +
                    "       FROM answer\n" +
                    "LEFT JOIN question\n" +
                    "on answer.question_id = question.id\n" +
                    "LEFT JOIN question_option qo\n" +
                    "on answer.question_id = qo.id\n" +
                    "LEFT JOIN \"user\" u on answer.user_id = u.id WHERE answer.id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                QuestionOption questionOption = QuestionOption.builder()
                        .id(resultSet.getInt("qo.id"))
                        .text(resultSet.getString("qo.text"))
                        .isCorrect(resultSet.getBoolean("is_correct"))
                        .build();
                User user = User.builder()
                        .id(resultSet.getInt("u.id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                return Optional.of(Answer.builder()
                        .id(resultSet.getInt("answer.id"))
                        .question(question)
                        .questionOption(questionOption)
                        .author(user)
                        .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return Optional.empty();
    }

    @Override
    public void update(Answer entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE answer SET question_id = ?, question_option_id = ?, user_id = ? WHERE id = ?;");
            Question question = entity.getQuestion();
            QuestionOption questionOption = entity.getQuestionOption();
            User user = entity.getAuthor();
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setInt(2, questionOption.getId());
            preparedStatement.setInt(3, user.getId());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM answer WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Answer> findAll() {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT question.id as \"q.id\",\n" +
                    "       question.text as \"q.text\",\n" +
                    "       qo.id as \"qo.id\",\n" +
                    "       qo.text as \"qo.text\",\n" +
                    "       qo.is_correct as \"is_correct\",\n" +
                    "       u.id as \"u.id\",\n" +
                    "       u.username as \"username\",\n" +
                    "       u.password as \"password\",\n" +
                    "       answer.id as \"answer.id\"\n" +
                    "       FROM answer\n" +
                    "LEFT JOIN question\n" +
                    "on answer.question_id = question.id\n" +
                    "LEFT JOIN question_option qo\n" +
                    "on answer.question_id = qo.id\n" +
                    "LEFT JOIN \"user\" u on answer.user_id = u.id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Answer> answers = new ArrayList<>();
            while (resultSet.next()) {
                Question question = Question.builder()
                        .id(resultSet.getInt("q.id"))
                        .text(resultSet.getString("q.text"))
                        .build();
                QuestionOption questionOption = QuestionOption.builder()
                        .id(resultSet.getInt("qo.id"))
                        .text(resultSet.getString("qo.text"))
                        .isCorrect(resultSet.getBoolean("is_correct"))
                        .build();
                User user = User.builder()
                        .id(resultSet.getInt("u.id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                answers.add(Answer.builder()
                        .id(resultSet.getInt("answer.id"))
                        .question(question)
                        .questionOption(questionOption)
                        .author(user)
                        .build());
            }
            return answers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}
