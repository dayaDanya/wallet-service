package org.ylab.domain.repos;

import org.ylab.domain.models.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Properties;

/**
 * @author dayaDanya
 * Класс отвечающий за регистрацию игрока
 */
public class PlayerRepo {

    Properties properties;
    private String URL;
    private String USER_NAME;
    private String PASSWORD;

    public PlayerRepo() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\danil\\Desktop\\тз\\wallet-service\\src\\main\\resources\\application.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = properties.getProperty("url");
        USER_NAME = properties.getProperty("db-username");
        PASSWORD = properties.getProperty("db-password");
    }

    public PlayerRepo(String URL, String USER_NAME, String PASSWORD) {
        this.URL = URL;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

    /**
     * @param username пользовательское имя
     * @param password пароль
     * @return новый объект Player с заданными параметрами
     */

    public void save(String username, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "INSERT INTO entities.player (username, password, " +
                    "date_of_registration) VALUES (?, ?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setString(1, username);
            insertDataStatement.setString(2, password);
            insertDataStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            insertDataStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Player> findByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.player WHERE username = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Player(resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getLong("balance"),
                        resultSet.getTimestamp("date_of_registration").toLocalDateTime()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public Optional<Player> findById(long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.player WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Player(resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getLong("balance"),
                        resultSet.getTimestamp("date_of_registration").toLocalDateTime()));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public long findBalanceById(long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT balance FROM entities.player WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("balance");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updatePlayerBalance(long playerId, long balance){
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "UPDATE entities.player SET balance= ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);

            statement.setLong(1, balance);
            statement.setLong(2, playerId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
