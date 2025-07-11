package com.example.codecolab.websocket;


import com.example.codecolab.controller.SocketController;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionDisconnectHandler {

    private final SocketController socketController;

    public SessionDisconnectHandler(SocketController socketController) {
        this.socketController = socketController;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        if (sessionId != null && roomId != null) {
            socketController.handleDisconnect(sessionId, roomId);
        }
    }
}