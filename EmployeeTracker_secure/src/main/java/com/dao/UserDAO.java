package com.dao;

import com.model.User;
import com.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAO {

    private static final String SELECT_USER_BY_EMAIL = "SELECT user_id, email, password_hash, name, role FROM users WHERE email = ?";
    private static final String INSERT_USER = "INSERT INTO users (user_id, email, password_hash, name, role) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM users WHERE role = 'employee'";

    public User authenticate(String email, String plainPassword) throws SQLException {
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_EMAIL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedPasswordHash = rs.getString("password_hash");
                    if (plainPassword.equals(storedPasswordHash)) {
                        user = new User();
                        user.setId(rs.getString("user_id"));
                        user.setEmail(rs.getString("email"));
                        user.setPasswordHash(storedPasswordHash);
                        user.setName(rs.getString("name"));
                        user.setRole(rs.getString("role"));
                    }
                }
            }
        }
        return user;
    }

    public boolean createUser(String email, String plainPassword, String name, String role) throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_USER)) {
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, email);
            ps.setString(3, plainPassword);
            ps.setString(4, name);
            ps.setString(5, role);
            return ps.executeUpdate() > 0;
        }
    }

    public List<User> getAllEmployees() throws SQLException {
        List<User> employees = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_EMPLOYEES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                employees.add(user);
            }
        }
        return employees;
    }
}
