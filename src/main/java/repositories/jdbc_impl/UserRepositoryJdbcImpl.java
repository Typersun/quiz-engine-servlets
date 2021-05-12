package repositories.jdbc_impl;

import models.User;
import repositories.UserRepository;
import utils.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryJdbcImpl implements UserRepository {
    @Override
    public void save(User entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO \"user\"(username, password) VALUES (?,?);");
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(int id) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.builder()
                .id(resultSet.getInt("id"))
                .username(resultSet.getString("username"))
                .password(resultSet.getString("password"))
                .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void update(User entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"user\" SET username = ?, password = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"user\" WHERE username = ?;");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void updateWithoutPassword(User entity) {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"user\" SET username = ? WHERE id = ?;");
            preparedStatement.setString(1, entity.getUsername());
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"user\" WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public List<User> findAll() {
        Connection connection = DbConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"user\";");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getInt("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .build());

            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new ArrayList<>();
    }
}
