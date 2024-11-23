/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package beans;

import entities.Message;
import entities.RoomKey;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author aadik
 */
@Stateless
public class MessageBean {

    @PersistenceContext(unitName = "messagePU")
    EntityManager em;
    
    public void uploadMessage(String sender, String content, LocalDateTime time, RoomKey roomKey){
        Message message = new Message();
        message.setSender(sender);
        message.setContent(content);
        message.setTime(time);
        message.setRoomKey(roomKey);
        
        em.persist(message);
    }
    
    public List<Message> getAllMessages(){
        List<Message> messages = null;
        return messages;
    }
}
