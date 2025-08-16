package edu.pahana.service;

import java.sql.SQLException;
import java.util.List;

import edu.pahana.dao.ProductDAO;
import edu.pahana.model.Product;

public class ProductService {

    private static ProductService instance;
    private ProductDAO productDAO;

    private ProductService() {
        this.productDAO = new ProductDAO();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            synchronized (ProductService.class) {
                if (instance == null) {
                    instance = new ProductService();
                }
            }
        }
        return instance;
    }

    public void addProduct(Product product) throws SQLException {
        productDAO.addProduct(product);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }
    
    public Product getProductById(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }
    
    public boolean updateProduct(Product product) throws SQLException {
        return productDAO.updateProduct(product);
    }
    
    public boolean deleteProduct(int productId) throws SQLException {
        return productDAO.deleteProduct(productId);
    }
}
