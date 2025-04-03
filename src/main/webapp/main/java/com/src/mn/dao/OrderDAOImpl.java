package com.src.mn.dao;

import com.src.mn.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean placeOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, food_id, order_date, status, total_price) VALUES (?, ?, NOW(), ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getFoodId());
            pstmt.setString(3, order.getStatus());
            pstmt.setDouble(4, order.getTotalPrice());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("food_id"),
                        rs.getTimestamp("order_date"),
                        rs.getString("status"),
                        rs.getDouble("total_price")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByCookId(int cookId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, f.name FROM orders o JOIN food f ON o.food_id = f.id WHERE f.cook_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, cookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("food_id"),
                        rs.getTimestamp("order_date"),
                        rs.getString("status"),
                        rs.getDouble("total_price")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
