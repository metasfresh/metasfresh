import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

export function connectWS(topic, onMessageCallback) {
  // Avoid disconnecting and reconnecting to same topic.
  // IMPORTANT: we assume the "onMessageCallback" is same
  if (this.sockTopic === topic) {
    // console.log("WS: Skip subscribing because already subscrinbed to %s", this.sockTopic);
    return;
  }

  const subscribe = ({ tries = 3 } = {}) => {
    if (this.sockClient.connected || tries <= 0) {
      this.sockSubscription = this.sockClient.subscribe(topic, msg => {
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
    this.sock = new SockJs(config.WS_URL);
    this.sockClient = Stomp.Stomp.over(this.sock);
    this.sockClient.debug = null;
    this.sockClient.connect({}, subscribe);
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
    this.sockClient.disconnect(onDisconnectCallback);
    this.sockTopic = null;
  }

  return connected;
}
