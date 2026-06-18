import { useEffect, useRef } from 'react';
import { useHistory } from 'react-router-dom';
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
 * pressed the sentinel is popped; we navigate to the screen-declared backLocation instead (which,
 * being a history.replace, lays down a fresh sentinel — see the listen() below). When a screen
 * declares no back (home / launchers) we just re-push the sentinel — a deliberate no-op so the
 * operator can't accidentally leave the PWA mid-job.
 *
 * Note: this intentionally does NOT fall through to history.go(-1) (the browser-stack back this
 * trap neutralizes). useMobileNavigation.goBack no longer uses go(-1) either — it goes Home when
 * no backLocation is set — precisely so the footer Back can't be swallowed by this trap. See
 * useMobileNavigation.js.
 *
 * Known characteristic: a sentinel trap grows the browser history by ~1 entry per forward
 * navigation (Back navigation is net-neutral: it pops the sentinel and the replace+re-prime nets to
 * zero). This is inherent to the pushState technique and is memory-cheap; it resets on reload.
 */
export const useDeviceBackButton = () => {
  const { goTo } = useMobileNavigation();
  const backLocation = useBackLocationFromHeaders();
  const history = useHistory();

  // The latest "go back" action, kept in a ref so the once-installed listeners always act on the
  // current screen's declared back target without being re-installed on every render.
  const onBackRef = useRef();
  onBackRef.current = () => {
    if (backLocation) {
      // goTo() calls history.replace() internally; the listen() below then lays down a fresh sentinel.
      goTo(backLocation);
    } else {
      // Top of the stack: no navigation, so re-prime here to keep trapping Back (stay put).
      primeSentinel();
    }
  };

  useEffect(() => {
    primeSentinel();

    // Re-prime after every in-app navigation: goTo() uses history.replace, which overwrites the
    // previous sentinel, so each new screen needs a fresh one. We key off the history ACTION —
    // PUSH / REPLACE is a real navigation, while POP is the Back press we already handle in
    // onPopState; re-priming on POP would stack up spurious entries (and POP fires once more from
    // connected-react-router when the sentinel itself is popped).
    const unlisten = history.listen((location, action) => {
      if (action !== 'POP') {
        primeSentinel();
      }
    });

    const onPopState = () => onBackRef.current();
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
