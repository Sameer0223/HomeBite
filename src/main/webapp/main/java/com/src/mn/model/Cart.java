package com.src.mn.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Food> foodItems;
    private static double total;
    
    public Cart() {
        this.foodItems = new ArrayList<>();
    }

    // Method to add a food item to the cart
    public void addItem(Food food) {
        foodItems.add(food);
        total+=food.getPrice();
    }

    // Method to remove a food item from the cart by Food object
    public void removeItem(Food food) {
        foodItems.remove(food);
        
    }

    // Method to remove a food item from the cart by Food ID
    public boolean removeItemById(int foodId) {
        Food foodToRemove = getItemById(foodId);
        if (foodToRemove != null) {
        	total-=foodToRemove.getPrice();
            foodItems.remove(foodToRemove);
            return true; // Item successfully removed
        }
        return false; // Item not found
    }

    // Method to get a food item by its ID
    public Food getItemById(int foodId) {
        for (Food food : foodItems) {
            if (food.getId() == foodId) {
                return food; // Item found
            }
        }
        return null; // Item not found
    }

    // Method to retrieve all food items in the cart
    public List<Food> getItems() {
        return foodItems;
    }

    // Method to clear the cart
    public void clear() {
        foodItems.clear();
    }
    public double getTotal() {
        return total;
    }
}
