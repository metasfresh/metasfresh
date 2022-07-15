import { useEffect } from 'react';
import * as StompJs from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import converters from '../api/converters';

const WS_DEBUG = true;

export const WSEventType_addOrChange = 'addOrChange';
export const WSEventType_remove = 'remove';
const WSEventType_conflictsChanged = 'conflictsChanged';

export const useCalendarWebsocketEvents = ({ simulationId, onWSEvents }) => {
  useEffect(() => {
    return connectToWS({ simulationId, onWSEvents });
  }, [simulationId]);
};

const connectToWS = ({ simulationId, onWSEvents }) => {
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
      const wsEvents = fromAPIWebsocketEventsArray(
        JSON.parse(msg.body)?.events
      );

      if (wsEvents) {
        onWSEvents(wsEvents);
      }
    });
  };

  client.onStompError = (frame) => {
    console.log('websocket error: ' + frame.headers['message'], {
      frame,
    });
  };

  if (WS_DEBUG) console.log('websocket activating for ' + wsTopicName + '...');
  client.activate();

  return () => {
    if (WS_DEBUG)
      console.log('websocket deactivating for ' + wsTopicName + '...');

    client && client.deactivate();
  };
};

// see JsonWebsocketEvent java interface impls
const fromAPIWebsocketEventsArray = (apiWSEventsArray) => {
  if (!apiWSEventsArray) {
    return null;
  }

  const entryEvents = [];
  const conflictEvents = [];

  apiWSEventsArray.forEach((apiWSEvent) => {
    if (apiWSEvent.type === WSEventType_addOrChange) {
      entryEvents.push({
        type: WSEventType_addOrChange,
        entry: converters.fromAPIEntry(apiWSEvent.entry),
      });
    } else if (apiWSEvent.type === WSEventType_remove) {
      entryEvents.push({
        type: WSEventType_remove,
        simulationId: apiWSEvent.simulationId,
        entryId: apiWSEvent.entryId,
      });
    } else if (apiWSEvent.type === WSEventType_conflictsChanged) {
      conflictEvents.push({
        // type: WSEventType_conflictsChanged, // not needed
        simulationId: apiWSEvent.simulationId,
        affectedEntryIds: apiWSEvent.affectedEntryIds,
        conflicts: apiWSEvent.conflicts.map(converters.fromAPIConflict),
      });
    }
  });

  if (entryEvents.length <= 0 && conflictEvents.length <= 0) {
    return null;
  }

  return { entryEvents, conflictEvents };
};
