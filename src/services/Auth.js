import SockJs from 'sockjs-client';

import Stomp from 'stompjs/lib/stomp.min.js';

class Auth {
  constructor() {
    this.notificationClient = null;
    this.sessionClient = null;
  }

  initNotificationClient = (topic, cb) => {
    this.notificationClient = Stomp.Stomp.over(new SockJs(config.WS_URL));
    this.notificationClient.debug = null;
    this.notificationClient.connect({}, () => {
      this.notificationClient.connected &&
        this.notificationClient.subscribe(topic.data, msg => {
          cb && cb(msg);
        });
    });
  };

  initSessionClient = (topic, cb) => {
    this.sessionClient = Stomp.Stomp.over(new SockJs(config.WS_URL));
    this.sessionClient.debug = null;
    this.sessionClient.connect({}, () => {
      this.sessionClient.connected &&
        this.sessionClient.subscribe(topic, msg => {
          cb && cb(msg);
        });
    });
  };

  close = () => {
    if (!this.notificationClient || !this.sessionClient) {
      return;
    }

    this.notificationClient.connected && this.notificationClient.disconnect();
    this.sessionClient.connected && this.sessionClient.disconnect();
  };
}

export default Auth;
