package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBExponentRepository implements Repository<Exponent> {
    public DBExponentRepository()
    {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found. " + e.getMessage());
        }
        //createTable();
    }

    private void createTable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE exponent (" +
                    "exponentId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "categoryId BIGINT NOT NULL, " +
                    "locationId BIGINT NOT NULL)");

            statement.execute("INSERT INTO exponent VALUES (1L, 'Exponent 1', 1L, 1L)");
            statement.execute("INSERT INTO exponent VALUES (2L, 'Exponent 2', 2L, 2L)");
            statement.execute("INSERT INTO exponent VALUES (3L, 'Exponent 3', 3L, 1L)");

            statement.execute("INSERT INTO exponent VALUES (4L, 'Exponent 4', 1L, 2L)");
            statement.execute("INSERT INTO exponent VALUES (5L, 'Exponent 5', 2L, 1L)");
            statement.execute("INSERT INTO exponent VALUES (6L, 'Exponent 6', 3L, 2L)");

        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Exponent> getAll() {
        try (Connection connection = PoolManager.getInstance().getConnection())
        {
            return TableUtils.selectQuery(connection, Exponent.class, "exponent", null);
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Exponent getById(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection())
        {
            List<Exponent> exponents = TableUtils.selectQuery(connection, Exponent.class,
                    "exponent", "exponentId=" + id);
            if (!exponents.isEmpty())
            {
                return exponents.get(0);
            }
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Exponent item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createInsertPreparedStatement(connection, item,
                        "exponent");
        ) {
            int rows = statement.executeUpdate();
            if (rows > 0)
            {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection();) {
            return TableUtils.createDeletePreparedStatement(connection, Exhibition.class, "exponent",
                    "exponentId="+id);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Exponent item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createUpdatePreparedStatement(connection, item,
                        "exponent", "exponentId")
        ) {
            int rows = statement.executeUpdate();
            if (rows > 0)
            {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public Long getCount() {
        try (Connection connection = PoolManager.getInstance().getConnection()) {
            return TableUtils.countQuery(connection, Exhibition.class, "exponent");
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
