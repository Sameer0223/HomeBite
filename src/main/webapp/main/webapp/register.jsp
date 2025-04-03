<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Food Ordering - Register</title>
    <style>
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
        .register-container {
            text-align: center;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #fff;
            width: 300px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
        }
        .register-container h2 {
            margin-bottom: 15px;
            color: #333;
        }
        .register-container label {
            font-size: 14px;
            display: block;
            margin: 10px 0 5px;
            color: #555;
        }
        .register-container input,
        .register-container select {
            padding: 10px;
            width: 100%;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }
        .register-container button {
            padding: 10px;
            width: 100%;
            background-color: rgba(255, 95, 31,1);
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }
        .register-container button:hover {
            background-color:  rgba(255, 120, 50,1);
        }
        .register-container p {
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
    <div class="register-container">
        <form action="register" method="post">
            <h2>Register</h2>

            <label>Username: </label>
            <input type="text" name="username" required>

            <label>Password: </label>
            <input type="password" name="password" required>

            <label>Confirm Password: </label>
            <input type="password" name="confirmPassword" required>
            
            <label>Register as:</label>
            <select name="role" required>
                <option value="cook">Cook</option>
                <option value="customer">Customer</option>
            </select>

            <button type="submit">Register</button>

            <p>Already have an account? <a href="login.jsp">Login here</a></p>
            
            <!-- Display any error message passed from the backend -->
         
        </form>
    </div>
</body>
</html>
