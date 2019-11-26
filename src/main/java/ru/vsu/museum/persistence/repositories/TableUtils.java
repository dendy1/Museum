package ru.vsu.museum.persistence.repositories;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableUtils {
    public static String createInsertStatementSQL(Class<?> zclass, String tableName)
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

        String Sql = "INSERT INTO " + tableName + "(" + fields.toString() + ") VALUES (" + vars.toString() + ")";
        return Sql;
    }

    public static PreparedStatement createInsertPreparedStatement(Connection connection, Object
            object, String tableName)
    {
        PreparedStatement preparedStatement = null;
        try
        {
            Class<?> zclass = object.getClass();
            String Sql = createInsertStatementSQL(zclass, tableName);
            preparedStatement = connection.prepareStatement(Sql);
            Field[] fields = zclass.getDeclaredFields();
            for(int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);
                preparedStatement.setObject((i+1), value);
            }
        }
        catch(SecurityException | IllegalArgumentException
                | IllegalAccessException | SQLException e)
        {
            String string="Unable to create prepared statement: " + e.getMessage();
            throw new RuntimeException(string, e);
        }
        return preparedStatement;
    }

    public static String createUpdateStatementSql(Class<?> zclass, String tableName, String primaryKey)
    {
        StringBuilder sets= new StringBuilder();
        String where = null;
        for (Field field : zclass.getDeclaredFields())
        {
            String name = field.getName();
            String pair = name + " = ?";
            if (name.equals(primaryKey))
            {
                where = " WHERE " + pair;
            }
            else
            {
                if(sets.length()>1)
                {
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

        String Sql = "UPDATE " + tableName + " SET " + sets.toString() + where;
        return Sql;
    }

    public static PreparedStatement createUpdatePreparedStatement(Connection connection, Object object,
                                                                  String tableName, String primaryKey) throws SQLException
    {
        PreparedStatement statement = null;
        try
        {
            Class<?> zclass = object.getClass();
            String Sql = createUpdateStatementSql(zclass, tableName, primaryKey);
            System.out.println(Sql);
            statement = connection.prepareStatement(Sql);
            Field[] fields = zclass.getDeclaredFields();
            int pkSequence = fields.length;
            for(int i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);
                String name = field.getName();
                if(name.equals(primaryKey))
                {
                    statement.setObject(pkSequence, value);
                }
                else
                {
                    statement.setObject(i, value);
                }
            }
        }
        catch(SecurityException | IllegalArgumentException
                | IllegalAccessException e)
        {
            String string="Unable to create PreparedStatement: " + e.getMessage();
            throw new RuntimeException(string,e);
        }
        return statement;
    }

    public static <T> boolean deleteQuery(Connection connection, Class<T> type, String tableName, String where)
            throws SQLException
    {
        List<T> list = new ArrayList<T>();
        String Sql = "DELETE FROM " + tableName;
        if(!(where == null || where.isEmpty()))
        {
            Sql += " WHERE " + where;
        }

        try (PreparedStatement statement = connection.prepareStatement(Sql))
        {
            int rows = statement.executeUpdate();

            if (rows > 0)
            {
                return true;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Unable to create record: " + e.getMessage(), e);
        }
        return false;
    }

    public static <T> List <T> selectQuery(Connection connection, Class<T> type, String tableName, String where)
            throws SQLException
    {
        List<T> list = new ArrayList<T>();
        String Sql = "SELECT * FROM " + tableName;
        if(!(where == null || where.isEmpty()))
        {
            Sql += " WHERE " + where;
        }

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(Sql);

            while(resultSet.next())
            {
                T t = type.newInstance();
                loadResultSetIntoObject(resultSet, t);
                list.add(t);
            }
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException("Unable to create record: " + e.getMessage(), e);
        }

        return list;
    }

    public static <T> Long countQuery(Connection connection, Class<T> type, String tableName)
            throws SQLException
    {
        String Sql = "SELECT count(1) AS cnt FROM " + tableName;

        try (Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery(Sql);

            List<Long> counts = new ArrayList<Long>();
            while (resultSet.next()) {
                Long count = resultSet.getLong("cnt");
                counts.add(count);
            }

            if (!counts.isEmpty())
            return counts.get(0);
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Unable to create record: " + e.getMessage(), e);
        }

        return null;
    }

    private static void loadResultSetIntoObject(ResultSet resultSet, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException
    {
        Class<?> zclass = object.getClass();
        for(Field field : zclass.getDeclaredFields())
        {
            String name = field.getName();
            field.setAccessible(true);
            Object value = resultSet.getObject(name);
            Class<?> type = field.getType();
            if(isPrimitive(type))
            {
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
}
