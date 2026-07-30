import { useEffect, useRef, useState } from 'react';

/**
 * A key that increases each time the operator RE-ENTERS the app — the window regains focus, or the
 * document becomes visible again after being hidden (app switch, tab switch, screen unlock).
 *
 * Feed it into the dependency array of an effect that loads session-global operator context (active
 * workplace, assigned workstation) so that context is re-read on re-entry instead of only on mount.
 * A screen that stays mounted across an app switch otherwise displays the value it loaded when it
 * first mounted, forever — see the module CLAUDE.md, "Shared operator state must not be read once
 * per mount".
 *
 * One re-entry yields exactly ONE increment. A single return to the app fires BOTH `focus` and
 * `visibilitychange`, and on React 17 a setState from a native listener is not batched — so counting
 * every event would run the dependent effect twice, firing two requests and, when the read fails,
 * showing the operator two error toasts for one failure. The key therefore tracks the background ->
 * foreground transition rather than an event count; this is the same reason TanStack Query's
 * focusManager and SWR's revalidateOnFocus hold a boolean rather than a counter.
 *
 * Listeners are registered here rather than via `useEventListener` because that helper is
 * window-only, while `visibilitychange` is dispatched at the document.
 */
export const useReentryKey = () => {
  const [key, setKey] = useState(0);
  const isForegroundRef = useRef(document.visibilityState !== 'hidden');

  useEffect(() => {
    const onLeftForeground = () => {
      isForegroundRef.current = false;
    };
    const onReturnedToForeground = () => {
      // The sibling event of a return already counted it — one transition, one increment.
      if (isForegroundRef.current) {
        return;
      }
      isForegroundRef.current = true;
      setKey((previous) => previous + 1);
    };
    const onVisibilityChange = () => {
      if (document.visibilityState === 'hidden') {
        onLeftForeground();
      } else {
        onReturnedToForeground();
      }
    };

    window.addEventListener('blur', onLeftForeground);
    window.addEventListener('focus', onReturnedToForeground);
    document.addEventListener('visibilitychange', onVisibilityChange);
    return () => {
      window.removeEventListener('blur', onLeftForeground);
      window.removeEventListener('focus', onReturnedToForeground);
      document.removeEventListener('visibilitychange', onVisibilityChange);
    };
  }, []);

  return key;
};
