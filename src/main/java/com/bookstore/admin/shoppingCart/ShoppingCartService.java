package com.bookstore.admin.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.Book;
import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.exception.ShoppingCartException;

@Service
public class ShoppingCartService {

	@Autowired private CartItemRepository cartRepo;

	public Integer addProduct(Integer bookId, Integer quantity, Customer customer) 
			throws ShoppingCartException {
		Integer updatedQuantity = quantity;
		Book book = new Book(bookId);

		CartItem cartItem = cartRepo.findByCustomerAndBook(customer, book);

		if (cartItem != null) {
			updatedQuantity = cartItem.getQuantity() + quantity;

			if (updatedQuantity > 5) {
				throw new ShoppingCartException("Could not add more " + quantity + " item(s)"
						+ " because there's already " + cartItem.getQuantity() + " item(s) "
						+ "in your shopping cart. Maximum allowed quantity is 5.");
			}
		} else {
			cartItem = new CartItem();
			cartItem.setCustomer(customer);
			cartItem.setBook(book);
		}

		cartItem.setQuantity(updatedQuantity);

		cartRepo.save(cartItem);

		return updatedQuantity;
	}
}