package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.Config;
import ru.vsu.museum.connectionPool.PoolManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCreator {

    public void CreateDatabases()
    {
        createCategoryTable();
        createEETable();
        createExhibitionTable();
        createExponentTable();
    }

    private void createCategoryTable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE category (" +
                    "categoryId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "description VARCHAR(200) NOT NULL)");

            statement.execute("INSERT INTO category VALUES (1L, 'Category 1', 'Description for Category 1')");
            statement.execute("INSERT INTO category VALUES (2L, 'Category 2', 'Description for Category 2')");
            statement.execute("INSERT INTO category VALUES (3L, 'Category 3', 'Description for Category 3')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    private void createExhibitionTable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE exhibition (" +
                    "exhibitionId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "date DATE NOT NULL, " +
                    "name VARCHAR(100) NOT NULL)");

            statement.execute("INSERT INTO exhibition VALUES (1L, '2019-12-01', 'Exhibition 1')");
            statement.execute("INSERT INTO exhibition VALUES (2L, '2019-12-04', 'Exhibition 2')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    private void createEETable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute(
                    "CREATE TABLE exponent_on_exhibition (" +
                            "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                            "exhibitionId BIGINT NOT NULL, " +
                            "exponentId BIGINT NOT NULL)");

            statement.execute("INSERT INTO exponent_on_exhibition VALUES (1L, 1L, 1L)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (2L, 1L, 2L)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (3L, 1L, 3L)");

            statement.execute("INSERT INTO exponent_on_exhibition VALUES (4L, 2L, 4L)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (5L, 2L, 5L)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (6L, 2L, 6L)");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    private void createExponentTable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE exponent (" +
                    "exponentId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "categoryId BIGINT NOT NULL)");

            statement.execute("INSERT INTO exponent VALUES (1L, 'Exponent 1', 1L)");
            statement.execute("INSERT INTO exponent VALUES (2L, 'Exponent 2', 2L)");
            statement.execute("INSERT INTO exponent VALUES (3L, 'Exponent 3', 3L)");

            statement.execute("INSERT INTO exponent VALUES (4L, 'Exponent 4', 1L)");
            statement.execute("INSERT INTO exponent VALUES (5L, 'Exponent 5', 2L)");
            statement.execute("INSERT INTO exponent VALUES (6L, 'Exponent 6', 3L)");

        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }
}
