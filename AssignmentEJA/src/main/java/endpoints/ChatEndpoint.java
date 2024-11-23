/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import beans.CreateChatBean;
import beans.MessageDecoder;
import beans.MessageEncoder;
import entities.Message;
import jakarta.inject.Inject;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 *
 * @author aadik
 */
@ServerEndpoint(value = "/chatRoom/{room}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatEndpoint {
    
    @Inject
    CreateChatBean chatBean;

    @OnOpen
    public void open(final Session session, @PathParam("room") final String room) {
        session.getUserProperties().put("room", room);
    }

    @OnMessage
    public void onMessage(final Session session, final Message chatMessage) throws IOException, EncodeException {
        String room = (String) session.getUserProperties().get("room");
        
        try {
            chatBean.saveMessage(chatMessage, room);

        } catch (Exception e) {

        }

        // Broadcast the message to all users in the same room
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
                s.getBasicRemote().sendObject(chatMessage);
            }
        }
    }
    
    @OnClose
    public void onClose(Session session){
        session.getUserProperties().remove("room");
        session.getUserProperties().remove("sender");
    }
}

