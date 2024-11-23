<%@ page import="entities.User" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="jakarta.persistence.PersistenceContext" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.RequestDispatcher" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container">
        <div class="user-card">
            <img src="user-profile.png" alt="Profile Picture" class="profile-pic"/>
            <h2>Hi, ${sessionScope.username}</h2>
            <p>You have ${sessionScope.newMessages} new messages!</p>
        </div>

        <!-- Conversations Button -->
        <form action="Conversations" method="get">
            <input type="submit" value="Conversations ${sessionScope.numChats}" class="btn"/>
        </form>
        <br/>

        <!-- Contacts Button -->
        <form action="contactsServlet" method="post">
            <input type="submit" value="Contacts" class="btn"/>
        </form>
        <br/>

        <!-- Memories Button -->
        <form action="memoriesServlet" method="post">
            <input type="submit" value="Memories" class="btn"/>
        </form>
        <br/>
        
        <div style="margin-top: 20px;">
        <button class="btn-new-chat" onclick="window.location.href='chat.jsp';">New Chat</button>
        </div>
    </div>
</body>
</html>