import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

/**
 * Fully neutralizes the device / browser Back button: pressing it does NOTHING, on every screen.
 *
 * Why: the mobile app navigates with history.replace and declares its own per-screen back target,
 * reached via the on-screen footer Back button (useMobileNavigation.goBack). The browser history
 * stack holds *arrival order*, not the app's logical flow, so a browser Back is meaningless — it
 * used to drop the operator out of the PWA or jump to the wrong screen, and (when it happened to
 * have a target) behaved inconsistently from one screen to the next. Operators navigate with the
 * on-screen Back / Home buttons; the hardware/browser Back is banned outright, consistently.
 *
 * Mechanism: a popstate cannot be cancelled (the navigation already happened by the time it fires),
 * so we keep a throwaway "sentinel" entry on top of the browser history. When Back is pressed the
 * sentinel is popped; we immediately re-push it, so the URL / stack never actually moves — the press
 * is a no-op. The sentinel is re-primed after every real in-app navigation (history PUSH / REPLACE),
 * and explicitly NOT on POP (POP is the Back press we are absorbing; re-priming on it would stack up
 * spurious entries, and connected-react-router also emits a POP when the sentinel itself is popped).
 *
 * Note: this never calls history.go(-1). The footer Back (useMobileNavigation.goBack) also avoids
 * go(-1) — it navigates to the declared backLocation, else Home — so the footer Back is a
 * history.replace and is never swallowed by this trap.
 */
export const useDeviceBackButton = () => {
  const history = useHistory();

  useEffect(() => {
    primeSentinel();

    const unlisten = history.listen((location, action) => {
      if (action !== 'POP') {
        primeSentinel();
      }
    });

    // Browser / device Back is fully neutralized: re-push the sentinel and do nothing else.
    const onPopState = () => primeSentinel();
    window.addEventListener('popstate', onPopState);

    return () => {
      unlisten();
      window.removeEventListener('popstate', onPopState);
    };
  }, [history]);
};

// Push a throwaway history entry at the current URL. pushState never fires popstate and is not
// observed by the history library (it only reacts to its own push/replace and to popstate), so this
// does not trigger an app navigation or re-enter the listen() above.
const primeSentinel = () => {
  window.history.pushState(null, '', window.location.href);
};
