package com.src.mn.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
import com.src.mn.util.enums.FoodCategory;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/home_bite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "061823@Sameer";
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Initialize the database connection
    	Connection conn = null;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not available.");
            return;
        }

        String mode = request.getParameter("mode");
        request.setAttribute("mode", mode); // Set mode as an attribute
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
    	Integer cookId = (Integer) session.getAttribute("user_id");
    	FoodDAO foodDAO = new FoodDAOImpl(conn);
    	OrderDAOImpl orderDAO = new OrderDAOImpl(conn);
        switch (mode) {
            case "addFoodItem":
                request.getRequestDispatcher("cookDashboard.jsp?mode=addFoodItem").forward(request, response);
                break;
            case "updateFoodItem":
            	System.out.println("get ");
                session.setAttribute("foodItems", foodDAO.getAllFoodsByCookId(cookId));
                request.setAttribute("id",request.getParameter("foodId"));
                request.getRequestDispatcher("cookDashboard.jsp?mode=updateFoodItem").forward(request, response);
                break;
            case "deleteFoodItem":
            	request.setAttribute("foodItems", foodDAO.getAllFoodsByCookId(cookId));
                request.getRequestDispatcher("cookDashboard.jsp?mode=deleteFoodItem").forward(request, response);
                break;
            case "viewOrders":
			try {
				viewOrdersByUser(request, orderDAO, userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                request.getRequestDispatcher("cookDashboard.jsp?mode=viewOrders").forward(request, response);
                break;
            case "updateOrderStatus":
			// Get orders for the specific cook that need status updates
			List<Order> ordersToUpdate = orderDAO.getOrdersByCookId(userId);
			request.setAttribute("ordersToUpdate", ordersToUpdate);
                request.getRequestDispatcher("cookDashboard.jsp?mode=updateOrderStatus").forward(request, response);
                break;
            case "updateForm":
                // Code to update order status can be added here
            	
            	request.setAttribute("foodItems", foodDAO.getAllFoodsByCookId(cookId));
            	
            	request.getRequestDispatcher("cookDashboard.jsp?mode=updateFoodItem").forward(request, response);
                break;
            case "viewAllFoodItems":
                List<Food> foodItems = foodDAO.getAllFoodsByCookId(cookId);
                session.setAttribute("foodItems", foodItems);  // Store the list in session
                request.getRequestDispatcher("cookDashboard.jsp?mode=viewAllFoodItems").forward(request, response);
                break;

            case "Logout":
            
            	
            	request.getRequestDispatcher("cookDashboard.jsp?mode=Logout").forward(request, response);
                break;
            default:
                response.sendRedirect("cookDashboard.jsp");
                break;
        }
    }
 private void viewOrdersByUser(HttpServletRequest request, OrderDAOImpl orderDAO, int userId) throws SQLException {
        
    	List<Order> orders = orderDAO.getOrdersByCookId(userId);
    	System.out.println(userId);
        request.setAttribute("orders", orders);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
            // Initialize the database connection
        	Connection conn = null;
        	try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    		} catch (ClassNotFoundException | SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

        if (conn == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection not available.");
            return;
        }

        String mode = request.getParameter("mode");
        FoodDAO foodDAO = new FoodDAOImpl(conn);
        OrderDAOImpl orderDAO = new OrderDAOImpl(conn);
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("user_id");
        Integer cookId = (Integer) session.getAttribute("user_id");
        System.out.println(mode);
        
        switch (mode) {
            case "addFoodItem":
            	// Retrieve form parameters from the request
            	// Retrieve form parameters from the request
            	String foodName = request.getParameter("foodName");
            	// Replace hyphens with underscores and convert to uppercase to match enum constants
            	String categoryStr = request.getParameter("category").replace("-", "_").toUpperCase();
            	
            	FoodCategory category = FoodCategory.valueOf(categoryStr);

            	  // Convert to FoodCategory enum
            	
            	  // Retrieve the cook_id from session
            	double price = Double.parseDouble(request.getParameter("price"));
            	String description = request.getParameter("description");

            	// Call the method to add the food item
            	boolean isAdded = foodDAO.addFoodItem(foodName, category, price, description, cookId);

            	
            	response.sendRedirect("cookDashboard.jsp?mode=addFoodItem&status=" + (isAdded ? "success" : "error"));


                break;
                
            case "updateFoodItem":
                int foodId = Integer.parseInt(request.getParameter("foodId"));
                String newName = request.getParameter("newName");
                String newCategory = request.getParameter("newCategory").replace("-", "_").toUpperCase();
                double newPrice = Double.parseDouble(request.getParameter("newPrice"));
                String newDescription = request.getParameter("newDescription");
                
            	
                boolean isUpdated = foodDAO.updateFoodItem(foodId, newName, newCategory, newPrice, newDescription);
            	System.out.println("post "+" "+cookId);
            	session.setAttribute("foodItems", foodDAO.getAllFoodsByCookId(cookId));
                response.sendRedirect("cookDashboard.jsp?mode=updateFoodItem&status=" + (true ? "success" : "error"));
                break;
                
            case "deleteFoodItem":
                String foodIds = (String) request.getParameter("foodId");
                boolean isDeleted = foodDAO.deleteFood(Integer.parseInt(foodIds));
                response.sendRedirect("cookDashboard.jsp?mode=deleteFoodItem&status=" + (isDeleted ? "success" : "error"));
                break;
                
            case "viewOrders":
                // Code to fetch and display orders can be added here
			try {
				viewOrdersByUser(request, orderDAO, userId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                response.sendRedirect("cookDashboard.jsp?mode=viewOrders");
                break;
                
            case "updateOrderStatus":
                try {
                    // Retrieve the order ID and new status from the form
                    int orderId = Integer.parseInt(request.getParameter("orderId"));
                    String newStatus = request.getParameter("newStatus");

                    // Update the order status in the database
                    boolean isUpdatedComplete = orderDAO.updateOrderStatus(orderId, newStatus);

                    // Redirect with a success or error status
                    response.sendRedirect("cookDashboard.jsp?mode=updateOrderStatus&status=" + (isUpdatedComplete ? "success" : "error"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendRedirect("cookDashboard.jsp?mode=updateOrderStatus&status=error");
                }
                break;
            case "updateForm":
            	
            	System.out.println((String) request.getParameter("foodIds")+"dhdfgh");
            	request.setAttribute("foodItems", foodDAO.getAllFoodsByCookId(cookId));
                response.sendRedirect("cookDashboard.jsp?mode=updateFoodItem&foodI="+request.getParameter("foodIds"));
                break;
                
            case "Logout":
            	
            	
                response.sendRedirect("login.jsp");
                break;
            default:
                response.sendRedirect("cookDashboard.jsp");
                break;
        }
    }
}
