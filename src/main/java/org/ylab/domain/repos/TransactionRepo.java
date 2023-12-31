package org.ylab.domain.repos;

import org.ylab.domain.models.Player;
import org.ylab.domain.models.Transaction;
import org.ylab.domain.models.TransactionType;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class TransactionRepo {

    Properties properties;
    private final String URL;
    private final String USER_NAME;
    private final String PASSWORD;

    public TransactionRepo() {
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

    public TransactionRepo(String URL, String USER_NAME, String PASSWORD) {
        this.URL = URL;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

    public void save(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "INSERT INTO entities.transaction (player_id, transaction_type, " +
                    "amount, unique_id) VALUES (?, ?, ?, ?)";
            PreparedStatement insertDataStatement = connection.prepareStatement(insertDataSQL);
            insertDataStatement.setLong(1, transaction.getPlayerId());
            insertDataStatement.setString(2, transaction.getTransactionType().name());
            insertDataStatement.setLong(3, transaction.getAmount());
            insertDataStatement.setString(4, transaction.getUniqueId());
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
                                resultSet.getString("unique_id"),
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
    public Optional<Transaction> findByUniqueId(String uniqueId) {

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            String insertDataSQL = "SELECT * FROM entities.transaction WHERE unique_id = ? ";
            PreparedStatement statement = connection.prepareStatement(insertDataSQL);
            statement.setString(1, uniqueId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Transaction(
                        resultSet.getLong("id"),
                        resultSet.getString("unique_id"),
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
                                resultSet.getString("unique_id"),
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

    public List<Transaction> findByIds(List<Long> idList){
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            // Подготовка SQL-запроса с использованием параметра IN
            String sql = "SELECT * FROM entities.transaction WHERE id IN (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Преобразование списка в массив
                Array array = connection.createArrayOf("LONG", idList.toArray());
                preparedStatement.setArray(1, array);

                // Выполнение запроса
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Обработка результатов запроса
                    while (resultSet.next()) {
                        transactions.add(
                                new Transaction(
                                        resultSet.getLong("id"),
                                        resultSet.getString("unique_id"),
                                        resultSet.getLong("player_id"),
                                        TransactionType.valueOf(
                                                resultSet.getString("transaction_type")),
                                        resultSet
                                                .getLong("amount")
                                )
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
