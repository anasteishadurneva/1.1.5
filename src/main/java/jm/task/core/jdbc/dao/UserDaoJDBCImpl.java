package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    // Используем подключение к базе данных из утилитного класса
    private static final Connection conn = Util.getConnection();
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    // Используем константы для SQL-запросов
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age TINYINT)";
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";
    private static final String SAVE_USER = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private static final String REMOVE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String CLEAN_USERS_TABLE = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            // Выполняем SQL-запрос для создания таблицы пользователей
            statement.executeUpdate(CREATE_USERS_TABLE);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception while creating users table: ", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            // Выполняем SQL-запрос для удаления таблицы пользователей
            statement.executeUpdate(DROP_USERS_TABLE);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception while dropping users table: ", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstm = conn.prepareStatement(SAVE_USER)) {
            // Используем PreparedStatement для подготовки SQL-запроса с параметрами
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception while saving user: ", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement pstm = conn.prepareStatement(REMOVE_USER_BY_ID)) {
            // Используем PreparedStatement для подготовки SQL-запроса с параметрами
            pstm.setLong(1, id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Exception while removing user: ", e);
        }
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = pstmt.executeQuery()) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error during getAllUsers: " + e.getMessage());
        }
        return users;
    }

    // Очищаем таблицу от всех записей
    // Если возникает ошибка, выводим ее сообщение в консоль
    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(CLEAN_USERS_TABLE);
        } catch (SQLException e) {
            System.err.println("Error during cleanUsersTable: " + e.getMessage());
        }
    }
}
