package ru.vsu.museum.persistence.repositories;

import ru.vsu.museum.connectionPool.ConnectionPool;
import ru.vsu.museum.connectionPool.PoolManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableUtils {
    private static Long lastInsertedId;

    public static Long getLastInsertedId()
    {
        return lastInsertedId;
    }

    private static String createInsertStatementSQL(Class<?> zclass, String tableName)
    {
        StringBuilder fields = new StringBuilder();
        StringBuilder vars = new StringBuilder();
        for (Field field : zclass.getDeclaredFields())
        {
            String name = field.getName();

            if (fields.length() > 1)
            {
                fields.append(",");
                vars.append(",");
            }

            fields.append(name);
            vars.append("?");
        }

        return "INSERT INTO " + tableName + "(" + fields.toString() + ") " +
                "VALUES (" + vars.toString() + ")";
    }


    private static String createUpdateStatementSQL(Class<?> zclass, String tableName,
                                                   String primaryKey)
    {
        StringBuilder sets= new StringBuilder();
        String where = null;

        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            String pair = name + " = ?";
            if (name.equals(primaryKey)) {
                where = " WHERE " + pair;
            }
            else {
                if(sets.length()>1) {
                    sets.append(", ");
                }
                sets.append(pair);
            }
        }

        if (where == null)
        {
            String string = "Primary key not found in '" + zclass.getName() + "'";
            throw new IllegalArgumentException(string);
        }

        return "UPDATE " + tableName + " SET " + sets.toString() + where;
    }

    private static PreparedStatement createInsertPreparedStatement(Connection connection, Object
            object, String tableName) throws SQLException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        String sql = createInsertStatementSQL(zclass, tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Field[] fields = zclass.getDeclaredFields();

        for (int i = 0; i < fields.length; i++)
        {
            Field field = fields[i];
            field.setAccessible(true);
            Object value = field.get(object);
            preparedStatement.setObject((i+1), value);
        }

        return preparedStatement;
    }

    private static PreparedStatement createUpdatePreparedStatement(Connection connection,
            Object object, String tableName, String primaryKey) throws SQLException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        String sql = createUpdateStatementSQL(zclass, tableName, primaryKey);
        PreparedStatement statement = connection.prepareStatement(sql);
        Field[] fields = zclass.getDeclaredFields();
        int pkSequence = fields.length;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object value = field.get(object);
            String name = field.getName();
            if(name.equals(primaryKey)) {
                statement.setObject(pkSequence, value);
            }
            else {
                statement.setObject(i, value);
            }
        }
        return statement;
    }

    private static void loadResultSetIntoObject(ResultSet resultSet, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException
    {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = resultSet.getObject(name);
            Class<?> type = field.getType();

            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);
                value = boxed.cast(value);
            }

            field.set(object, value);
        }
    }

    private static boolean isPrimitive(Class<?> type)
    {
        return (type==int.class || type==long.class ||
                type==double.class  || type==float.class
                || type==boolean.class || type==byte.class
                || type==char.class || type==short.class);
    }

    private static Class<?> boxPrimitiveClass(Class<?> type)
    {
        if(type==int.class){return Integer.class;}
        else if(type==long.class){return Long.class;}
        else if (type==double.class){return Double.class;}
        else if(type==float.class){return Float.class;}
        else if(type==boolean.class){return Boolean.class;}
        else if(type==byte.class){return Byte.class;}
        else if(type==char.class){return Character.class;}
        else if(type==short.class){return Short.class;}
        else
        {
            String string="class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

    public static <T> List<T> selectQuery(Class<T> type, String tableName, String where) {
        String sql = "SELECT * FROM " + tableName;
        if(!(where == null || where.isEmpty())) {
            sql += " WHERE " + where;
        }

        try {
            Connection connection = PoolManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            List<T> list = new ArrayList<T>();
            while(resultSet.next()) {
                T t = type.newInstance();
                loadResultSetIntoObject(resultSet, t);
                list.add(t);
            }

            statement.close();
            PoolManager.releaseConnection(connection);

            return list;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> boolean deleteQuery(Class<T> type, String tableName, String where) {
        String sql = "DELETE FROM " + tableName;
        if(!(where == null || where.isEmpty())) {
            sql += " WHERE " + where;
        }

        try {
            Connection connection = PoolManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            PoolManager.releaseConnection(connection);

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static <T> boolean updateQuery(T item, String tableName, String primaryKey) {

        try {
            Connection connection = PoolManager.getConnection();
            PreparedStatement preparedStatement = createUpdatePreparedStatement(connection, item,
                    tableName, primaryKey);
            int rows = preparedStatement.executeUpdate();
            preparedStatement.close();
            PoolManager.releaseConnection(connection);

            return rows > 0;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static <T> boolean insertQuery(T item, String tableName) {
        try {
            Connection connection = PoolManager.getConnection();
            PreparedStatement preparedStatement = createInsertPreparedStatement(connection, item,
                    tableName);
            int rows = preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                lastInsertedId = (long)rs.getInt(1);
            }

            preparedStatement.close();
            PoolManager.releaseConnection(connection);

            return rows > 0;
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static <T> Long countQuery(Class<T> type, String tableName) {
        String sql = "SELECT count(1) AS cnt FROM " + tableName;
        try {
            Connection connection = PoolManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            PoolManager.releaseConnection(connection);

            List<Long> counts = new ArrayList<Long>();
            while (resultSet.next()) {
                Long count = resultSet.getLong("cnt");
                counts.add(count);
            }

            if (!counts.isEmpty())
                return counts.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
