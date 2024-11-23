<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="style.css"/>
</head>
<body>
    <div class="container">
    <h2 class="title">Mzansi Chat Zone - Login</h2>
    <form action="Login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input class="btn" type="submit" value="Login">
    </form>
    <a href="register.jsp">Register</a>
    </div>
</body>
</html>

