package edu.pahana.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Bill {
    private int billId;
    private int customerId;
    private String customerName;
    private String accountNumber;
    private LocalDateTime billDate;
    private BigDecimal totalAmount;
    private String status;
    private List<BillItem> items;
    
    // Default constructor
    public Bill() {
        this.billDate = LocalDateTime.now();
        this.status = "pending";
        this.totalAmount = BigDecimal.ZERO;
    }
    
    // Constructor with customer info
    public Bill(int customerId, String customerName, String accountNumber) {
        this();
        this.customerId = customerId;
        this.customerName = customerName;
        this.accountNumber = accountNumber;
    }
    
    // Getters and Setters
    public int getBillId() {
        return billId;
    }
    
    public void setBillId(int billId) {
        this.billId = billId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    public LocalDateTime getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<BillItem> getItems() {
        return items;
    }
    
    public void setItems(List<BillItem> items) {
        this.items = items;
    }
    
    // Helper method to calculate total
    public void calculateTotal() {
        if (items != null) {
            this.totalAmount = items.stream()
                .map(BillItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "billId=" + billId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", billDate=" + billDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
} 