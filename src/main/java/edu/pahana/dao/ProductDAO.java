package edu.pahana.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.pahana.model.Product;

public class ProductDAO {
    public void addProduct(Product product) throws SQLException {
        String query = "INSERT INTO Product (name, price, description, isbn, author, publisher, publication_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getIsbn());
        statement.setString(5, product.getAuthor());
        statement.setString(6, product.getPublisher());
        if (product.getPublicationDate() != null) {
            statement.setDate(7, new Date(product.getPublicationDate().getTime()));
        } else {
            statement.setNull(7, java.sql.Types.DATE);
        }
        statement.executeUpdate();
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";

        Connection connection = DBConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) 
        {
        	int id = resultSet.getInt("product_id");
        	String name = resultSet.getString("name");
        	double price = resultSet.getDouble("price");
        	String desc = resultSet.getString("description");
        	String isbn = resultSet.getString("isbn");
        	String author = resultSet.getString("author");
        	String publisher = resultSet.getString("publisher");
        	Date pubDate = resultSet.getDate("publication_date");
        	
        	Product product = new Product(id, name, desc, price, isbn, author, publisher, pubDate);
        	products.add(product);
        }

        return products;
    }
    
    public Product getProductById(int productId) throws SQLException {
        String query = "SELECT * FROM Product WHERE product_id = ?";
        Product product = null;
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            String description = resultSet.getString("description");
            String isbn = resultSet.getString("isbn");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            Date pubDate = resultSet.getDate("publication_date");
            
            product = new Product(productId, name, description, price, isbn, author, publisher, pubDate);
        }
        
        return product;
    }
    
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE Product SET name = ?, price = ?, description = ?, isbn = ?, author = ?, publisher = ?, publication_date = ? WHERE product_id = ?";
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, product.getName());
        statement.setDouble(2, product.getPrice());
        statement.setString(3, product.getDescription());
        statement.setString(4, product.getIsbn());
        statement.setString(5, product.getAuthor());
        statement.setString(6, product.getPublisher());
        if (product.getPublicationDate() != null) {
            statement.setDate(7, new Date(product.getPublicationDate().getTime()));
        } else {
            statement.setNull(7, java.sql.Types.DATE);
        }
        statement.setInt(8, product.getProductId());
        
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    }
    
    public boolean deleteProduct(int productId) throws SQLException {
        String query = "DELETE FROM Product WHERE product_id = ?";
        
        Connection connection = DBConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, productId);
        
        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;
    }
}