package com.src.mn.dao;

import java.sql.SQLException;

import com.src.mn.model.User;

public interface UserDAO {
    boolean saveUser(User user);
    
    User getUserByUsernameAndPassword(String username, String password) throws SQLException;
    boolean registerUser(User user) throws SQLException;

	boolean authenticateUser(String username, String password, String role);

	boolean registerUser(String username, String password, String role);

	boolean isUsernameTaken(String username);

	
}
