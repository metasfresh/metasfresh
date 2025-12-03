import { useEffect, useRef } from 'react';
import {
  connectWS,
  disconnectWS,
  newSubscriptionId,
} from '../utils/websockets';

const WS_DEBUG = false;
const log = WS_DEBUG ? console.debug : () => {};

export const useWebsocket = ({
  topic: topicParam,
  traceName = '',
  onMessage,
}) => {
  const onMessageRef = useRef(onMessage);
  const topicRef = useRef();
  const subscriptionIdRef = useRef();

  const topic = normalizeTopicName(topicParam);

  // Update refs without triggering re-connection
  useEffect(() => {
    onMessageRef.current = onMessage;
  }, [onMessage]);

  useEffect(() => {
    // do nothing if no topic changed
    if (topicRef.current === topic) return;

    // Disconnect from the previous topic
    const oldSubscriptionId = subscriptionIdRef.current;
    if (oldSubscriptionId) {
      subscriptionIdRef.current = null;
      disconnectWS({ subscriptionId: oldSubscriptionId });
      log(
        `[WS ${topicRef.current} ID=${oldSubscriptionId} ${traceName}] Disconnected due to topic change`
      );
    }

    // Connect to the new topic
    if (topic) {
      const subscriptionId = newSubscriptionId();
      subscriptionIdRef.current = subscriptionId;
      topicRef.current = topic;

      try {
        connectWS({
          subscriptionId,
          topic: topic,
          onMessageCallback: (event) => {
            // log(
            //   `[WS ${topic} ID=${subscriptionId} ${traceName}] Received event`,
            //   { event }
            // );

            // Use ref to get the latest callback to avoid stale closures
            if (onMessageRef.current) {
              onMessageRef.current({ topic, event });
            }
          },
        });

        log(`[WS ${topic} ID=${subscriptionId} ${traceName}] Connected`);
      } catch (error) {
        console.error(
          `[WS ${topic} ID=${subscriptionId} ${traceName}] Connection failed:`,
          error
        );
      }
    }

    return () => {
      if (subscriptionIdRef.current) {
        try {
          disconnectWS({ subscriptionId: subscriptionIdRef.current });
          log(
            `[WS ${topicRef.current} ID=${subscriptionIdRef.current} ${traceName}] Disconnected (cleanup)`
          );
        } catch (error) {
          console.error(
            `[WS ${topicRef.current} ID=${subscriptionIdRef.current} ${traceName}] Disconnection failed on cleanup:`,
            error
          );
        }
      }
    };
  }, [topic, traceName]); // Only depend on the topic and traceName, not onMessage
};

//
//
// ----------------------------
//
//

const normalizeTopicName = (topic) => {
  if (!topic) return null;

  let topicNorm = topic.trim();
  if (topicNorm.length === 0) return null;

  return topicNorm;
};
