// import {io} from 'socket.io-client';

// export const initSocket = async () =>{
//     const options = {
//         'force new connection': true,
//         reconnectionAttempts : 'Infinity',
//         timeout: 10000,
//         transports: ['websocket'],
//     };
//     return io(process.env.REACT_APP_BACKEND_URL, options);
// }
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

let stompClient = null;

export const initSocket = async () => {
  const socket = new SockJS(`${process.env.REACT_APP_BACKEND_URL}/ws`);
  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    debug: (str) => console.log(str),
  });

  return {
    on: (event, callback, roomId) => {
      stompClient.onConnect = () => {
        console.log('Connected to STOMP');
        if (event === 'JOINED' || event === 'CODE_CHANGE' || event === 'DISCONNECTED') {
          stompClient.subscribe(`/topic/room/${roomId}`, (message) => {
            const data = JSON.parse(message.body);
            if (data.type === event) {
              callback(data.payload);
            }
          });
        }
      };
      stompClient.onStompError = (frame) => {
        console.error('STOMP error:', frame);
      };
      stompClient.onWebSocketError = (error) => {
        console.error('WebSocket error:', error);
      };
      stompClient.activate();
    },
    emit: (event, payload) => {
      if (stompClient && stompClient.connected) {
        const destination = {
          JOIN: '/app/join',
          CODE_CHANGE: '/app/code-change',
          SYNC_CODE: '/app/sync-code',
        }[event];
        if (destination) {
          stompClient.publish({
            destination,
            body: JSON.stringify({ type: event, payload }),
          });
          console.log(`Emitted ${event}:`, payload);
        } else {
          console.warn(`Unknown event: ${event}`);
        }
      } else {
        console.warn('STOMP client not connected');
      }
    },
    disconnect: () => {
      if (stompClient) {
        stompClient.deactivate();
        stompClient = null;
        console.log('STOMP client disconnected');
      }
    },
    id: null, // STOMP client doesn't expose session ID, handled by backend
  };
};