package org.ylab.infrastructure.input;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class MigrationConfig {

        private Properties properties;

        private String URL;

        private String USER_NAME;

        private String PASSWORD;


        public MigrationConfig() {
            properties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream("/home/danya/IdeaProjects/wallet-service/src/main/resources/application.properties");
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            URL = properties.getProperty("url");
            USER_NAME = properties.getProperty("db-username");
            PASSWORD = properties.getProperty("db-password");

        }

    public MigrationConfig(String URL, String USER_NAME, String PASSWORD) {
        this.URL = URL;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
    }

        public void migrate() throws ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            try {
                Connection connection = DriverManager.getConnection(
                        URL,
                        USER_NAME,
                        PASSWORD
                );
                Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
                liquibase.update();
                System.out.println("migration completed!");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM information_schema.tables;")) {
                while (resultSet.next()) {
                    int tablesCount = resultSet.getInt("count");
                    System.out.println("There is " + tablesCount + " tables in database");
                }
            } catch (SQLException exception) {
                System.out.println("Got SQL Exception " + exception.getMessage());
            }
        }
}
