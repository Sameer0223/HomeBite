package com.src.mn.dao;

import com.src.mn.model.Order;

import java.util.List;

public interface OrderDAO {
    boolean placeOrder(Order order);
//    List<Order> getOrdersByUserId(int userId);
	List<Order> getOrdersByCustomerId(int customerId);
	List<Order> getOrdersByCookId(int cookId);
	boolean updateOrderStatus(int orderId, String status);
}
