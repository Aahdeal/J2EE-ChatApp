<%@ page import="jakarta.json.Json, jakarta.json.JsonArray, jakarta.json.JsonObject, jakarta.json.JsonReader, java.io.StringReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Conversations</title>
    <style>
        body{
            background-color: #f0f2f5;
        }
        form{
            display: flex;
            justify-content: center;
        }
        .chat{
            max-width: 400px;
            width:90%;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #ffffff;
            border: 0px;
        }
        
        .chat:hover{
            background-color: #f0f2f5;
        }
        .info{
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            text-align: left;
        }
        .content{
            width:300px;
        }
        .pfp{
            justify-content: center;
            align-content: center;
            border-radius: 25%;
            padding-right: 10px;
        }
        .nav{
            display: inline-block;
            width: fit-content;
            margin: 10px 0;
            padding: 10px 20px;
            background-color: #4BB5C1;
            color: #fff;
            border: none;
            border-radius: 20px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    
    <h1 style="text-align:center;">Your Conversations</h1><br/>

<%
    // Retrieve the JSON string from the session attribute
    String chatsJson = (String) session.getAttribute("Chats");

    if (chatsJson != null && !chatsJson.isEmpty()) {
        // Parse the JSON string to create a JsonReader and JsonArray
        try (JsonReader jsonReader = Json.createReader(new StringReader(chatsJson))) {
            JsonArray chatsArray = jsonReader.readArray();

            // Check if chatsArray is not null and has content
            if (chatsArray != null && !chatsArray.isEmpty()) {
                // Loop through each chat in the array and display the information
                for (int i = 0; i < chatsArray.size(); i++) {
                    JsonObject chat = chatsArray.getJsonObject(i);
                    String roomKey = chat.getString("roomKey", "N/A");
                    String user1 = chat.getString("user1", "Unknown");
                    String user2 = chat.getString("user2", "Unknown");
                    String lastMessage = chat.getString("lastMessage", "No messages yet");
                    String timestamp = chat.getString("timestamp", "time");
%>

            <!-- HTML code to display each chat with a clickable link -->
            
                <form action="newChatServlet" method="get">
                    <input type="text" id="receiver" name="receiver" value="<%= session.getAttribute("username").equals(user1) ? user2 : user1%>" hidden="True">
                    <button class="chat" type="submit">
                        <div class="info">
                            <div class="pfp">
                                <img src="pfp.png" alt="alt"/>
                            </div>
                            <div class="content">
                                <p><strong><%= session.getAttribute("username").equals(user1) ? user2 : user1%></strong></p>
                                <p><%= lastMessage %></p>
                            </div>
                            <div>
                                <p><%= timestamp %></p>
                            </div>
                        </div>
                    </button> <!-- Link to enter the chat room -->
                </form>
            

<%
                } // End of for loop
            } else {
%>
            <p>No chat rooms available.</p>
<%
            }
        } catch (Exception e) {
%>
            <p>Error parsing chat data. Please try again later.</p>
            <pre><%= e.getMessage() %></pre>
<%
        }
    } else {
%>
    <p>No conversations found.</p>
<%
    }
%>

        <div style="text-align: center;">
            <button class="nav" onclick="window.location.href='home.jsp';">Return to Home</button><br/>
            <button class="nav" onclick="window.location.href='chat.jsp';">New Chat</button>           
        </div>     

</body>
</html>

