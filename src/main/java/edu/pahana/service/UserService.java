package edu.pahana.service;

import java.sql.SQLException;
import java.util.List;

import edu.pahana.dao.UserDAO;
import edu.pahana.model.User;

/**
 * Service class for User-related operations.
 * Provides business logic and serves as an intermediary between controllers and DAO.
 */
public class UserService {
    
    private static UserService instance;
    private UserDAO userDAO;
    
    /**
     * Private constructor for Singleton pattern
     */
    private UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Gets the singleton instance of UserService
     * 
     * @return The UserService instance
     */
    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }
    
    /**
     * Authenticates a user by username and password
     * 
     * @param username The username
     * @param password The password
     * @return User object if authentication successful, null otherwise
     * @throws SQLException if a database error occurs
     */
    public User authenticate(String username, String password) throws SQLException {
        return userDAO.authenticate(username, password);
    }
    
    /**
     * Registers a new user
     * 
     * @param username The username
     * @param password The password
     * @param role The role (default is "user")
     * @return true if registration successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean registerUser(String username, String password, String role) throws SQLException {
        // Check if username already exists
        if (userDAO.getUserByUsername(username) != null) {
            return false;
        }
        
        User user = new User(username, password, role);
        return userDAO.addUser(user);
    }
    
    /**
     * Registers a new user with default role "user"
     * 
     * @param username The username
     * @param password The password
     * @return true if registration successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean registerUser(String username, String password) throws SQLException {
        return registerUser(username, password, "user");
    }
    
    /**
     * Gets a user by ID
     * 
     * @param userId The user ID
     * @return User object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public User getUserById(int userId) throws SQLException {
        return userDAO.getUserById(userId);
    }
    
    /**
     * Gets a user by username
     * 
     * @param username The username
     * @return User object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public User getUserByUsername(String username) throws SQLException {
        return userDAO.getUserByUsername(username);
    }
    
    /**
     * Gets all users
     * 
     * @return List of all users
     * @throws SQLException if a database error occurs
     */
    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }
    
    /**
     * Updates a user
     * 
     * @param user The user to update
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateUser(User user) throws SQLException {
        return userDAO.updateUser(user);
    }
    
    /**
     * Deletes a user
     * 
     * @param userId The ID of the user to delete
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteUser(int userId) throws SQLException {
        return userDAO.deleteUser(userId);
    }
}