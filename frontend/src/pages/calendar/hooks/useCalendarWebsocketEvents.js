import { useEffect } from 'react';
import * as StompJs from '@stomp/stompjs';
import SockJS from 'sockjs-client';

import converters from '../api/converters';
import { getQueryString } from '../../../utils';

const WS_TOPIC_NAME_PREFIX = '/v2/calendar';
const WS_DEBUG = true;

export const WSEventType_entryAddOrChange = 'addOrChange';
export const WSEventType_entryRemove = 'remove';
const WSEventType_conflictsChanged = 'conflictsChanged';
const WSEventType_simulationPlanChanged = 'simulationPlanChanged';

export const useCalendarWebsocketEvents = ({
  simulationId,
  onlyResourceIds,
  onlyProjectId,
  onWSEvents,
}) => {
  useEffect(() => {
    return connectToWS({
      simulationId,
      onlyResourceIds,
      onlyProjectId,
      onWSEvents,
    });
  }, [simulationId, onlyResourceIds, onlyProjectId]);
};

const toWSTopicName = ({ simulationId, onlyResourceIds, onlyProjectId }) => {
  const queryParams = getQueryString({
    simulationId,
    onlyResourceIds:
      onlyResourceIds && onlyResourceIds.length > 0 ? onlyResourceIds : null,
    onlyProjectId,
  });

  return queryParams
    ? `${WS_TOPIC_NAME_PREFIX}?${queryParams}`
    : WS_TOPIC_NAME_PREFIX;
};

const connectToWS = ({
  simulationId,
  onlyResourceIds,
  onlyProjectId,
  onWSEvents,
}) => {
  const wsTopicName = toWSTopicName({
    simulationId,
    onlyResourceIds,
    onlyProjectId,
  });

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
  const changedSimulationIds = [];

  apiWSEventsArray.forEach((apiWSEvent) => {
    if (apiWSEvent.type === WSEventType_entryAddOrChange) {
      entryEvents.push({
        type: WSEventType_entryAddOrChange,
        entry: converters.fromAPIEntry(apiWSEvent.entry),
      });
    } else if (apiWSEvent.type === WSEventType_entryRemove) {
      entryEvents.push({
        type: WSEventType_entryRemove,
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
    } else if (apiWSEvent.type === WSEventType_simulationPlanChanged) {
      if (!changedSimulationIds.includes(apiWSEvent.simulationId)) {
        changedSimulationIds.push(apiWSEvent.simulationId);
      }
    } else {
      console.log('Ignored unknown WS event: ', apiWSEvent);
    }
  });

  if (
    entryEvents.length <= 0 &&
    conflictEvents.length <= 0 &&
    changedSimulationIds.length <= 0
  ) {
    return null;
  }

  return { entryEvents, conflictEvents, changedSimulationIds };
};
