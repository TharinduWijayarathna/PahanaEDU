package edu.pahana.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Utility class for input validation using only Java standard libraries.
 * Provides validation methods for common input types.
 */
public class ValidationUtils {

	// Regex patterns for validation
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9\\s\\-\\(\\)]{7,15}$");
	private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[A-Za-z0-9]{3,20}$");
	private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z0-9_]{3,20}$");
	private static final Pattern ISBN_PATTERN = Pattern.compile(
			"^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

	/**
	 * Validates if a string is not null, not empty, and not just whitespace
	 */
	public static boolean isNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}

	/**
	 * Validates if a string has a minimum length
	 */
	public static boolean hasMinLength(String value, int minLength) {
		return isNotEmpty(value) && value.trim().length() >= minLength;
	}

	/**
	 * Validates if a string has a maximum length
	 */
	public static boolean hasMaxLength(String value, int maxLength) {
		return value != null && value.length() <= maxLength;
	}

	/**
	 * Validates if a string has a length within a range
	 */
	public static boolean hasLengthBetween(String value, int minLength, int maxLength) {
		return hasMinLength(value, minLength) && hasMaxLength(value, maxLength);
	}

	/**
	 * Validates if a string matches a regex pattern
	 */
	public static boolean matchesPattern(String value, Pattern pattern) {
		return isNotEmpty(value) && pattern.matcher(value.trim()).matches();
	}

	/**
	 * Validates if a string is a valid email
	 */
	public static boolean isValidEmail(String email) {
		return matchesPattern(email, EMAIL_PATTERN);
	}

	/**
	 * Validates if a string is a valid phone number
	 */
	public static boolean isValidPhone(String phone) {
		return matchesPattern(phone, PHONE_PATTERN);
	}

	/**
	 * Validates if a string is a valid account number
	 */
	public static boolean isValidAccountNumber(String accountNumber) {
		return matchesPattern(accountNumber, ACCOUNT_NUMBER_PATTERN);
	}

	/**
	 * Validates if a string is a valid username
	 */
	public static boolean isValidUsername(String username) {
		return matchesPattern(username, USERNAME_PATTERN);
	}

	/**
	 * Validates if a string is a valid ISBN
	 */
	public static boolean isValidISBN(String isbn) {
		return matchesPattern(isbn, ISBN_PATTERN);
	}

	/**
	 * Validates if a number is positive
	 */
	public static boolean isPositive(double value) {
		return value > 0;
	}

	/**
	 * Validates if a number is positive or zero
	 */
	public static boolean isPositiveOrZero(double value) {
		return value >= 0;
	}

	/**
	 * Validates if an integer is positive
	 */
	public static boolean isPositive(int value) {
		return value > 0;
	}

	/**
	 * Validates if an integer is positive or zero
	 */
	public static boolean isPositiveOrZero(int value) {
		return value >= 0;
	}

	/**
	 * Validates if a number is within a range
	 */
	public static boolean isInRange(double value, double min, double max) {
		return value >= min && value <= max;
	}

	/**
	 * Validates if an integer is within a range
	 */
	public static boolean isInRange(int value, int min, int max) {
		return value >= min && value <= max;
	}

	/**
	 * Validates if a string contains only letters and spaces
	 */
	public static boolean containsOnlyLettersAndSpaces(String value) {
		return isNotEmpty(value) && value.matches("^[A-Za-z\\s]+$");
	}

	/**
	 * Validates if a string contains only letters, numbers, and common punctuation
	 */
	public static boolean containsOnlySafeCharacters(String value) {
		return isNotEmpty(value) && value.matches("^[A-Za-z0-9\\s\\.,!?@#$%&*()\\-_+=:;\"'<>\\[\\]{}|\\\\/]+$");
	}

	/**
	 * Sanitizes a string by removing potentially dangerous characters
	 */
	public static String sanitizeString(String value) {
		if (value == null) {
			return null;
		}
		// Remove script tags and other potentially dangerous content
		return value.replaceAll("<script[^>]*>.*?</script>", "").replaceAll("<[^>]*>", "").replaceAll("javascript:", "")
				.replaceAll("on\\w+\\s*=", "").trim();
	}

	/**
	 * Validates a customer object
	 */
	public static Map<String, String> validateCustomer(String accountNumber, String name, String address,
			String telephone) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(accountNumber)) {
			errors.put("accountNumber", "Account number is required");
		} else if (!isValidAccountNumber(accountNumber)) {
			errors.put("accountNumber",
					"Account number must be 3-20 characters long and contain only letters and numbers");
		}

		if (!isNotEmpty(name)) {
			errors.put("name", "Name is required");
		} else if (!hasLengthBetween(name, 2, 100)) {
			errors.put("name", "Name must be between 2 and 100 characters");
		} else if (!containsOnlyLettersAndSpaces(name)) {
			errors.put("name", "Name can only contain letters and spaces");
		}

		if (!isNotEmpty(address)) {
			errors.put("address", "Address is required");
		} else if (!hasLengthBetween(address, 5, 255)) {
			errors.put("address", "Address must be between 5 and 255 characters");
		}

		if (!isNotEmpty(telephone)) {
			errors.put("telephone", "Telephone number is required");
		} else if (!isValidPhone(telephone)) {
			errors.put("telephone", "Please enter a valid telephone number");
		}

		return errors;
	}

	/**
	 * Validates a product object
	 */
	public static Map<String, String> validateProduct(String name, String description, String price, String isbn,
			String author, String publisher) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(name)) {
			errors.put("name", "Product name is required");
		} else if (!hasLengthBetween(name, 2, 100)) {
			errors.put("name", "Product name must be between 2 and 100 characters");
		}

		if (isNotEmpty(description) && !hasMaxLength(description, 500)) {
			errors.put("description", "Description must not exceed 500 characters");
		}

		if (!isNotEmpty(price)) {
			errors.put("price", "Price is required");
		} else {
			try {
				double priceValue = Double.parseDouble(price);
				if (!isPositive(priceValue)) {
					errors.put("price", "Price must be greater than 0");
				} else if (!isInRange(priceValue, 0.01, 999999.99)) {
					errors.put("price", "Price must be between 0.01 and 999,999.99");
				}
			} catch (NumberFormatException e) {
				errors.put("price", "Please enter a valid price");
			}
		}

		if (isNotEmpty(isbn) && !isValidISBN(isbn)) {
			errors.put("isbn", "Please enter a valid ISBN");
		}

		if (isNotEmpty(author) && !hasLengthBetween(author, 2, 100)) {
			errors.put("author", "Author name must be between 2 and 100 characters");
		}

		if (isNotEmpty(publisher) && !hasLengthBetween(publisher, 2, 100)) {
			errors.put("publisher", "Publisher name must be between 2 and 100 characters");
		}

		return errors;
	}

	/**
	 * Validates a user object
	 */
	public static Map<String, String> validateUser(String username, String password, String role) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(username)) {
			errors.put("username", "Username is required");
		} else if (!isValidUsername(username)) {
			errors.put("username",
					"Username must be 3-20 characters long and contain only letters, numbers, and underscores");
		}

		if (!isNotEmpty(password)) {
			errors.put("password", "Password is required");
		} else if (!hasMinLength(password, 6)) {
			errors.put("password", "Password must be at least 6 characters long");
		}

		if (!isNotEmpty(role)) {
			errors.put("role", "Role is required");
		} else if (!role.equals("admin") && !role.equals("user")) {
			errors.put("role", "Role must be either 'admin' or 'user'");
		}

		return errors;
	}

	/**
	 * Validates login credentials
	 */
	public static Map<String, String> validateLogin(String username, String password) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(username)) {
			errors.put("username", "Username is required");
		}

		if (!isNotEmpty(password)) {
			errors.put("password", "Password is required");
		}

		return errors;
	}

	/**
	 * Validates registration form
	 */
	public static Map<String, String> validateRegistration(String firstName, String lastName, String username,
			String email, String password, String confirmPassword) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(firstName)) {
			errors.put("firstName", "First name is required");
		} else if (!hasLengthBetween(firstName, 2, 50)) {
			errors.put("firstName", "First name must be between 2 and 50 characters");
		} else if (!containsOnlyLettersAndSpaces(firstName)) {
			errors.put("firstName", "First name can only contain letters and spaces");
		}

		if (!isNotEmpty(lastName)) {
			errors.put("lastName", "Last name is required");
		} else if (!hasLengthBetween(lastName, 2, 50)) {
			errors.put("lastName", "Last name must be between 2 and 50 characters");
		} else if (!containsOnlyLettersAndSpaces(lastName)) {
			errors.put("lastName", "Last name can only contain letters and spaces");
		}

		if (!isNotEmpty(username)) {
			errors.put("username", "Username is required");
		} else if (!isValidUsername(username)) {
			errors.put("username",
					"Username must be 3-20 characters long and contain only letters, numbers, and underscores");
		}

		if (!isNotEmpty(email)) {
			errors.put("email", "Email is required");
		} else if (!isValidEmail(email)) {
			errors.put("email", "Please enter a valid email address");
		}

		if (!isNotEmpty(password)) {
			errors.put("password", "Password is required");
		} else if (!hasMinLength(password, 6)) {
			errors.put("password", "Password must be at least 6 characters long");
		}

		if (!isNotEmpty(confirmPassword)) {
			errors.put("confirmPassword", "Please confirm your password");
		} else if (!password.equals(confirmPassword)) {
			errors.put("confirmPassword", "Passwords do not match");
		}

		return errors;
	}

	/**
	 * Validates bill item
	 */
	public static Map<String, String> validateBillItem(String productId, String quantity, String unitPrice) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(productId)) {
			errors.put("productId", "Product is required");
		} else {
			try {
				int productIdValue = Integer.parseInt(productId);
				if (!isPositive(productIdValue)) {
					errors.put("productId", "Invalid product selected");
				}
			} catch (NumberFormatException e) {
				errors.put("productId", "Invalid product selected");
			}
		}

		if (!isNotEmpty(quantity)) {
			errors.put("quantity", "Quantity is required");
		} else {
			try {
				int quantityValue = Integer.parseInt(quantity);
				if (!isPositive(quantityValue)) {
					errors.put("quantity", "Quantity must be greater than 0");
				} else if (!isInRange(quantityValue, 1, 9999)) {
					errors.put("quantity", "Quantity must be between 1 and 9999");
				}
			} catch (NumberFormatException e) {
				errors.put("quantity", "Please enter a valid quantity");
			}
		}

		if (!isNotEmpty(unitPrice)) {
			errors.put("unitPrice", "Unit price is required");
		} else {
			try {
				double unitPriceValue = Double.parseDouble(unitPrice);
				if (!isPositive(unitPriceValue)) {
					errors.put("unitPrice", "Unit price must be greater than 0");
				}
			} catch (NumberFormatException e) {
				errors.put("unitPrice", "Please enter a valid unit price");
			}
		}

		return errors;
	}

	/**
	 * Validates bill status update
	 */
	public static Map<String, String> validateBillStatusUpdate(String billId, String status) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(billId)) {
			errors.put("billId", "Bill ID is required");
		} else {
			try {
				int billIdValue = Integer.parseInt(billId);
				if (!isPositive(billIdValue)) {
					errors.put("billId", "Invalid bill ID");
				}
			} catch (NumberFormatException e) {
				errors.put("billId", "Invalid bill ID format");
			}
		}

		if (!isNotEmpty(status)) {
			errors.put("status", "Status is required");
		} else if (!status.equals("pending") && !status.equals("paid") && !status.equals("cancelled")) {
			errors.put("status", "Status must be either 'pending', 'paid', or 'cancelled'");
		}

		return errors;
	}

	/**
	 * Validates profile update form
	 */
	public static Map<String, String> validateProfileUpdate(String username, String newPassword) {
		Map<String, String> errors = new HashMap<>();

		if (!isNotEmpty(username)) {
			errors.put("username", "Username is required");
		} else if (!isValidUsername(username)) {
			errors.put("username",
					"Username must be 3-20 characters long and contain only letters, numbers, and underscores");
		}

		if (isNotEmpty(newPassword) && !hasMinLength(newPassword, 6)) {
			errors.put("newPassword", "Password must be at least 6 characters long");
		}

		return errors;
	}
}
