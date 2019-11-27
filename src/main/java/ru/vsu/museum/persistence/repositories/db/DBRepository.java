package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DBRepository<T> implements Repository<T> {
    private final Class<T> type;
    private final String tableName;
    private final String primaryKey;

    public DBRepository(Class<T> type, String tableName, String primaryKey) {
        this.type = type;
        this.tableName = tableName;
        this.primaryKey = primaryKey;

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found. (" + e.getMessage() + ")");
        }
    }

    @Override
    public List<T> getAll(String where) {
        try {
            Connection connection = PoolManager.getConnection();
            var result = TableUtils.selectQuery(connection, type, tableName, where);
            PoolManager.releaseConnection(connection);
            return result;
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public T getById(Long id) {
        try {
            Connection connection = PoolManager.getConnection();
            List<T> categories = TableUtils.selectQuery(connection, type, tableName,
                    primaryKey+'='+id);
            PoolManager.releaseConnection(connection);
            if (!categories.isEmpty()) {
                return categories.get(0);
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean create(T item) {
        try {
            Connection connection = PoolManager.getConnection();
            var result = TableUtils.insertQuery(connection, item, tableName);
            PoolManager.releaseConnection(connection);
            return result;
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(T item) {
        try  {
            Connection connection = PoolManager.getConnection();
            var result = TableUtils.updateQuery(connection, item, tableName, primaryKey);
            PoolManager.releaseConnection(connection);
            return result;
        } catch (SQLException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        try {
            Connection connection = PoolManager.getConnection();
            var result = TableUtils.deleteQuery(connection, type, tableName, primaryKey+"="+id);
            PoolManager.releaseConnection(connection);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public Long getCount() {
        try {
            Connection connection = PoolManager.getConnection();
            var result = TableUtils.countQuery(connection, type, tableName);
            PoolManager.releaseConnection(connection);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
