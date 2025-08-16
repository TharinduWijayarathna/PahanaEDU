package edu.pahana.dao;

import edu.pahana.model.Bill;
import edu.pahana.model.BillItem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    
    public boolean createBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO Bill (customer_id, bill_date, total_amount, status) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, bill.getCustomerId());
            pstmt.setTimestamp(2, Timestamp.valueOf(bill.getBillDate()));
            pstmt.setBigDecimal(3, bill.getTotalAmount());
            pstmt.setString(4, bill.getStatus());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    bill.setBillId(rs.getInt(1));
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    public Bill getBillById(int billId) throws SQLException {
        String sql = "SELECT b.*, c.name as customer_name, c.account_number " +
                    "FROM Bill b " +
                    "JOIN Customer c ON b.customer_id = c.customer_id " +
                    "WHERE b.bill_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, billId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setCustomerName(rs.getString("customer_name"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                bill.setStatus(rs.getString("status"));
                
                // Load bill items
                bill.setItems(getBillItems(billId));
                
                return bill;
            }
            
        }
        
        return null;
    }
    
    public List<Bill> getBillsByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT b.*, c.name as customer_name, c.account_number " +
                    "FROM Bill b " +
                    "JOIN Customer c ON b.customer_id = c.customer_id " +
                    "WHERE b.customer_id = ? " +
                    "ORDER BY b.bill_date DESC";
        
        List<Bill> bills = new ArrayList<>();
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setCustomerName(rs.getString("customer_name"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                bill.setStatus(rs.getString("status"));
                
                bills.add(bill);
            }
            
        }
        
        return bills;
    }
    
    public List<Bill> getAllBills() throws SQLException {
        String sql = "SELECT b.*, c.name as customer_name, c.account_number " +
                    "FROM Bill b " +
                    "JOIN Customer c ON b.customer_id = c.customer_id " +
                    "ORDER BY b.bill_date DESC";
        
        List<Bill> bills = new ArrayList<>();
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setCustomerId(rs.getInt("customer_id"));
                bill.setCustomerName(rs.getString("customer_name"));
                bill.setAccountNumber(rs.getString("account_number"));
                bill.setBillDate(rs.getTimestamp("bill_date").toLocalDateTime());
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                bill.setStatus(rs.getString("status"));
                
                bills.add(bill);
            }
            
        }
        
        return bills;
    }
    
    public boolean updateBillStatus(int billId, String status) throws SQLException {
        String sql = "UPDATE Bill SET status = ? WHERE bill_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, billId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        }
    }
    
    public boolean deleteBill(int billId) throws SQLException {
        // First delete bill items, then delete the bill
        String deleteItemsSql = "DELETE FROM BillItem WHERE bill_id = ?";
        String deleteBillSql = "DELETE FROM Bill WHERE bill_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                // Delete bill items first
                try (PreparedStatement pstmt = conn.prepareStatement(deleteItemsSql)) {
                    pstmt.setInt(1, billId);
                    pstmt.executeUpdate();
                }
                
                // Delete the bill
                try (PreparedStatement pstmt = conn.prepareStatement(deleteBillSql)) {
                    pstmt.setInt(1, billId);
                    int affectedRows = pstmt.executeUpdate();
                    
                    if (affectedRows > 0) {
                        conn.commit();
                        return true;
                    }
                }
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        
        return false;
    }
    
    private List<BillItem> getBillItems(int billId) throws SQLException {
        String sql = "SELECT bi.*, p.name as product_name " +
                    "FROM BillItem bi " +
                    "JOIN Product p ON bi.product_id = p.product_id " +
                    "WHERE bi.bill_id = ?";
        
        List<BillItem> items = new ArrayList<>();
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, billId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                BillItem item = new BillItem();
                item.setBillItemId(rs.getInt("bill_item_id"));
                item.setBillId(rs.getInt("bill_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getBigDecimal("unit_price"));
                item.setSubtotal(rs.getBigDecimal("subtotal"));
                
                items.add(item);
            }
            
        }
        
        return items;
    }
} 