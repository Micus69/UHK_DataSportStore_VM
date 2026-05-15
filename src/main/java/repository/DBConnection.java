package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides connection to the MySQL database running in Docker.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3307/pujcovna";
    private static final String USER = "app_user";
    private static final String PASSWORD = "app123";

    private DBConnection() {
        // Utility class - prevents creating instances.
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}