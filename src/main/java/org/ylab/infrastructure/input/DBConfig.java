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

public class DBConfig {

        private Properties properties;

        public DBConfig() {
            properties = new Properties();
            try {
                FileInputStream fileInputStream = new FileInputStream("C:\\Users\\danil\\Desktop\\тз\\wallet-service\\src\\main\\resources\\application.properties");
                properties.load(fileInputStream);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getProperty(String propertyName) {
            return properties.getProperty(propertyName);
        }

        public void connect(){
            String URL = getProperty("url");
            System.out.println(URL);
            String USER_NAME = getProperty("db-username");
            System.out.println(USER_NAME);
            String PASSWORD = getProperty("db-password");
            System.out.println(PASSWORD);
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
