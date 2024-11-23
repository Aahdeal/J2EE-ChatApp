/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;


import entities.Message;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;
import java.io.StringReader;

/**
 *
 * @author aadik
 */
public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public void init(final EndpointConfig config) {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    @Override
    public Message decode(final String textMessage) throws DecodeException {
        Message chatMessage = new Message();
        JsonObject obj = Json.createReader(new StringReader(textMessage)).readObject();
        chatMessage.setContent(obj.getString("message"));
        chatMessage.setSender(obj.getString("sender"));
        return chatMessage;
    }

    @Override
    public boolean willDecode(final String s) {
        return (s != null);
    }
}
