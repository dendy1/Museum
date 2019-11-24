package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.domain.Category;
import ru.vsu.museum.persistence.CategoryStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCategoryStore implements CategoryStore {
    //Callback
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

    private static final String URL = "jdbc:h2:./museum";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public DBCategoryStore() {
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
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE categories (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, description VARCHAR(200) NOT NULL)");
            statement.execute("INSERT INTO categories VALUES ('Category 1', 'Description for Category 1')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public Category getCategoryById(Long id)
    {
        Mapper mapper = new Mapper<Category>() {
            @Override
            public List<Category> foo(ResultSet resultSet) {
                System.out.println("Method getCategoryById");
                try {
                    List<Category> categories = new ArrayList<Category>();
                    while (resultSet.next()) {
                        Long categoryId = resultSet.getLong("id");
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

        List<Category> categories = executeSelectQuery(String.format("SELECT * FROM categories WHERE id=%d", id), mapper);
        if (categories != null && !categories.isEmpty())
            return categories.get(0);
        return null;
    }

    @Override
    public List<Category> getCategories() //try with resources
    {
        Mapper mapper = new Mapper<Category>() {
            @Override
            public List<Category> foo(ResultSet resultSet) {
                System.out.println("Method getCategories");
                try {
                    List<Category> categories = new ArrayList<Category>();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
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

        return executeSelectQuery("SELECT * FROM categories", mapper);
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

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM categories", mapper);
        if (rs != null && !rs.isEmpty())
            return rs.get(0);

        return null;
    }

    private <R> List<R> executeSelectQuery(String query, Mapper<R> mapper)
    {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
