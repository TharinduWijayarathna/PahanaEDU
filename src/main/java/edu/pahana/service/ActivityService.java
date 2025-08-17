package edu.pahana.service;

import edu.pahana.dao.ActivityDAO;
import edu.pahana.model.Activity;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing system activities and events. Provides business
 * logic for activity tracking and retrieval.
 */
public class ActivityService {

	private ActivityDAO activityDAO;

	public ActivityService() {
		this.activityDAO = new ActivityDAO();
	}

	/**
	 * Initialize the activity system (create table if needed)
	 */
	public void initialize() throws SQLException {
		activityDAO.createTableIfNotExists();
	}

	/**
	 * Log a new activity
	 */
	public boolean logActivity(Activity activity) throws SQLException {
		return activityDAO.createActivity(activity);
	}

	/**
	 * Log bill creation activity
	 */
	public boolean logBillCreated(int billId, String customerName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_BILL_CREATED, "New bill generated", "bill", billId, customerName,
				username);
		return logActivity(activity);
	}

	/**
	 * Log bill payment activity
	 */
	public boolean logBillPaid(int billId, String customerName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_BILL_PAID, "Bill marked as paid", "bill", billId, customerName,
				username);
		return logActivity(activity);
	}

	/**
	 * Log customer addition activity
	 */
	public boolean logCustomerAdded(int customerId, String customerName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_CUSTOMER_ADDED, "New customer added", "customer", customerId,
				customerName, username);
		return logActivity(activity);
	}

	/**
	 * Log customer update activity
	 */
	public boolean logCustomerUpdated(int customerId, String customerName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_CUSTOMER_UPDATED, "Customer information updated", "customer",
				customerId, customerName, username);
		return logActivity(activity);
	}

	/**
	 * Log product addition activity
	 */
	public boolean logProductAdded(int productId, String productName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_PRODUCT_ADDED, "New product added", "product", productId,
				productName, username);
		return logActivity(activity);
	}

	/**
	 * Log product update activity
	 */
	public boolean logProductUpdated(int productId, String productName, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_PRODUCT_UPDATED, "Product information updated", "product",
				productId, productName, username);
		return logActivity(activity);
	}

	/**
	 * Log user login activity
	 */
	public boolean logUserLogin(String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_USER_LOGIN, "User logged in", username);
		return logActivity(activity);
	}

	/**
	 * Log system backup activity
	 */
	public boolean logSystemBackup(String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_SYSTEM_BACKUP, "System backup completed successfully", username);
		return logActivity(activity);
	}

	/**
	 * Log system maintenance activity
	 */
	public boolean logSystemMaintenance(String description, String username) throws SQLException {
		Activity activity = new Activity(Activity.TYPE_SYSTEM_MAINTENANCE, description, username);
		return logActivity(activity);
	}

	/**
	 * Get all activities
	 */
	public List<Activity> getAllActivities() throws SQLException {
		return activityDAO.getAllActivities();
	}

	/**
	 * Get recent activities with limit
	 */
	public List<Activity> getRecentActivities(int limit) throws SQLException {
		return activityDAO.getRecentActivities(limit);
	}

	/**
	 * Get activities by type
	 */
	public List<Activity> getActivitiesByType(String activityType) throws SQLException {
		return activityDAO.getActivitiesByType(activityType);
	}

	/**
	 * Get activities by username
	 */
	public List<Activity> getActivitiesByUsername(String username) throws SQLException {
		return activityDAO.getActivitiesByUsername(username);
	}

	/**
	 * Get activities by entity
	 */
	public List<Activity> getActivitiesByEntity(String entityType, int entityId) throws SQLException {
		return activityDAO.getActivitiesByEntity(entityType, entityId);
	}

	/**
	 * Get activities within date range
	 */
	public List<Activity> getActivitiesByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
		return activityDAO.getActivitiesByDateRange(startDate, endDate);
	}

	/**
	 * Get today's activities
	 */
	public List<Activity> getTodayActivities() throws SQLException {
		LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
		LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
		return activityDAO.getActivitiesByDateRange(startOfDay, endOfDay);
	}

	/**
	 * Get this week's activities
	 */
	public List<Activity> getThisWeekActivities() throws SQLException {
		LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
		LocalDateTime endOfWeek = LocalDateTime.now();
		return activityDAO.getActivitiesByDateRange(startOfWeek, endOfWeek);
	}

	/**
	 * Get activity count by type
	 */
	public int getActivityCountByType(String activityType) throws SQLException {
		return activityDAO.getActivityCountByType(activityType);
	}

	/**
	 * Clean up old activities
	 */
	public boolean cleanupOldActivities(int daysToKeep) throws SQLException {
		return activityDAO.deleteOldActivities(daysToKeep);
	}

	/**
	 * Get activity statistics
	 */
	public java.util.Map<String, Object> getActivityStatistics() throws SQLException {
		java.util.Map<String, Object> stats = new java.util.HashMap<>();

		// Get counts for different activity types
		stats.put("totalActivities", activityDAO.getAllActivities().size());
		stats.put("billActivities", getActivityCountByType(Activity.TYPE_BILL_CREATED));
		stats.put("customerActivities", getActivityCountByType(Activity.TYPE_CUSTOMER_ADDED));
		stats.put("productActivities", getActivityCountByType(Activity.TYPE_PRODUCT_ADDED));
		stats.put("systemActivities", getActivityCountByType(Activity.TYPE_SYSTEM_BACKUP));

		// Get recent activity count
		stats.put("recentActivities", getRecentActivities(10).size());

		return stats;
	}
}
