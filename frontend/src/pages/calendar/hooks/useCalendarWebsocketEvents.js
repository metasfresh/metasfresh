import { useEffect } from 'react';
import * as StompJs from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import converters from '../api/converters';

const WS_DEBUG = true;

export const useCalendarWebsocketEvents = ({
  simulationId,
  onWSEventsArray,
}) => {
  useEffect(() => {
    return connectToWS({ simulationId, onWSEventsArray });
  }, [simulationId]);
};

const connectToWS = ({ simulationId, onWSEventsArray }) => {
  const wsTopicName = `/v2/calendar/${simulationId || 'actual'}`;

  const stompJsConfig = {
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
  };
  if (WS_DEBUG) {
    stompJsConfig.debug = (msg) => console.log('STOMP DEBUG: ' + msg);
  }

  const client = new StompJs.Client(stompJsConfig);

  client.webSocketFactory = () => new SockJS(config.WS_URL);

  client.onConnect = (frame) => {
    if (WS_DEBUG) console.log('websocket connected: ', frame);

    client.subscribe(wsTopicName, (msg) => {
      const wsEventsArray =
        JSON.parse(msg.body)?.events?.map(converters.fromAPIWebsocketEvent) ||
        [];
      onWSEventsArray(wsEventsArray);
    });
  };

  client.onStompError = function (frame) {
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  client.activate();

  return () => {
    client && client.deactivate();
  };
};
