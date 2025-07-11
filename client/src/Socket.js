
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { ACTIONS } from "./Actions";

let subscriptions = [];

export async function initSocket() {
  const socket = new SockJS(`${process.env.REACT_APP_BACKEND_URL}/ws`);
  const client = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    onConnect: () => {
      console.log("STOMP client connected");
    },
    onStompError: (frame) => {
      console.error("STOMP error:", frame);
    },
    onWebSocketError: (error) => {
      console.error("WebSocket error:", error);
    },
  });

  client.activate();

  // Wait for connection
  await new Promise((resolve, reject) => {
    client.onConnect = () => resolve();
    client.onWebSocketError = (error) => reject(error);
    setTimeout(() => reject(new Error("Socket connection timeout")), 10000);
  });

  return {
    emit: (event, data) => {
      if (client.connected) {
        console.log("Emitting event:", event, data);
        client.publish({ destination: `/app/${event.toLowerCase()}`, body: JSON.stringify(data) });
      } else {
        console.warn("STOMP client not connected, cannot emit:", event);
      }
    },
    on: (event, callback, roomId) => {
      const subscription = client.subscribe(`/topic/room/${roomId}`, (message) => {
        const data = JSON.parse(message.body);
        console.log("Received message:", event, data);
        if (data.type === event) {
          callback(data, roomId);
        }
      });
      subscriptions.push(subscription);
    },
    disconnect: () => {
      subscriptions.forEach((sub) => sub.unsubscribe());
      subscriptions = [];
      client.deactivate();
      console.log("STOMP client disconnected");
    },
  };
}