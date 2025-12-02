import { useEffect, useRef } from 'react';
import { connectWS, disconnectWS } from '../utils/websockets';

const WS_DEBUG = true;
const log = WS_DEBUG ? console.debug : () => {};

export const useWebsocket = ({ topic, traceName = '', onMessage }) => {
  const wsStateRef = useRef({});
  const topicRef = useRef(topic);
  const onMessageRef = useRef(onMessage);

  // Update refs without triggering re-connection
  useEffect(() => {
    onMessageRef.current = onMessage;
  }, [onMessage]);

  useEffect(() => {
    if (!topic) {
      // Clean up if the topic becomes null/undefined
      if (topicRef.current) {
        disconnectWS.call(wsStateRef.current);
        log(
          `[WS ${topicRef.current} ${traceName}] Disconnected due to empty topic`
        );
      }
      topicRef.current = topic;
      return;
    }

    // Only reconnect if the topic actually changed
    if (topicRef.current !== topic) {
      // Disconnect from the previous topic
      if (topicRef.current) {
        disconnectWS.call(wsStateRef.current);
        log(
          `[WS ${topicRef.current} ${traceName}] Disconnected due to topic change`
        );
      }

      // Connect to the new topic
      topicRef.current = topic;

      try {
        connectWS.call(wsStateRef.current, topic, (event) => {
          log(`[WS ${topic} ${traceName}] Received event`, { event });

          // Use ref to get the latest callback to avoid stale closures
          if (onMessageRef.current) {
            onMessageRef.current({ topic, event });
          }
        });

        log(`[WS ${topic} ${traceName}] Connected`);
      } catch (error) {
        console.error(`[WS ${topic} ${traceName}] Connection failed:`, error);
      }
    }

    return () => {
      if (wsStateRef.current && topicRef.current) {
        try {
          disconnectWS.call(wsStateRef.current);
          log(`[WS ${topicRef.current} ${traceName}] Disconnected (cleanup)`);
        } catch (error) {
          console.error(
            `[WS ${topicRef.current} ${traceName}] Disconnection failed on cleanup:`,
            error
          );
        }
      }
    };
  }, [topic, traceName]); // Only depend on the topic and traceName, not onMessage
};
