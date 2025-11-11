package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for establishing and managing the database connection (JDBC).
 * This uses a simplified approach suitable for a demo/educational project.
 */
public class DBConnection {
    // NOTE: In a real environment, these values would be loaded from a secure configuration file (e.g., JNDI, .properties).
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/peptdb";
    private static final String JDBC_USER = "pept_user";
    private static final String JDBC_PASSWORD = "pept_password";
    private static final String JDBC_DRIVER = "org.postgresql.Driver"; // Assuming PostgreSQL, change if using MySQL

    /**
     * Attempts to establish a connection to the database.
     * @return A valid Connection object.
     * @throws SQLException if a database access error occurs or the JDBC driver cannot be found.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver (required for older environments, though often optional now)
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (ClassNotFoundException e) {
            // Log this exception properly in a real app
            System.err.println("JDBC Driver not found: " + JDBC_DRIVER);
            throw new SQLException("Database driver not available.", e);
        }
    }

    /**
     * Closes the given connection gracefully.
     * @param connection The Connection object to close.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}