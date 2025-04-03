package com.src.mn.dao;

import com.src.mn.model.User;
import com.src.mn.util.TableCreator;
import com.src.mn.util.enums.UserRole;
import com.src.mn.util.annotations.Field;

import java.sql.*;

public class UserDAOImpl implements UserDAO {
    private Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
        try {
            // Ensure table is created on startup
            TableCreator.createTableIfNotExists(User.class, conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean saveUser(User user) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole().toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                        UserRole.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registerUser(User user) {
        return saveUser(user);
    }

	@Override
	public boolean authenticateUser(String username, String password, String role) {
		
	        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, username);
	            stmt.setString(2, password);  // For real applications, hash the password
	            stmt.setString(3, role);

	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                return true;  // User is authenticated
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;  // User not found or invalid credentials
	    
	}

	@Override
	public boolean registerUser(String username, String password, String role) {
	    String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        // Set the parameters for the SQL statement
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        stmt.setString(3, role);

	        // Execute the insert statement and check if a row was added
	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	 
	 @Override
	 public boolean isUsernameTaken(String username) {
	     String query = "SELECT COUNT(*) FROM users WHERE username = ?";
	     try (PreparedStatement stmt = conn.prepareStatement(query)) {
	         stmt.setString(1, username);
	         try (ResultSet rs = stmt.executeQuery()) {
	             if (rs.next()) {
	                 return rs.getInt(1) > 0;
	             }
	         }
	     } catch (SQLException e) {
	         e.printStackTrace();
	     }
	     return false;
	 }


	
}
