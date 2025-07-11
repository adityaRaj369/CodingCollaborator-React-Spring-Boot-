package com.example.codecolab.controller;

import com.example.codecolab.model.SavedCode;
import com.example.codecolab.model.SocketMessage;
import com.example.codecolab.model.SocketMessage.Client;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import jakarta.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class SocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, Client> userSessionMap = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> roomSessions = new ConcurrentHashMap<>();

    public SocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

   @MessageMapping("/join")
public void handleJoin(@Payload SocketMessage message) {
    String sessionId = UUID.randomUUID().toString();
    Client client = new Client();
    client.setSocketId(sessionId);
    client.setUsername(message.getUsername() != null ? message.getUsername() : "Anonymous");
    client.setEmail(message.getEmail() != null ? message.getEmail() : "");
    client.setPhotoURL(message.getPhotoURL() != null ? message.getPhotoURL() : "");

    userSessionMap.put(sessionId, client);
    roomSessions.computeIfAbsent(message.getRoomId(), k -> new HashSet<>()).add(sessionId);

    List<Client> clients = getClientsInRoom(message.getRoomId());
    SocketMessage response = new SocketMessage();
    response.setType("JOINED");
    response.setClients(clients);
    response.setUsername(client.getUsername());
    response.setSocketId(sessionId);
    response.setRoomId(message.getRoomId());

    messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), response);
}

    @MessageMapping("/code-change")
    public void handleCodeChange(@Payload SocketMessage message) {
        SocketMessage response = new SocketMessage();
        response.setType("CODE_CHANGE");
        response.setCode(message.getCode());
        response.setRoomId(message.getRoomId());
        messagingTemplate.convertAndSend("/topic/room/" + message.getRoomId(), response);
    }

    @MessageMapping("/sync-code")
    public void handleSyncCode(@Payload SocketMessage message) {
        SocketMessage response = new SocketMessage();
        response.setType("CODE_CHANGE");
        response.setCode(message.getCode());
        response.setRoomId(message.getRoomId());
        messagingTemplate.convertAndSendToUser(message.getSocketId(), "/queue/code", response);
    }

    @MessageMapping("/leave")
    public void handleLeave(@Payload SocketMessage message) {
        handleDisconnect(message.getSocketId(), message.getRoomId());
    }

    public void handleDisconnect(String sessionId, String roomId) {
        Client client = userSessionMap.get(sessionId);
        if (client != null) {
            userSessionMap.remove(sessionId);
            Set<String> sessions = roomSessions.get(roomId);
            if (sessions != null) {
                sessions.remove(sessionId);
                if (sessions.isEmpty()) {
                    roomSessions.remove(roomId);
                }
            }
            SocketMessage response = new SocketMessage();
            response.setType("DISCONNECTED");
            response.setSocketId(sessionId);
            response.setUsername(client.getUsername());
            response.setRoomId(roomId);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, response);
        }
    }

    private List<Client> getClientsInRoom(String roomId) {
        Set<String> sessionIds = roomSessions.getOrDefault(roomId, new HashSet<>());
        List<Client> clients = new ArrayList<>();
        for (String sessionId : sessionIds) {
            Client client = userSessionMap.get(sessionId);
            if (client != null) {
                clients.add(client);
            }
        }
        return clients;
    }

    @PreDestroy
    public void cleanup() {
        userSessionMap.clear();
        roomSessions.clear();
    }
}