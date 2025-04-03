package com.src.mn.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.src.mn.dao.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/home_bite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "061823@Sameer";

    // Override the doPost method to handle login logic
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data from the login page
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        Connection conn = null;

        try {
            // Step 1: Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            getServletContext().setAttribute("DBConnection", conn);
            System.out.println("Database connected");

            // Step 2: Create DAO objects for user, food, and order
            UserDAO userDAO = new UserDAOImpl(conn);
            FoodDAO foodDAO = new FoodDAOImpl(conn);
            OrderDAO orderDAO = new OrderDAOImpl(conn);

            // Step 3: Authenticate user (check username, password, and role)
            boolean isAuthenticated = userDAO.authenticateUser(username, password, role);

            if (isAuthenticated) {
                // If authentication is successful, get the user_id from the database
                String getUserIdQuery = "SELECT userid FROM users WHERE username = ? AND password = ? AND role = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(getUserIdQuery)) {
                    pstmt.setString(1, username);  // Set the username
                    pstmt.setString(2, password);  // Set the password
                    pstmt.setString(3, role);      // Set the role
                    ResultSet rs = pstmt.executeQuery();  // Execute the query

                    // Check if a result is returned
                    if (rs.next()) {
                        int userId = rs.getInt("userId");  // Get the user_id from the result
                        
                        // Create session and set attributes, including user_id
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);
                        session.setAttribute("user_id", userId);  // Set the user_id in the session

                        // Step 4: Redirect user based on their role
                        if ("cook".equals(role)) {
                        	
                            response.sendRedirect("cookDashboard.jsp"); // Redirect to cook's dashboard
                        } else if ("customer".equals(role)) {
                            response.sendRedirect("customerDashboard.jsp"); // Redirect to customer's dashboard
                        } else {
                            response.sendRedirect("index.jsp?error=Invalid role.");
                        }
                    } else {
                        // If no user found or credentials are incorrect
                        response.sendRedirect("index.jsp?error=Invalid credentials or role.");
                    }
                }

            } else {
                // If authentication fails, set an error message and redirect to login page
                response.sendRedirect("index.jsp?error=Invalid credentials or role.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp?error=Database connection error.");
        } finally {
            // Step 5: Close the database connection
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
