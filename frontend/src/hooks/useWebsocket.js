import { useEffect } from 'react';
import { connectWS, disconnectWS } from '../utils/websockets';

const WS_DEBUG = true;
const log = WS_DEBUG ? console.debug : () => {};

export const useWebsocket = ({ topic, traceName = '', onMessage }) => {
  useEffect(() => {
    if (!topic) {
      return () => {};
    }

    const wsState = {};

    if (topic) {
      connectWS.call(wsState, topic, (event) => {
        log(`[WS ${topic} ${traceName}] Received event`, { event });

        onMessage({ topic, event });
      });

      log(`[WS ${topic} ${traceName}] Connected`, { wsState });
    }

    return () => {
      if (!wsState) {
        return;
      }

      disconnectWS.call(wsState);
      log(`[WS ${topic} ${traceName}] Disconnected`, { wsState });
    };
  }, [topic]);
};
