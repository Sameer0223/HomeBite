<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home Bite - Food Ordering</title>
    <style>
        /* Google Fonts */
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap');
		@import url('https://fonts.googleapis.com/css2?family=Pacifico&family=Sour+Gummy:ital,wght@0,100..900;1,100..900&display=swap');
        /* Basic reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
       
         body {
        font-family: 'Poppins', sans-serif;
        background-image: url('images/loginbackground.png'); /* Path to your background image */
        background-size: cover; /* Ensure background covers the entire screen */
        background-position: center; /* Center the background */
        background-repeat: no-repeat; /* Prevent repeating */
        color: #333;
        min-height: 100vh; /* Ensure body takes full height */
        display: flex;
        flex-direction: column;
        padding: 0;
    }

        /* Header Styling */
        .header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px 30px;
            background-color: rgba(255, 95, 31, 0.8);
            color: white;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            position: fixed;
            top: 0;
            z-index: 10;
        }

        .logo {
            display: flex;
            align-items: center;
        }

        .logo img {
            width: 50px;
            height: 50px;
            margin-right: 10px;
            transition: transform 0.3s ease;
        }

        .logo img:hover {
            transform: scale(1.1);
        }

        .logo h1 {
            font-size: 28px;
            color: rgba(158, 240, 26, 1);
            font-weight: 600;
        }

        /* Navbar Styling */
        .nav {
            display: flex;
            gap: 20px;
        }

        .nav a {
            color: white;
            text-decoration: none;
            font-weight: 600;
            transition: color 0.3s ease;
            padding: 5px;
        }

        .nav a:hover {
            color: rgba(158, 240, 26, 1);
        }

        /* Hamburger Menu Styling */
        .menu-toggle {
            display: none;
            font-size: 30px;
            cursor: pointer;
            color: white;
        }

        /* Mobile Responsive Nav */
        @media (max-width: 768px) {
            .nav {
                display: none;
                position: absolute;
                top: 60px;
                right: 20px;
                background-color: rgba(255, 95, 31, 0.9);
                flex-direction: column;
                gap: 15px;
                padding: 20px;
                width: 200px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            }

            .nav.active {
                display: flex;
            }

            .menu-toggle {
                display: block;
            }
        }

        #main {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding-top: 80px;
        }

        /* Content and About Section Styling */
        .content, .about-section {
            max-width: 90%;
            text-align: center;
            padding: 40px;
            background-color: rgba(255, 255, 255, 0.85);
            margin: 20px;
            border-radius: 10px;
            box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .content:hover, .about-section:hover {
            transform: translateY(-10px);
            box-shadow: 0px 10px 30px rgba(0, 0, 0, 0.2);
        }

        h2 {
            font-size: 2rem;
            font-weight: 600;
            color: rgba(255, 95, 31, 1);
            margin-bottom: 10px;
        }

        p {
            font-size: 1.1rem;
            font-weight: 300;
            line-height: 1.6;
            color: #555;
        }

        /* Footer styling */
        .footer {
            padding: 15px;
            text-align: center;
            background-color: rgba(255, 95, 31, 0.8);
            color: white;
            width: 100%;
            font-size: 14px;
            position: sticky;
            bottom: 0;
        }

        .footer a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        .footer a:hover {
            text-decoration: underline;
        }
        .loggo{
        font-family: "Pacifico", cursive;
  font-weight: 400;
  font-style: normal;
        }
    </style>
</head>
<body>
    <!-- Header Section -->
    <header class="header">
        <div class="logo">
            <img src="images/applogo.jpg" alt="Home Bite Logo">
            <h1 class="loggo">Home Bite</h1>
        </div>
        <nav class="nav" id="navbar">
            <a href="index.jsp">HOME</a>
            <a href="#about">ABOUT US</a>
            <a href="contact.jsp">CONTACT US</a>
            <a href="login.jsp">LOGIN</a>
        </nav>
        <div class="menu-toggle" id="menu-toggle">&#9776;</div>
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
    </div>

    <!-- Footer Section -->
    <footer class="footer">
        <p>For more information, <a href="contact.jsp">Contact Us</a></p>
        <p>&copy; 2024 Home Bite. All rights reserved.</p>
    </footer>

    <script>
        // Toggle the menu visibility on small screens when hamburger icon is clicked
        document.getElementById("menu-toggle").onclick = function() {
            const navbar = document.getElementById("navbar");
            navbar.classList.toggle("active");
        };
    </script>
</body>
</html>
