package com.example.websocket;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class SocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Socket connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Implement handling of JOIN, JOINED, DISCONNECTED, CODE_CHANGE, SYNC_CODE, LEAVE using custom JSON structure
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session.getId());
        System.out.println("Socket disconnected: " + session.getId());
    }
}