import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { connectionError } from '../actions/AppActions';
import { BAD_GATEWAY_ERROR, NO_CONNECTION_ERROR } from '../constants/Constants';
import store from '../store/store';
import _ from 'lodash';
import { getUserSession } from '../api/userSession';

function socketFactory() {
  return new SockJS(config.WS_URL);
}

export function connectWS(topic, onMessageCallback) {
  const maxReconnectTimesNo = 4; // -- set here the max number of reconnect times
  // Avoid disconnecting and reconnecting to same topic.
  // IMPORTANT: we assume the "onMessageCallback" is same
  if (this.sockTopic === topic) {
    // console.log("WS: Skip subscribing because already subscrinbed to %s", this.sockTopic);
    return;
  }

  const subscribe = ({ tries = 3 } = {}) => {
    if (this.sockClient.connected || tries <= 0) {
      this.sockSubscription = this.sockClient.subscribe(topic, (msg) => {
        // console.log("WS: Got event on %s: %s", topic, msg.body);
        if (topic === this.sockTopic) {
          onMessageCallback(JSON.parse(msg.body));
        } else {
          // console.warn(
          //   "Discard event because the WS topic changed. Current WS topic is %s",
          //   this.sockTopic
          // );
        }
      });

      this.sockTopic = topic;
      // console.log("WS: Subscribed to %s (tries=%s)", this.sockTopic, tries);
    } else {
      // not ready yet
      setTimeout(() => {
        subscribe({ tries: tries - 1 });
      }, 200);
    }
  };

  const connect = () => {
    let reconnectCounter = 0;
    let connectionErrorType = NO_CONNECTION_ERROR;
    this.sockClient = new Client({
      brokerURL: config.WS_URL,
      debug(strMessage) {
        // console.log('debug: ', strMessage);
        // -- detect reconnect and increment the reconnect counter
        if (strMessage.includes('reconnect')) {
          getUserSession()
            .then(({ data, status }) => {
              reconnectCounter =
                data && !data.loggedIn ? reconnectCounter + 1 : 0;

              if (status === 502) connectionErrorType = BAD_GATEWAY_ERROR;
            })
            .catch(() => {
              reconnectCounter += 1;
            });
        }

        // -- if more than max allowed reconnect times  ->  deactivate
        if (reconnectCounter > maxReconnectTimesNo) {
          this.reconnectDelay = 0; // 0 - deactivates the sockClient
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
      this.sockClient.webSocketFactory = socketFactory;
    }
    this.sockClient.onConnect = subscribe;

    /*eslint-disable no-console */
    this.sockClient.onStompError = function (frame) {
      // Will be invoked in case of error encountered at Broker
      // Bad login/passcode typically will cause an error
      // Complaint brokers will set `message` header with a brief message. Body may contain details.
      // Compliant brokers will terminate the connection after any error
      console.log('Broker reported error: ' + frame.headers['message']);
      console.log('Additional details: ' + frame.body);
    };
    /*eslint-enable no-console */

    // this.sockClient.onUnhandledFrame = (e) => console.log('onUnhandledFrame: ', e);
    // this.sockClient.onUnhalndedMessage = (e) => console.log('onUnhandledMessage: ', e);
    // this.sockClient.onUnhandledReceipt = (e) => console.log('onUnhandledReceipt: ', e)
    // this.sockClient.onWebSocketClose = (e) => console.log('onWebSocketClose: ', e)
    // this.sockClient.onWebSocketError = (e) => console.log('onWebSocketError: ', e)

    this.sockClient.activate();
  };

  const wasConnected = disconnectWS.call(this, connect);
  if (
    !wasConnected ||
    (this.sockTopic !== topic && _.includes(topic, 'view'))
  ) {
    connect();
  }
}

export function disconnectWS(onDisconnectCallback) {
  const connected =
    this.sockClient && this.sockClient.connected && this.sockSubscription;

  if (connected) {
    // console.log("WS: Unsubscribing from %s", this.sockTopic);
    this.sockSubscription.unsubscribe();
    this.sockClient.deactivate(onDisconnectCallback);
    this.sockTopic = null;
  }

  return connected;
}
