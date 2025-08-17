package edu.pahana.controller;

import edu.pahana.service.BillService;
import edu.pahana.service.CustomerService;
import edu.pahana.service.ProductService;
import edu.pahana.service.UserService;
import edu.pahana.service.ActivityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

	private BillService billService;
	private CustomerService customerService;
	private ProductService productService;
	private UserService userService;
	private ActivityService activityService;

	@Override
	public void init() throws ServletException {
		billService = new BillService();
		customerService = CustomerService.getInstance();
		productService = ProductService.getInstance();
		userService = UserService.getInstance();
		activityService = new ActivityService();

		// Initialize activity system
		try {
			activityService.initialize();
		} catch (Exception e) {
			// Log error but don't fail initialization
			System.err.println("Failed to initialize activity system: " + e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			action = "home";
		}

		switch (action) {
		case "home":
			showDashboard(request, response);
			break;
		case "help":
			showHelp(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		case "reports":
			showReports(request, response);
			break;
		default:
			showDashboard(request, response);
			break;
		}
	}

	private void showDashboard(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if user is logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("auth?action=login");
			return;
		}

		// Get dashboard statistics
		try {
			// Basic statistics
			int totalCustomers = customerService.getAllCustomers().size();
			int totalProducts = productService.getAllProducts().size();
			int totalBills = billService.getAllBills().size();
			int totalUsers = userService.getAllUsers().size();

			request.setAttribute("totalCustomers", totalCustomers);
			request.setAttribute("totalProducts", totalProducts);
			request.setAttribute("totalBills", totalBills);
			request.setAttribute("totalUsers", totalUsers);

			// Revenue Analytics Data
			Map<String, Object> revenueData = getRevenueAnalytics();
			request.setAttribute("revenueData", revenueData);

			// Quick Stats Summary
			Map<String, Object> quickStats = getQuickStats();
			request.setAttribute("quickStats", quickStats);

			// Recent Bills Table
			List<Map<String, Object>> recentBills = getRecentBills(5);
			request.setAttribute("recentBills", recentBills);

			// Top Customers
			List<Map<String, Object>> topCustomers = getTopCustomers(5);
			request.setAttribute("topCustomers", topCustomers);

			// System Status
			Map<String, Object> systemStatus = getSystemStatus();
			request.setAttribute("systemStatus", systemStatus);

			// Recent Activity
			List<Map<String, Object>> recentActivities = getRecentActivities(5);
			request.setAttribute("recentActivities", recentActivities);

		} catch (Exception e) {
			request.setAttribute("error", "Error loading dashboard data: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp").forward(request, response);
	}

	private Map<String, Object> getRevenueAnalytics() throws SQLException {
		Map<String, Object> revenueData = new HashMap<>();

		// Get current month and last month data
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime currentMonthStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		LocalDateTime lastMonthStart = currentMonthStart.minusMonths(1);
		LocalDateTime lastMonthEnd = currentMonthStart.minusSeconds(1);

		List<edu.pahana.model.Bill> allBills = billService.getAllBills();

		// Calculate current month revenue
		BigDecimal currentMonthRevenue = allBills.stream()
				.filter(bill -> bill.getBillDate() != null && bill.getBillDate().isAfter(currentMonthStart)
						&& "paid".equalsIgnoreCase(bill.getStatus()))
				.map(edu.pahana.model.Bill::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		// Calculate last month revenue
		BigDecimal lastMonthRevenue = allBills.stream()
				.filter(bill -> bill.getBillDate() != null && bill.getBillDate().isAfter(lastMonthStart)
						&& bill.getBillDate().isBefore(currentMonthStart) && "paid".equalsIgnoreCase(bill.getStatus()))
				.map(edu.pahana.model.Bill::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		// Calculate percentage change
		double percentageChange = 0.0;
		if (lastMonthRevenue.compareTo(BigDecimal.ZERO) > 0) {
			percentageChange = ((currentMonthRevenue.doubleValue() - lastMonthRevenue.doubleValue())
					/ lastMonthRevenue.doubleValue()) * 100;
		}

		revenueData.put("currentMonthRevenue", currentMonthRevenue);
		revenueData.put("lastMonthRevenue", lastMonthRevenue);
		revenueData.put("percentageChange", percentageChange);
		revenueData.put("currentMonthName", now.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
		revenueData.put("lastMonthName", lastMonthStart.format(DateTimeFormatter.ofPattern("MMMM yyyy")));

		return revenueData;
	}

	private Map<String, Object> getQuickStats() throws SQLException {
		Map<String, Object> quickStats = new HashMap<>();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime currentMonthStart = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

		List<edu.pahana.model.Bill> allBills = billService.getAllBills();
		List<edu.pahana.model.Customer> allCustomers = customerService.getAllCustomers();

		// This month revenue
		BigDecimal thisMonthRevenue = allBills.stream()
				.filter(bill -> bill.getBillDate() != null && bill.getBillDate().isAfter(currentMonthStart)
						&& "paid".equalsIgnoreCase(bill.getStatus()))
				.map(edu.pahana.model.Bill::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		// Bills generated this month
		long billsGenerated = allBills.stream()
				.filter(bill -> bill.getBillDate() != null && bill.getBillDate().isAfter(currentMonthStart)).count();

		// New customers this month
		long newCustomers = allCustomers.stream().filter(customer -> customer.getCreatedAt() != null
				&& customer.getCreatedAt().toLocalDateTime().isAfter(currentMonthStart)).count();

		// Average bill value
		BigDecimal avgBillValue = BigDecimal.ZERO;
		if (!allBills.isEmpty()) {
			BigDecimal totalRevenue = allBills.stream().filter(bill -> "paid".equalsIgnoreCase(bill.getStatus()))
					.map(edu.pahana.model.Bill::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
			avgBillValue = totalRevenue.divide(BigDecimal.valueOf(allBills.size()), 2, BigDecimal.ROUND_HALF_UP);
		}

		quickStats.put("thisMonthRevenue", thisMonthRevenue);
		quickStats.put("billsGenerated", billsGenerated);
		quickStats.put("newCustomers", newCustomers);
		quickStats.put("avgBillValue", avgBillValue);

		return quickStats;
	}

	private List<Map<String, Object>> getRecentBills(int limit) throws SQLException {
		List<edu.pahana.model.Bill> allBills = billService.getAllBills();

		return allBills.stream().sorted((b1, b2) -> {
			if (b1.getBillDate() == null)
				return 1;
			if (b2.getBillDate() == null)
				return -1;
			return b2.getBillDate().compareTo(b1.getBillDate());
		}).limit(limit).map(bill -> {
			Map<String, Object> billData = new HashMap<>();
			billData.put("billId", "BILL-" + String.format("%04d", bill.getBillId()));
			billData.put("customerName", bill.getCustomerName());
			billData.put("amount", bill.getTotalAmount());
			billData.put("status", bill.getStatus());
			billData.put("billDate", bill.getBillDate());
			return billData;
		}).collect(Collectors.toList());
	}

	private List<Map<String, Object>> getTopCustomers(int limit) throws SQLException {
		List<edu.pahana.model.Bill> allBills = billService.getAllBills();
		List<edu.pahana.model.Customer> allCustomers = customerService.getAllCustomers();

		// Group bills by customer and calculate total revenue
		Map<Integer, BigDecimal> customerRevenue = new HashMap<>();
		Map<Integer, Long> customerBillCount = new HashMap<>();
		Map<Integer, String> customerNames = new HashMap<>();

		// Initialize customer names
		for (edu.pahana.model.Customer customer : allCustomers) {
			customerNames.put(customer.getCustomerId(), customer.getName());
		}

		// Calculate revenue and bill count for each customer
		for (edu.pahana.model.Bill bill : allBills) {
			if ("paid".equalsIgnoreCase(bill.getStatus())) {
				int customerId = bill.getCustomerId();
				customerRevenue.merge(customerId, bill.getTotalAmount(), BigDecimal::add);
				customerBillCount.merge(customerId, 1L, Long::sum);
			}
		}

		// Create top customers list
		return customerRevenue.entrySet().stream().sorted(Map.Entry.<Integer, BigDecimal>comparingByValue().reversed())
				.limit(limit).map(entry -> {
					Map<String, Object> customerData = new HashMap<>();
					int customerId = entry.getKey();
					customerData.put("customerId", customerId);
					customerData.put("name", customerNames.get(customerId));
					customerData.put("totalRevenue", entry.getValue());
					customerData.put("billCount", customerBillCount.get(customerId));
					customerData.put("initials", getInitials(customerNames.get(customerId)));
					return customerData;
				}).collect(Collectors.toList());
	}

	private String getInitials(String name) {
		if (name == null || name.trim().isEmpty()) {
			return "NA";
		}

		String[] parts = name.trim().split("\\s+");
		if (parts.length >= 2) {
			return (parts[0].charAt(0) + "" + parts[1].charAt(0)).toUpperCase();
		} else {
			return name.substring(0, Math.min(2, name.length())).toUpperCase();
		}
	}

	private Map<String, Object> getSystemStatus() {
		Map<String, Object> systemStatus = new HashMap<>();

		// Simulate system status checks
		systemStatus.put("databaseConnection", "Connected");
		systemStatus.put("webServer", "Running");
		systemStatus.put("fileSystem", "Healthy");
		systemStatus.put("backupSystem",
				"Last: " + LocalDateTime.now().minusHours(2).format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
		systemStatus.put("overallStatus", "All Systems Operational");

		return systemStatus;
	}

	private List<Map<String, Object>> getRecentActivities(int limit) throws SQLException {
		List<edu.pahana.model.Activity> activities = activityService.getRecentActivities(limit);

		return activities.stream().map(activity -> {
			Map<String, Object> activityData = new HashMap<>();
			activityData.put("activityType", activity.getActivityType());
			activityData.put("description", activity.getDescription());
			activityData.put("entityName", activity.getEntityName());
			activityData.put("username", activity.getUsername());
			activityData.put("timestamp", activity.getTimestamp());
			activityData.put("timeAgo", activity.getTimeAgo());
			activityData.put("color", activity.getActivityColor());
			activityData.put("icon", activity.getActivityIcon());
			return activityData;
		}).collect(Collectors.toList());
	}

	private void showReports(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if user is logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("auth?action=login");
			return;
		}

		// For now, redirect to dashboard with reports view
		// You can implement a separate reports page later
		response.sendRedirect("dashboard");
	}

	private void showHelp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if user is logged in
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("user") == null) {
			response.sendRedirect("auth?action=login");
			return;
		}

		request.getRequestDispatcher("/WEB-INF/view/help.jsp").forward(request, response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		request.setAttribute("success", "You have been logged out successfully");
		response.sendRedirect("auth?action=login");
	}
}