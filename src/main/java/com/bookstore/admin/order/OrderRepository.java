package com.bookstore.admin.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.admin.entity.Customer;
import com.bookstore.admin.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query("SELECT DISTINCT o FROM Order o JOIN o.orderDetails od JOIN od.book b "
			+ "WHERE o.customer.id = ?2 "
			+ "AND (b.name LIKE %?1% OR o.status LIKE %?1%)")
	public Page<Order> findAll(String keyword, Integer customerId, Pageable pageable);

	@Query("SELECT o FROM Order o WHERE o.customer.id = ?1")
	public Page<Order> findAll(Integer customerId, Pageable pageable);	

	public Order findByIdAndCustomer(Integer id, Customer customer);
}
