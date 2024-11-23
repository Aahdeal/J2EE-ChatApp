<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Start a New Chat</title>
    </head>
    <body>
        <div class="form-container">
            <h2>Start a New Chat</h2>
            <form action="newChatServlet" method="get">
                <!-- Input for the receiver's username -->
                <div class="form-group">
                    <label for="receiver">Receiver Username:</label>
                    <input type="text" id="receiver" name="receiver" required>
                </div>
                <!-- Submit button to start a new chat -->
                <div class="form-group">
                    <button type="submit">Start Chat</button>
                </div>
            </form>
        </div>
    </body>
</html>
