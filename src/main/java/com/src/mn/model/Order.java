package com.src.mn.model;

import java.util.Date;

import com.src.mn.util.annotations.Field;
import com.src.mn.util.annotations.Table;

@Table(name = "orders")
public class Order {
		@Field(name = "id", type = "INT AUTO_INCREMENT", isPrimaryKey = true)
	    private int id; // Order ID

	    @Field(name = "user_id", type = "INT")
	    private int userId; // Customer ID (foreign key to User table)

	    @Field(name = "food_id", type = "INT")
	    private int foodId; // Food ID (foreign key to FoodItem table)

	    @Field(name = "order_date", type = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	    private Date orderDate; // Order date

	    @Field(name = "status", type = "VARCHAR(100)")
	    private String status; // Order status

	    @Field(name = "total_price", type = "DECIMAL(10, 2)")
	    private double totalPrice; // Total price for the order
	    
	    

    // Constructor
    public Order(int id, int userId, int foodId, Date orderDate, String status, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice; // Initialize totalPrice
    }
    public Order(int userId, int foodId, String status, double totalPrice) {
        this.userId = userId;
        this.foodId = foodId;
        this.status = status;
        this.totalPrice = totalPrice; // Initialize totalPrice
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice; // Return the total price for the order
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice; // Set the total price for the order
    }

    // Methods to get Order ID and Customer ID
    public int getOrderId() {
        return this.id; // Return the order ID
    }

    public int getCustomerId() {
        return this.userId; // Return the user ID as the customer ID
    }
}
