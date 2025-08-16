package edu.pahana.dao;

import edu.pahana.model.BillItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillItemDAO {
    
    public boolean createBillItem(BillItem item) throws SQLException {
        String sql = "INSERT INTO BillItem (bill_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, item.getBillId());
            pstmt.setInt(2, item.getProductId());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setBigDecimal(4, item.getUnitPrice());
            pstmt.setBigDecimal(5, item.getSubtotal());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    item.setBillItemId(rs.getInt(1));
                    return true;
                }
            }
            
        }
        
        return false;
    }
    
    public boolean createBillItems(List<BillItem> items) throws SQLException {
        String sql = "INSERT INTO BillItem (bill_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            for (BillItem item : items) {
                pstmt.setInt(1, item.getBillId());
                pstmt.setInt(2, item.getProductId());
                pstmt.setInt(3, item.getQuantity());
                pstmt.setBigDecimal(4, item.getUnitPrice());
                pstmt.setBigDecimal(5, item.getSubtotal());
                
                pstmt.addBatch();
            }
            
            int[] results = pstmt.executeBatch();
            
            // Check if all insertions were successful
            for (int result : results) {
                if (result <= 0) {
                    return false;
                }
            }
            
            return true;
            
        }
    }
    
    public List<BillItem> getBillItemsByBillId(int billId) throws SQLException {
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
    
    public boolean updateBillItem(BillItem item) throws SQLException {
        String sql = "UPDATE BillItem SET quantity = ?, unit_price = ?, subtotal = ? WHERE bill_item_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, item.getQuantity());
            pstmt.setBigDecimal(2, item.getUnitPrice());
            pstmt.setBigDecimal(3, item.getSubtotal());
            pstmt.setInt(4, item.getBillItemId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        }
    }
    
    public boolean deleteBillItem(int billItemId) throws SQLException {
        String sql = "DELETE FROM BillItem WHERE bill_item_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, billItemId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        }
    }
    
    public boolean deleteBillItemsByBillId(int billId) throws SQLException {
        String sql = "DELETE FROM BillItem WHERE bill_id = ?";
        
        try (Connection conn = DBConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, billId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
            
        }
    }
} 