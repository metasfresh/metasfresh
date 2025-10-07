import SockJS from 'sockjs-client';
import { BAD_GATEWAY_ERROR, NO_CONNECTION_ERROR } from '../constants/Constants';
import { Client } from '@stomp/stompjs';
import { getUserSession } from '../api/userSession';
import store from '../store/store';
import { connectionError } from '../actions/AppActions';
import { useEffect } from 'react';

const maxReconnectTimesNo = 4; // -- set here the max number of reconnect times

export const useWebsocketTopic = ({ topic, disabled, onMessage }) => {
  useEffect(() => {
    let sockClient = null;
    if (!disabled) {
      sockClient = connect({ topic, onMessage });
    }

    return () => {
      disconnectWS({ sockClient, topic });
    };
  }, [topic, disabled]);
};

function socketFactory() {
  return new SockJS(config.WS_URL);
}

const connect = ({ topic, onMessage }) => {
  let reconnectCounter = 0;
  let connectionErrorType = NO_CONNECTION_ERROR;
  const sockClient = new Client({
    brokerURL: config.WS_URL,
    reconnectDelay: 5000,
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
    debug: (strMessage) => {
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

      // -- if more than max allowed to reconnect times  ->  deactivate
      if (reconnectCounter > maxReconnectTimesNo) {
        //this.reconnectDelay = 0; // 0 - deactivates the sockClient
        store.dispatch(
          connectionError({
            errorType: connectionErrorType,
          })
        );
      }
    },
  });

  if (process.env.JEST_WORKER_ID === undefined) {
    sockClient.webSocketFactory = socketFactory;
  }

  sockClient.onConnect = () => {
    subscribe({ sockClient, topic, onMessage, tries: 3 });
  };

  sockClient.onStompError = (frame) => {
    // Will be invoked in case of error encountered at Broker
    // Bad login/passcode typically will cause an error
    // Complaint brokers will set `message` header with a brief message. Body may contain details.
    // Compliant brokers will terminate the connection after any error
    console.log('Broker reported error: ' + frame.headers['message']);
    console.log('Additional details: ' + frame.body);
  };

  sockClient.activate();

  return sockClient;
};

const subscribe = ({ sockClient, topic, onMessage, tries = 3 }) => {
  if (sockClient.connected || tries <= 0) {
    console.log(`websocket ${topic} - Connected`);
    sockClient.subscribe(topic, (msg) => {
      console.log(`websocket ${topic} - Got message`, { msg });
      onMessage(JSON.parse(msg.body));
    });
    console.log(`websocket ${topic} - Subscribed`);
  } else {
    console.log(`websocket ${topic} - Not connected yet, retry subscribing...`);
    // not ready yet, try again
    setTimeout(() => {
      subscribe({ sockClient, topic, onMessage, tries: tries - 1 });
    }, 200);
  }
};

export function disconnectWS({ sockClient, topic }) {
  if (sockClient?.connected) {
    // console.log("WS: Unsubscribing from %s", this.sockTopic);
    // this.sockSubscription.unsubscribe();
    sockClient.deactivate();
    console.log(`websocket ${topic} - Disconnected`);
  }
}
