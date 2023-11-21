package org.ylab.domain.repos;

import org.ylab.domain.models.Action;
import org.ylab.domain.models.Operation;
import org.ylab.domain.models.TransactionType;
import org.ylab.domain.models.dto.OperationDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class OperationRepo {
    Properties properties;
    private final String URL;
    private final String USER_NAME;
    private final String PASSWORD;

    public OperationRepo() {
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

    public OperationRepo(String URL, String USER_NAME, String PASSWORD) {
        this.URL = URL;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

    public void save(Operation operation) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "INSERT INTO entities.operation (player_id, transaction_id, " +
                    "action, date) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setLong(1, operation.getPlayerId());
            insertDataStatement.setString(2, operation.getTransUID());
            insertDataStatement.setString(3, operation.getAction().name());
            insertDataStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            insertDataStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Operation> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.operation";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            ResultSet resultSet = statement.executeQuery();
            List<Operation> operations = new ArrayList<>();
            while (resultSet.next()) {
                operations.add(
                        new Operation(
                                resultSet.getLong("id"),
                                resultSet.getLong("player_id"),
                                Action.valueOf(resultSet.getString("action")),
                                resultSet.getString("transaction_id"),
                                resultSet.getTimestamp("date").toLocalDateTime()));

            }
            return operations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<OperationDTO> findAllOperations() {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT operation.action, operation.date, " +
                    "player.username, transaction.transaction_type " +
                    "FROM entities.operation " +
                    "INNER JOIN entities.player ON operation.player_id = player.id " +
                    "LEFT JOIN entities.transaction ON operation.transaction_id = transaction.unique_id";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            ResultSet resultSet = statement.executeQuery();
            List<OperationDTO> operations = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                Action action = Action.valueOf(resultSet.getString("action"));
                String transTypeStr = resultSet.getString("transaction_type");
                LocalDateTime date = resultSet.getTimestamp("date").toLocalDateTime();
                TransactionType transType = null;
                if (transTypeStr != null) {
                    transType = TransactionType.valueOf(transTypeStr);
                }
                operations.add(
                        new OperationDTO(username,
                                action, Optional.ofNullable(transType), date));

            }
            return operations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
