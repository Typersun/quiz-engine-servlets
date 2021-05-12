package repositories.jdbc_impl;

import models.Quiz;
import models.User;
import repositories.QuizRepository;
import utils.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuizRepositoryImpl implements QuizRepository {

    @Override
    public void save(Quiz entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
           PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO quiz(name, user_id) VALUES (?, ?);");
           preparedStatement.setString(1, entity.getName());
           User user = entity.getUser();
           preparedStatement.setInt(2, user.getId());
           preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Quiz> findById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quiz LEFT JOIN \"user\" u on quiz.user_id = u.id WHERE quiz.id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("u.id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                return Optional.of(Quiz.builder().id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .user(user)
                        .build());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(Quiz entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE quiz SET name = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM quiz WHERE id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Quiz> findAll() {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quiz LEFT JOIN \"user\" AS u on quiz.user_id = u.id;");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Quiz> quizzes = new ArrayList<>();
            while (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("user_id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                quizzes.add(Quiz.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .user(user)
                        .build());
            }
            return quizzes;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Quiz> findByName(String name) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM quiz LEFT JOIN \"user\" u on quiz.user_id = u.id WHERE quiz.name = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = User.builder()
                        .id(resultSet.getInt("user_id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build();
                return Optional.of(Quiz.builder().id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .user(user)
                        .build());

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
