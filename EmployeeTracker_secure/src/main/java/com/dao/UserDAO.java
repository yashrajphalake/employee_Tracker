package com.dao;

import com.pept.model.User;
import java.sql.*;

/**
 * UserDAO.java
 * Handles all database operations for the User entity.
 * NOTE: In a production environment, database connection details and utility 
 * methods (like getConnection) would be handled by a separate, robust DBUtil 
 * class or a connection pool.
 */
public class UserDAO {

    // --- JDBC Connection Details (Placeholder - Update with your settings) ---
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pept_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "your_db_username";
    private static final String DB_PASS = "your_db_password";
    // -----------------------------------------------------------------------

    // SQL Queries
    private static final String SELECT_USER_BY_USERNAME = 
        "SELECT u.user_id, u.username, u.password_hash, u.full_name, u.email, u.role_id, r.role_name " +
        "FROM Users u JOIN Roles r ON u.role_id = r.role_id WHERE u.username = ?";
    
    private static final String INSERT_USER = 
        "INSERT INTO Users (username, password_hash, full_name, email, role_id) VALUES (?, ?, ?, ?, ?)";
        
    /**
     * Helper method to establish a database connection.
     * @return Connection object
     * @throws SQLException
     */
    private Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: " + e.getMessage());
            throw new SQLException("Database driver not available.", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /**
     * Retrieves a user by their username, including their role name.
     * This is the primary method used during the login process.
     *
     * @param username The username to search for.
     * @return A User object if found, or null otherwise.
     */
    public User findByUsername(String username) {
        User user = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)) {

            preparedStatement.setString(1, username);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getString("role_name")
                    );
                }
            }
        } catch (SQLException e) {
            // Log the exception in a real application
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Saves a new user record to the database.
     * @param user The User object to save.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean saveUser(User user) {
        int result = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setInt(5, user.getRoleId());

            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // Log the exception, especially for duplicate key errors (username/email)
            e.printStackTrace();
        }
        return result > 0;
    }
}