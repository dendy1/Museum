package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.domain.Exhibition;
import ru.vsu.museum.persistence.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBExhibitionRepository implements Repository<Exhibition> {
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

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
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE exhibition (exhibition_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, date DATE NOT NULL, name VARCHAR(100) NOT NULL)");
            statement.execute("INSERT INTO exhibition VALUES (1, '2019-12-01', 'Exhibition 1')");
            statement.execute("INSERT INTO exhibition VALUES (2, '2019-12-02', 'Exhibition 2')");
            statement.execute("INSERT INTO exhibition VALUES (3, '2019-12-03', 'Exhibition 3')");
            statement.execute("INSERT INTO exhibition VALUES (4, '2019-12-04', 'Exhibition 4')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Exhibition> getAll() {
        Mapper mapper = new Mapper<Exhibition>() {
            @Override
            public List<Exhibition> foo(ResultSet resultSet) {
                try {
                    List<Exhibition> exhibitions = new ArrayList<Exhibition>();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("exhibition_id");
                        Date date = resultSet.getDate("date");
                        String name = resultSet.getString("name");
                        exhibitions.add(new Exhibition(id, date, name   ));
                    }
                    return exhibitions;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return executeSelectQuery("SELECT * FROM exhibition", mapper);
    }

    @Override
    public Exhibition getById(Long id) {
        Mapper mapper = new Mapper<Exhibition>() {
            @Override
            public List<Exhibition> foo(ResultSet resultSet) {
                try {
                    List<Exhibition> exhibitions = new ArrayList<Exhibition>();
                    while (resultSet.next()) {
                        Long exhibitionId = resultSet.getLong("exhibition_id");
                        if (!exhibitionId.equals(id)) {
                            continue;
                        }
                        Date date = resultSet.getDate("date");
                        String name = resultSet.getString("name");
                        exhibitions.add(new Exhibition(exhibitionId, date, name));
                        break;
                    }
                    return exhibitions;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<Exhibition> exhibitions = executeSelectQuery(String.format("SELECT * FROM exhibition WHERE exhibition_id=%d", id), mapper);
        if (exhibitions != null && !exhibitions.isEmpty())
            return exhibitions.get(0);
        return null;
    }

    @Override
    public Boolean create(Exhibition item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "insert into exhibition (exhibition_id, date, name) values (null, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(item.getDate().getTime()));
            preparedStatement.setString(2, item.getName());

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
            String sql = "delete from exhibition where exhibition_id = ?";
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
    public Boolean update(Exhibition item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "update exhibition set date = ?, name = ? where exhibition_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(item.getDate().getTime()));
            preparedStatement.setString(2, item.getName());
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

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM exhibition", mapper);
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
