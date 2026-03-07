import { useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { connectionError } from '../actions/AppActions';
import { BAD_GATEWAY_ERROR, NO_CONNECTION_ERROR } from '../constants/Constants';
import store from '../store/store';
import { getUserSession } from '../api/userSession';
import { useIsLoggedIn } from './useIsLoggedIn';

//
// Config
const socketFactory = () => new SockJS(config.WS_URL);
const MAX_RECONNECT_TIMES = 4;
const CLIENT_DISCONNECT_DELAY_MS = 3000; // 3-second delay before disconnecting
const log = (...params) =>
  globalWsState.debugLogging ? console.debug(...params) : () => {};

// Global connection state to prevent multiple connections
const globalWsState = {
  //
  // Client-related state
  client: null,
  connecting: false,
  reconnectCounter: 0,
  reconnectLastErrorType: null,
  disconnectTimer: null,

  //
  // Topic subscriptions
  nextSubscriptionId: 1,
  subscriptionsById: new Map(),
  pendingSubscriptionsById: new Map(),

  //
  // Misc
  debugLogging: false,
};

//
//
// -----------------------------------------------------------------------------
//
//

export const useWebsocket = ({
  topic: topicParam,
  traceName = '',
  onMessage,
}) => {
  const isLoggedIn = useIsLoggedIn();
  const onMessageRef = useRef(onMessage);
  const topicRef = useRef();
  const subscriptionIdRef = useRef();

  const topic = isLoggedIn ? normalizeTopicName(topicParam) : null;

  // Update refs without triggering re-connection
  useEffect(() => {
    onMessageRef.current = onMessage;
  }, [onMessage]);

  useEffect(() => {
    // do nothing if no topic changed
    if (topicRef.current === topic) return;

    // Disconnect from the previous topic
    const oldSubscriptionId = subscriptionIdRef.current;
    if (oldSubscriptionId) {
      subscriptionIdRef.current = null;
      disconnectWS({ subscriptionId: oldSubscriptionId });
    }

    // Connect to the new topic
    if (topic) {
      const subscriptionId = newSubscriptionId(traceName);
      subscriptionIdRef.current = subscriptionId;
      topicRef.current = topic;

      try {
        connectWS({
          subscriptionId,
          topic: topic,
          onMessageCallback: (event) => {
            // Use ref to get the latest callback to avoid stale closures
            if (onMessageRef.current) {
              onMessageRef.current({ topic, event });
            }
          },
        });
      } catch (error) {
        console.error(
          `[WS ${topic} ID=${subscriptionId}] Connection failed:`,
          error
        );
      }
    }

    return () => {
      if (subscriptionIdRef.current) {
        try {
          disconnectWS({ subscriptionId: subscriptionIdRef.current });
        } catch (error) {
          console.error(
            `[WS ${topicRef.current} ID=${subscriptionIdRef.current}] Disconnection failed on cleanup:`,
            error
          );
        }
      }
    };
  }, [topic, traceName]); // Only depend on the topic and traceName, not onMessage
};

//
//
// ----------------------------
//
//

const normalizeTopicName = (topic) => {
  if (!topic) return null;

  let topicNorm = topic.trim();
  if (topicNorm.length === 0) return null;

  return topicNorm;
};

//
//
// -----------------------------------------------------------------------------
//
//

const isClientEligibleForDisconnection = () => {
  return (
    globalWsState.client &&
    !globalWsState.connecting &&
    globalWsState.subscriptionsById.size === 0 &&
    globalWsState.pendingSubscriptionsById.size === 0
  );
};

//
//
// -----------------------------------------------------------------------------
//
//

const cancelScheduledClientDisconnect = ({ reason } = {}) => {
  if (!globalWsState.disconnectTimer) return;

  clearTimeout(globalWsState.disconnectTimer);
  globalWsState.disconnectTimer = null;

  if (reason) log(`WS: Cancelled scheduled disconnect because ${reason}`);
};

//
//
// -----------------------------------------------------------------------------
//
//

const scheduleClientDisconnectIfEligible = () => {
  if (!isClientEligibleForDisconnection()) return;

  cancelScheduledClientDisconnect();
  log(`WS: Scheduling disconnect in ${CLIENT_DISCONNECT_DELAY_MS}ms`);
  globalWsState.disconnectTimer = setTimeout(() => {
    // Double-check conditions haven't changed
    if (isClientEligibleForDisconnection()) {
      disconnectClient();
    }
  }, CLIENT_DISCONNECT_DELAY_MS);
};

//
//
// -----------------------------------------------------------------------------
//
//

const disconnectClient = () => {
  cancelScheduledClientDisconnect();

  if (!globalWsState.client) return;

  try {
    globalWsState.client.deactivate();
    globalWsState.client = null;
    // log('WS: Client disconnected');
  } catch (error) {
    console.error('WS: Error deactivating client:', error);
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const newSubscriptionId = (prefix) => {
  const subscriptionId = globalWsState.nextSubscriptionId++;
  return prefix ? prefix + '-' + subscriptionId : subscriptionId;
};

//
//
// -----------------------------------------------------------------------------
//
//

const connectWS = ({
  subscriptionId: subscriptionIdParam,
  topic,
  onMessageCallback,
}) => {
  if (!topic) {
    console.warn('WS: Topic is required to subscribe to');
    return;
  }
  if (!onMessageCallback) {
    console.warn('WS: onMessageCallback is required to subscribe to');
    return;
  }

  cancelScheduledClientDisconnect({ reason: 'new subscription' });

  // Generate a unique subscription ID to track this specific subscription
  const subscriptionId = subscriptionIdParam
    ? subscriptionIdParam
    : newSubscriptionId();

  // Avoid disconnecting and reconnecting.
  // IMPORTANT: we assume the "onMessageCallback" is the same
  const subscriptionData = globalWsState.subscriptionsById.get(subscriptionId);
  if (subscriptionData && subscriptionData.topic === topic) {
    log(`WS: Already subscribed to ${topic} (ID=${subscriptionId}), skipping`);
    return subscriptionId;
  }

  addToPendingSubscriptions({
    subscriptionId,
    topic,
    callback: onMessageCallback,
  });

  checkClientConnectionActive();

  return subscriptionId;
};

//
//
// -----------------------------------------------------------------------------
//
//

const addToPendingSubscriptions = ({ subscriptionId, topic, callback }) => {
  globalWsState.pendingSubscriptionsById.set(subscriptionId, {
    topic,
    callback,
  });
};

//
//
// -----------------------------------------------------------------------------
//
//

const disconnectWS = ({ subscriptionId }) => {
  if (!subscriptionId) return false;

  let wasConnected = false;

  const subscriptionData = globalWsState.subscriptionsById.get(subscriptionId);
  if (subscriptionData) {
    wasConnected = unsubscribe({ subscriptionData });
    globalWsState.subscriptionsById.delete(subscriptionId);
  }

  globalWsState.pendingSubscriptionsById.delete(subscriptionId);

  scheduleClientDisconnectIfEligible();

  return wasConnected;
};

//
//
// -----------------------------------------------------------------------------
//
//

const unsubscribe = ({ subscriptionData }) => {
  try {
    if (
      subscriptionData.subscription &&
      globalWsState.client &&
      globalWsState.client.connected
    ) {
      subscriptionData.subscription.unsubscribe();

      log(
        `WS: Unsubscribed from ${subscriptionData.topic} (ID=${subscriptionData.subscriptionId})`
      );
      return true; // wasConnected
    }
  } catch (error) {
    console.error(
      `WS: Error unsubscribing from ${subscriptionData.topic} (ID=${subscriptionData.subscriptionId}):`,
      error
    );
  }

  return false;
};

//
//
// -----------------------------------------------------------------------------
//
//

const checkClientConnectionActive = () => {
  // Clean up the existing client if it's in a bad state
  if (
    globalWsState.client &&
    (globalWsState.client.state === 'CLOSED' ||
      globalWsState.client.state === 'CLOSING')
  ) {
    try {
      globalWsState.client.deactivate();
    } catch (error) {
      console.warn('WS: Error cleaning up old client:', error);
    }
    globalWsState.client = null;
    globalWsState.connecting = false;
    log('WS: Client was recreated due to bad state');
  }

  // Prevent multiple connection attempts
  if (
    globalWsState.connecting ||
    (globalWsState.client &&
      globalWsState.client.connected &&
      globalWsState.client.state === 'CONNECTED')
  ) {
    processPendingSubscriptions();
    return;
  }

  // Only create a new client if we don't have one
  if (!globalWsState.client) {
    globalWsState.connecting = true;
    log('WS: Creating new client connection');

    globalWsState.client = new Client({
      brokerURL: config.WS_URL,
      debug: readWebsocketDebugMessagesAndTrackConnectionState,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    if (process.env.JEST_WORKER_ID === undefined) {
      globalWsState.client.webSocketFactory = socketFactory;
    }

    globalWsState.client.onConnect = () => {
      // Reset on a successful connection:
      globalWsState.connecting = false;
      globalWsState.reconnectCounter = 0;
      globalWsState.reconnectLastErrorType = null;
      log('WS: Client connected successfully');

      resubscribeActiveSubscriptions();
      processPendingSubscriptions();
    };
    globalWsState.client.onDisconnect = () => {
      globalWsState.connecting = false;
      globalWsState.subscriptionsById.clear();
      globalWsState.pendingSubscriptionsById.clear();
      log('WS: Client disconnected');
    };
    globalWsState.client.onStompError = (frame) => {
      globalWsState.connecting = false;
      globalWsState.pendingSubscriptionsById.clear();
      console.log('Broker reported error: ' + frame.headers['message']);
      console.log('Additional details: ' + frame.body);
    };
    globalWsState.client.onWebSocketError = (error) => {
      globalWsState.connecting = false;
      globalWsState.pendingSubscriptionsById.clear();
      console.error('WS: WebSocket error:', error);
    };
    globalWsState.client.onWebSocketClose = (event) => {
      globalWsState.connecting = false;
      log('WS: WebSocket closed:', event);
    };

    try {
      globalWsState.client.activate();
    } catch (error) {
      globalWsState.connecting = false;
      console.error('WS: Failed to activate client:', error);
    }
  } else {
    // Client exists and is in a good state, just process pending subscriptions
    processPendingSubscriptions();
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const readWebsocketDebugMessagesAndTrackConnectionState = (strMessage) => {
  // console.log('debug: ', strMessage);

  // -- detect reconnect and increment the reconnect counter
  if (strMessage.includes('reconnect')) {
    getUserSession()
      .then(({ data, status }) => {
        console.log('Got user session response', { data, status });
        if (status === 502) {
          globalWsState.reconnectLastErrorType = BAD_GATEWAY_ERROR;
          globalWsState.reconnectCounter++;
        } else if (data && !data.loggedIn) {
          globalWsState.reconnectCounter++;
        } else {
          // Reset counter on successful authentication
          globalWsState.reconnectCounter = 0;
        }
      })
      .catch((error) => {
        console.log('Got error while getting user session', { error });
        globalWsState.reconnectCounter++;
      });
  }

  // -- if more than the maximum allowed reconnecting times -> deactivate
  if (globalWsState.reconnectCounter > MAX_RECONNECT_TIMES) {
    console.warn(
      'WS: Max reconnection attempts reached, clearing browser state...'
    );

    globalWsState.client.reconnectDelay = 0; // 0 - deactivates the sockClient
    store.dispatch(
      connectionError({
        errorType: globalWsState.reconnectLastErrorType ?? NO_CONNECTION_ERROR,
      })
    );
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const resubscribeActiveSubscriptions = () => {
  if (globalWsState.subscriptionsById.size === 0) return;

  // Move active subscriptions to pending for re-subscription
  let movedCount = 0;
  for (const [
    subscriptionId,
    subscriptionData,
  ] of globalWsState.subscriptionsById) {
    addToPendingSubscriptions({
      subscriptionId,
      topic: subscriptionData.topic,
      callback: subscriptionData.callback,
    });
    movedCount++;
  }

  // Clear the active subscriptions since they're now invalid
  globalWsState.subscriptionsById.clear();

  log(`WS: Moved ${movedCount} subscriptions to pending for re-subscription`);
};

//
//
// -----------------------------------------------------------------------------
//
//

const processPendingSubscriptions = ({ tries = 5 } = {}) => {
  if (globalWsState.pendingSubscriptionsById.size === 0) return;

  // Check if the client exists and is in a valid state
  if (
    !globalWsState.client ||
    globalWsState.client.state === 'CLOSED' ||
    globalWsState.client.state === 'CLOSING'
  ) {
    if (tries > 0) {
      setTimeout(() => {
        processPendingSubscriptions({ tries: tries - 1 });
      }, 300); // Slightly longer delay
      return;
    } else {
      console.error(
        `WS: Client is closing. Aborting all pending subscriptions.`
      );
      globalWsState.pendingSubscriptionsById.clear();
      return;
    }
  }

  if (globalWsState.client?.connected) {
    processPendingSubscriptionsNow();
  } else if (tries > 0) {
    // Client exists but not connected yet, retry
    setTimeout(() => {
      processPendingSubscriptions({ tries: tries - 1 });
    }, 300);
  } else {
    console.error(
      `WS: Client not connected after retries. Aborting all pending subscriptions.`
    );
    globalWsState.pendingSubscriptionsById.clear();
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const processPendingSubscriptionsNow = () => {
  const pendingSubscriptionsById = globalWsState.pendingSubscriptionsById;
  if (pendingSubscriptionsById.size === 0) return;

  const subscriptionIds = Array.from(pendingSubscriptionsById.keys());
  for (const subscriptionId of subscriptionIds) {
    processSinglePendingSubscription({ subscriptionId });
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const processSinglePendingSubscription = ({ subscriptionId }) => {
  const subscriptionsById = globalWsState.subscriptionsById;
  const pendingSubscriptionsById = globalWsState.pendingSubscriptionsById;
  const pendingSubscriptionData = pendingSubscriptionsById.get(subscriptionId);
  if (!pendingSubscriptionData) return;

  const { topic, callback } = pendingSubscriptionData;
  try {
    const subscriptionData = subscriptionsById.get(subscriptionId);
    if (subscriptionData) {
      unsubscribe({ subscriptionData });
      subscriptionsById.delete(subscriptionId);
    }

    const subscription = globalWsState.client.subscribe(topic, (msg) => {
      fireMessageCallback({ subscriptionId, msg });
    });

    // Store subscription in the global state
    subscriptionsById.set(subscriptionId, {
      topic,
      subscriptionId,
      subscription,
      callback,
    });

    pendingSubscriptionsById.delete(subscriptionId);
    log(`WS: Successfully subscribed to ${topic} (ID=${subscriptionId})`);
  } catch (error) {
    console.error(
      `WS: Failed to subscribe to ${topic} (ID=${subscriptionId}):`,
      error
    );
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const fireMessageCallback = ({ subscriptionId, msg }) => {
  const subscriptionData = globalWsState.subscriptionsById.get(subscriptionId);
  if (!subscriptionData?.callback) return;

  try {
    subscriptionData.callback(JSON.parse(msg.body));
  } catch (error) {
    console.warn('Failed forwarding websocket message', {
      msg,
      subscriptionData,
      error,
    });
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

const dumpStatus = () => {
  console.log('Websocket connections status', globalWsState);

  console.log('client: ', globalWsState.client);
  console.log('connecting: ', globalWsState.connecting);
  console.log(
    `reconnectCounter: ${
      globalWsState.reconnectCounter
    }/${MAX_RECONNECT_TIMES} (last error: ${
      globalWsState.reconnectLastErrorType ?? '-'
    })`
  );
  console.log('disconnectTimer: ', globalWsState.disconnectTimer);

  if (globalWsState.pendingSubscriptionsById.size > 0) {
    for (const pendingSubscriptionData of globalWsState.pendingSubscriptionsById.values()) {
      console.log(
        `pending: ${pendingSubscriptionData.topic} ID=${pendingSubscriptionData.subscriptionId}`,
        pendingSubscriptionData
      );
    }
  } else {
    console.log('No pending subscriptions');
  }

  if (globalWsState.subscriptionsById.size > 0) {
    for (const subscriptionData of globalWsState.subscriptionsById.values()) {
      console.log(
        `subscription: ${subscriptionData.topic} ID=${subscriptionData.subscriptionId}`,
        subscriptionData
      );
    }
  } else {
    console.log('No subscriptions');
  }

  console.log('nextSubscriptionId: ', globalWsState.nextSubscriptionId);
};

//
//
// -----------------------------------------------------------------------------
//
//

window.websockets = {
  status: dumpStatus,
  debug: () => {
    globalWsState.debugLogging = true;
    console.log('Enabled websocket debug logging');
  },
};
