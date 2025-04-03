<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.src.mn.model.Food" %>
<%@ page import="com.src.mn.model.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard - Home Bite</title>
    <style>
        /* Basic reset */
        * { box-sizing: border-box; margin: 0; padding: 0; }
        body { font-family: Arial, sans-serif; display: flex; min-height: 100vh; }

        /* Sidebar styling */
        .sidebar {
            width: 200px;
            background-color: #333;
            color: #fff;
            padding: 20px;
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
        }
        .sidebar h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .sidebar ul {
            list-style: none;
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

        /* Main content area */
        .main-content {
            margin-left: 200px;
            padding: 20px;
            flex: 1;
            background-color: #f4f4f4;
        }
        .main-content h3 {
            font-size: 20px;
            margin-bottom: 20px;
            color: #333;
        }

        /* Section display control */
        .section { display: none; }
        .section.active { display: block; }

        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #333;
            color: #fff;
        }

        /* Button styling */
        button {
            background-color: #28a745;
            color: #fff;
            border: none;
            padding: 10px 15px;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <!-- Sidebar Navigation -->
    <div class="sidebar">
        <h1>Home Bite</h1>
        <ul>
            <li><a href="CustomerServlet?mode=viewAllFoodItems" class="<%= "viewAllFoodItems".equals(request.getAttribute("mode")) ? "active" : "" %>">View All Food Items</a></li>
            <li><a href="CustomerServlet?mode=searchFoodByCategory" class="<%= "searchFoodByCategory".equals(request.getAttribute("mode")) ? "active" : "" %>">Search Food by Category</a></li>
            <li><a href="CustomerServlet?mode=viewAllCookIds" class="<%= "searchFoodByCookId".equals(request.getAttribute("mode")) ? "active" : "" %>">Search Food by Cook ID</a></li>
            <li><a href="CustomerServlet?mode=viewCart" class="<%= "viewCart".equals(request.getAttribute("mode")) ? "active" : "" %>">View Cart</a></li>
            <li><a href="CustomerServlet?mode=viewOrders" class="<%= "viewOrders".equals(request.getAttribute("mode")) ? "active" : "" %>">View Orders</a></li>
             <li><a href="CustomerServlet?mode=Logout"class="section <%= "Logout".equals(request.getAttribute("mode")) ? "active" : "" %>">Logout</a></li>
        </ul>
    </div>

    <!-- Main Content Area -->
    <div class="main-content">
        <%
            String mode = request.getParameter("mode");
            String message = (String) request.getAttribute("message");
            if (message != null) {
        %>
            <p style="color: green;"><%= message %></p>
        <%
            }
        %>

        <!-- View All Food Items Section -->
        <div id="viewAllFoodItems" class="section <%= "viewAllFoodItems".equals(mode) ? "active" : "" %>">
            <h3>All Food Items</h3>
            <table>
                <tr>
                    <th>Food ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Cook ID</th>
                    <th>Time</th>
                    <th>Action</th>
                </tr>
                <%
                    List<Food> foodList = (List<Food>) request.getAttribute("foodItems");
                    if (foodList != null) {
                        for (Food food : foodList) {
                %>
                    <tr>
                        <td><%= food.getId() %></td>
                        <td><%= food.getName() %></td>
                        <td><%= food.getDescription() %></td>
                        <td><%= food.getPrice() %></td>
                        <td><%= food.getCategory() %></td>
                        <td><%= food.getCookId() %></td>
                        <td><%= food.getTime() %></td>
                        <td>
                            <form action="CustomerServlet" method="post" style="display:inline;">
                                <input type="hidden" name="mode" value="addFoodToCart">
                                <input type="hidden" name="foodId" value="<%= food.getId() %>">
                                <button type="submit">Add to Cart</button>
                            </form>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>

        <!-- Search Food by Category Section -->
        <div id="searchFoodByCategory" class="section <%= "searchFoodByCategory".equals(mode) ? "active" : "" %>">
            <h3>Search Food by Category</h3>
            <form action="CustomerServlet" method="get">
                <input type="hidden" name="mode" value="searchFoodByCategory">
                <label for="category">Category:</label>
                <select name="category" id="category" required>
                    <option value="VEG">VEG</option>
                    <option value="NON_VEG">NON-VEG</option>
                    <option value="SOUP">SOUPS</option>
                    <option value="DESSERT">DESSERT</option>
                </select>
                <button type="submit">Search</button>
            </form>

            <table>
                <tr>
                    <th>Food ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Cook ID</th>
                    
                    <th>Action</th>
                </tr>
                <%
                    List<Food> filteredFoodList = (List<Food>) request.getAttribute("filteredFoodItems");
                    if (filteredFoodList != null) {
                        for (Food food : filteredFoodList) {
                %>
                    <tr>
                        <td><%= food.getId() %></td>
                        <td><%= food.getName() %></td>
                        <td><%= food.getDescription() %></td>
                        <td><%= food.getPrice() %></td>
                        <td><%= food.getCategory() %></td>
                        <td><%= food.getCookId() %></td>
                        
                        <td>
                            <form action="CustomerServlet" method="post" style="display:inline;">
                                <input type="hidden" name="mode" value="addFoodToCart">
                                <input type="hidden" name="foodId" value="<%= food.getId() %>">
                                <button type="submit">Add to Cart</button>
                            </form>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </table>
        </div>
        
        <!-- Search Food by Cook ID Section -->

		<div id="searchFoodByCookId"
			class="section <%="searchFoodByCookId".equals(mode) ? "active" : ""%>">
			<h3>Search Food by Cook ID</h3>
			<form action="CustomerServlet" method="get">
				<input type="hidden" name="mode" value="searchFoodByCookId">
				<label for="cookId">Cook ID:</label> <select name="cookId"
					id="cookId" required>
					<%
					List<Integer> cookIds = (List<Integer>) request.getAttribute("cookIds");
					if (cookIds != null) {
						for (Integer cookId : cookIds) {
					%>
					<option value="<%=cookId%>"><%=cookId%></option>
					<%
					}
					}
					
					%>
				</select>
				<button type="submit">Search</button>
			</form>

			<table>
				<tr>
					<th>Food ID</th>
					<th>Name</th>
					<th>Description</th>
					<th>Price</th>
					<th>Category</th>
					<th>Action</th>
				</tr>
				<%
				List<Food> filteredFoodItems = (List<Food>) request.getAttribute("filteredFoodItems");
				if (filteredFoodItems != null) {
					for (Food food : filteredFoodItems) {
				%>
				<tr>
					<td><%=food.getId()%></td>
					<td><%=food.getName()%></td>
					<td><%=food.getDescription()%></td>
					<td><%=food.getPrice()%></td>
					<td><%=food.getCategory()%></td>
					<td>
						<form action="CustomerServlet" method="post"
							style="display: inline;">
							<input type="hidden" name="mode" value="addFoodToCart"> <input
								type="hidden" name="foodId" value="<%=food.getId()%>">
							<button type="submit">Add to Cart</button>
						</form>
					</td>
				</tr>
				<%
				}
				}
				%>
			</table>
		</div>

		<!-- View Orders Section -->
<div id="viewOrders" class="section <%="viewOrders".equals(mode) ? "active" : ""%>">
    <h3>Your Orders</h3>
    <table>
        <tr>
            <th>Order ID</th>
            <th>Food ID</th>
            <th>Order Date</th>
            <th>Status</th>
            <th>Total Price</th>
        </tr>
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
    </table>
</div>
        
        <!-- View Cart Section -->
   <div id="viewCart" class="section <%= "viewCart".equals(mode) ? "active" : "" %>">
    <h3>Your Cart</h3>
    <table>
        <tr>
            <th>Food ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Category</th>
            <th>Cook ID</th>
         
            <th>Action</th>
        </tr>
        <%
            List<Food> cartItems = (List<Food>) session.getAttribute("cart");
            double totalCost = 0;  // Variable to hold the total cost

            if (cartItems != null && !cartItems.isEmpty()) {
                for (int i = 0; i < cartItems.size(); i++) {
                    Food food = cartItems.get(i);
                    totalCost += food.getPrice();  // Add food price to total cost
        %>
            <tr>
                <td><%= food.getId() %></td>
                <td><%= food.getName() %></td>
                <td><%= food.getDescription() %></td>
                <td><%= food.getPrice() %></td>
                <td><%= food.getCategory() %></td>
                <td><%= food.getCookId() %></td>
                
                <td>
                    <form action="CustomerServlet" method="post" style="display:inline;">
                        <input type="hidden" name="mode" value="removeFoodFromCart">
                        <input type="hidden" name="cartIndex" value="<%= i %>">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        <%
                }
        %>
            <tr>
                <td colspan="6" style="text-align: right; font-weight: bold;">Total Cost:</td>
                <td><%= totalCost %></td>
            </tr>
        <%
            } else {
        %>
            <tr>
                <td colspan="8">Your cart is empty.</td>
            </tr>
        <%
            }
        %>
    </table>
</div>

    <% if (cartItems != null && !cartItems.isEmpty()) { %>
                <form action="CustomerServlet" method="post">
                    <input type="hidden" name="mode" value="placeOrder">
                    <button type="submit">Place Order</button>
                </form>
            <% } %>
            
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
