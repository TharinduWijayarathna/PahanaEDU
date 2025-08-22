package edu.pahana.service;

import edu.pahana.dao.BillDAO;
import edu.pahana.dao.BillItemDAO;
import edu.pahana.dao.CustomerDAO;
import edu.pahana.dao.ProductDAO;
import edu.pahana.model.Bill;
import edu.pahana.model.BillItem;
import edu.pahana.model.Customer;
import edu.pahana.model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillService {

	private BillDAO billDAO;
	private BillItemDAO billItemDAO;
	private CustomerDAO customerDAO;
	private ProductDAO productDAO;

	public BillService() {
		this.billDAO = new BillDAO();
		this.billItemDAO = new BillItemDAO();
		this.customerDAO = new CustomerDAO();
		this.productDAO = new ProductDAO();
	}

	public boolean createBill(Bill bill) throws SQLException {
		// Validate customer exists
		Customer customer = customerDAO.getCustomerById(bill.getCustomerId());
		if (customer == null) {
			return false;
		}

		// Validate bill items including stock availability (only if items exist)
		if (bill.getItems() != null && !bill.getItems().isEmpty()) {
			if (!validateBillItems(bill.getItems())) {
				return false;
			}
		}

		// Calculate total amount
		bill.calculateTotal();

		// Create bill
		boolean billCreated = billDAO.createBill(bill);

		if (billCreated && bill.getItems() != null && !bill.getItems().isEmpty()) {
			// Set bill ID for all items
			for (BillItem item : bill.getItems()) {
				item.setBillId(bill.getBillId());
			}

			// Create bill items
			boolean itemsCreated = billItemDAO.createBillItems(bill.getItems());

			if (itemsCreated) {
				// Update stock quantities after successful bill creation
				try {
					for (BillItem item : bill.getItems()) {
						productDAO.updateStockQuantity(item.getProductId(), item.getQuantity());
					}
				} catch (Exception e) {
					System.err.println("Failed to update stock quantities: " + e.getMessage());
					// Note: In a production system, you might want to rollback the transaction here
				}
			}

			return itemsCreated;
		}

		return billCreated;
	}

	public Bill getBillById(int billId) throws SQLException {
		return billDAO.getBillById(billId);
	}

	public List<Bill> getBillsByCustomerId(int customerId) throws SQLException {
		return billDAO.getBillsByCustomerId(customerId);
	}

	/**
	 * Gets all bills
	 * 
	 * @return List of all bills
	 * @throws SQLException if a database error occurs
	 */
	public List<Bill> getAllBills() throws SQLException {
		return billDAO.getAllBills();
	}

	/**
	 * Searches bills by customer name, account number, or bill ID
	 * 
	 * @param searchTerm The search term to look for
	 * @return List of matching bills
	 * @throws SQLException if a database error occurs
	 */
	public List<Bill> searchBills(String searchTerm) throws SQLException {
		return billDAO.searchBills(searchTerm);
	}

	/**
	 * Gets paginated bills
	 * 
	 * @param offset The offset for pagination
	 * @param limit  The limit for pagination
	 * @return List of bills for the current page
	 * @throws SQLException if a database error occurs
	 */
	public List<Bill> getBillsPaginated(int offset, int limit) throws SQLException {
		return billDAO.getBillsPaginated(offset, limit);
	}

	/**
	 * Searches bills with pagination
	 * 
	 * @param searchTerm The search term to look for
	 * @param offset     The offset for pagination
	 * @param limit      The limit for pagination
	 * @return List of matching bills for the current page
	 * @throws SQLException if a database error occurs
	 */
	public List<Bill> searchBillsPaginated(String searchTerm, int offset, int limit) throws SQLException {
		return billDAO.searchBillsPaginated(searchTerm, offset, limit);
	}

	/**
	 * Gets the total count of bills
	 * 
	 * @return Total number of bills
	 * @throws SQLException if a database error occurs
	 */
	public int getBillCount() throws SQLException {
		return billDAO.getBillCount();
	}

	/**
	 * Gets the count of bills matching a search term
	 * 
	 * @param searchTerm The search term to look for
	 * @return Count of matching bills
	 * @throws SQLException if a database error occurs
	 */
	public int getBillSearchCount(String searchTerm) throws SQLException {
		return billDAO.getBillSearchCount(searchTerm);
	}

	public boolean updateBillStatus(int billId, String status) throws SQLException {
		// Get bill details before status update
		Bill bill = billDAO.getBillById(billId);
		boolean updated = billDAO.updateBillStatus(billId, status);
		
		// Restore stock if bill is cancelled
		if (updated && "Cancelled".equalsIgnoreCase(status) && bill != null && bill.getItems() != null) {
			for (BillItem item : bill.getItems()) {
				productDAO.restoreStockQuantity(item.getProductId(), item.getQuantity());
			}
		}
		
		return updated;
	}

	public boolean deleteBill(int billId) throws SQLException {
		// Get bill details before deletion
		Bill bill = billDAO.getBillById(billId);
		boolean deleted = billDAO.deleteBill(billId);
		
		// Restore stock if bill had items
		if (deleted && bill != null && bill.getItems() != null) {
			for (BillItem item : bill.getItems()) {
				productDAO.restoreStockQuantity(item.getProductId(), item.getQuantity());
			}
		}
		
		return deleted;
	}

	public Bill createBillFromItems(int customerId, List<BillItem> items, BigDecimal billDiscount) throws SQLException {
		// Get customer information
		Customer customer = customerDAO.getCustomerById(customerId);
		if (customer == null) {
			return null;
		}

		// Create bill
		Bill bill = new Bill(customerId, customer.getName(), customer.getAccountNumber());

		// Validate and set items
		List<BillItem> validItems = new ArrayList<>();
		for (BillItem item : items) {
			Product product = productDAO.getProductById(item.getProductId());
			if (product != null) {
				// Check stock availability
				if (product.getQuantity() >= item.getQuantity()) {
					item.setProductName(product.getName());
					// Keep the unit price from the form (which may include custom pricing)
					if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(BigDecimal.ZERO) == 0) {
						item.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
					}
					item.calculateSubtotal();
					validItems.add(item);
				}
			}
		}

		bill.setItems(validItems);
		bill.calculateTotal();

		// Apply bill-level discount
		if (billDiscount != null && billDiscount.compareTo(BigDecimal.ZERO) > 0) {
			bill.setDiscount(billDiscount);
			BigDecimal discountAmount = bill.getTotalAmount().multiply(billDiscount)
					.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
			bill.setTotalAmount(bill.getTotalAmount().subtract(discountAmount).setScale(2, BigDecimal.ROUND_HALF_UP));
		}

		return bill;
	}

	public String generateBillNumber() throws SQLException {
		// Generate a unique bill number
		List<Bill> allBills = getAllBills();
		int nextNumber = allBills.size() + 1;
		return String.format("BILL%06d", nextNumber);
	}

	public BigDecimal calculateTotalAmount(List<BillItem> items) {
		if (items == null || items.isEmpty()) {
			return BigDecimal.ZERO;
		}

		return items.stream().map(BillItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean validateBillItems(List<BillItem> items) throws SQLException {
		// Empty lists are invalid for validation (must have at least one item)
		if (items == null || items.isEmpty()) {
			return false;
		}

		for (BillItem item : items) {
			Product product = productDAO.getProductById(item.getProductId());
			if (product == null) {
				return false;
			}

			if (item.getQuantity() <= 0) {
				return false;
			}

			// Check stock availability
			if (product.getQuantity() < item.getQuantity()) {
				return false;
			}
		}

		return true;
	}
}