/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import beans.CreateChatBean;
import entities.Message;
import entities.RoomKey;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author aadik
 */
public class ChatServlet extends HttpServlet {
    
    @Inject
    CreateChatBean chatBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get the current user's username from the session
        String sender = (String) session.getAttribute("username");
        String receiver = request.getParameter("receiver");

        // Check if the receiver is provided
        if (receiver == null || receiver.isEmpty()) {
            // Handle the case where the receiver is not provided
            response.sendRedirect("chat.jsp?error=Please+provide+a+receiver+username");
            return;
        }

        // Create or find a chat room between the sender and receiver
        RoomKey room = chatBean.createChatRoom(sender, receiver);
        String roomID = ""+room.getId();
        
        //get messages and display
         List<Message> messages = chatBean.getAllMessages(room);
         session.setAttribute("currentMessages", messages);

        // Set the room key ID in the session
        session.setAttribute("room", roomID);
        session.setAttribute("receiver", receiver);

        // Redirect to the chatRoom.jsp page with the room key ID
        response.sendRedirect("chatRoom.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Handles navigation to a specific chat room.";
    }
}

