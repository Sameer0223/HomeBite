<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.src.mn.model.*" %>
<%@ page import="com.src.mn.dao.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cook Dashboard - Home Bite</title>
    <style>
    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

    body {
        font-family: Arial, sans-serif;
        background-image: url('images/loginbackground.png'); /* Path to your background image */
        background-size: cover; /* Ensure background covers the entire screen */
        background-position: center; /* Center the background */
        background-repeat: no-repeat; /* Prevent repeating */
        color: #333;
        min-height: 100vh; /* Ensure body takes full height */
        display: flex;
        flex-direction: row;
        padding: 0;
    }

    /* Wrapper for the sidebar and main content */
    .container {
        display: flex;
        flex-grow: 1;
        flex-direction: row;
        width: 100%;
        height: 100vh;
        overflow: auto; /* Allow scrolling if the content exceeds viewport */
    }

    /* Sidebar */
    .sidebar {
        width: 220px;
        background-color: rgba(0, 0, 0, 0.5);
        color: #fff;
        padding: 20px;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        box-shadow: 3px 0 5px rgba(0, 0, 0, 0.3);
        overflow: auto; /* Allow sidebar to scroll */
    }

    .sidebar h1 {
        font-size: 24px;
        margin-bottom: 20px;
        color: #ff6f1f;
    }

    .sidebar ul {
        list-style: none;
        padding: 0;
    }

    .sidebar ul li {
        margin-bottom: 15px;
    }

    .sidebar ul li a {
        color: #ccc;
        text-decoration: none;
        font-size: 16px;
        display: block;
        padding: 10px;
        border-radius: 4px;
        transition: background-color 0.3s ease, color 0.3s ease;
    }

    .sidebar ul li a:hover, .sidebar ul li a.active {
        background-color: #555;
        color: #fff;
    }

    /* Main Content */
    .main-content {
        margin-left: 240px; /* Offset to account for the sidebar */
        padding: 20px;
        background-color: rgba(255, 255, 255, 0.8);
        width: 100%;
        min-height: 100%; /* Ensure content takes full available height */
        flex-grow: 1;
        overflow: auto; /* Allow main content to scroll */
    }

    .main-content h3 {
        font-size: 20px;
        margin-bottom: 20px;
        color: #333;
    }

    .section {
        display: none;
    }

    .section.active {
        display: block;
    }

    form {
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        margin-bottom: 20px;
    }

    label {
        font-weight: bold;
        display: block;
        margin-top: 15px;
    }

    input[type="text"], input[type="number"], select {
        width: 100%;
        padding: 8px;
        margin-top: 5px;
        margin-bottom: 15px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    button {
        background-color: rgba(255, 95, 31, 1);
        color: #fff;
        border: none;
        padding: 10px 15px;
        font-size: 16px;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    button:hover {
        background-color: rgba(255, 120, 50, 1);
    }

    /* Table for displaying food items */
    .food-table, .orders-table, .update-orders-table {
        width: 100%;
        border-collapse: collapse;
        background-color: #fff;
        margin-top: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 5px;
        overflow: hidden;
    }

    .food-table th, .orders-table th, .update-orders-table th,
    .food-table td, .orders-table td, .update-orders-table td {
        padding: 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .food-table th, .orders-table th, .update-orders-table th {
        background-color: #333;
        color: #fff;
    }

    .food-table tr:hover, .orders-table tr:hover, .update-orders-table tr:hover {
        background-color: #f1f1f1;
    }

    /* Adjust buttons inside the tables */
    .orders-table button, .update-orders-table button {
        background-color: #ff6f1f;
        color: white;
        padding: 5px 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .orders-table button:hover, .update-orders-table button:hover {
        background-color: #ff8c42;
    }
</style>



</head>
<body>
    <!-- Sidebar Navigation -->
    <div class="sidebar">
        <h1>Home Bite</h1>
        <ul>
            <li><a href="MenuServlet?mode=addFoodItem" class="<%= "addFoodItem".equals(request.getAttribute("mode")) ? "active" : "" %>">Add Food Item</a></li>
            <li><a href="MenuServlet?mode=updateFoodItem" class="<%= "updateFoodItem".equals(request.getAttribute("mode")) ? "active" : "" %>">Update Food Item</a></li>
            <li><a href="MenuServlet?mode=viewAllFoodItems" class="<%= "viewAllFoodItems".equals(request.getAttribute("mode")) ? "active" : "" %>">View All Food Items</a></li>
            <li><a href="MenuServlet?mode=deleteFoodItem" class="<%= "deleteFoodItem".equals(request.getAttribute("mode")) ? "active" : "" %>">Delete Food Item</a></li>
            <li><a href="MenuServlet?mode=viewOrders" class="<%= "viewOrders".equals(request.getAttribute("mode")) ? "active" : "" %>">View Orders</a></li>
            <li><a href="MenuServlet?mode=updateOrderStatus" class="<%= "updateOrderStatus".equals(request.getAttribute("mode")) ? "active" : "" %>">Update Order Status</a></li>
            <li><a href="MenuServlet?mode=Logout" class="<%= "Logout".equals(request.getAttribute("mode")) ? "active" : "" %>">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content Area -->
    <div class="main-content">
        <%
            String mode = request.getParameter("mode");
        %>
		<!-- Add Food Item Section -->
        <div id="addFoodItem" class="section <%= "addFoodItem".equals(mode) ? "active" : "" %>">
            <h3>Add Food Item</h3>
            <form action="MenuServlet?mode=addFoodItem" method="post">
                <label for="foodName">Enter food name:</label>
                <input type="text" id="foodName" name="foodName" required>

                <label for="category">Enter category:</label>
                <select id="category" name="category" required>
                    <option value="veg">Veg</option>
                    <option value="non-veg">Non-Veg</option>
                    <option value="soups">Soups</option>
                    <option value="dessert">Dessert</option>
                </select>

                <label for="price">Enter price:</label>
                <input type="number" id="price" name="price" required>

                <label for="description">Enter description:</label>
                <input type="text" id="description" name="description" required>

                <button type="submit">Add Item</button>
            </form>
        </div>
  
  		<!-- Update Food Item Section -->

       <div id="updateFoodItem" class="section <%= "updateFoodItem".equals(mode) ? "active" : "" %>">
        <h3>Update Food Item</h3>
        <form action="MenuServlet?mode=updateFoodItem" method="post">
            <label for="foodId">Select Food Item to Update :</label>
            <select id="foodId" name="foodId">
                <option value="">-- Select Food Item --</option>
                
                <% 
                    // Fetch the list of food items from the request attribute
                    
                    List<Food> UfoodItems = (List<Food>)session.getAttribute("foodItems");
                
                    if (UfoodItems != null) {
                        for (Food food : UfoodItems) {
                %>
                            <option value="<%= food.getId() %>"><%= food.getName() %></option>
                <% 
                
                        }
                    }
                %>
            </select>
           
		            <label for="newName">Enter new food name:</label>
		            <input type="text" id="newName" name="newName" >
		    
		            <label for="newCategory">Enter new category :</label>
		            <select name="newCategory">
		        <option value="VEG">VEG</option>
		        <option value="NON_VEG">NON_VEG</option>
		        <option value="DESSERT">DESSERT</option>
		        <option value="SOUP">SOUP</option>
		    </select>
    
    
            <label for="newPrice">Enter new price :</label>
            <input type="number" id="newPrice" name="newPrice" >
    
            <label for="newDescription">Enter new description :</label>
            <input type="text" id="newDescription" name="newDescription" >
    
            <button type="submit">Update Item</button>
        </form>
    </div>
        <!-- View All Food Items Section -->
        <div id="viewAllFoodItems" class="section <%= "viewAllFoodItems".equals(mode) ? "active" : "" %>">
            <h3>View All Food Items</h3>
            <table class="food-table">
                <thead>
                    <tr>
                        <th>Food ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Description</th>
                        <th>Time </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Food> foodItems = (List<Food>) session.getAttribute("foodItems");
                        if (foodItems != null && !foodItems.isEmpty()) {
                            for (Food food : foodItems) {
                    %>
                    <tr>
                        <td><%= food.getId() %></td>
                        <td><%= food.getName() %></td>
                        <td><%= food.getCategory() %></td>
                        <td>Rs. <%= food.getPrice() %></td>
                        <td><%= food.getDescription() %></td>
                        <td><%= food.getTime() %></td>
                    </tr>
                    <% 
                            }
                        } else { 
                    %>
                    <tr>
                        <td colspan="5" style="text-align:center;">No food items found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
		
		<!-- Delete Food Item Section -->
		<div id="deleteFoodItem"
			class="section <%="deleteFoodItem".equals(mode) ? "active" : ""%>">
			<h3>Delete Food Item</h3>
			<form action="MenuServlet?mode=deleteFoodItem" method="post">
				<select id="foodId" name="foodId">
					<!-- Make sure there are no [] here -->
					<option value="">-- Select Food Item --</option>
					<%
					// Fetch the list of food items from the request attribute
					List<Food> DfoodItems = (List<Food>) request.getAttribute("foodItems");

					if (DfoodItems != null) {
						for (Food food : DfoodItems) {
					%>
					<option value="<%=food.getId()%>"><%=food.getName()%></option>

					<%
					}
					}
					%>
				</select>
				<button type="submit">Delete Selected</button>
			</form>
		</div>
		
	<!-- viewOrders Section -->
		<div id="viewOrders" class="section <%= "viewOrders".equals(mode) ? "active" : "" %>">
    <h3>Your Orders</h3>
    <table class="orders-table">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Food ID</th>
                <th>Order Date</th>
                <th>Status</th>
                <th>Total Price</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                if (orders != null && !orders.isEmpty()) {
                    for (Order order : orders) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getFoodId() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getStatus() %></td>
                <td><%= order.getTotalPrice() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5">No orders placed yet.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>


        <!-- Update Order Status Section -->
        <div id="updateOrderStatus" class="section <%= "updateOrderStatus".equals(mode) ? "active" : "" %>">
    <h3>Update Order Status</h3>
    <p>Manage order statuses for processing, cooking, and completion.</p>
    <%
        List<Order> ordersToUpdate = (List<Order>) request.getAttribute("ordersToUpdate");
        if (ordersToUpdate != null && !ordersToUpdate.isEmpty()) {
    %>
    <table class="update-orders-table">
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Current Status</th>
                <th>Update Status</th>
            </tr>
        </thead>
        <tbody>
            <%
                for (Order order : ordersToUpdate) {
            %>
            <tr>
                <td><%= order.getId() %></td>
                <td><%= order.getStatus() %></td>
                <td>
                    <form action="MenuServlet" method="post">
                        <input type="hidden" name="mode" value="updateOrderStatus">
                        <input type="hidden" name="orderId" value="<%= order.getId() %>">
                        <select name="newStatus">
                            <option value="Processing" <%= "Processing".equals(order.getStatus()) ? "selected" : "" %>>Processing</option>
                            <option value="Cooking" <%= "Cooking".equals(order.getStatus()) ? "selected" : "" %>>Cooking</option>
                            <option value="Completed" <%= "Completed".equals(order.getStatus()) ? "selected" : "" %>>Completed</option>
                            <option value="Delivered" <%= "Delivered".equals(order.getStatus()) ? "selected" : "" %>>Delivered</option>
                        </select>
                        <button type="submit">Update</button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    <%
        } else {
    %>
    <p>No orders available for status update.</p>
    <%
        }
    %>
</div>

        
       <!-- Logout -->
       <div id="Logout" class="section <%= "Logout".equals(mode) ? "active" : "" %>">
    <h3>Logout Account</h3>
    <form action="LogoutServlet" method="get">
        <button type="submit">Logout</button>
    </form>
</div>
    </div>
</body>
</html>
