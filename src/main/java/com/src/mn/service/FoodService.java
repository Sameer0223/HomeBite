package com.src.mn.service;

import com.src.mn.dao.FoodDAO;
import com.src.mn.model.Food;

import java.util.List;

public class FoodService {
    private FoodDAO foodDAO;

    public FoodService(FoodDAO foodDAO) {
        this.foodDAO = foodDAO;
    }

    // Method to add a food item
    public boolean addFood(Food food) {
        return foodDAO.addFood(food);
    }

    // Method to get a food item by ID
    public Food getFoodById(int foodId) {
        return foodDAO.getFoodById(foodId);
    }

    // Method to get all food items by cook ID
    public List<Food> getFoodsByCook(int cookId) {
        return foodDAO.getAllFoodsByCookId(cookId);
    }

    // Method to get all food items
    public List<Food> getAllFoods() {
        return foodDAO.getAllFoods();
    }

    // Method to update a food item
    public boolean updateFood(Food food) {
        return foodDAO.updateFood(food);
    }

    // Method to delete a food item
    public boolean deleteFood(int foodId) {
        return foodDAO.deleteFood(foodId);
    }

    // Method to get food items by category
    public List<Food> getFoodByCategory(String category) {
        return foodDAO.getFoodByCategory(category);
    }
}
