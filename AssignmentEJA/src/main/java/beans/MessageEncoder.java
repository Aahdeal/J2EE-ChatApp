/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beans;

import entities.Message;
import jakarta.json.Json;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public void init(final EndpointConfig config) {
        // Initialization logic if needed
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
    }

    @Override
    public String encode(final Message chatMessage) throws EncodeException {
        return Json.createObjectBuilder()
                .add("message", chatMessage.getContent())
                .add("sender", chatMessage.getSender())
                .build()
                .toString();
    }
}

