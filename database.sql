-- PahanaEDU Billing System Database Schema

-- Create database
CREATE DATABASE IF NOT EXISTS pahanaedudb;
USE pahanaedudb;

-- Users table
CREATE TABLE IF NOT EXISTS User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Customers table
CREATE TABLE IF NOT EXISTS Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products table
CREATE TABLE IF NOT EXISTS Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bills table
CREATE TABLE IF NOT EXISTS Bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(5, 2) DEFAULT 0.00,
    status VARCHAR(20) DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

-- Bill items table
CREATE TABLE IF NOT EXISTS BillItem (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES Bill(bill_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Activities table for tracking system activities
CREATE TABLE IF NOT EXISTS activities (
    activity_id INT AUTO_INCREMENT PRIMARY KEY,
    activity_type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    entity_type VARCHAR(50),
    entity_id INT,
    entity_name VARCHAR(255),
    username VARCHAR(100) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'completed',
    INDEX idx_timestamp (timestamp),
    INDEX idx_activity_type (activity_type),
    INDEX idx_username (username),
    INDEX idx_entity (entity_type, entity_id)
);

-- Insert default admin user (password: admin123)
INSERT INTO User (username, password, role) VALUES
('admin', 'admin123', 'admin');

-- Insert sample customers
INSERT INTO Customer (account_number, name, address, telephone) VALUES
('CUST001', 'John Smith', '123 Main St, Colombo', '0771234567'),
('CUST002', 'Mary Johnson', '456 Park Ave, Colombo', '0772345678'),
('CUST003', 'David Williams', '789 Oak Rd, Colombo', '0773456789');

-- Insert sample products
INSERT INTO Product (name, description, price, stock_quantity) VALUES
('Java Programming', 'Comprehensive guide to Java programming', 45.99, 50),
('Data Structures and Algorithms', 'Essential computer science concepts', 39.99, 30),
('Web Development Basics', 'HTML, CSS, and JavaScript fundamentals', 29.99, 40),
('Database Design', 'Principles of relational database design', 34.99, 25),
('Python for Beginners', 'Introduction to Python programming', 24.99, 60);

-- Insert sample activities
INSERT INTO activities (activity_type, description, entity_type, entity_id, entity_name, username, timestamp) VALUES 
('user_login', 'User logged in', NULL, NULL, NULL, 'admin', NOW() - INTERVAL 1 HOUR),
('customer_added', 'New customer added', 'customer', 1, 'John Smith', 'admin', NOW() - INTERVAL 2 HOUR),
('product_added', 'New product added', 'product', 1, 'Java Programming', 'admin', NOW() - INTERVAL 3 HOUR),
('bill_created', 'New bill generated', 'bill', 1, 'John Smith', 'admin', NOW() - INTERVAL 4 HOUR),
('system_backup', 'System backup completed successfully', NULL, NULL, NULL, 'system', NOW() - INTERVAL 5 HOUR);