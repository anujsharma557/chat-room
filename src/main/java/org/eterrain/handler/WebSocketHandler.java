package org.eterrain.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author:sharma.anuj557@gmail.com
 * @Description:
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessionList= new ArrayList<>();
    public List<WebSocketSession> getSessionList(){
        return sessionList;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionList.add(session);
        System.out.println("Connection Established, session added to list");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        System.out.println("Connection closed, session removed from list");
    }
    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        System.out.println("Inside handleTextMessage"+message.getPayload());
            sessionList.forEach(e-> {
                try {
                    e.sendMessage(Objects.requireNonNull(message));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

