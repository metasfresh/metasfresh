import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

function socketFactory() {
  return new SockJS(config.WS_URL);
}

export function connectWS(topic, onMessageCallback) {
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
    this.sockClient = new Client({
      brokerURL: config.WS_URL,
      // debug: function(str) {
      //   console.log('debug: ', str);
      // },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    if (process.env.JEST_WORKER_ID === undefined) {
      this.sockClient.webSocketFactory = socketFactory;
    }
    this.sockClient.onConnect = subscribe;

    /*eslint-disable no-console */
    this.sockClient.onStompError = function(frame) {
      // Will be invoked in case of error encountered at Broker
      // Bad login/passcode typically will cause an error
      // Complaint brokers will set `message` header with a brief message. Body may contain details.
      // Compliant brokers will terminate the connection after any error
      console.log('Broker reported error: ' + frame.headers['message']);
      console.log('Additional details: ' + frame.body);
    };
    /*eslint-enable no-console */

    this.sockClient.activate({}, subscribe);
  };

  const wasConnected = disconnectWS.call(this, connect);
  if (!wasConnected) {
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
