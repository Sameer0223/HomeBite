package com.src.mn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.src.mn.model.Food;
import com.src.mn.util.TableCreator;
import com.src.mn.util.enums.FoodCategory;

public class FoodDAOImpl implements FoodDAO  {
    private Connection connection;

    public FoodDAOImpl(Connection connection) {
        this.connection = connection;
        try {
            // Ensure table is created on startup
            TableCreator.createTableIfNotExists(Food.class, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public FoodDAOImpl() {
//		
//	}

	@Override
    public boolean addFood(Food food) {
        String sql = "INSERT INTO food (food_name, category, price, description, cook_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, food.getName());
            pstmt.setString(2, food.getCategory().toString()); // Assuming FoodCategory is an Enum
            pstmt.setDouble(3, food.getPrice()); // Convert BigDecimal to double
            pstmt.setString(4, food.getDescription());
            pstmt.setInt(5, food.getCookId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	// In FoodDAOImpl class
		@Override
		public List<Food> getAllFoodsByCookId(int cookId) {
		    List<Food> foods = new ArrayList<>();
		    String query = "SELECT * FROM food WHERE cook_id = ?";
		    try (PreparedStatement stmt = connection.prepareStatement(query)) {
		        stmt.setInt(1, cookId);
		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		                Food food = new Food();
		                food.setId(rs.getInt("id"));
		                food.setName(rs.getString("name"));
		                food.setCategory(FoodCategory.valueOf(rs.getString("category").toUpperCase()));
		                food.setPrice(rs.getDouble("price"));
		                food.setDescription(rs.getString("description"));
		                food.setTime(rs.getTimestamp("time"));  // Timestamp from the database
		                foods.add(food);
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return foods;
		}
		
	@Override
    public List<Food> getAllFoods() {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
            	Food food = new Food(
                	    rs.getInt("id"),
                	    rs.getString("name"),
                	    rs.getString("description"),
                	    rs.getBigDecimal("price").doubleValue(),
                	    FoodCategory.valueOf(rs.getString("category").toUpperCase()),
                	    rs.getInt("cook_id"),
                	    rs.getTimestamp("time") // Timestamp from the database
                	);
                
                foods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }

    // Method to get a food item by ID
    @Override
    public Food getFoodById(int foodId) {
        Food food = null;
        String sql = "SELECT * FROM food WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                food = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price").doubleValue(), // Convert BigDecimal to double
                        FoodCategory.valueOf(rs.getString("category").toUpperCase()), // Convert string to enum
                        rs.getInt("cook_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }
    @Override
    public Food getFoodByCookId(int foodId) {
        Food food = null;
        String sql = "SELECT * FROM food WHERE cook_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                food = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price").doubleValue(), // Convert BigDecimal to double
                        FoodCategory.valueOf(rs.getString("category").toUpperCase()), // Convert string to enum
                        rs.getInt("cook_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }
    @Override
    public boolean updateFood(Food food) {
        String sql = "UPDATE food SET name = ?, category = ?, price = ?, description = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, food.getName());
            pstmt.setString(2, food.getCategory().toString()); // Assuming FoodCategory is an Enum
            pstmt.setDouble(3, food.getPrice()); // Convert BigDecimal to double
            pstmt.setString(4, food.getDescription());
            pstmt.setInt(5, food.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFood(int foodId) {
        String sql = "DELETE FROM food WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Food> getFoodByCategory(String category) {
        List<Food> foods = new ArrayList<>();
        String sql = "SELECT * FROM food WHERE category = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Food food = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("price").doubleValue(),
                        FoodCategory.valueOf(rs.getString("category").toUpperCase()),
                        rs.getInt("cook_id"),
                        rs.getTimestamp("time")
                );
                foods.add(food);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foods;
    }


    @Override
    public boolean addFoodItem(String name, FoodCategory category, double price, String description, int cookId) {
        // SQL to insert a new food item
        String sql = "INSERT INTO food (name, description, price, category, cook_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Convert FoodCategory to String for SQL query
            String categoryName = category.name();  // Convert the enum to a string value

            // Step 1: Insert the food item into the food table
            stmt.setString(1, name);       // food_name
            stmt.setString(2, description);  // description
            stmt.setDouble(3, price);       // price
            stmt.setString(4, categoryName); // category_name
            stmt.setInt(5, cookId);         // cook_id

            int rowsAffected = stmt.executeUpdate();  // Execute the insert statement
            return rowsAffected > 0;  // Return true if at least one row was inserted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if any exception occurs
        }
    }


    
    @Override
    public boolean updateFoodItem(int foodId, String newName, String newCategory, double newPrice, String newDescription) {
        StringBuilder sql = new StringBuilder("UPDATE food SET ");

        // Dynamically build the query based on which fields are provided
        boolean firstField = true;
        if (newName != null && !newName.isEmpty()) {
            sql.append("name = ?");
            firstField = false;
        }
        if (newDescription != null && !newDescription.isEmpty()) {
            if (!firstField) sql.append(", ");
            sql.append("description = ?");
            firstField = false;
        }
        if (newPrice >= 0) { // Check for a valid price
            if (!firstField) sql.append(", ");
            sql.append("price = ?");
            firstField = false;
        }
        if (newCategory != null && !newCategory.isEmpty()) {
            if (!firstField) sql.append(", ");
            sql.append("category = ?");
        }

        sql.append(" WHERE id = ?");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set parameters based on provided values
            if (newName != null && !newName.isEmpty()) {
                statement.setString(paramIndex++, newName);
            }
            if (newDescription != null && !newDescription.isEmpty()) {
                statement.setString(paramIndex++, newDescription);
            }
            if (newPrice >= 0) { // Setting the price as double without null check
                statement.setDouble(paramIndex++, newPrice);
            }
            if (newCategory != null && !newCategory.isEmpty()) {
                statement.setString(paramIndex++, newCategory);
            }

            statement.setInt(paramIndex, foodId);

            // Execute update and check if any row was affected
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteFoodItems(String[] foodIds) {
        if (foodIds == null || foodIds.length == 0) {
            return false;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM food WHERE id IN (");
        for (int i = 0; i < foodIds.length; i++) {
            sql.append("?");
            if (i < foodIds.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            // Bind each foodId to the respective parameter
            for (int i = 0; i < foodIds.length; i++) {
                statement.setInt(i + 1, Integer.parseInt(foodIds[i]));
            }

            // Execute batch delete and check if any rows were affected
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addToCart(int foodId, int userId) {
        String sql = "INSERT INTO cart (user_id, food_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, foodId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Logging error with meaningful message
            System.err.println("Error adding food item with ID " + foodId + " to the cart for user ID " + userId);
            e.printStackTrace(); // or use a logging framework
        }
    }

    @Override
    public List<Food> getCartItems(int userId) {
        List<Food> cartItems = new ArrayList<>();
        String sql = "SELECT f.id, f.name, f.description, f.price, f.category, f.cook_id, f.time " +
                     "FROM food f " +
                     "INNER JOIN orders o ON f.id = o.food_id " +
                     "WHERE o.user_id = ? AND o.status = 'IN_CART'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Food food = new Food();
                food.setId(rs.getInt("id"));
                food.setName(rs.getString("name"));
                food.setDescription(rs.getString("description"));
                food.setPrice(rs.getDouble("price"));
                food.setCategory(FoodCategory.valueOf(rs.getString("category").toUpperCase()));
                food.setCookId(rs.getInt("cook_id"));
                food.setTime(rs.getTimestamp("time"));
                cartItems.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public List<Integer> getAllCookIds() {
        List<Integer> cookIds = new ArrayList<>();
        String query = "SELECT DISTINCT cook_id FROM food";
        try {
			try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			    while (rs.next()) {
			        cookIds.add(rs.getInt("cook_id"));
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return cookIds;
    }

 
}
