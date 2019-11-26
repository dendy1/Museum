package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.domain.ExponentOnExhibition;
import ru.vsu.museum.persistence.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBExponentOnExhibitionRepository implements Repository<ExponentOnExhibition> {
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

    public DBExponentOnExhibitionRepository() {
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
            statement.execute(
                    "CREATE TABLE exponent_on_exhibition (" +
                            "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                            "exhibition_id INT NOT NULL, " +
                            "exponent_id INT NOT NULL,  " +
                            "FOREIGN KEY (exhibition_id) REFERENCES exhibition (exhibition_id)," +
                            "FOREIGN KEY (exponent_id) REFERENCES exponent (exponent_id))");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (1, 1)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (1, 2)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (1, 3)");

            statement.execute("INSERT INTO exponent_on_exhibition VALUES (2, 4)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (2, 5)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (2, 6)");

            statement.execute("INSERT INTO exponent_on_exhibition VALUES (3, 1)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (3, 2)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (3, 3)");

            statement.execute("INSERT INTO exponent_on_exhibition VALUES (4, 4)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (4, 5)");
            statement.execute("INSERT INTO exponent_on_exhibition VALUES (4, 6)");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<ExponentOnExhibition> getAll() {
        Mapper mapper = new Mapper<ExponentOnExhibition>() {
            @Override
            public List<ExponentOnExhibition> foo(ResultSet resultSet) {
                try {
                    List<ExponentOnExhibition> exponentOnExhibitions = new ArrayList<ExponentOnExhibition>();
                    while (resultSet.next()) {
                        Long exhibitionId = resultSet.getLong("exhibition_id");
                        Long exponentId = resultSet.getLong("exponent_id");
                        exponentOnExhibitions.add(new ExponentOnExhibition(null, exhibitionId, exponentId));
                    }
                    return exponentOnExhibitions;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return executeSelectQuery("SELECT * FROM exponent_on_exhibition", mapper);
    }

    @Override
    public ExponentOnExhibition getById(Long id) {
        Mapper mapper = new Mapper<ExponentOnExhibition>() {
            @Override
            public List<ExponentOnExhibition> foo(ResultSet resultSet) {
                try {
                    List<ExponentOnExhibition> exponentOnExhibitions = new ArrayList<ExponentOnExhibition>();
                    while (resultSet.next()) {
                        Long expId = resultSet.getLong("id");
                        if (!expId.equals(id)) {
                            continue;
                        }
                        Long exhibitionId = resultSet.getLong("exhibition_id");
                        Long exponentId = resultSet.getLong("exponent_id");
                        exponentOnExhibitions.add(new ExponentOnExhibition(null, exhibitionId, exponentId));
                        break;
                    }
                    return exponentOnExhibitions;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<ExponentOnExhibition> exponentOnExhibitions = executeSelectQuery(String.format("SELECT * FROM exponent_on_exhibition WHERE id=%d", id), mapper);
        if (exponentOnExhibitions != null && !exponentOnExhibitions.isEmpty())
            return exponentOnExhibitions.get(0);
        return null;
    }

    @Override
    public Boolean create(ExponentOnExhibition item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "insert into expo (id, exhibition_id, exponent_id) values (null, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, item.getExhibitionId());
            preparedStatement.setLong(2, item.getExponentId());

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
            String sql = "delete from exponent_on_exhibition where id = ?";
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
    public Boolean update(ExponentOnExhibition item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "update exponent_on_exhibition set exhibition_id = ?, exponent_id = ? where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, item.getExhibitionId());
            preparedStatement.setLong(2, item.getExponentId());
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

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM exponent_on_exhibition", mapper);
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
