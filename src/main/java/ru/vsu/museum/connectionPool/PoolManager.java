package ru.vsu.museum.connectionPool;

import ru.vsu.museum.Config;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolManager {
    private static ConnectionPool connectionPool;

    public static Connection getConnection() throws SQLException {
        if (connectionPool == null) {
            connectionPool = BasicConnectionPool.create(Config.URL, Config.USER, Config.PASSWORD);
        }
        return connectionPool.getConnection();
    }

    public static boolean releaseConnection(Connection connection) throws SQLException {
        if (connectionPool == null) {
            connectionPool = BasicConnectionPool.create(Config.URL, Config.USER, Config.PASSWORD);
        }

        return connectionPool.releaseConnection(connection);
    }

    public static void shutdown() throws SQLException {
        if (connectionPool != null) {
            connectionPool.shutdown();
        }
    }
}
