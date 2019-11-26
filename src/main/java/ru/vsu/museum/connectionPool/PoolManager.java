package ru.vsu.museum.connectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class PoolManager {
    private static PoolManager instance;
    private ConnectionPool connectionPool;

    public static PoolManager getInstance()
    {
        if (instance == null) {
            instance = new PoolManager();

            try {
                instance.connectionPool = BasicConnectionPool.create("jdbc:h2:./museum", "sa", "sa");
            } catch (SQLException e) {
                System.out.println("Connection error. " + e.getMessage());
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return this.connectionPool.getConnection();
    }

    public boolean releaseConnection(Connection connection)
    {
        return this.connectionPool.releaseConnection(connection);
    }

    public void shutdown() throws SQLException {
        this.connectionPool.shutdown();
    }
}
