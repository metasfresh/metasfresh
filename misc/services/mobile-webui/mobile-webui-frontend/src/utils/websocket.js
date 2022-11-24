import * as StompJs from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

import { stompUrl } from '../constants';

const DEBUG = true;

export const connectAndSubscribe = ({ topic, onWebsocketMessage }) => {
  const client = new StompJs.Client({
    debug: DEBUG ? (msg) => console.log('STOMP DEBUG: ' + msg) : null,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  });

  client.webSocketFactory = () => new SockJS(stompUrl);

  client.onConnect = (frame) => {
    console.log('websocket connected: ', frame);
    client.subscribe(topic, onWebsocketMessage);
  };

  client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate();

  return client;
};

export const disconnectClient = (client) => {
  client && client.deactivate();
};
