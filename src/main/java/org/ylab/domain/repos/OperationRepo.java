package org.ylab.domain.repos;

import org.ylab.domain.models.Action;
import org.ylab.domain.models.Operation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationRepo {
    private static final String URL = "jdbc:postgresql://localhost:32768/wallet-service";
    private static final String USER_NAME = "daya_danya";
    private static final String PASSWORD = "daya_danya";

    public void save(Operation operation){
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "INSERT INTO entities.operation (player_id, transaction_id, " +
                    "action, date) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setLong(1, operation.getPlayerId());
            insertDataStatement.setLong(2, operation.getTransaction());
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
                                resultSet.getLong("transaction_id"),
                                resultSet.getTimestamp("date").toLocalDateTime()));




            }
            return operations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
