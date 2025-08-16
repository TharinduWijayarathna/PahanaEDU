package edu.pahana.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import edu.pahana.dao.ProductDAO;
import edu.pahana.model.Product;

/**
 * Test class for ProductService
 * Tests all business logic methods with proper mocking
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private Product existingProduct;

    @Before
    public void setUp() {
        // Create test product
        testProduct = new Product();
        testProduct.setProductId(1);
        testProduct.setName("Test Book");
        testProduct.setDescription("A test book for testing");
        testProduct.setPrice(29.99);

        // Create existing product for duplicate tests
        existingProduct = new Product();
        existingProduct.setProductId(2);
        existingProduct.setName("Existing Book");
        existingProduct.setDescription("An existing book");
        existingProduct.setPrice(19.99);
    }

    @Test
    public void testGetInstance_ShouldReturnSameInstance() {
        // Test singleton pattern
        ProductService instance1 = ProductService.getInstance();
        ProductService instance2 = ProductService.getInstance();
        
        assertSame("Should return the same instance", instance1, instance2);
    }

    @Test
    public void testAddProduct_Success() throws SQLException {
        // Arrange
        Product newProduct = new Product(0, "New Book", "A new book", 39.99, "1234567890", "Author", "Publisher", new Date(System.currentTimeMillis()));
        doNothing().when(productDAO).addProduct(newProduct);

        // Act
        productService.addProduct(newProduct);

        // Assert
        verify(productDAO).addProduct(newProduct);
    }

    @Test
    public void testAddProduct_DatabaseError() throws SQLException {
        // Arrange
        Product newProduct = new Product(0, "New Book", "A new book", 39.99, "1234567890", "Author", "Publisher", new Date(System.currentTimeMillis()));
        doThrow(new SQLException("Database error")).when(productDAO).addProduct(newProduct);

        // Act & Assert
        try {
            productService.addProduct(newProduct);
            fail("Should throw SQLException");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
        verify(productDAO).addProduct(newProduct);
    }

    @Test
    public void testGetAllProducts_Success() throws SQLException {
        // Arrange
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(testProduct);
        expectedProducts.add(existingProduct);
        
        when(productDAO.getAllProducts()).thenReturn(expectedProducts);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull("Product list should not be null", result);
        assertEquals("Should return correct number of products", 2, result.size());
        assertTrue("Should contain test product", result.contains(testProduct));
        assertTrue("Should contain existing product", result.contains(existingProduct));
        verify(productDAO).getAllProducts();
    }

    @Test
    public void testGetAllProducts_EmptyList() throws SQLException {
        // Arrange
        List<Product> emptyList = new ArrayList<>();
        when(productDAO.getAllProducts()).thenReturn(emptyList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull("Product list should not be null", result);
        assertTrue("Product list should be empty", result.isEmpty());
        verify(productDAO).getAllProducts();
    }

    @Test
    public void testGetAllProducts_DatabaseError() throws SQLException {
        // Arrange
        when(productDAO.getAllProducts()).thenThrow(new SQLException("Database error"));

        // Act & Assert
        try {
            productService.getAllProducts();
            fail("Should throw SQLException");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
        verify(productDAO).getAllProducts();
    }

    @Test
    public void testGetProductById_Success() throws SQLException {
        // Arrange
        int productId = 1;
        when(productDAO.getProductById(productId)).thenReturn(testProduct);

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull("Product should be found", result);
        assertEquals("Product ID should match", productId, result.getProductId());
        assertEquals("Product name should match", "Test Book", result.getName());
        assertEquals("Product price should match", 29.99, result.getPrice(), 0.01);
        verify(productDAO).getProductById(productId);
    }

    @Test
    public void testGetProductById_NotFound() throws SQLException {
        // Arrange
        int productId = 999;
        when(productDAO.getProductById(productId)).thenReturn(null);

        // Act
        Product result = productService.getProductById(productId);

        // Assert
        assertNull("Product should not be found", result);
        verify(productDAO).getProductById(productId);
    }

    @Test
    public void testGetProductById_DatabaseError() throws SQLException {
        // Arrange
        int productId = 1;
        when(productDAO.getProductById(productId)).thenThrow(new SQLException("Database error"));

        // Act & Assert
        try {
            productService.getProductById(productId);
            fail("Should throw SQLException");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
        verify(productDAO).getProductById(productId);
    }

    @Test
    public void testUpdateProduct_Success() throws SQLException {
        // Arrange
        Product productToUpdate = new Product(1, "Updated Book", "Updated description", 49.99, "1234567890", "Author", "Publisher", new Date(System.currentTimeMillis()));
        when(productDAO.updateProduct(productToUpdate)).thenReturn(true);

        // Act
        boolean result = productService.updateProduct(productToUpdate);

        // Assert
        assertTrue("Update should succeed", result);
        verify(productDAO).updateProduct(productToUpdate);
    }

    @Test
    public void testUpdateProduct_Failure() throws SQLException {
        // Arrange
        Product productToUpdate = new Product(999, "Nonexistent Book", "Description", 29.99, "1234567890", "Author", "Publisher", new Date(System.currentTimeMillis()));
        when(productDAO.updateProduct(productToUpdate)).thenReturn(false);

        // Act
        boolean result = productService.updateProduct(productToUpdate);

        // Assert
        assertFalse("Update should fail", result);
        verify(productDAO).updateProduct(productToUpdate);
    }

    @Test
    public void testUpdateProduct_DatabaseError() throws SQLException {
        // Arrange
        Product productToUpdate = new Product(1, "Test Book", "Description", 29.99, "1234567890", "Author", "Publisher", new Date(System.currentTimeMillis()));
        when(productDAO.updateProduct(productToUpdate)).thenThrow(new SQLException("Database error"));

        // Act & Assert
        try {
            productService.updateProduct(productToUpdate);
            fail("Should throw SQLException");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
        verify(productDAO).updateProduct(productToUpdate);
    }

    @Test
    public void testDeleteProduct_Success() throws SQLException {
        // Arrange
        int productId = 1;
        when(productDAO.deleteProduct(productId)).thenReturn(true);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertTrue("Delete should succeed", result);
        verify(productDAO).deleteProduct(productId);
    }

    @Test
    public void testDeleteProduct_Failure() throws SQLException {
        // Arrange
        int productId = 999;
        when(productDAO.deleteProduct(productId)).thenReturn(false);

        // Act
        boolean result = productService.deleteProduct(productId);

        // Assert
        assertFalse("Delete should fail", result);
        verify(productDAO).deleteProduct(productId);
    }

    @Test
    public void testDeleteProduct_DatabaseError() throws SQLException {
        // Arrange
        int productId = 1;
        when(productDAO.deleteProduct(productId)).thenThrow(new SQLException("Database error"));

        // Act & Assert
        try {
            productService.deleteProduct(productId);
            fail("Should throw SQLException");
        } catch (SQLException e) {
            assertEquals("Database error", e.getMessage());
        }
        verify(productDAO).deleteProduct(productId);
    }

    // Note: ProductService doesn't have search or category methods in the current implementation
    // These tests are removed as they reference non-existent methods
}
