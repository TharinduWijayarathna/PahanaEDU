package edu.pahana.model;

import java.util.Date;

/**
 * Product model class representing products in the system. Used for product
 * management and billing.
 */
public class Product {
	private int productId;
	private String name;
	private String description;
	private double price;
	private int quantity; // Stock quantity
	private String isbn;
	private String author;
	private String publisher;
	private Date publicationDate;

	/**
	 * Default constructor
	 */
	public Product() {
	}

	/**
	 * Constructor with all fields
	 */
	public Product(int productId, String name, String description, double price, int quantity, String isbn,
			String author, String publisher, Date publicationDate) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.isbn = isbn;
		this.author = author;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
	}

	/**
	 * Constructor without description
	 */
	public Product(int productId, String name, double price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	/**
	 * Constructor with minimal fields
	 */
	public Product(int productId, double price) {
		this.productId = productId;
		this.price = price;
	}

	// Getters and Setters
	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@Override
	public String toString() {
		return "Product{" + "productId=" + productId + ", name='" + name + '\'' + ", description='" + description + '\''
				+ ", price=" + price + ", quantity=" + quantity + ", isbn='" + isbn + '\'' + ", author='" + author
				+ '\'' + ", publisher='" + publisher + '\'' + ", publicationDate=" + publicationDate + '}';
	}
}