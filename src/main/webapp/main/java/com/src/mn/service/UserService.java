package com.src.mn.service;

import java.sql.SQLException;

import com.src.mn.dao.UserDAO;
import com.src.mn.model.User;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User authenticateUser(String username, String password) throws SQLException {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean registerUser(User user) {
        return userDAO.saveUser(user);
    }
}
