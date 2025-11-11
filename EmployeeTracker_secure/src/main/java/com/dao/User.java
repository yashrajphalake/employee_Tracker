package com.dao;

/**
 * User.java
 * This model represents a user record from the Users table.
 * It is used to carry data between the DAO and the servlets.
 */
public class User {
    private int userId;
    private String username;
    private String passwordHash; // Store the hashed password
    private String fullName;
    private String email;
    private int roleId;
    private String roleName; // Convenience field for role-based routing

    // Default Constructor
    public User() {
    }

    // Constructor used when fetching from DB
    public User(int userId, String username, String passwordHash, String fullName, String email, int roleId, String roleName) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.email = email;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}