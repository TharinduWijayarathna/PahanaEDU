package edu.pahana.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.pahana.model.User;
import edu.pahana.service.UserService;

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
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
        }
    }
    
    /**
     * Processes registration request
     */
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Validate input
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required");
            request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
            return;
        }
        
        try {
            boolean success = userService.registerUser(username, password);
            
            if (success) {
                // Registration successful
                request.setAttribute("successMessage", "Registration successful. Please login.");
                request.getRequestDispatcher("WEB-INF/view/auth/login.jsp").forward(request, response);
            } else {
                // Registration failed (likely username already exists)
                request.setAttribute("errorMessage", "Username already exists");
                request.getRequestDispatcher("WEB-INF/view/auth/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
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