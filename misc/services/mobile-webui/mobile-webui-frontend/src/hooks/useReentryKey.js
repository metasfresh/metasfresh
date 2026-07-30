import { useEffect, useState } from 'react';

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
 * Listeners are registered here rather than via `useEventListener` because that helper is
 * window-only, while `visibilitychange` is dispatched at the document.
 */
export const useReentryKey = () => {
  const [key, setKey] = useState(0);

  useEffect(() => {
    const bump = () => setKey((previous) => previous + 1);
    const onVisibilityChange = () => {
      if (document.visibilityState !== 'hidden') {
        bump();
      }
    };

    window.addEventListener('focus', bump);
    document.addEventListener('visibilitychange', onVisibilityChange);
    return () => {
      window.removeEventListener('focus', bump);
      document.removeEventListener('visibilitychange', onVisibilityChange);
    };
  }, []);

  return key;
};
