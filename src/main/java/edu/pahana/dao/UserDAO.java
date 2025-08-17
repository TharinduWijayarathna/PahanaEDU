package edu.pahana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pahana.model.User;

/**
 * Data Access Object for User entity. Handles all database operations related
 * to users.
 */
public class UserDAO {

	/**
	 * Authenticates a user by username and password
	 * 
	 * @param username The username
	 * @param password The password
	 * @return User object if authentication successful, null otherwise
	 * @throws SQLException if a database error occurs
	 */
	public User authenticate(String username, String password) throws SQLException {
		String query = "SELECT * FROM User WHERE username = ? AND password = ?";
		User user = null;

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
		}

		return user;
	}

	/**
	 * Adds a new user to the database
	 * 
	 * @param user The user to add
	 * @return true if successful, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean addUser(User user) throws SQLException {
		String query = "INSERT INTO User (username, password, role) VALUES (?, ?, ?)";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getRole());

		int rowsInserted = statement.executeUpdate();
		return rowsInserted > 0;
	}

	/**
	 * Gets a user by ID
	 * 
	 * @param userId The user ID
	 * @return User object if found, null otherwise
	 * @throws SQLException if a database error occurs
	 */
	public User getUserById(int userId) throws SQLException {
		String query = "SELECT * FROM User WHERE user_id = ?";
		User user = null;

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userId);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
		}

		return user;
	}

	/**
	 * Gets a user by username
	 * 
	 * @param username The username
	 * @return User object if found, null otherwise
	 * @throws SQLException if a database error occurs
	 */
	public User getUserByUsername(String username) throws SQLException {
		String query = "SELECT * FROM User WHERE username = ?";
		User user = null;

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
		}

		return user;
	}

	/**
	 * Gets all users from the database
	 * 
	 * @return List of all users
	 * @throws SQLException if a database error occurs
	 */
	public List<User> getAllUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM User ORDER BY username";

		Connection connection = DBConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			User user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
			users.add(user);
		}

		return users;
	}

	/**
	 * Gets paginated users from the database
	 * 
	 * @param offset The offset for pagination
	 * @param limit  The limit for pagination
	 * @return List of users for the current page
	 * @throws SQLException if a database error occurs
	 */
	public List<User> getUsersPaginated(int offset, int limit) throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM User ORDER BY username LIMIT ? OFFSET ?";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, limit);
		statement.setInt(2, offset);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			User user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
			users.add(user);
		}

		return users;
	}

	/**
	 * Gets the total count of users
	 * 
	 * @return Total number of users
	 * @throws SQLException if a database error occurs
	 */
	public int getUserCount() throws SQLException {
		String query = "SELECT COUNT(*) FROM User";

		Connection connection = DBConnectionFactory.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		if (resultSet.next()) {
			return resultSet.getInt(1);
		}

		return 0;
	}

	/**
	 * Searches users by username or role
	 * 
	 * @param searchTerm The search term to look for
	 * @return List of matching users
	 * @throws SQLException if a database error occurs
	 */
	public List<User> searchUsers(String searchTerm) throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM User WHERE username LIKE ? OR role LIKE ? ORDER BY username";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		String searchPattern = "%" + searchTerm + "%";
		statement.setString(1, searchPattern);
		statement.setString(2, searchPattern);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			User user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
			users.add(user);
		}

		return users;
	}

	/**
	 * Searches users by username or role with pagination
	 * 
	 * @param searchTerm The search term to look for
	 * @param offset     The offset for pagination
	 * @param limit      The limit for pagination
	 * @return List of matching users for the current page
	 * @throws SQLException if a database error occurs
	 */
	public List<User> searchUsersPaginated(String searchTerm, int offset, int limit) throws SQLException {
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM User WHERE username LIKE ? OR role LIKE ? ORDER BY username LIMIT ? OFFSET ?";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		String searchPattern = "%" + searchTerm + "%";
		statement.setString(1, searchPattern);
		statement.setString(2, searchPattern);
		statement.setInt(3, limit);
		statement.setInt(4, offset);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			User user = new User();
			user.setUserId(resultSet.getInt("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setRole(resultSet.getString("role"));
			user.setCreatedAt(resultSet.getTimestamp("created_at"));
			users.add(user);
		}

		return users;
	}

	/**
	 * Gets the count of users matching a search term
	 * 
	 * @param searchTerm The search term to look for
	 * @return Count of matching users
	 * @throws SQLException if a database error occurs
	 */
	public int getUserSearchCount(String searchTerm) throws SQLException {
		String query = "SELECT COUNT(*) FROM User WHERE username LIKE ? OR role LIKE ?";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		String searchPattern = "%" + searchTerm + "%";
		statement.setString(1, searchPattern);
		statement.setString(2, searchPattern);
		ResultSet resultSet = statement.executeQuery();

		if (resultSet.next()) {
			return resultSet.getInt(1);
		}

		return 0;
	}

	/**
	 * Updates a user in the database
	 * 
	 * @param user The user to update
	 * @return true if successful, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean updateUser(User user) throws SQLException {
		String query = "UPDATE User SET username = ?, password = ?, role = ? WHERE user_id = ?";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, user.getUsername());
		statement.setString(2, user.getPassword());
		statement.setString(3, user.getRole());
		statement.setInt(4, user.getUserId());

		int rowsUpdated = statement.executeUpdate();
		return rowsUpdated > 0;
	}

	/**
	 * Deletes a user from the database
	 * 
	 * @param userId The ID of the user to delete
	 * @return true if successful, false otherwise
	 * @throws SQLException if a database error occurs
	 */
	public boolean deleteUser(int userId) throws SQLException {
		String query = "DELETE FROM User WHERE user_id = ?";

		Connection connection = DBConnectionFactory.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, userId);

		int rowsDeleted = statement.executeUpdate();
		return rowsDeleted > 0;
	}
}