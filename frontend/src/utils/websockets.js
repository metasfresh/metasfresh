// import { noConnection } from '../actions/WindowActions';
// import { setWebsocketsStatus } from '../actions/WebsocketsActions';
// import { store } from '../containers/App';
// import { getUserSession } from '../api';
// import _ from 'lodash';

import { getWSformatURLfromHTTP } from './index';

import Stomp from 'stompjs/lib/stomp.min.js';
class WS {
  constructor() {
    if (!WS.instance) {
      WS.instance = this;
    }
    this.subscriptions = [];

    if (!this.sockClient) {
      this.sockClient = Stomp.Stomp.client(
        getWSformatURLfromHTTP(config.WS_URL)
      );
      this.sockClient.connect({}, () => {});
    }
    this.sockClient.onConnect = function() {
      // console.log('CONNECTED:', this);
    };

    this.sockClient.onDisconnect = function() {
      // console.log('DISCONNECTED:', this);
    };

    return WS.instance;
  }

  getSocketConnection = () => {
    return this.sockClient;
  };

  activateConnection = () => {
    if (!this.sockClient._active) {
      this.sockClient.activate();
    }
  };

  getStatus = () => {
    return this.sockClient._active;
  };

  unsubscribeTopic = (topic) => {
    this.sockClient.unsubscribe(topic);
  };

  subscribeTopic = (topic, onMessageCallback) => {
    // console.log('TOPIC =>', topic);
    // console.log('CB =>', onMessageCallback);
    // TODO: multiple subscription store ;)
    this.sockClient.subscribe(topic, (msg) => {
      onMessageCallback && onMessageCallback(msg);
    });
  };
}

const wsConnection = new WS();
Object.freeze(wsConnection);
export default wsConnection;
