package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.domain.Category;
import ru.vsu.museum.persistence.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCategoryRepository implements Repository<Category> {
    //Callback
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

    public DBCategoryRepository() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found. " + e.getMessage());
        }

        createTable();
    }

    private void createTable()
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE category (category_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, description VARCHAR(200) NOT NULL)");
            statement.execute("INSERT INTO category VALUES (1, 'Category 1', 'Description for Category 1')");
            statement.execute("INSERT INTO category VALUES (2, 'Category 2', 'Description for Category 2')");
            statement.execute("INSERT INTO category VALUES (3, 'Category 3', 'Description for Category 3')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Category> getAll() {
        Mapper mapper = new Mapper<Category>() {
            @Override
            public List<Category> foo(ResultSet resultSet) {
                try {
                    List<Category> categories = new ArrayList<Category>();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("category_id");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        categories.add(new Category(id, name, description));
                    }
                    return categories;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return executeSelectQuery("SELECT * FROM category", mapper);
    }

    @Override
    public Category getById(Long id) {
        Mapper mapper = new Mapper<Category>() {
            @Override
            public List<Category> foo(ResultSet resultSet) {
                try {
                    List<Category> categories = new ArrayList<Category>();
                    while (resultSet.next()) {
                        Long categoryId = resultSet.getLong("category_id");
                        if (!categoryId.equals(id)) {
                            continue;
                        }
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        categories.add(new Category(categoryId, name, description));
                        break;
                    }
                    return categories;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<Category> categories = executeSelectQuery(String.format("SELECT * FROM category WHERE category_id=%d", id), mapper);
        if (categories != null && !categories.isEmpty())
            return categories.get(0);
        return null;
    }

    @Override
    public Boolean create(Category item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "insert into category (category_id, name, description) values (null, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rows > 0)
            {
                return true;
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "delete from category where category_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rows > 0)
            {
                return true;
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean update(Category item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "update category set name = ?, description = ? where category_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setLong(3, item.getId());

            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (rows > 0)
            {
                return true;
            }

            return false;
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Long getCount()
    {
        Mapper mapper = new Mapper<Long>() {
            @Override
                public List<Long> foo(ResultSet resultSet) {
                System.out.println("Method getCount");
                try {
                    List<Long> counts = new ArrayList<Long>();
                    while (resultSet.next()) {
                        Long count = resultSet.getLong("cnt");
                        counts.add(count);
                    }
                    return counts;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM category", mapper);
        if (rs != null && !rs.isEmpty())
            return rs.get(0);

        return null;
    }

    private <R> List<R> executeSelectQuery(String query, Mapper<R> mapper)
    {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            return mapper.foo(resultSet);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
