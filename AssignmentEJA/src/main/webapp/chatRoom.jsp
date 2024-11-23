<%-- 
    Document   : chatRoom
    Created on : 25 Aug 2024, 13:47:19
    Author     : aadik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Chat Room</title>
        <style>
            body {
                background-color: #f0f2f5;
                font-family: Arial, sans-serif;
            }

            .chat-container {
                width: 400px;
                max-width: 100%;
                margin: 0 auto;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                padding: 20px;
                display: flex;
                flex-direction: column;
            }

            .chat-header {
                text-align: center;
                color: #1e88e5;
                margin-bottom: 15px;
            }

            .chat-messages {
                max-height: 300px;
                overflow-y: auto;
                padding: 2px;
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                background-color: #f7f7f7;
                margin-bottom: 15px;
            }

            .chat-message {
                display: flex;
                margin-bottom: 10px;
            }

            .sent {
                justify-content: flex-end;
            }

            .received {
                justify-content: flex-start;
            }

            .message-content {
                max-width: 70%;
                padding: 5px;
                border-radius: 15px;
                background-color: #e0f7fa;
                position: relative;
                word-wrap: break-word;
            }

            .sent .message-content {
                background-color: #1e88e5;
                color: #fff;
            }

            .message-time {
                font-size: 10px;
                color: #999;
                align-self: flex-end;
                margin-left: 5px;
            }

            .message-input-container {
                display: flex;
                align-items: center;
                border-top: 1px solid #e0e0e0;
                padding-top: 10px;
            }

            .message-input {
                flex: 1;
                padding: 10px;
                border: 1px solid #e0e0e0;
                border-radius: 20px;
                outline: none;
            }

            .send-button {
                background: none;
                border: none;
                padding: 0 10px;
                cursor: pointer;
            }

            .send-button img {
                width: 20px;
                height: 20px;
            }

            .return-home {
                margin-top: 10px;
                padding: 10px;
                width: 100%;
                background-color: #1e88e5;
                color: #fff;
                border: none;
                border-radius: 20px;
                cursor: pointer;
                text-align: center;
            }
        </style>
        <script>
            let ws;
            function connectToChat(roomId) {
                ws = new WebSocket("ws://" + window.location.host + "/AssignmentEJA/chatRoom/" + roomId);
                
                ws.onmessage = function(event) {
                    let chatArea = document.getElementById('chatArea');
                    let message = JSON.parse(event.data);
                    const sessionUsername = '${sessionScope.username}'; // Current user's username
                    const messageSender = message.sender; // Sender of the message
                    const messageContent = message.message; // Content of the message
                    
                    // Determine the message type based on the sender
                    const type = sessionUsername === message.sender ? 'sent' : 'received';

                    chatArea.innerHTML += `
                                            <div class="chat-message `+type+`">
                                                <div class="message-content">
                                                    <p>`+messageContent+`</p>
                                                </div>
                                            </div>
                                        `;
    
                   document.getElementById('chatArea').scrollTop = document.getElementById('chatArea').scrollHeight;
                };

                ws.onopen = function() {
                    console.log('Connected to room ' + roomId);
                };

                ws.onclose = function() {
                    console.log('Disconnected');
                };
            }

            function sendMessage() {
                let messageInput = document.getElementById('messageInput');
                let message = {
                    sender: "<%= session.getAttribute("username") %>",
                    message: messageInput.value
                };
                ws.send(JSON.stringify(message));
                messageInput.value = '';
            }
        </script>
    </head>
    <body>
    <div class="chat-container">
        <h2 class="chat-header">${sessionScope.receiver}</h2>
        <div id="chatArea" class="chat-messages">
            <!-- Display previous messages -->
            <c:forEach var="message" items="${sessionScope.currentMessages}">
                <div class="chat-message ${sessionScope.username == message.sender ? 'sent' : 'received'}">
                    <div class="message-content">
                        <p>${message.content}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="message-input-container">
            <input type="text" id="messageInput" class="message-input" placeholder="Type a message..." />
            <button onclick="sendMessage()" class="send-button">Send</button>
        </div>
        <br>
        <!-- Button to return to home -->
        <button onclick="window.location.href='home.jsp'" class="return-home">Return to Home</button>
    </div>

    <script>
        // Connect to WebSocket when chat room is present
        connectToChat("${sessionScope.room}");
    </script>
</body>
</html>
