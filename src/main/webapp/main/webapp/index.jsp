<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home Bite - Food Ordering</title>
    <style>
        /* Basic styling for the header */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('images/index1background.jpg'); /* Add your background image here */
            background-size: cover;
            background-position: center;
            color: #333;
        }
        
        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 10px 20px;
            background-color: rgba(255, 95, 31,0.6);
            color: white;
        }
        #main{
         display: flex;
            align-items: center;
            justify-content: space-between;
            flex-direction: column;
        }
        .logo {
            display: flex;
            align-items: center;
        }
        
        .logo img {
            width: 50px;
            height: 50px;
            margin-right: 10px;
        }
        
        .logo h1 {
            margin: 0;
            font-size: 24px;
        }
        
        .nav {
            display: flex;
            gap: 15px;
        }
        
        .nav a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        
        .nav a:hover {
            color:rgba(158, 240, 26, 1);
        }
        
        /* Page content styling */
        .content {
         	display: flex;
            align-items: center;
            justify-content: space-between;
            
            flex-direction: column;
            text-align: center;
            padding: 50px;
            background-color: rgba(255, 255, 255, 0.5);
            margin: 20px;
            border-radius: 8px;
        }

        .about-section {
         display: flex;
            align-items: center;
            justify-content: space-between;
            
            flex-direction: column;
            margin-top: 40px;
            padding: 20px;
          
            width:80rem;
            background-color: rgba(255, 255, 255, 0.5);
            border-radius: 8px;
        }

        /* Footer styling */
        .footer {
            padding: 10px;
            text-align: center;
            background-color: rgba(255, 95, 31,0.6);
            
            color: white;
            position: fixed;
            bottom: 0;
            width: 100%;
        }

        .footer a {
            color: white;
            text-decoration: none;
        }

        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <!-- Header Section -->
    <header class="header">
        <div class="logo">
            <img src="images/applogo.jpg" alt="Home Bite Logo"> <!-- Add your logo image path -->
            <h1 style="color:rgba(158, 240, 26, 1);">Home Bite</h1>
        </div>
        <nav class="nav">
            <a href="index.jsp">HOME</a>
            <a href="#about">ABOUT US</a>
            <a href="contact.jsp">CONTACT US</a>
            <a href="login.jsp">LOGIN</a>
           
        </nav>
    </header>
<div id="main">
    <!-- Main Content Section -->
    <div class="content">
        <h2>Welcome to Home Bite!</h2>
        <p>Your one-stop solution for delicious food ordering.</p>
    </div>

    <!-- ABOUT US Section -->
    <div id="about" class="about-section">
        <h2>About Us</h2>
        <p>Home Bite is dedicated to bringing delicious, freshly made food right to your doorstep. Whether you're craving your favorite meal or looking to try something new, we offer a wide range of options to satisfy your hunger. Our cooks are passionate about food and committed to quality, ensuring every bite is as enjoyable as the last.</p>
    </div>

    <!-- Footer Section -->
    <footer class="footer">
        <p>For more information, <a href="contact.jsp">Contact Us</a></p>
        <p>&copy; 2024 Home Bite. All rights reserved.</p>
    </footer>
    </div>
</body>
</html>
