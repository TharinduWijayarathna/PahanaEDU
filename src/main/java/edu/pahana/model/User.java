package edu.pahana.model;

import java.sql.Timestamp;

/**
 * User model class representing users in the system.
 * Used for authentication and authorization.
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private String role;
    private Timestamp createdAt;
    
    /**
     * Default constructor
     */
    public User() {
    }
    
    /**
     * Constructor with all fields except createdAt
     */
    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Constructor with all fields
     */
    public User(int userId, String username, String password, String role, Timestamp createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    /**
     * Constructor for creating a new user (without userId and createdAt)
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", role=" + role + "]";
    }
}