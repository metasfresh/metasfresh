import { useEffect, useRef } from 'react';

/**
 * Polls while {@code isActive}: calls {@code poll()} every {@code intervalMillis}, the next request only after the
 * previous one finished, so polls never overlap. Polling restarts when {@code pollKey} changes.
 * <ul>
 * <li>A result is passed to {@code onResult} unless polling stopped meanwhile or {@code markChanged} was called after
 * the request was sent: a state set from another response is never overwritten by an older poll answer.</li>
 * <li>{@code onTick} runs after every poll, answered or not; a failed poll is only logged.</li>
 * </ul>
 *
 * @returns {{ markChanged: function }} to call whenever the polled state is set from another response
 */
export const usePollWhile = ({ isActive, pollKey, intervalMillis, poll, onResult, onTick }) => {
  // the latest callbacks, so a running poll never calls the ones of an earlier render
  const callbacksRef = useRef(null);
  callbacksRef.current = { poll, onResult, onTick };
  const changeCountRef = useRef(0);

  useEffect(() => {
    if (!isActive) {
      return;
    }

    let isStopped = false;
    let timeoutId = null;
    const pollOnce = () => {
      const changeCountAtSend = changeCountRef.current;
      callbacksRef.current
        .poll()
        .then((result) => {
          if (!isStopped && changeCountRef.current === changeCountAtSend) {
            callbacksRef.current.onResult(result);
          }
        })
        .catch((axiosError) => console.warn('Failed polling', { pollKey, axiosError }))
        .finally(() => {
          if (!isStopped) {
            callbacksRef.current.onTick?.();
            timeoutId = setTimeout(pollOnce, intervalMillis);
          }
        });
    };
    timeoutId = setTimeout(pollOnce, intervalMillis);

    return () => {
      isStopped = true;
      clearTimeout(timeoutId);
    };
  }, [isActive, pollKey, intervalMillis]);

  return {
    markChanged: () => {
      changeCountRef.current += 1;
    },
  };
};
