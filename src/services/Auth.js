import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

class Auth {
    constructor() {
        this.notificationClient = null;
    }

    initNotificationClient = (topic, cb) => {
        const sock = new SockJs(config.WS_URL);
        this.notificationClient = Stomp.Stomp.over(sock);
        this.notificationClient.debug = null;
        this.notificationClient.connect({}, () => {
            this.notificationClient.connected &&
                this.notificationClient.subscribe(topic.data, msg => {
                    cb && cb(msg);
                });
        });
    }

    closeNotificationClient = () => {
        this.notificationClient && this.notificationClient.disconnect();
    }
}

export default Auth;
