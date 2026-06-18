import { useEffect, useRef } from 'react';
import { useHistory } from 'react-router-dom';
import { useMobileNavigation } from './useMobileNavigation';

/**
 * Routes the device / browser Back button through the app's own navigation: pressing it does exactly
 * what the on-screen footer Back button does — navigate to the current screen's declared backLocation,
 * or Home when the screen declares none (useMobileNavigation.goBack).
 *
 * Why: the app navigates with history.replace, so the browser history stack holds *arrival order*, not
 * the app's logical screen flow. A raw browser Back (history.go(-1)) is therefore meaningless — it used
 * to drop the operator out of the PWA or jump to the wrong screen. So we intercept the browser/device
 * Back and replay the in-app Back navigation, making hardware/browser Back and the footer Back behave
 * identically and consistently on every screen.
 *
 * Mechanism: a popstate cannot be cancelled (the navigation already happened by the time it fires), so
 * we keep a throwaway "sentinel" entry on top of the browser history. A Back press pops the sentinel —
 * never a real app entry, so the browser never leaves the PWA — and in the popstate handler we re-prime
 * the sentinel and invoke goBack(). goBack() navigates via history.replace, which the history.listen
 * below re-primes too. The sentinel is (re)primed on mount and after every real navigation (history
 * PUSH / REPLACE) but explicitly NOT on POP (POP is the Back press we are handling; connected-react-router
 * also emits a POP when the sentinel itself is popped).
 *
 * goBack is read through a ref so the popstate handler always uses the current screen's backLocation
 * (the handler is registered once, but goBack is re-created on every render as the location changes).
 */
export const useDeviceBackButton = () => {
  const history = useHistory();
  const { goBack } = useMobileNavigation();
  const goBackRef = useRef(goBack);
  goBackRef.current = goBack;

  useEffect(() => {
    primeSentinel();

    const unlisten = history.listen((location, action) => {
      if (action !== 'POP') {
        primeSentinel();
      }
    });

    // Device / browser Back: re-prime the sentinel (so the browser stack never escapes the app) and
    // replay the in-app Back navigation — identical to tapping the on-screen footer Back button.
    const onPopState = () => {
      primeSentinel();
      goBackRef.current();
    };
    window.addEventListener('popstate', onPopState);

    return () => {
      unlisten();
      window.removeEventListener('popstate', onPopState);
    };
  }, [history]);
};

// Push a throwaway history entry at the current URL. pushState never fires popstate and is not observed
// by the history library (it only reacts to its own push/replace and to popstate), so this does not
// trigger an app navigation or re-enter the listen() above.
const primeSentinel = () => {
  window.history.pushState(null, '', window.location.href);
};
