package org.ylab.domain.repos;

import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TransactionRepo {

    private static final String URL = "jdbc:postgresql://localhost:32768/wallet-service";
    private static final String USER_NAME = "daya_danya";
    private static final String PASSWORD = "daya_danya";

    public void save(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "INSERT INTO entities.transaction (player_id, transaction_type, " +
                    "amount, unique_id) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setLong(1, transaction.getPlayerId());
            insertDataStatement.setString(2, transaction.getTransactionType().name());
            insertDataStatement.setLong(3, transaction.getAmount());
            insertDataStatement.setLong(4, transaction.getUniqueId());
            insertDataStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Transaction> findById(long id) {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.transaction WHERE id = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Transaction(
                                resultSet.getLong("id"),
                                resultSet.getLong("unique_id"),
                                resultSet.getLong("player_id"),
                                TransactionType.valueOf(
                                        resultSet.getString("transaction_type")),
                                resultSet
                                        .getLong("amount")
                        ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Transaction> findByPlayerId(long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.transaction WHERE player_id = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Transaction> transactions = new ArrayList<>();
            while (resultSet.next()) {
                transactions.add(
                        new Transaction(
                                resultSet.getLong("id"),
                                resultSet.getLong("unique_id"),
                                resultSet.getLong("player_id"),
                                TransactionType.valueOf(
                                        resultSet.getString("transaction_type")),
                                resultSet
                                        .getLong("amount")
                        )
                );

            }
            return transactions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
