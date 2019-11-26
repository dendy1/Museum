package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.domain.Exponent;
import ru.vsu.museum.persistence.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBExponentRepository implements Repository<Exponent> {
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

    public DBExponentRepository()
    {
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
            statement.execute("CREATE TABLE exponent (exponent_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL, category_id INT NOT NULL, location_id INT NOT NULL)");
            statement.execute("INSERT INTO exponent VALUES (1, 'Exponent 1', 1, 1)");
            statement.execute("INSERT INTO exponent VALUES (2, 'Exponent 2', 1, 2)");
            statement.execute("INSERT INTO exponent VALUES (3, 'Exponent 3', 1, 3)");

            statement.execute("INSERT INTO exponent VALUES (4, 'Exponent 4', 2, 1)");
            statement.execute("INSERT INTO exponent VALUES (5, 'Exponent 5', 2, 2)");
            statement.execute("INSERT INTO exponent VALUES (6, 'Exponent 6', 2, 3)");

            statement.execute("INSERT INTO exponent VALUES (7, 'Exponent 7', 3, 1)");
            statement.execute("INSERT INTO exponent VALUES (8, 'Exponent 8', 3, 2)");
            statement.execute("INSERT INTO exponent VALUES (9, 'Exponent 9', 3, 3)");

        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Exponent> getAll() {
        Mapper mapper = new Mapper<Exponent>() {
            @Override
            public List<Exponent> foo(ResultSet resultSet) {
                try {
                    List<Exponent> exponents = new ArrayList<Exponent>();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("exponent_id");
                        String name = resultSet.getString("name");
                        Long categoryId = resultSet.getLong("category_id");
                        Long locationId = resultSet.getLong("location_id");
                        exponents.add(new Exponent(id, name, categoryId, locationId));
                    }
                    return exponents;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return executeSelectQuery("SELECT * FROM exponent", mapper);
    }

    @Override
    public Exponent getById(Long id) {
        Mapper mapper = new Mapper<Exponent>() {
            @Override
            public List<Exponent> foo(ResultSet resultSet) {
                try {
                    List<Exponent> exponents = new ArrayList<Exponent>();
                    while (resultSet.next()) {
                        Long exponentId = resultSet.getLong("exponent_id");
                        if (!exponentId.equals(id)) {
                            continue;
                        }
                        String name = resultSet.getString("name");
                        Long categoryId = resultSet.getLong("category_id");
                        Long locationId = resultSet.getLong("location_id");
                        exponents.add(new Exponent(exponentId, name, categoryId, locationId));
                        break;
                    }
                    return exponents;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<Exponent> exponents = executeSelectQuery(String.format("SELECT * FROM exponent WHERE exponent_id=%d", id), mapper);
        if (exponents != null && !exponents.isEmpty())
            return exponents.get(0);
        return null;
    }

    @Override
    public Boolean create(Exponent item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "insert into exponent (exponent_id, name, category_id, location_id) values (null, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setLong(2, item.getCategoryId());
            preparedStatement.setLong(3, item.getLocationId());

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
            String sql = "delete from exponent where exponent_id = ?";
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
    public Boolean update(Exponent item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "update exponent set name = ?, category_id = ?, location_id = ? where exponent_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setLong(2, item.getCategoryId());
            preparedStatement.setLong(3, item.getLocationId());
            preparedStatement.setLong(4, item.getId());

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
    public Long getCount() {
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

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM exponent", mapper);
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
