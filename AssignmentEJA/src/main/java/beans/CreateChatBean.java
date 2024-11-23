/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package beans;

import com.google.gson.JsonObject;
import entities.Message;
import entities.RoomKey;
import jakarta.ejb.Stateless;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import static java.lang.Long.parseLong;
import java.util.List;

/**
 *
 * @author aadik
 */
@Stateless
public class CreateChatBean {

    @PersistenceContext(unitName = "roomPU")
    EntityManager em;
    
    private RoomKey roomKey = new RoomKey();
    private Message message = new Message();
    
    public RoomKey createChatRoom(String user1, String user2){
        try {
        // Query to find a room with user1 and user2 in any order
            RoomKey existingRoom = em.createQuery(
                    "SELECT rk FROM RoomKey rk WHERE (rk.user1 = :user1 AND rk.user2 = :user2) OR (rk.user1 = :user2 AND rk.user2 = :user1)", 
                    RoomKey.class)
                    .setParameter("user1", user1)
                    .setParameter("user2", user2)
                    .getSingleResult();

            // If a room is found, return the existing room
            return existingRoom;
        } catch (NoResultException e) {
            // If no room is found, create a new RoomKey instance
            RoomKey roomKey = new RoomKey();
            roomKey.setUser1(user1);
            roomKey.setUser2(user2);

            // Persist the new room
            em.persist(roomKey);

            return roomKey;
        }
    }
    
    public int countConversations(String user){
       TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(r) FROM RoomKey r WHERE r.user1 = :user OR r.user2 = :user", 
            Long.class
        );
        query.setParameter("user", user);

        Long count = query.getSingleResult();

        return count.intValue();
    }
    
    public RoomKey findRoomById(String roomId) {
        return em.find(RoomKey.class, roomId);
    }
    
    public String getChats(String user) {
        // Step 1: Find all RoomKey entities where user1 or user2 is the provided user
        TypedQuery<RoomKey> roomQuery = em.createQuery(
            "SELECT r FROM RoomKey r WHERE r.user1 = :user OR r.user2 = :user", 
            RoomKey.class
        );
        roomQuery.setParameter("user", user);
        List<RoomKey> rooms = roomQuery.getResultList();

        // Step 2: Prepare JSON response
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (RoomKey room : rooms) {
            // Step 3: Find the most recent message for each room
            TypedQuery<Message> messageQuery = em.createQuery(
                "SELECT m FROM Message m WHERE m.roomKey = :roomKey ORDER BY m.id DESC", 
                Message.class
            );
            messageQuery.setParameter("roomKey", room);
            messageQuery.setMaxResults(1); // Limit to the most recent message
            List<Message> messages = messageQuery.getResultList();

            // Check if there is at least one message
            if (!messages.isEmpty()) {
                Message lastMessage = messages.get(0);

                // Step 4: Build JSON object for each chat
                JsonObjectBuilder chatObjectBuilder = Json.createObjectBuilder()
                    .add("roomKey", ""+room.getId())
                    .add("user1", room.getUser1())
                    .add("user2", room.getUser2())
                    .add("lastMessage", lastMessage.getContent())
                    .add("timestamp", (lastMessage.getTime() == null) ? "N/A" : lastMessage.getTime().toString()); // Assuming timestamp is a Date type

                jsonArrayBuilder.add(chatObjectBuilder);
            }
        }

        // Convert the JSON array to a string
        return jsonArrayBuilder.build().toString();
    }
    
    public void saveMessage(Message chatMessage, String roomKey){
        if (chatMessage == null || roomKey == null) {
            throw new IllegalArgumentException("Message or RoomKey cannot be null");
        }

        try {
            RoomKey room = em.find(RoomKey.class, parseLong(roomKey)); // Find the roomKey entity
            if (room == null) {
                throw new IllegalArgumentException("Invalid RoomKey");
            }

            chatMessage.setRoomKey(room); // Set the room key for the message
            em.persist(chatMessage); // Persist the message
            em.flush(); // Ensure the message is saved
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save message to database", e);
        }
    }

    
    public List<Message> getAllMessages(RoomKey roomKey){
       // Ensure the roomKey is not null
        if (roomKey == null) {
            throw new IllegalArgumentException("RoomKey cannot be null");
        }

        // Query to get all messages associated with the given RoomKey
        try{
            TypedQuery<Message> query = em.createQuery(
                "SELECT m FROM Message m WHERE m.roomKey = :roomKey ORDER BY m.id ASC", 
                Message.class)
                .setParameter("roomKey", roomKey);

        // Execute the query and get the result list
        return query.getResultList();
        }catch (Exception e){
            return null;
        }
    }
}
