import * as StompJs from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

import { stompUrl } from '../constants';

const DEBUG = true;

export const connectAndSubscribe = ({ topic, onWebsocketMessage, debug = DEBUG, headers = {} }) => {
  const config = {
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  };

  if (debug) {
    config.debug = (msg) => console.log('STOMP DEBUG: ' + msg);
  }

  const client = new StompJs.Client(config);

  client.webSocketFactory = () => new SockJS(stompUrl);

  client.onConnect = (frame) => {
    if (debug) console.log('websocket connected: ', frame);

    client.subscribe(topic, onWebsocketMessage, headers);
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
