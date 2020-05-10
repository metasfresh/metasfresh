import wsConnection from '../utils/websockets';

class Auth {
  constructor() {
    this.notificationClient = null;
    this.sessionClient = null;
  }

  initNotificationClient = (topic, cb) => {
    wsConnection.subscribeTopic(topic.data, cb);
  };

  initSessionClient = (topic, cb) => {
    wsConnection.subscribeTopic(topic, cb);
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
