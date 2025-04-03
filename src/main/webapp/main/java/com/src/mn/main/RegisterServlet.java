package com.src.mn.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.src.mn.dao.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/home_bite";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "061823@Sameer";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords do not match");
            return;
        }

        Connection conn = null;

        try {
            // Step 1: Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Step 2: Create UserDAO instance
            UserDAO userDAO = new UserDAOImpl(conn);
            PrintWriter out = response.getWriter();
            // Step 3: Check if the username already exists
            if (userDAO.isUsernameTaken(username)) {
            	
                response.sendRedirect("register.jsp?error=Username already taken");
                return;
            }

            // Step 4: Register the user
            boolean isRegistered = userDAO.registerUser(username, password, role);

            if (isRegistered) {
                // Registration successful, redirect to login page
                response.sendRedirect("login.jsp?message=Registration successful, please log in.");
            } else {
                response.sendRedirect("register.jsp?error=Registration failed. Please try again.");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Database connection error.");
        } finally {
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
