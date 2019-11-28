package ru.vsu.museum.persistence.repositories.db;

import ru.vsu.museum.connectionPool.PoolManager;
import ru.vsu.museum.persistence.Repository;
import ru.vsu.museum.persistence.repositories.TableUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBRepository<T> implements Repository<T> {
    private final Class<T> type;
    private final String TABLE_NAME;
    private final String PRIMARY_KEY;

    public DBRepository(Class<T> type, String tableName, String primaryKey) {
        this.type = type;
        this.TABLE_NAME = tableName;
        this.PRIMARY_KEY = primaryKey;

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found. (" + e.getMessage() + ")");
        }
    }

    @Override
    public List<T> getAll(String where) {
        return TableUtils.selectQuery(type, TABLE_NAME, where);
    }

    @Override
    public T getById(Long id) {
        List<T> categories = TableUtils.selectQuery(type, TABLE_NAME, PRIMARY_KEY +'='+id);
        if (categories != null && !categories.isEmpty()) {
            return categories.get(0);
        }
        return null;
    }

    @Override
    public boolean create(T item) {
        return TableUtils.insertQuery(item, TABLE_NAME);
    }

    @Override
    public boolean update(T item) {
        return TableUtils.updateQuery(item, TABLE_NAME, PRIMARY_KEY);
    }

    @Override
    public boolean delete(Long id) {
        return TableUtils.deleteQuery(type, TABLE_NAME, PRIMARY_KEY + "=" + id);
    }

    @Override
    public Long getCount() {
        return TableUtils.countQuery(type, TABLE_NAME);
    }
}
