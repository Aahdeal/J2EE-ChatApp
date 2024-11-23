<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="style.css"/>
</head>
<body>
    <div class="container">
    <h2 class="title">Maznsi Chat Zone - Register</h2>
    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <p style="color: red;"><%= errorMessage %></p>
    <% } %>
    <form action="Register" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>
        
        <input class="btn" type="submit" value="Register">
    </form>
    <a href="login.jsp">Login</a>
    </div>
</body>
</html>
