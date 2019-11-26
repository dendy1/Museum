package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.Category;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.*;
import java.util.List;

public class DBCategoryRepository implements Repository<Category> {
    public DBCategoryRepository() {
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

    @Override
    public List<Category> getAll() {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
           return TableUtils.selectQuery(connection, Category.class, "category", null);
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Category getById(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            List<Category> categories = TableUtils.selectQuery(connection, Category.class,
                    "category", "categoryId=" + id);
            if (!categories.isEmpty())
            {
                return categories.get(0);
            }
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Category item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createInsertPreparedStatement(connection, item, "category");
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
            return TableUtils.deleteQuery(connection, Category.class, "category", "categoryId="+id);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Category item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createUpdatePreparedStatement(connection, item, "category", "categoryId")
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
    public Long getCount()
    {
        try (Connection connection = PoolManager.getInstance().getConnection()) {
            return TableUtils.countQuery(connection, Category.class, "category");
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
