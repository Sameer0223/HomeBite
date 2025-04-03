<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Food Ordering - Login</title>
    <style>
        /* Basic reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        /* Font and Body Styling */
        body {
            font-family: 'Poppins', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-image: url('images/loginbackground.png'); /* Background image */
            background-size: cover; /* Cover the entire background */
            background-position: center;
            background-repeat: no-repeat;
        }

        /* Login Container */
        .login-container {
            text-align: center;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: rgba(255, 255, 255, 0.85); /* Slightly transparent background */
            width: 100%;
            max-width: 350px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .login-container:hover {
            transform: translateY(-10px);
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
        }

        .login-container h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 1.8rem;
        }

        /* Form Labels */
        .login-container label {
            font-size: 14px;
            display: block;
            margin: 10px 0 5px;
            color: #555;
        }

        /* Form Inputs */
        .login-container input, .login-container select {
            padding: 10px;
            width: 100%;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        /* Submit Button */
        .login-container button {
            padding: 12px;
            width: 100%;
            background-color: rgba(255, 95, 31, 1); /* Main color */
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .login-container button:hover {
            background-color: rgba(255, 120, 50, 1); /* Hover color */
        }

        /* Footer and Links */
        .login-container p {
            margin-top: 15px;
            font-size: 14px;
        }

        .login-container a {
            color: rgba(255, 95, 31, 1);
            text-decoration: none;
            font-weight: bold;
        }

        .login-container a:hover {
            text-decoration: underline;
        }

        /* Error Message */
        .error-message {
            color: red;
            font-size: 14px;
        }

        /* Mobile Responsiveness */
        @media (max-width: 768px) {
            .login-container {
                padding: 20px;
                width: 80%;
            }

            .login-container h2 {
                font-size: 1.6rem;
            }

            .login-container input, .login-container select {
                padding: 8px;
            }

            .login-container button {
                padding: 10px;
            }
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
            <div class="error-message">
                <!-- Insert error message here -->
            </div>
        </form>
    </div>
</body>
</html>
