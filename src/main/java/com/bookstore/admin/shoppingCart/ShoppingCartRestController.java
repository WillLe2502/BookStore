package com.bookstore.admin.shoppingCart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.admin.Utility;
import com.bookstore.admin.customer.CustomerService;
import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.exception.CustomerNotFoundException;
import com.bookstore.admin.exception.ShoppingCartException;

@RestController
public class ShoppingCartRestController {
	@Autowired private ShoppingCartService cartService;
	@Autowired private CustomerService customerService;

	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {

		try {
			Customer customer = getAuthenticatedCustomer(request);
			Integer updatedQuantity = cartService.addProduct(productId, quantity, customer);

			return updatedQuantity + " item(s) of this product were added to your shopping cart.";
		} catch (CustomerNotFoundException ex) {
			return "You must login to add this product to cart.";
		} catch (ShoppingCartException ex) {
			return ex.getMessage();
		}

	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) 
			throws CustomerNotFoundException {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		if (email == null) {
			throw new CustomerNotFoundException("No authenticated customer");
		}

		return customerService.getCustomerByEmail(email);
	}
	
	@PostMapping("/cart/update/{bookId}/{quantity}")
	public String updateQuantity(
			@PathVariable("bookId") Integer bookId,
			@PathVariable("quantity") Integer quantity, 
			HttpServletRequest request) {
		try {
			Customer customer = getAuthenticatedCustomer(request);
			float subtotal = cartService.updateQuantity(bookId, quantity, customer);

			return String.valueOf(subtotal);
		} catch (CustomerNotFoundException ex) {
			return "You must login to change quantity of product.";
		}	
	}
	
	@DeleteMapping("/cart/remove/{bookId}")
	public String removeProduct(@PathVariable("bookId") Integer bookId,
			HttpServletRequest request) {
		try {
			Customer customer = getAuthenticatedCustomer(request);
			cartService.removeBook(bookId, customer);

			return "The product has been removed from your shopping cart.";

		} catch (CustomerNotFoundException e) {
			return "You must login to remove product.";
		}
	}
}