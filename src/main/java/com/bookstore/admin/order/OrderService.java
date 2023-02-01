package com.bookstore.admin.order;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.checkout.CheckoutInfo;
import com.bookstore.admin.entity.Address;
import com.bookstore.admin.entity.CartItem;
import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.entity.book.Book;
import com.bookstore.admin.entity.order.Order;
import com.bookstore.admin.entity.order.OrderDetail;
import com.bookstore.admin.entity.order.OrderStatus;
import com.bookstore.admin.entity.order.PaymentMethod;

@Service
public class OrderService {

	@Autowired private OrderRepository repo;

	public Order createOrder(Customer customer, Address address, List<CartItem> cartItems,
			PaymentMethod paymentMethod, CheckoutInfo checkoutInfo) {
		Order newOrder = new Order();
		newOrder.setOrderTime(new Date());
		newOrder.setStatus(OrderStatus.NEW);
		newOrder.setCustomer(customer);
		newOrder.setProductCost(checkoutInfo.getProductCost());
		newOrder.setSubtotal(checkoutInfo.getProductTotal());
		newOrder.setShippingCost(checkoutInfo.getShippingCostTotal());
		newOrder.setTax(0.0f);
		newOrder.setTotal(checkoutInfo.getPaymentTotal());
		newOrder.setPaymentMethod(paymentMethod);
		newOrder.setDeliverDays(checkoutInfo.getDeliverDays());
		newOrder.setDeliverDate(checkoutInfo.getDeliverDate());

		if (address == null) {
			newOrder.copyAddressFromCustomer();
		} else {
			newOrder.copyShippingAddress(address);
		}

		Set<OrderDetail> orderDetails = newOrder.getOrderDetails();

		for (CartItem cartItem : cartItems) {
			Book book = cartItem.getBook();

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(newOrder);
			orderDetail.setBook(book);
			orderDetail.setQuantity(cartItem.getQuantity());
			orderDetail.setUnitPrice(book.getDiscountPrice());
			orderDetail.setProductCost(book.getCost() * cartItem.getQuantity());
			orderDetail.setSubtotal(cartItem.getSubtotal());
			orderDetail.setShippingCost(cartItem.getShippingCost());

			orderDetails.add(orderDetail);
		}


		return repo.save(newOrder);
	}
}