<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Food Ordering - Login</title>
    <style>
        /* Basic styling for the login form */
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-image: url('images/loginbackground.png'); /* Set the background image */
            background-size: cover; /* Cover the entire background */
            background-position: center; /* Center the background image */
            background-repeat: no-repeat; /* Prevent background from repeating */
        }
        .login-container {
            text-align: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: rgba(255, 255, 255, 0.9); /* Slightly transparent background for contrast */
            width: 300px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        }
        .login-container h2 {
            margin-bottom: 15px;
            color: #333;
        }
        .login-container label {
            font-size: 14px;
            display: block;
            margin: 10px 0 5px;
            color: #555;
        }
        .login-container input, .login-container select {
            padding: 10px;
            width: 100%;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .login-container button {
            padding: 10px;
            width: 100%;
            background-color: rgba(255, 95, 31,1);;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }
        .login-container button:hover {
            background-color:  rgba(255, 120, 50,1);
        }
        .login-container p {
            margin-top: 10px;
            font-size: 14px;
        }
        .error-message {
            color: red;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <form action="login" method="post">
            <h2>Login</h2>
            
            <label>Username: </label>
            <input type="text" name="username" required>

            <label>Password: </label>
            <input type="password" name="password" required>
            
            <label>Login in as:</label>
            <select name="role" required>
                <option value="cook">Cook</option>
                <option value="customer">Customer</option>
            </select>

            <button type="submit">Login</button>

            <p>Don't Have an Account? <a href="register.jsp">Register here</a></p>
            
            <!-- Display any error message passed from the backend -->
            
        </form>
    </div>
</body>
</html>
