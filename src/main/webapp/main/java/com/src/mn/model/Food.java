package com.src.mn.model;


import java.sql.*;

import com.src.mn.util.annotations.Field;
import com.src.mn.util.annotations.Table;
import com.src.mn.util.enums.FoodCategory;

@Table(name = "food")
public class Food {
    
    
    @Field(name = "id", type = "INT AUTO_INCREMENT", isPrimaryKey = true)
    private int id; // Unique identifier for the food item

    @Field(name = "name", type = "VARCHAR(100)", isUnique = true)
    private String name; // Name of the food item

    @Field(name = "description", type = "VARCHAR(255)")
    private String description; // Description of the food item

    @Field(name = "price", type = "DECIMAL(10, 2)")
    private double price; // Price of the food item

    @Field(name = "category", type = "VARCHAR(50)")
    private FoodCategory category; // Category of the food item

    @Field(name = "cook_id", type = "INT")
    private int cookId; // ID of the cook who created this food item (foreign key to User table)

    @Field(name = "time", type = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp time; // Timestamp for when the food item was created
    // Constructor
    public Food(int id, String name, String description, double price, FoodCategory category, int cookId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.cookId = cookId;
    }
    

    public Food() {
		// TODO Auto-generated constructor stub
	}


    public Food(int id, String name, String description, double price, FoodCategory category, int cookId, Timestamp time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.cookId = cookId;
        this.time = time;
    }
	


	


	// Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FoodCategory getCategory() {
        return category;
    }

    public void setCategory(FoodCategory category) {
        this.category = category;
    }

    public int getCookId() {
        return cookId;
    }

    public void setCookId(int cookId) {
        this.cookId = cookId;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", cookId=" + cookId +
                '}';
    }

	public String getTime() {
		System.out.println(time.toString());
		return time.toString();
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}
