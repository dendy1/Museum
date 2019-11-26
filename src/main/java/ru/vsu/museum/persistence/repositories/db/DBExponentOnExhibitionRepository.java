package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.*;
import java.util.List;

public class DBExponentOnExhibitionRepository implements Repository<ExponentOnExhibition> {
    public DBExponentOnExhibitionRepository() {
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
                Connection connection = PoolManager.getInstance().getConnection();
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

    @Override
    public List<ExponentOnExhibition> getAll() {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            return TableUtils.selectQuery(connection, ExponentOnExhibition.class,
                    "exponent_on_exhibition", null);
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public ExponentOnExhibition getById(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            List<ExponentOnExhibition> exponentOnExhibitions = TableUtils.selectQuery(connection,
                    ExponentOnExhibition.class, "exponent_on_exhibition", "id="+id);
            if (!exponentOnExhibitions.isEmpty())
            {
                return exponentOnExhibitions.get(0);
            }
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(ExponentOnExhibition item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createInsertPreparedStatement(connection, item,
                        "exponent_on_exhibition");
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
            return TableUtils.deleteQuery(connection, ExponentOnExhibition.class,
                    "exponent_on_exhibition", "id="+id);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(ExponentOnExhibition item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createUpdatePreparedStatement(connection, item,
                        "exponent_on_exhibition", "id")
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
            return TableUtils.countQuery(connection, ExponentOnExhibition.class,
                    "exponent_on_exhibition");
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
