package edu.pahana.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.pahana.model.Product;
import edu.pahana.service.ProductService;
import edu.pahana.validation.ValidationUtils;

@WebServlet("/product")
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductService productService;
	
	public void init() throws ServletException {
		productService = ProductService.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
	        if (action == null || action.isEmpty()) {
	            action = "list";
	        }
	        
	        switch (action) {
	            case "list":
	                listProducts(request, response);
	                break;
	            case "add":
	                showAddForm(request, response);
	                break;
	            case "view":
	                viewProduct(request, response);
	                break;
	            case "edit":
	                showEditForm(request, response);
	                break;
	            case "delete":
	                deleteProduct(request, response);
	                break;
	            default:
	                listProducts(request, response);
	                break;
	        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
	        if (action.equals("add")) {
	            addProduct(request, response);
	        } else if (action.equals("update")) {
	            updateProduct(request, response);
	        }
	}
	
	 private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
	 IOException {
			
	    	List<Product> productList = new ArrayList<Product>();
			try {
				productList = productService.getAllProducts();
				request.setAttribute("products", productList);
			} catch ( SQLException e) {
				request.setAttribute("errorMessage", e.getMessage());
	            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	            return;
			}
	    	
	        request.getRequestDispatcher("WEB-INF/view/product/listProducts.jsp").forward(request, response);
	    }

	    private void showAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.getRequestDispatcher("WEB-INF/view/product/addProduct.jsp").forward(request, response);
	    }

	    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String name = request.getParameter("name");
	        String priceStr = request.getParameter("price");
	        String description = request.getParameter("description");
	        String isbn = request.getParameter("isbn");
	        String author = request.getParameter("author");
	        String publisher = request.getParameter("publisher");
	        String publicationDateStr = request.getParameter("publicationDate");
	        
	        // Sanitize inputs
	        name = ValidationUtils.sanitizeString(name);
	        description = ValidationUtils.sanitizeString(description);
	        isbn = ValidationUtils.sanitizeString(isbn);
	        author = ValidationUtils.sanitizeString(author);
	        publisher = ValidationUtils.sanitizeString(publisher);
	        publicationDateStr = ValidationUtils.sanitizeString(publicationDateStr);
	        
	        // Validate input
	        Map<String, String> validationErrors = ValidationUtils.validateProduct(name, description, priceStr, isbn, author, publisher);
	        
	        if (!validationErrors.isEmpty()) {
	            // Validation failed - show errors
	            request.setAttribute("fieldErrors", validationErrors);
	            request.setAttribute("name", name);
	            request.setAttribute("price", priceStr);
	            request.setAttribute("description", description);
	            request.setAttribute("isbn", isbn);
	            request.setAttribute("author", author);
	            request.setAttribute("publisher", publisher);
	            request.setAttribute("publicationDate", publicationDateStr);
	            request.getRequestDispatcher("WEB-INF/view/product/addProduct.jsp").forward(request, response);
	            return;
	        }
	        
	        // Parse price
	        double price = Double.parseDouble(priceStr);
	        
	        Date publicationDate = null;
	        if (publicationDateStr != null && !publicationDateStr.trim().isEmpty()) {
	            try {
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                publicationDate = dateFormat.parse(publicationDateStr);
	            } catch (ParseException e) {
	                // Handle date parsing error
	                request.setAttribute("error", "Invalid publication date format. Use YYYY-MM-DD.");
	                request.setAttribute("name", name);
	                request.setAttribute("price", priceStr);
	                request.setAttribute("description", description);
	                request.setAttribute("isbn", isbn);
	                request.setAttribute("author", author);
	                request.setAttribute("publisher", publisher);
	                request.setAttribute("publicationDate", publicationDateStr);
	                request.getRequestDispatcher("WEB-INF/view/product/addProduct.jsp").forward(request, response);
	                return;
	            }
	        }
	        
	        Product product = new Product();
	        product.setName(name);
	        product.setPrice(price);
	        product.setDescription(description);
	        product.setIsbn(isbn);
	        product.setAuthor(author);
	        product.setPublisher(publisher);
	        product.setPublicationDate(publicationDate);
	        
	        try {
	            productService.addProduct(product);
	            response.sendRedirect("product?action=list");
	        } catch (SQLException e) {
	            request.setAttribute("error", "Database error: " + e.getMessage());
	            request.setAttribute("name", name);
	            request.setAttribute("price", priceStr);
	            request.setAttribute("description", description);
	            request.setAttribute("isbn", isbn);
	            request.setAttribute("author", author);
	            request.setAttribute("publisher", publisher);
	            request.setAttribute("publicationDate", publicationDateStr);
	            request.getRequestDispatcher("WEB-INF/view/product/addProduct.jsp").forward(request, response);
	        }
	    }
	    
	    private void viewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int productId = Integer.parseInt(request.getParameter("id"));
	        try {
	            Product product = productService.getProductById(productId);
	            request.setAttribute("product", product);
	            request.getRequestDispatcher("WEB-INF/view/product/viewProduct.jsp").forward(request, response);
	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());
	            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	        }
	    }
	    
	    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int productId = Integer.parseInt(request.getParameter("id"));
	        try {
	            Product product = productService.getProductById(productId);
	            request.setAttribute("product", product);
	            request.getRequestDispatcher("WEB-INF/view/product/editProduct.jsp").forward(request, response);
	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());
	            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	        }
	    }
	    
	    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int productId = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        String priceStr = request.getParameter("price");
	        String description = request.getParameter("description");
	        String isbn = request.getParameter("isbn");
	        String author = request.getParameter("author");
	        String publisher = request.getParameter("publisher");
	        String publicationDateStr = request.getParameter("publicationDate");
	        
	        // Sanitize inputs
	        name = ValidationUtils.sanitizeString(name);
	        description = ValidationUtils.sanitizeString(description);
	        isbn = ValidationUtils.sanitizeString(isbn);
	        author = ValidationUtils.sanitizeString(author);
	        publisher = ValidationUtils.sanitizeString(publisher);
	        publicationDateStr = ValidationUtils.sanitizeString(publicationDateStr);
	        
	        // Validate input
	        Map<String, String> validationErrors = ValidationUtils.validateProduct(name, description, priceStr, isbn, author, publisher);
	        
	        if (!validationErrors.isEmpty()) {
	            // Validation failed - show errors
	            request.setAttribute("fieldErrors", validationErrors);
	            request.setAttribute("productId", productId);
	            request.setAttribute("name", name);
	            request.setAttribute("price", priceStr);
	            request.setAttribute("description", description);
	            request.setAttribute("isbn", isbn);
	            request.setAttribute("author", author);
	            request.setAttribute("publisher", publisher);
	            request.setAttribute("publicationDate", publicationDateStr);
	            request.getRequestDispatcher("WEB-INF/view/product/editProduct.jsp").forward(request, response);
	            return;
	        }
	        
	        // Parse price
	        double price = Double.parseDouble(priceStr);
	        
	        Date publicationDate = null;
	        if (publicationDateStr != null && !publicationDateStr.trim().isEmpty()) {
	            try {
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	                publicationDate = dateFormat.parse(publicationDateStr);
	            } catch (ParseException e) {
	                // Handle date parsing error
	                request.setAttribute("error", "Invalid publication date format. Use YYYY-MM-DD.");
	                request.setAttribute("productId", productId);
	                request.setAttribute("name", name);
	                request.setAttribute("price", priceStr);
	                request.setAttribute("description", description);
	                request.setAttribute("isbn", isbn);
	                request.setAttribute("author", author);
	                request.setAttribute("publisher", publisher);
	                request.setAttribute("publicationDate", publicationDateStr);
	                request.getRequestDispatcher("WEB-INF/view/product/editProduct.jsp").forward(request, response);
	                return;
	            }
	        }
	        
	        Product product = new Product(productId, name, description, price, isbn, author, publisher, publicationDate);
	        
	        try {
	            productService.updateProduct(product);
	            response.sendRedirect("product?action=list");
	        } catch (SQLException e) {
	            request.setAttribute("error", "Database error: " + e.getMessage());
	            request.setAttribute("productId", productId);
	            request.setAttribute("name", name);
	            request.setAttribute("price", priceStr);
	            request.setAttribute("description", description);
	            request.setAttribute("isbn", isbn);
	            request.setAttribute("author", author);
	            request.setAttribute("publisher", publisher);
	            request.setAttribute("publicationDate", publicationDateStr);
	            request.getRequestDispatcher("WEB-INF/view/product/editProduct.jsp").forward(request, response);
	        }
	    }
	    
	    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int productId = Integer.parseInt(request.getParameter("id"));
	        try {
	            productService.deleteProduct(productId);
	            response.sendRedirect("product?action=list");
	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());
	            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
	        }
	    }
}
