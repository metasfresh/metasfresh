import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

/**
 * Neutralises the device / browser Back button: pressing it does NOTHING. It never leaves the PWA and
 * never changes the visible screen. Operators navigate exclusively with the on-screen footer Back
 * button (which goes to the screen's declared backLocation, else Home) and the Home button.
 *
 * Why a pure no-op — and explicitly NOT "replay the footer Back" (a design we tried and reverted):
 *  - The app navigates with history.replace, so the browser history stack holds *arrival order*, not
 *    the app's logical screen flow. A raw browser Back (history.go(-1)) is therefore meaningless and
 *    used to drop the operator out of the PWA / jump to the wrong screen.
 *  - Replaying the footer Back from HERE cannot be done correctly: this trap is mounted ONCE (in
 *    ApplicationRoot) and has no access to the active screen's declared back target. A screen passes
 *    its own backLocation to its OWN useMobileNavigation({ backLocation }) instance (via
 *    useScreenDefinition); this hook, calling useMobileNavigation() with no argument, only ever sees
 *    the headers-derived backLocation — which is pushed in a useEffect and so lags the active screen.
 *    So mirroring navigated to a stale / wrong target, and the extra history.replace it issued
 *    mismanaged the sentinel stack below, so a *second* Back could pop a real entry and exit the PWA —
 *    the exact failure this feature exists to prevent.
 *  - Therefore: absorb the Back and do nothing. The footer Back, which lives inside each screen and
 *    holds the correct screen-local backLocation, owns all real navigation.
 *
 * Mechanism: a popstate cannot be cancelled (the navigation already happened by the time it fires), so
 * we keep a throwaway "sentinel" entry on top of the browser history. A Back press pops the sentinel —
 * never a real app entry, so the browser never leaves the PWA and the screen never changes — and the
 * popstate handler simply re-primes the sentinel. The sentinel is (re)primed on mount and after every
 * real navigation (history PUSH / REPLACE) but NOT on POP (POP is the Back press we are handling;
 * connected-react-router also emits a POP when the sentinel itself is popped).
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

    // Device / browser Back: re-prime the sentinel and do nothing else. The sentinel is therefore
    // always on top of the browser history, so a Back press can only ever pop the sentinel — never a
    // real app entry → the operator can never be dropped out of the PWA, and the current screen is
    // left untouched. Real navigation is the footer Back button's job.
    const onPopState = () => {
      primeSentinel();
      // Field diagnostic for handheld debugging: proves every device/browser Back is a no-op. If the
      // app ever exits or changes screen on Back, this line (captured from the device console) shows
      // it was not this handler.
      console.log(`[deviceBack] Back pressed on ${window.location.pathname} → no-op (PWA unchanged)`);
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
