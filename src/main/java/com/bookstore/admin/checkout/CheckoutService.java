package com.bookstore.admin.checkout;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.ShippingRate;
import com.bookstore.admin.entity.book.Book;

@Service
public class CheckoutService {
	private static final int DIM_DIVISOR = 139;

	public CheckoutInfo prepareCheckout(List<CartItem> cartItems, ShippingRate shippingRate) {
		CheckoutInfo checkoutInfo = new CheckoutInfo();

		float productCost = calculateProductCost(cartItems);
		float productTotal = calculateProductTotal(cartItems);
		float shippingCostTotal = calculateShippingCost(cartItems, shippingRate);
		float paymentTotal = productTotal + shippingCostTotal;

		checkoutInfo.setProductCost(productCost);
		checkoutInfo.setProductTotal(productTotal);
		checkoutInfo.setShippingCostTotal(shippingCostTotal);
		checkoutInfo.setPaymentTotal(paymentTotal);

		checkoutInfo.setDeliverDays(shippingRate.getDays());
		checkoutInfo.setCodSupported(shippingRate.isCodSupported());

		return checkoutInfo;
	}

	private float calculateShippingCost(List<CartItem> cartItems, ShippingRate shippingRate) {
		float shippingCostTotal = 0.0f;

		for (CartItem item : cartItems) {
			Book book = item.getBook();
			float dimWeight = (book.getLength()/1000 * book.getWidth()/1000 * book.getHeight()/1000) / DIM_DIVISOR;
			float finalWeight = book.getWeight()/1000 > dimWeight ? book.getWeight()/1000 : dimWeight;
			float shippingCost = finalWeight * item.getQuantity() * shippingRate.getRate();

			item.setShippingCost(shippingCost);

			shippingCostTotal += shippingCost;
		}

		return shippingCostTotal;
	}

	private float calculateProductTotal(List<CartItem> cartItems) {
		float total = 0.0f;

		for (CartItem item : cartItems) {
			total += item.getSubtotal();
		}

		return total;
	}

	private float calculateProductCost(List<CartItem> cartItems) {
		float cost = 0.0f;

		for (CartItem item : cartItems) {
			cost += item.getQuantity() * item.getBook().getCost();
		}

		return cost;
	}
}