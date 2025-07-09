package com.example.controller;

import com.example.model.SocketMessage;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/code-change") // React sends to /app/code-change
    public void handleCodeChange(@Payload SocketMessage message) {
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), message);
    }
}
