package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.*;
import java.util.List;

public class DBExhibitionRepository implements Repository<Exhibition> {
    public DBExhibitionRepository()
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
                Connection connection = PoolManager.getInstance().getConnection();
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

    @Override
    public List<Exhibition> getAll() {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            return TableUtils.selectQuery(connection, Exhibition.class, "exhibition", null);
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Exhibition getById(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            List<Exhibition> exhibitions = TableUtils.selectQuery(connection, Exhibition.class,
                    "exhibition", "exhibitionId=" + id);
            if (!exhibitions.isEmpty())
            {
                return exhibitions.get(0);
            }
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Exhibition item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createInsertPreparedStatement(connection, item,
                        "exhibition");
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
            return TableUtils.deleteQuery(connection, Exhibition.class, "exhibition",
                    "exhibitionId="+id);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Exhibition item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createUpdatePreparedStatement(connection, item,
                        "exhibition", "exhibitionId")
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
            return TableUtils.countQuery(connection, Exhibition.class, "exhibition");
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
