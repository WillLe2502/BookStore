package com.bookstore.admin.order;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.admin.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
