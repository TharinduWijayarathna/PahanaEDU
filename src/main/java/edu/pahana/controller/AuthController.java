package edu.pahana.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.pahana.model.User;
import edu.pahana.service.UserService;
import edu.pahana.validation.ValidationUtils;

/**
 * Controller for handling authentication-related requests.
 * Manages login, logout, and registration processes.
 */
@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserService userService;
    
    /**
     * Initializes the controller
     */
    public void init() throws ServletException {
        userService = UserService.getInstance();
    }
    
    /**
     * Handles GET requests for login, logout, and registration forms
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            // Default to login page
            showLoginForm(request, response);
        } else if (action.equals("login")) {
            showLoginForm(request, response);
        } else if (action.equals("register")) {
            showRegisterForm(request, response);
        } else if (action.equals("logout")) {
            logout(request, response);
        } else if (action.equals("profile")) {
            showProfile(request, response);
        } else {
            // Default to login page
            showLoginForm(request, response);
        }
    }
    
    /**
     * Handles POST requests for login and registration
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action.equals("login")) {
            login(request, response);
        } else if (action.equals("register")) {
            register(request, response);
        }
    }
    
    /**
     * Shows the login form
     */
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
    }
    
    /**
     * Shows the registration form
     */
    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
    }
    
    /**
     * Shows the user profile
     */
    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("auth?action=login");
            return;
        }
        
        request.getRequestDispatcher("WEB-INF/view/auth/profile.jsp").forward(request, response);
    }
    
    /**
     * Processes login request
     */
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Sanitize inputs
        username = ValidationUtils.sanitizeString(username);
        password = ValidationUtils.sanitizeString(password);
        
        // Validate login inputs
        Map<String, String> validationErrors = ValidationUtils.validateLogin(username, password);
        
        if (!validationErrors.isEmpty()) {
            // Validation failed - show errors
            request.setAttribute("fieldErrors", validationErrors);
            request.setAttribute("username", username); // Preserve username for form
            request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
            return;
        }
        
        try {
            User user = userService.authenticate(username, password);
            
            if (user != null) {
                // Authentication successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());
                
                // Redirect to dashboard or home page
                response.sendRedirect("dashboard");
            } else {
                // Authentication failed
                request.setAttribute("error", "Invalid username or password");
                request.setAttribute("username", username); // Preserve username for form
                request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.setAttribute("username", username); // Preserve username for form
            request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
        }
    }
    
    /**
     * Processes registration request
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Sanitize inputs
        firstName = ValidationUtils.sanitizeString(firstName);
        lastName = ValidationUtils.sanitizeString(lastName);
        username = ValidationUtils.sanitizeString(username);
        email = ValidationUtils.sanitizeString(email);
        password = ValidationUtils.sanitizeString(password);
        confirmPassword = ValidationUtils.sanitizeString(confirmPassword);
        
        // Validate input
        Map<String, String> validationErrors = ValidationUtils.validateRegistration(firstName, lastName, username, email, password, confirmPassword);
        
        if (!validationErrors.isEmpty()) {
            // Validation failed - show errors
            request.setAttribute("fieldErrors", validationErrors);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
            return;
        }
        
        try {
            boolean success = userService.registerUser(username, password);
            
            if (success) {
                // Registration successful
                request.setAttribute("success", "Registration successful. Please login.");
                request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
            } else {
                // Registration failed (likely username already exists)
                request.setAttribute("error", "Username already exists");
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
        }
    }
    
    /**
     * Processes logout request
     */
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        response.sendRedirect("auth?action=login");
    }
}