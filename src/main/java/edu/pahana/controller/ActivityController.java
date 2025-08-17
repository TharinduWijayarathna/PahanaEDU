package edu.pahana.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.pahana.model.Activity;
import edu.pahana.service.ActivityService;

/**
 * Controller for handling activity viewing and filtering operations.
 */
@WebServlet("/activity")
public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ActivityService activityService;

	/**
	 * Initializes the controller
	 */
	public void init() throws ServletException {
		activityService = new ActivityService();
	}

	/**
	 * Handles GET requests for activity operations
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if user is logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("auth?action=login");
			return;
		}

		String action = request.getParameter("action");

		if (action == null || action.equals("list")) {
			listActivities(request, response);
		} else {
			listActivities(request, response);
		}
	}

	/**
	 * Lists all activities with optional filters
	 */
	private void listActivities(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Get filter parameters
			String activityType = request.getParameter("activityType");
			String username = request.getParameter("username");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String searchTerm = request.getParameter("search");

			List<Activity> activities;

			// Apply filters
			if (activityType != null && !activityType.trim().isEmpty()) {
				activities = activityService.getActivitiesByType(activityType.trim());
			} else if (username != null && !username.trim().isEmpty()) {
				activities = activityService.getActivitiesByUsername(username.trim());
			} else if (startDate != null && !startDate.trim().isEmpty() && endDate != null && !endDate.trim().isEmpty()) {
				try {
					LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
					LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");
					activities = activityService.getActivitiesByDateRange(startDateTime, endDateTime);
				} catch (DateTimeParseException e) {
					request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD format.");
					activities = activityService.getAllActivities();
				}
			} else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
				// For search, we'll get all activities and filter in memory
				// In a real application, you'd want to implement search in the DAO
				activities = activityService.getAllActivities();
				activities = activities.stream()
						.filter(activity -> activity.getDescription().toLowerCase().contains(searchTerm.toLowerCase()) ||
								(activity.getEntityName() != null && activity.getEntityName().toLowerCase().contains(searchTerm.toLowerCase())) ||
								activity.getUsername().toLowerCase().contains(searchTerm.toLowerCase()))
						.toList();
			} else {
				// Get all activities
				activities = activityService.getAllActivities();
			}

			// Get activity statistics for the filter panel
			Map<String, Object> activityStats = activityService.getActivityStatistics();

			// Set attributes
			request.setAttribute("activities", activities);
			request.setAttribute("activityStats", activityStats);
			request.setAttribute("filterActivityType", activityType);
			request.setAttribute("filterUsername", username);
			request.setAttribute("filterStartDate", startDate);
			request.setAttribute("filterEndDate", endDate);
			request.setAttribute("filterSearchTerm", searchTerm);

			// Get unique activity types for filter dropdown
			List<String> activityTypes = List.of(
					Activity.TYPE_BILL_CREATED,
					Activity.TYPE_BILL_UPDATED,
					Activity.TYPE_BILL_PAID,
					Activity.TYPE_CUSTOMER_ADDED,
					Activity.TYPE_CUSTOMER_UPDATED,
					Activity.TYPE_PRODUCT_ADDED,
					Activity.TYPE_PRODUCT_UPDATED,
					Activity.TYPE_USER_LOGIN,
					Activity.TYPE_SYSTEM_BACKUP,
					Activity.TYPE_SYSTEM_MAINTENANCE
			);
			request.setAttribute("activityTypes", activityTypes);

			request.getRequestDispatcher("WEB-INF/view/activity/listActivities.jsp").forward(request, response);
		} catch (SQLException e) {
			request.setAttribute("error", "Error loading activities: " + e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
		}
	}
}
