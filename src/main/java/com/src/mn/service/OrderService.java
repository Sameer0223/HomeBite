package com.src.mn.service;

import com.src.mn.dao.OrderDAO;
import com.src.mn.model.Cart;  // Import Cart class
import com.src.mn.model.Food;  // Import Food class
import com.src.mn.model.Order;

import java.util.Date;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;
    private Cart cart; // Add a Cart instance

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
        this.cart = new Cart(); // Initialize the cart
    }

    // Method to place an order
    public boolean placeOrder(Order order) {
        return orderDAO.placeOrder(order);
    }

    // Method to get orders by customer ID
    public List<Order> getOrdersByCustomerId(int userId) {
        return orderDAO.getOrdersByCustomerId(userId);
    }

    // Method to get orders by cook ID
    public List<Order> getOrdersByCookId(int cookId) {
        return orderDAO.getOrdersByCookId(cookId);
    }

    // Method to update order status
    public boolean updateOrderStatus(int orderId, String status) {
        return orderDAO.updateOrderStatus(orderId, status);
    }

    // Method to get the cart
    public Cart getCart() {
        return cart; // Return the current cart
    }

    // Method to place an order from the cart
    public boolean placeOrderFromCart(int customerId) {
        double totalPrice = 0; // Initialize total price
        
        // Calculate total price of all items in the cart
        for (Food food : cart.getItems()) {
            totalPrice += food.getPrice(); // Assuming getPrice() returns the price of the food item
        }

        // Place orders for each food item in the cart
        for (Food food : cart.getItems()) {
            Order order = new Order(0, customerId, food.getId(), new Date(), "Pending", totalPrice); // Use current date and total price
            if (!orderDAO.placeOrder(order)) {
                return false; // If any order fails to be placed, return false
            }
        }
        
        cart.clear(); // Clear the cart after placing the order
        return true; // Order placed successfully
    }

}
