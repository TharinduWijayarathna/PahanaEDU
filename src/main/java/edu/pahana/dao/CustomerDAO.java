package edu.pahana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.pahana.model.Customer;

/**
 * Data Access Object for Customer entity.
 * Handles all database operations related to customers.
 */
public class CustomerDAO {
    
    /**
     * Adds a new customer to the database
     * 
     * @param customer The customer to add
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean addCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customer (account_number, name, address, telephone) VALUES (?, ?, ?, ?)";
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, customer.getAccountNumber());
        statement.setString(2, customer.getName());
        statement.setString(3, customer.getAddress());
        statement.setString(4, customer.getTelephone());
        
        int rowsInserted = statement.executeUpdate();
        return rowsInserted > 0;
    }
    
    /**
     * Gets a customer by ID
     * 
     * @param customerId The customer ID
     * @return Customer object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE customer_id = ?";
        Customer customer = null;
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, customerId);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            customer = new Customer();
            customer.setCustomerId(resultSet.getInt("customer_id"));
            customer.setAccountNumber(resultSet.getString("account_number"));
            customer.setName(resultSet.getString("name"));
            customer.setAddress(resultSet.getString("address"));
            customer.setTelephone(resultSet.getString("telephone"));
            customer.setCreatedAt(resultSet.getTimestamp("created_at"));
            customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        }
        
        return customer;
    }
    
    /**
     * Gets a customer by account number
     * 
     * @param accountNumber The account number
     * @return Customer object if found, null otherwise
     * @throws SQLException if a database error occurs
     */
    public Customer getCustomerByAccountNumber(String accountNumber) throws SQLException {
        String query = "SELECT * FROM Customer WHERE account_number = ?";
        Customer customer = null;
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, accountNumber);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            customer = new Customer();
            customer.setCustomerId(resultSet.getInt("customer_id"));
            customer.setAccountNumber(resultSet.getString("account_number"));
            customer.setName(resultSet.getString("name"));
            customer.setAddress(resultSet.getString("address"));
            customer.setTelephone(resultSet.getString("telephone"));
            customer.setCreatedAt(resultSet.getTimestamp("created_at"));
            customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
        }
        
        return customer;
    }
    
    /**
     * Gets all customers from the database
     * 
     * @return List of all customers
     * @throws SQLException if a database error occurs
     */
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customer";
        
        Connection connection = DBConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setCustomerId(resultSet.getInt("customer_id"));
            customer.setAccountNumber(resultSet.getString("account_number"));
            customer.setName(resultSet.getString("name"));
            customer.setAddress(resultSet.getString("address"));
            customer.setTelephone(resultSet.getString("telephone"));
            customer.setCreatedAt(resultSet.getTimestamp("created_at"));
            customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            customers.add(customer);
        }
        
        return customers;
    }
    
    /**
     * Updates a customer in the database
     * 
     * @param customer The customer to update
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE Customer SET account_number = ?, name = ?, address = ?, telephone = ? WHERE customer_id = ?";
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, customer.getAccountNumber());
        statement.setString(2, customer.getName());
        statement.setString(3, customer.getAddress());
        statement.setString(4, customer.getTelephone());
        statement.setInt(5, customer.getCustomerId());
        
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    }
    
    /**
     * Deletes a customer from the database
     * 
     * @param customerId The ID of the customer to delete
     * @return true if successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM Customer WHERE customer_id = ?";
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, customerId);
        
        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;
    }
}