package com.src.mn.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.src.mn.dao.FoodDAO;
import com.src.mn.dao.FoodDAOImpl;
import com.src.mn.dao.OrderDAOImpl;
import com.src.mn.model.Food;
import com.src.mn.model.Order;

@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/home_bite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "061823@Sameer";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        System.out.println(mode);  // Assuming userId is set in session during login
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            FoodDAO foodDAO = new FoodDAOImpl(conn);
            OrderDAOImpl orderDAO = new OrderDAOImpl(conn);


            switch (mode) {
                case "viewAllFoodItems":
                    viewAllFoodItems(request, foodDAO);
                    request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
                    break;

                case "searchFoodByCategory":
                    String category = request.getParameter("category");
                    if (category != null && !category.isEmpty()) {
                        List<Food> filteredFoodItems = foodDAO.getFoodByCategory(category);
                        request.setAttribute("filteredFoodItems", filteredFoodItems);
                    }
                    request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
                    break;
                    
                case "viewAllCookIds":
                    List<Integer> cookIds = foodDAO.getAllCookIds();
                    request.setAttribute("cookIds", cookIds);
                    request.getRequestDispatcher("customerDashboard.jsp?mode=searchFoodByCookId").forward(request, response);
                    break;
                
                case "searchFoodByCookId":
                    String cookIdStr = request.getParameter("cookId");
                    if (cookIdStr != null && !cookIdStr.isEmpty()) {
                        int cookId = Integer.parseInt(cookIdStr);
                        List<Food> filteredFoodItems = (List<Food>) foodDAO.getAllFoodsByCookId(cookId);
                        request.setAttribute("filteredFoodItems", filteredFoodItems);
                    }
                    request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
                    break;

                case "viewCart":
                    request.getRequestDispatcher("customerDashboard.jsp").forward(request, response);
                    break;
                 
                case "viewOrders":
                    viewOrdersByUser(request, orderDAO, userId);
                    request.getRequestDispatcher("customerDashboard.jsp?mode=viewOrders").forward(request, response);
                    break;    
                case "Logout":
                	
                	
                	request.getRequestDispatcher("customerDashboard.jsp?mode=Logout").forward(request, response);
                    break;

                default:
                    response.getWriter().write("Invalid action.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database connection error.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        System.out.println(mode); 
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            FoodDAO foodDAO = new FoodDAOImpl(conn);
            OrderDAOImpl orderDAO = new OrderDAOImpl(conn);

            switch (mode) {
                case "addFoodToCart":
                    addFoodToCart(request, session, foodDAO, response);
                    request.getRequestDispatcher("customerDashboard.jsp?mode=viewCart").forward(request, response);	
                    break;

                case "placeOrder":
                    placeOrder(session, response,conn);
                   
                    break;
                case "removeFoodFromCart":
                	removeFoodFromCart(request, session,  response);
                    request.getRequestDispatcher("customerDashboard.jsp?mode=viewCart").forward(request, response);
                    break;
                case "viewCart":
                    
                    request.getRequestDispatcher("customerDashboard.jsp?mode=viewCart").forward(request, response);
                    break;
                case "viewOrders":
                	viewOrdersByUser(request, orderDAO, userId);
                	request.getRequestDispatcher("customerDashboard.jsp?mode=viewOrders").forward(request, response);
                	break;
                case "Logout":
                	
                	
                    response.sendRedirect("login.jsp");
                    break;	
                default:
                    response.getWriter().write("Invalid action.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database connection error.");
        }
    }

    private void viewAllFoodItems(HttpServletRequest request, FoodDAO foodDAO) throws SQLException {
        List<Food> foodItems = foodDAO.getAllFoods();
        request.setAttribute("foodItems", foodItems);
    }

    private void addFoodToCart(HttpServletRequest request, HttpSession session, FoodDAO foodDAO, HttpServletResponse response) throws IOException {
        String foodIdStr = request.getParameter("foodId");
       
        if (foodIdStr != null) {
            int foodId = Integer.parseInt(foodIdStr);
            List<Food> cart = (List<Food>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
            }
            Food food = foodDAO.getFoodById(foodId);
            
            if (food != null) {
                cart.add(food);
                session.setAttribute("cart", cart);
                System.out.println(cart);
                response.getWriter().write("Food item added to cart successfully.");
            } else {
                response.getWriter().write("Food item not found.");
            }
        } else {
            response.getWriter().write("Invalid food ID.");
        }
    }

    private void placeOrder(HttpSession session, HttpServletResponse response, Connection conn) throws IOException {
    	//HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
    	List<Food> cart = (List<Food>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.getWriter().write("Cart is empty.");
            return;
        }
        double totalPrice = cart.stream().mapToDouble(Food::getPrice).sum();

        // Assuming the user ID is 1 for simplicity
       // int userId = 1;
        OrderDAOImpl orderDAO = new OrderDAOImpl(conn);
        for (Food food : cart) {
            Order order = new Order(userId, food.getId(), "Pending", food.getPrice());
            orderDAO.placeOrder(order);
        }
        cart.clear();
        session.setAttribute("cart", cart); // Clear cart after placing the order
        response.getWriter().write("Order placed successfully. Total Price: " + totalPrice);
    }
    
    private void viewOrdersByUser(HttpServletRequest request, OrderDAOImpl orderDAO, int userId) throws SQLException {
        
    	List<Order> orders = orderDAO.getOrdersByCustomerId(userId);
    	System.out.println(userId);
        request.setAttribute("orders", orders);
    }
    private void removeFoodFromCart(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
    	
    	    String cartIndexStr = request.getParameter("cartIndex");

    	    if (cartIndexStr != null) {
    	        int cartIndex = Integer.parseInt(cartIndexStr);
    	        List<Food> cart = (List<Food>) session.getAttribute("cart");

    	        if (cart != null && cartIndex >= 0 && cartIndex < cart.size()) {
    	            // Remove the item at the specified index
    	            cart.remove(cartIndex);

    	            // Update the session with the modified cart
    	            session.setAttribute("cart", cart);

    	            // Send a confirmation message back
    	            response.getWriter().write("Food item removed from cart successfully.");
    	        } else {
    	            response.getWriter().write("Invalid cart index or empty cart.");
    	        }
    	    } else {
    	        response.getWriter().write("Invalid cart index.");
    	    }
    	}


}
