import { useEffect } from 'react';
import { connectWS, disconnectWS } from '../utils/websockets';

export const useWebsocket = ({ topic, onMessage }) => {
  useEffect(() => {
    if (!topic) {
      return () => {};
    }

    const wsState = {};

    if (topic) {
      connectWS.call(wsState, topic, (event) => {
        console.debug(`[WS ${topic}] Received event`, { event });
        onMessage({ topic, event });
      });
      console.debug(`[WS ${topic}] Connected`, { wsState });
    }

    return () => {
      if (!wsState) {
        return;
      }

      disconnectWS.call(wsState);
      console.debug(`[WS ${topic}] Disconnected`, { wsState });
    };
  }, [topic]);
};
