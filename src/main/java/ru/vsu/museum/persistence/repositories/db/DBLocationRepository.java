package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.Config;
import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.domain.Location;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBLocationRepository implements Repository<Location> {
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
            Connection connection = DriverManager.getConnection(Config.URL);
            Statement statement = connection.createStatement();
        ) {
            statement.execute("CREATE TABLE location (" +
                    "locationId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(100) NOT NULL)");

            statement.execute("INSERT INTO location VALUES (1L, 'Location 1')");
            statement.execute("INSERT INTO location VALUES (2L, 'Location 2')");
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
    }

    @Override
    public List<Location> getAll() {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            return TableUtils.selectQuery(connection, Location.class, "location", null);
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public Location getById(Long id) {
        try (Connection connection = PoolManager.getInstance().getConnection();)
        {
            List<Location> locations = TableUtils.selectQuery(connection, Location.class,
                    "location", "locationId=" + id);
            if (!locations.isEmpty())
            {
                return locations.get(0);
            }
        } catch (SQLException e) {
            System.out.println("DDL or DML statement error. " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(Location item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createInsertPreparedStatement(connection, item,
                        "location");
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
            return TableUtils.createDeletePreparedStatement(connection, Location.class, "location",
                    "locationId="+id);
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Location item) {
        try (
                Connection connection = PoolManager.getInstance().getConnection();
                PreparedStatement statement = TableUtils.createUpdatePreparedStatement(connection, item,
                        "location", "locationId")
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
            return TableUtils.countQuery(connection, Location.class, "location");
        } catch (SQLException e) {
            System.out.println("Connection error. " + e.getMessage());
        }
        return null;
    }
}
