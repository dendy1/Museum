package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBLocationRepository implements Repository<Location> {
    interface Mapper<T> {
        List<T> foo(ResultSet resultSet);
    }

    public DBLocationRepository()
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
            statement.execute("CREATE TABLE location (location_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100) NOT NULL)");
            statement.execute("INSERT INTO location VALUES (1, 'Location 1')");
            statement.execute("INSERT INTO location VALUES (2, 'Location 2')");
            statement.execute("INSERT INTO location VALUES (3, 'Location 3')");

        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Location> getAll() {
        Mapper mapper = new Mapper<Location>() {
            @Override
            public List<Location> foo(ResultSet resultSet) {
                try {
                    List<Location> exponents = new ArrayList<Location>();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("location_id");
                        String name = resultSet.getString("name");
                        exponents.add(new Location(id, name));
                    }
                    return exponents;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return executeSelectQuery("SELECT * FROM location", mapper);
    }

    @Override
    public Location getById(Long id) {
        Mapper mapper = new Mapper<Location>() {
            @Override
            public List<Location> foo(ResultSet resultSet) {
                try {
                    List<Location> locations = new ArrayList<Location>();
                    while (resultSet.next()) {
                        Long locationId = resultSet.getLong("location_id");
                        if (!locationId.equals(id)) {
                            continue;
                        }
                        String name = resultSet.getString("name");
                        locations.add(new Location(locationId, name));
                        break;
                    }
                    return locations;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        List<Location> locations = executeSelectQuery(String.format("SELECT * FROM location WHERE location_id=%d", id), mapper);
        if (locations != null && !locations.isEmpty())
            return locations.get(0);
        return null;
    }

    @Override
    public Boolean create(Location item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "insert into location (location_id, name) values (null, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());

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
            String sql = "delete from location where location_id = ?";
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
    public Boolean update(Location item) {
        try (
                Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD);
        ) {
            String sql = "update location set name = ? where location_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setLong(2, item.getId());

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

        List<Long> rs = executeSelectQuery("SELECT count(1) AS cnt FROM location", mapper);
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
