import { useEffect, useRef } from 'react';
import { useLocation } from 'react-router-dom';
import { useMobileNavigation } from './useMobileNavigation';
import { useBackLocationFromHeaders } from '../reducers/headers';

/**
 * Makes the device / browser Back button follow the app's own screen-declared back navigation
 * instead of the browser history stack.
 *
 * Why this is needed: the mobile app navigates with history.replace (see useMobileNavigation.goTo)
 * and every screen declares its own back target via headers (backLocation), independent of how the
 * operator actually arrived. The browser history stack therefore holds *arrival order*, not the
 * app's logical back graph — so the hardware Back button pops the wrong entry and breaks the flow.
 *
 * Mechanism: a popstate event cannot be cancelled (the navigation already happened by the time it
 * fires), so we keep a throwaway "sentinel" entry on top of the browser history. When Back is
 * pressed the sentinel is popped; we immediately re-push it (so the URL / stack never actually
 * moves) and instead navigate to the screen-declared backLocation. When a screen declares no back
 * (home / launchers) we re-push only — a deliberate no-op so the operator can't accidentally leave
 * the PWA mid-job.
 *
 * Note: this intentionally does NOT fall through to history.go(-1) the way useMobileNavigation.goBack
 * does when no backLocation is set — that go(-1) is the browser-stack back we are neutralizing here.
 */
export const useDeviceBackButton = () => {
  const { goTo } = useMobileNavigation();
  const backLocation = useBackLocationFromHeaders();
  const location = useLocation();

  // The popstate listener is installed once; keep the latest navigation context in a ref so it
  // always acts on the current screen's declared back target.
  const onBackRef = useRef();
  onBackRef.current = () => {
    if (backLocation) {
      goTo(backLocation);
    }
    // else: no declared back (top of stack) → stay put (the re-primed sentinel keeps us here).
  };

  // Re-prime the sentinel after every navigation: goTo() uses history.replace, which overwrites the
  // previous sentinel, so each screen must lay down a fresh one for the next Back to absorb.
  useEffect(() => {
    primeSentinel();
  }, [location.key]);

  useEffect(() => {
    const onPopState = () => {
      primeSentinel();
      onBackRef.current();
    };
    window.addEventListener('popstate', onPopState);
    return () => window.removeEventListener('popstate', onPopState);
  }, []);
};

// Push a throwaway history entry at the current URL. pushState never fires popstate and is invisible
// to the router (which only syncs on popstate), so this does not trigger an app navigation.
const primeSentinel = () => {
  window.history.pushState(null, '', window.location.href);
};
