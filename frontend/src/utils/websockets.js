import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { connectionError } from '../actions/AppActions';
import { BAD_GATEWAY_ERROR, NO_CONNECTION_ERROR } from '../constants/Constants';
import store from '../store/store';
import { getUserSession } from '../api/userSession';

// Global connection state to prevent multiple connections
const globalWsState = {
  client: null,
  subscriptions: new Map(),
  connecting: false,
  clientConnectionId: 0, // Renamed: tracks client connection lifecycle
  pendingSubscriptions: new Map(), // Changed: Map to track per-subscription state
  disconnectTimer: null,
  DISCONNECT_DELAY: 3000, // 3 seconds delay before disconnecting
  nextSubscriptionId: 0, // Added: unique ID generator for subscriptions
};

//
//
// -----------------------------------------------------------------------------
//
//

function socketFactory() {
  return new SockJS(config.WS_URL);
}

//
//
// -----------------------------------------------------------------------------
//
//

// Create browserState utility inline to avoid import issues
const clearBrowserState = () => {
  try {
    console.log('Clearing browser state...');

    // Clear all storage
    localStorage.clear();
    sessionStorage.clear();

    // Clear service worker cache if present
    if ('serviceWorker' in navigator && 'caches' in window) {
      caches.keys().then((names) => {
        names.forEach((name) => {
          caches.delete(name);
        });
      });
    }

    console.log('Browser state cleared successfully');
  } catch (error) {
    console.error('Error clearing browser state:', error);
  }
};

//
//
// -----------------------------------------------------------------------------
//
//

function isClientEligibleForDisconnection() {
  return (
    globalWsState.subscriptions.size === 0 &&
    globalWsState.pendingSubscriptions.size === 0 &&
    !globalWsState.connecting &&
    globalWsState.client
  );
}

//
//
// -----------------------------------------------------------------------------
//
//

function scheduleDisconnectIfEmpty(onDisconnectCallback) {
  // Clear any existing disconnect timer
  if (globalWsState.disconnectTimer) {
    clearTimeout(globalWsState.disconnectTimer);
    globalWsState.disconnectTimer = null;
  }

  // Only schedule disconnect if no subscriptions and not connecting
  if (isClientEligibleForDisconnection()) {
    console.debug(
      `WS: Scheduling disconnect in ${globalWsState.DISCONNECT_DELAY}ms`
    );

    globalWsState.disconnectTimer = setTimeout(() => {
      // Double-check conditions haven't changed
      if (isClientEligibleForDisconnection()) {
        try {
          globalWsState.client.deactivate();
          globalWsState.client = null;
          globalWsState.disconnectTimer = null;
          console.debug('WS: Client deactivated after delay');

          if (onDisconnectCallback) {
            onDisconnectCallback();
          }
        } catch (error) {
          console.error('WS: Error deactivating client:', error);
        }
      }
    }, globalWsState.DISCONNECT_DELAY);
  }
}

//
//
// -----------------------------------------------------------------------------
//
//

export function connectWS(topic, onMessageCallback) {
  const maxReconnectTimesNo = 4;

  // Cancel any scheduled disconnect when new connection is requested
  if (globalWsState.disconnectTimer) {
    clearTimeout(globalWsState.disconnectTimer);
    globalWsState.disconnectTimer = null;
    console.debug('WS: Cancelled scheduled disconnect due to new subscription');
  }

  // Generate a unique subscription ID to track this specific subscription
  const subscriptionId = ++globalWsState.nextSubscriptionId;
  const clientConnectionId = globalWsState.clientConnectionId;

  // Avoid disconnecting and reconnecting to same topic.
  // IMPORTANT: we assume the "onMessageCallback" is the same
  if (this.sockTopic === topic) {
    // console.log("WS: Skip subscribing because already subscribed to %s", this.sockTopic);
    return;
  }

  const subscribe = ({ tries = 5 } = {}) => {
    // Check if this specific subscription was cancelled
    if (
      !globalWsState.pendingSubscriptions.has(topic) ||
      globalWsState.pendingSubscriptions.get(topic).subscriptionId !==
        subscriptionId
    ) {
      // Subscription was superseded or cancelled, abort
      console.debug(
        `WS: Subscription ${subscriptionId} to ${topic} was cancelled`
      );
      return;
    }

    // Check if the client connection was recreated (legitimate abort case)
    if (clientConnectionId !== globalWsState.clientConnectionId) {
      console.debug(
        `WS: Client was recreated, aborting subscription ${subscriptionId} to ${topic}`
      );
      globalWsState.pendingSubscriptions.delete(topic);
      return;
    }

    // Check if client exists and is in a valid state - KEY FIX
    if (
      !globalWsState.client ||
      globalWsState.client.state === 'CLOSED' ||
      globalWsState.client.state === 'CLOSING'
    ) {
      if (tries > 0) {
        setTimeout(() => {
          subscribe({ tries: tries - 1 });
        }, 300); // Slightly longer delay
        return;
      } else {
        console.error(
          `WS: Failed to subscribe to ${topic} - client not available`
        );
        globalWsState.pendingSubscriptions.delete(topic);
        return;
      }
    }

    if (globalWsState.client && globalWsState.client.connected) {
      try {
        this.sockSubscription = globalWsState.client.subscribe(topic, (msg) => {
          //console.log('WS: Got event on %s: %s', topic, msg.body);
          if (topic === this.sockTopic) {
            try {
              onMessageCallback(JSON.parse(msg.body));
            } catch (error) {
              console.log('Failed forwarding websocket message', {
                msg,
                onMessageCallback,
                error,
              });
            }
          } else {
            // console.warn(
            //   "Discard event because the WS topic changed. Current WS topic is %s",
            //   this.sockTopic
            // );
          }
        });

        this.sockTopic = topic;
        this.subscriptionId = subscriptionId; // Track subscription ID instead of connection ID

        // Track subscription globally
        globalWsState.subscriptions.set(topic, {
          subscription: this.sockSubscription,
          callback: onMessageCallback,
          subscriptionId,
          clientConnectionId,
          context: this,
        });

        globalWsState.pendingSubscriptions.delete(topic);
        console.debug(
          `WS: Successfully subscribed to ${topic} (subscriptionId=${subscriptionId})`
        );
      } catch (error) {
        console.error(`WS: Failed to subscribe to ${topic}:`, error);
        globalWsState.pendingSubscriptions.delete(topic);
        // If subscription fails, retry if we have tries left
        if (tries > 0) {
          setTimeout(() => {
            subscribe({ tries: tries - 1 });
          }, 500);
        }
      }
    } else if (tries > 0) {
      // Client exists but not connected yet, retry
      setTimeout(() => {
        subscribe({ tries: tries - 1 });
      }, 300);
    } else {
      console.error(
        `WS: Failed to subscribe to ${topic} - client not connected after retries`
      );
      globalWsState.pendingSubscriptions.delete(topic);
    }
  };

  const connect = () => {
    let clientWasRecreated = false;

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
      // Increment client connection ID when we recreate the client
      globalWsState.clientConnectionId++;
      clientWasRecreated = true;
      console.debug('WS: Client was recreated due to bad state');
    }

    // Prevent multiple connection attempts
    if (
      globalWsState.connecting ||
      (globalWsState.client &&
        globalWsState.client.connected &&
        globalWsState.client.state === 'CONNECTED')
    ) {
      globalWsState.pendingSubscriptions.set(topic, { subscriptionId });
      subscribe();
      return;
    }

    // Only create new client if we don't have one
    if (!globalWsState.client) {
      globalWsState.connecting = true;
      globalWsState.pendingSubscriptions.set(topic, { subscriptionId });
      let reconnectCounter = 0;
      let connectionErrorType = NO_CONNECTION_ERROR;

      // Only increment if we haven't already done so during cleanup
      if (!clientWasRecreated) {
        globalWsState.clientConnectionId++;
        console.debug('WS: Creating new client connection');
      }

      globalWsState.client = new Client({
        brokerURL: config.WS_URL,
        debug: (strMessage) => {
          // console.log('debug: ', strMessage);
          // -- detect reconnect and increment the reconnect counter
          if (strMessage.includes('reconnect')) {
            getUserSession()
              .then(({ data, status }) => {
                if (status === 502) {
                  connectionErrorType = BAD_GATEWAY_ERROR;
                  reconnectCounter += 1;
                } else if (data && !data.loggedIn) {
                  reconnectCounter += 1;
                } else {
                  // Reset counter on successful authentication
                  reconnectCounter = 0;
                }
              })
              .catch(() => {
                reconnectCounter += 1;
              });
          }

          // -- if more than max allowed to reconnect times  ->  deactivate
          if (reconnectCounter > maxReconnectTimesNo) {
            console.warn(
              'WS: Max reconnection attempts reached, clearing browser state...'
            );

            // Clear browser state and reload page as last resort
            try {
              clearBrowserState();
              // Give user a chance to see what happened
              setTimeout(() => {
                if (
                  window.confirm(
                    'Connection issues detected. Reload page to recover?'
                  )
                ) {
                  window.location.reload();
                }
              }, 2000);
            } catch (error) {
              console.error('Failed to clear browser state:', error);
            }

            globalWsState.client.reconnectDelay = 0; // 0 - deactivates the sockClient
            store.dispatch(
              connectionError({
                errorType: connectionErrorType,
              })
            );
          }
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });

      if (process.env.JEST_WORKER_ID === undefined) {
        globalWsState.client.webSocketFactory = socketFactory;
      }

      globalWsState.client.onConnect = () => {
        globalWsState.connecting = false;
        reconnectCounter = 0; // Reset on a successful connection
        console.debug('WS: Client connected successfully');

        // Process all pending subscriptions
        Array.from(globalWsState.pendingSubscriptions.keys()).forEach(
          (pendingTopic) => {
            if (pendingTopic === topic) {
              subscribe();
            }
          }
        );
      };

      globalWsState.client.onDisconnect = () => {
        globalWsState.connecting = false;
        console.debug('WS: Client disconnected');
      };

      globalWsState.client.onStompError = function (frame) {
        globalWsState.connecting = false;
        globalWsState.pendingSubscriptions.clear();
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      };

      globalWsState.client.onWebSocketError = (error) => {
        globalWsState.connecting = false;
        globalWsState.pendingSubscriptions.clear();
        console.error('WS: WebSocket error:', error);
      };

      // Add onWebSocketClose handler
      globalWsState.client.onWebSocketClose = (event) => {
        globalWsState.connecting = false;
        console.debug('WS: WebSocket closed:', event);
      };

      try {
        globalWsState.client.activate();
      } catch (error) {
        globalWsState.connecting = false;
        globalWsState.pendingSubscriptions.delete(topic);
        console.error('WS: Failed to activate client:', error);
      }
    } else {
      // Client exists and is in good state, just add to pending subscriptions
      globalWsState.pendingSubscriptions.set(topic, { subscriptionId });
      subscribe();
    }
  };

  const wasConnected = disconnectWS.call(this, connect);
  if (!wasConnected) {
    connect();
  }
}

//
//
// -----------------------------------------------------------------------------
//
//

export function disconnectWS(onDisconnectCallback) {
  let wasConnected = false;

  if (this.sockTopic && globalWsState.subscriptions.has(this.sockTopic)) {
    const subscriptionData = globalWsState.subscriptions.get(this.sockTopic);

    try {
      if (
        subscriptionData.subscription &&
        globalWsState.client &&
        globalWsState.client.connected
      ) {
        subscriptionData.subscription.unsubscribe();
        wasConnected = true;
      }
    } catch (error) {
      console.error(`WS: Error unsubscribing from ${this.sockTopic}:`, error);
    }

    globalWsState.subscriptions.delete(this.sockTopic);
    globalWsState.pendingSubscriptions.delete(this.sockTopic);
    console.debug(`WS: Unsubscribed from ${this.sockTopic}`);
  } else if (this.sockSubscription) {
    // Fallback for old-style subscriptions
    try {
      if (globalWsState.client && globalWsState.client.connected) {
        this.sockSubscription.unsubscribe();
        wasConnected = true;
      }
    } catch (error) {
      console.error('WS: Error unsubscribing fallback:', error);
    }
  }

  // Clean up context references
  this.sockTopic = null;
  this.sockSubscription = null;
  this.subscriptionId = null; // Changed from connectionId

  // Schedule delayed disconnect if no more subscriptions - KEY CHANGE
  scheduleDisconnectIfEmpty(onDisconnectCallback);

  return wasConnected;
}
