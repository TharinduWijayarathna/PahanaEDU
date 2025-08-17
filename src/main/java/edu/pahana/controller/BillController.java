package edu.pahana.controller;

import edu.pahana.model.Bill;
import edu.pahana.model.BillItem;
import edu.pahana.model.Customer;
import edu.pahana.model.Product;
import edu.pahana.service.BillService;
import edu.pahana.service.CustomerService;
import edu.pahana.service.ProductService;
import edu.pahana.service.ActivityService;
import edu.pahana.validation.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/bill")
public class BillController extends HttpServlet {

	private BillService billService;
	private CustomerService customerService;
	private ProductService productService;
	private ActivityService activityService;

	@Override
	public void init() throws ServletException {
		billService = new BillService();
		customerService = CustomerService.getInstance();
		productService = ProductService.getInstance();
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
			action = "list";
		}

		switch (action) {
		case "list":
			listBills(request, response);
			break;
		case "view":
			viewBill(request, response);
			break;
		case "create":
			showCreateBillForm(request, response);
			break;
		case "print":
			printBill(request, response);
			break;
		case "delete":
			deleteBill(request, response);
			break;
		default:
			listBills(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "create":
			createBill(request, response);
			break;
		case "update":
			updateBill(request, response);
			break;
		default:
			response.sendRedirect("bill?action=list");
			break;
		}
	}

	private void listBills(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			List<Bill> bills = billService.getAllBills();
			request.setAttribute("bills", bills);
		} catch (Exception e) {
			request.setAttribute("error", "Error loading bills: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/view/bill/listBills.jsp").forward(request, response);
	}

	private void viewBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String billIdStr = request.getParameter("id");
		if (billIdStr != null && !billIdStr.trim().isEmpty()) {
			try {
				int billId = Integer.parseInt(billIdStr);
				Bill bill = billService.getBillById(billId);

				if (bill != null) {
					request.setAttribute("bill", bill);
					request.getRequestDispatcher("/WEB-INF/view/bill/viewBill.jsp").forward(request, response);
				} else {
					request.setAttribute("error", "Bill not found");
					response.sendRedirect("bill?action=list");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid bill ID");
				response.sendRedirect("bill?action=list");
			} catch (Exception e) {
				request.setAttribute("error", "Error loading bill: " + e.getMessage());
				response.sendRedirect("bill?action=list");
			}
		} else {
			response.sendRedirect("bill?action=list");
		}
	}

	private void showCreateBillForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get all customers for dropdown
			List<Customer> customers = customerService.getAllCustomers();
			request.setAttribute("customers", customers);

			// Get all products for dropdown
			List<Product> products = productService.getAllProducts();
			request.setAttribute("products", products);

		} catch (Exception e) {
			request.setAttribute("error", "Error loading form data: " + e.getMessage());
		}

		request.getRequestDispatcher("/WEB-INF/view/bill/createBill.jsp").forward(request, response);
	}

	private void createBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Get and sanitize customer ID
			String customerIdStr = ValidationUtils.sanitizeString(request.getParameter("customerId"));

			// Get items from request
			String[] productIds = request.getParameterValues("productId");
			String[] quantities = request.getParameterValues("quantity");
			String[] unitPrices = request.getParameterValues("unitPrice");

			// Get bill-level discount
			String billDiscountStr = request.getParameter("billDiscount");

			// Validate customer ID
			if (customerIdStr == null || customerIdStr.trim().isEmpty()) {
				request.setAttribute("error", "Please select a customer");
				showCreateBillForm(request, response);
				return;
			}

			int customerId;
			try {
				customerId = Integer.parseInt(customerIdStr);
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid customer selected");
				showCreateBillForm(request, response);
				return;
			}

			if (productIds == null || quantities == null || unitPrices == null || productIds.length == 0) {
				request.setAttribute("error", "Please add at least one item to the invoice");
				showCreateBillForm(request, response);
				return;
			}

			List<BillItem> items = new ArrayList<>();
			Map<String, String> validationErrors = new java.util.HashMap<>();

			for (int i = 0; i < productIds.length; i++) {
				if (productIds[i] != null && !productIds[i].trim().isEmpty() && quantities[i] != null
						&& !quantities[i].trim().isEmpty() && unitPrices[i] != null
						&& !unitPrices[i].trim().isEmpty()) {

					// Validate bill item (no discount validation needed for items)
					Map<String, String> itemErrors = ValidationUtils.validateBillItem(productIds[i], quantities[i],
							unitPrices[i]);

					if (!itemErrors.isEmpty()) {
						validationErrors.putAll(itemErrors);
					} else {
						try {
							int productId = Integer.parseInt(productIds[i]);
							int quantity = Integer.parseInt(quantities[i]);
							double unitPrice = Double.parseDouble(unitPrices[i]);

							Product product = productService.getProductById(productId);
							if (product != null) {
								BillItem item = new BillItem(productId, product.getName(), quantity,
										BigDecimal.valueOf(unitPrice));
								item.calculateSubtotal();
								items.add(item);
							} else {
								validationErrors.put("productId", "Invalid product selected");
							}
						} catch (NumberFormatException e) {
							validationErrors.put("quantity", "Invalid quantity or price value");
						}
					}
				}
			}

			if (!validationErrors.isEmpty()) {
				request.setAttribute("fieldErrors", validationErrors);
				showCreateBillForm(request, response);
				return;
			}

			if (items.isEmpty()) {
				request.setAttribute("error", "Please add at least one valid item to the invoice");
				showCreateBillForm(request, response);
				return;
			}

			// Validate bill discount
			double billDiscount = 0.0;
			if (billDiscountStr != null && !billDiscountStr.trim().isEmpty()) {
				try {
					billDiscount = Double.parseDouble(billDiscountStr);
					if (billDiscount < 0 || billDiscount > 100) {
						validationErrors.put("billDiscount", "Bill discount must be between 0 and 100 percent");
					}
				} catch (NumberFormatException e) {
					validationErrors.put("billDiscount", "Please enter a valid discount percentage");
				}
			}

			if (!validationErrors.isEmpty()) {
				request.setAttribute("fieldErrors", validationErrors);
				showCreateBillForm(request, response);
				return;
			}

			// Create bill
			Bill bill = billService.createBillFromItems(customerId, items, BigDecimal.valueOf(billDiscount));

			if (bill != null && billService.createBill(bill)) {
				// Log bill creation activity
				try {
					HttpSession session = request.getSession(false);
					String username = session != null ? (String) session.getAttribute("username") : "system";
					activityService.logBillCreated(bill.getBillId(), bill.getCustomerName(), username);
				} catch (Exception e) {
					// Log error but don't fail bill creation
					System.err.println("Failed to log bill creation activity: " + e.getMessage());
				}

				request.setAttribute("success", "Bill created successfully");
				response.sendRedirect("bill?action=view&id=" + bill.getBillId());
			} else {
				request.setAttribute("error", "Failed to create bill");
				showCreateBillForm(request, response);
			}

		} catch (Exception e) {
			request.setAttribute("error", "An error occurred: " + e.getMessage());
			showCreateBillForm(request, response);
		}
	}

	private void updateBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String billIdStr = request.getParameter("billId");
		String status = request.getParameter("status");

		// Sanitize inputs
		billIdStr = ValidationUtils.sanitizeString(billIdStr);
		status = ValidationUtils.sanitizeString(status);

		// Validate input
		Map<String, String> validationErrors = ValidationUtils.validateBillStatusUpdate(billIdStr, status);

		if (!validationErrors.isEmpty()) {
			// Validation failed - show errors
			request.setAttribute("fieldErrors", validationErrors);
			request.setAttribute("billId", billIdStr);
			request.setAttribute("status", status);

			// Redirect back to the bill view page with errors
			if (billIdStr != null) {
				try {
					int billId = Integer.parseInt(billIdStr);
					response.sendRedirect("bill?action=view&id=" + billId);
				} catch (NumberFormatException e) {
					response.sendRedirect("bill?action=list");
				}
			} else {
				response.sendRedirect("bill?action=list");
			}
			return;
		}

		try {
			int billId = Integer.parseInt(billIdStr);
			boolean updated = billService.updateBillStatus(billId, status);

			if (updated) {
				request.setAttribute("success", "Bill status updated successfully");
			} else {
				request.setAttribute("error", "Failed to update bill status");
			}

			response.sendRedirect("bill?action=view&id=" + billId);

		} catch (NumberFormatException e) {
			request.setAttribute("error", "Invalid bill ID");
			response.sendRedirect("bill?action=list");
		} catch (Exception e) {
			request.setAttribute("error", "Error updating bill: " + e.getMessage());
			response.sendRedirect("bill?action=list");
		}
	}

	private void deleteBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String billIdStr = request.getParameter("id");

		if (billIdStr != null) {
			try {
				int billId = Integer.parseInt(billIdStr);
				boolean deleted = billService.deleteBill(billId);

				if (deleted) {
					request.setAttribute("success", "Bill deleted successfully");
				} else {
					request.setAttribute("error", "Failed to delete bill");
				}

			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid bill ID");
			} catch (Exception e) {
				request.setAttribute("error", "Error deleting bill: " + e.getMessage());
			}
		}

		response.sendRedirect("bill?action=list");
	}

	private void printBill(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String billIdStr = request.getParameter("id");

		if (billIdStr != null) {
			try {
				int billId = Integer.parseInt(billIdStr);
				Bill bill = billService.getBillById(billId);

				if (bill != null) {
					request.setAttribute("bill", bill);
					request.getRequestDispatcher("/WEB-INF/view/bill/printBill.jsp").forward(request, response);
				} else {
					request.setAttribute("error", "Bill not found");
					response.sendRedirect("bill?action=list");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("error", "Invalid bill ID");
				response.sendRedirect("bill?action=list");
			} catch (Exception e) {
				request.setAttribute("error", "Error loading bill for printing: " + e.getMessage());
				response.sendRedirect("bill?action=list");
			}
		} else {
			response.sendRedirect("bill?action=list");
		}
	}
}