-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS pahanaedudb;

-- Use the database
USE pahanaedudb;

-- Create the User table for authentication
CREATE TABLE IF NOT EXISTS User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the Customer table
CREATE TABLE IF NOT EXISTS Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    address TEXT NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the Product table with book-related fields
CREATE TABLE IF NOT EXISTS Product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    isbn VARCHAR(20),
    author VARCHAR(100),
    publisher VARCHAR(100),
    publication_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the Bill table
CREATE TABLE IF NOT EXISTS Bill (
    bill_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

-- Create the BillItem table (for items in each bill)
CREATE TABLE IF NOT EXISTS BillItem (
    bill_item_id INT AUTO_INCREMENT PRIMARY KEY,
    bill_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES Bill(bill_id),
    FOREIGN KEY (product_id) REFERENCES Product(product_id)
);

-- Insert default admin user
INSERT INTO User (username, password, role) VALUES
('admin', 'admin123', 'admin');

-- Insert some sample data for products (books) with new fields
INSERT INTO Product (name, description, price, stock_quantity, isbn, author, publisher, publication_date) VALUES
('Java Programming', 'Comprehensive guide to Java programming', 45.99, 50, '978-0134685991', 'Joshua Bloch', 'Addison-Wesley', '2017-12-15'),
('Data Structures and Algorithms', 'Essential computer science concepts', 39.99, 30, '978-0262033848', 'Thomas H. Cormen', 'MIT Press', '2009-07-31'),
('Web Development Basics', 'HTML, CSS, and JavaScript fundamentals', 29.99, 40, '978-1118008188', 'Jon Duckett', 'Wiley', '2011-11-08'),
('Database Design', 'Principles of relational database design', 34.99, 25, '978-0321294494', 'C.J. Date', 'Addison-Wesley', '2003-06-15'),
('Python for Beginners', 'Introduction to Python programming', 24.99, 60, '978-1593276034', 'Eric Matthes', 'No Starch Press', '2015-11-01');

-- Insert some sample customers
INSERT INTO Customer (account_number, name, address, telephone) VALUES
('CUST001', 'John Smith', '123 Main St, Colombo', '0771234567'),
('CUST002', 'Mary Johnson', '456 Park Ave, Colombo', '0772345678'),
('CUST003', 'David Williams', '789 Oak Rd, Colombo', '0773456789');