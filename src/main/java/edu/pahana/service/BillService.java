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
            return billItemDAO.createBillItems(bill.getItems());
        }
        
        return billCreated;
    }
    
    public Bill getBillById(int billId) throws SQLException {
        return billDAO.getBillById(billId);
    }
    
    public List<Bill> getBillsByCustomerId(int customerId) throws SQLException {
        return billDAO.getBillsByCustomerId(customerId);
    }
    
    public List<Bill> getAllBills() throws SQLException {
        return billDAO.getAllBills();
    }
    
    public boolean updateBillStatus(int billId, String status) throws SQLException {
        return billDAO.updateBillStatus(billId, status);
    }
    
    public boolean deleteBill(int billId) throws SQLException {
        return billDAO.deleteBill(billId);
    }
    
    public Bill createBillFromItems(int customerId, List<BillItem> items) throws SQLException {
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
                item.setProductName(product.getName());
                item.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
                item.calculateSubtotal();
                validItems.add(item);
            }
        }
        
        bill.setItems(validItems);
        bill.calculateTotal();
        
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
        
        return items.stream()
                .map(BillItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public boolean validateBillItems(List<BillItem> items) throws SQLException {
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
        }
        
        return true;
    }
} 