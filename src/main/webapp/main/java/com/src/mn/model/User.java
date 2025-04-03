package com.src.mn.model;

import com.src.mn.util.annotations.Field;
import com.src.mn.util.annotations.Table;
import com.src.mn.util.enums.UserRole;
@Table(name = "users")
public class User {
	
    @Field(name = "id", type = "INT AUTO_INCREMENT", isPrimaryKey = true)
    private int id; // Unique identifier for the user

    @Field(name = "username", type = "VARCHAR(50)", isUnique = true)
    private String username; // Username for the user, unique

    @Field(name = "password", type = "VARCHAR(255)")
    private String password; // Password for the user (should be stored hashed)

    @Field(name = "role", type = "ENUM('COOK', 'CUSTOMER')")
    private UserRole role; // Role of the user, either COOK or CUSTOMER

    // Constructor
    public User(int id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
