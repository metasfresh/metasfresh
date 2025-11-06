import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

class Auth {
  constructor() {
    this.sessionClient = null;
  }

  initSessionClient = (topic, cb) => {
    this.sessionClient = Stomp.Stomp.over(new SockJs(config.WS_URL));
    this.sessionClient.debug = null;

    this.sessionClient.connect({}, () => {
      this.sessionClient.connected &&
        this.sessionClient.subscribe(topic, (msg) => {
          cb && cb(msg);
        });
    });
  };

  close = () => {
    if (!this.sessionClient) {
      return;
    }

    this.sessionClient.connected && this.sessionClient.disconnect();
  };
}

export default Auth;
