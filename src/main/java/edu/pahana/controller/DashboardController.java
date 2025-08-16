package edu.pahana.controller;

import edu.pahana.service.BillService;
import edu.pahana.service.CustomerService;
import edu.pahana.service.ProductService;
import edu.pahana.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {
    
    private BillService billService;
    private CustomerService customerService;
    private ProductService productService;
    private UserService userService;
    
    @Override
    public void init() throws ServletException {
        billService = new BillService();
        customerService = CustomerService.getInstance();
        productService = ProductService.getInstance();
        userService = UserService.getInstance();
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
            int totalCustomers = customerService.getAllCustomers().size();
            int totalProducts = productService.getAllProducts().size();
            int totalBills = billService.getAllBills().size();
            int totalUsers = userService.getAllUsers().size();
            
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalBills", totalBills);
            request.setAttribute("totalUsers", totalUsers);

        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard data: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/view/dashboard.jsp").forward(request, response);
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
    
    private void logout(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        request.setAttribute("success", "You have been logged out successfully");
        response.sendRedirect("auth?action=login");
    }
} 