package com.bookstore.admin.shoppingCart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookstore.admin.Utility;
import com.bookstore.admin.customer.CustomerService;
import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.Customer;

@Controller
public class ShoppingCartController {
	@Autowired private CustomerService customerService;
	@Autowired private ShoppingCartService cartService;

	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request) {
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);

		float estimatedTotal = 0.0F;

		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);

		return "cart/shopping_cart";
	}

	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);				
		return customerService.getCustomerByEmail(email);
	}	
}