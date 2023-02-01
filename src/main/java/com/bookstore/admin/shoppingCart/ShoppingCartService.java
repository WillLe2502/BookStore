package com.bookstore.admin.shoppingCart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.book.BookRepository;
import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.entity.book.Book;
import com.bookstore.admin.exception.ShoppingCartException;

@Service
@Transactional
public class ShoppingCartService {

	@Autowired private CartItemRepository cartRepo;
	@Autowired private BookRepository bookRepo;

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
	
	public List<CartItem> listCartItems(Customer customer) {
		return cartRepo.findByCustomer(customer);
	}
	
	public float updateQuantity(Integer bookId, Integer quantity, Customer customer) {
		cartRepo.updateQuantity(quantity, customer.getId(), bookId);
		Book book = bookRepo.findById(bookId).get();
		float subtotal = book.getDiscountPrice() * quantity;
		return subtotal;
	}
	
	public void removeBook(Integer bookId, Customer customer) {
		cartRepo.deleteByCustomerAndProduct(customer.getId(), bookId);
	}
	
	public void deleteByCustomer(Customer customer) {
		cartRepo.deleteByCustomer(customer.getId());
	}
}