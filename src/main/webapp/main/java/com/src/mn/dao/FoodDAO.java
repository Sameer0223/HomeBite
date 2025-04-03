package com.src.mn.dao;
import java.sql.*;
import com.src.mn.model.Food;
import com.src.mn.util.enums.FoodCategory;

import java.util.List;

public interface FoodDAO {
    boolean addFood(Food food); // Adds a new food item to the database

    List<Food> getAllFoodsByCookId(int cookId); // Retrieves all food items for a specific cook

    List<Food> getAllFoods(); // Retrieves all food items from the database

    Food getFoodById(int foodId); // Retrieves a food item by its ID

    boolean updateFood(Food food); // Updates an existing food item

    boolean deleteFood(int foodId); // Deletes a food item based on its ID

    List<Food> getFoodByCategory(String category); // Retrieves food items based on their category

	boolean updateFoodItem(int foodId, String newName, String newCategory, double newPrice, String newDescription);

	boolean deleteFoodItems(String[] foodIds);

	

	boolean addFoodItem(String name, FoodCategory category, double price, String description, int cookId);

	void addToCart(int foodId, int userId);

	List<Food> getCartItems(int userId);

	Food getFoodByCookId(int cookId);

	List<Integer> getAllCookIds();
	
	



	
}
