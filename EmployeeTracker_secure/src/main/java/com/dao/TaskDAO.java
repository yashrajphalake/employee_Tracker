package com.dao;

import com.itco.pept.model.User;
import com.itco.pept.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID; // Used for generating unique User IDs

/**
 * Data Access Object (DAO) for managing User data in the SQL database.
 * Handles authentication and basic user retrieval.
 */
public class UserDAO {

    private static final String SELECT_USER_BY_EMAIL = 
        "SELECT user_id, email, password_hash, name, role FROM users WHERE email = ?";
    private static final String INSERT_USER = 
        "INSERT INTO users (user_id, email, password_hash, name, role) VALUES (?, ?, ?, ?, ?)";
    
    /**
     * Attempts to authenticate a user based on email and password.
     * NOTE: In a production application, actual password hashing (like BCrypt) 
     * would be used instead of simple string comparison.
     * * @param email The user's email address.
     * @param plainPassword The plain text password entered by the user.
     * @return The User object if authentication succeeds, null otherwise.
     * @throws SQLException If a database error occurs.
     */
    public User authenticate(String email, String plainPassword) throws SQLException {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(SELECT_USER_BY_EMAIL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password_hash");
                
                // *** SIMULATED PASSWORD CHECK ***
                // In a real application, you would compare plainPassword against storedPasswordHash using a secure hashing library.
                // For this demo, we assume the stored hash is simply the plain password itself for testing.
                if (plainPassword.equals(storedPasswordHash)) {
                    user = new User();
                    user.setUserId(rs.getString("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPasswordHash(storedPasswordHash);
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                }
            }
        } finally {
            // Close resources in reverse order of acquisition
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            DBConnection.closeConnection(conn);
        }
        return user;
    }

    /**
     * A helper method to create a user for initial setup and testing.
     */
    public boolean createUser(String email, String plainPassword, String name, String role) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBConnection.getConnection();
            ps = conn.prepareStatement(INSERT_USER);
            
            ps.setString(1, UUID.randomUUID().toString()); // Generate a unique ID
            ps.setString(2, email);
            // Storing the plain password as the "hash" for demo purposes
            ps.setString(3, plainPassword); 
            ps.setString(4, name);
            ps.setString(5, role);
            
            return ps.executeUpdate() > 0;
        } finally {
            if (ps != null) ps.close();
            DBConnection.closeConnection(conn);
        }
    }
}