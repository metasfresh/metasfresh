import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

/**
 * Neutralises the device / browser Back button: pressing it does NOTHING. It never leaves the PWA and
 * never changes the visible screen — no matter how fast or how many times the operator presses it.
 * Operators navigate exclusively with the on-screen footer Back button (which goes to the screen's
 * declared backLocation, else Home) and the Home button.
 *
 * Why a pure no-op — and explicitly NOT "replay the footer Back" (a design we tried and reverted):
 * the trap is mounted once (ApplicationRoot) and has no access to the active screen's declared back
 * target, so mirroring navigated to a stale/wrong target and could drop the operator out of the PWA.
 * The footer Back, which holds the correct screen-local backLocation, owns all real navigation.
 *
 * Mechanism — a *buffer* of sentinels, not a single one. A popstate cannot be cancelled (the
 * navigation already happened by the time it fires), so we keep throwaway "sentinel" entries on top of
 * the browser history at the current URL. A Back press pops a sentinel — never a real app entry — so
 * the URL never moves and the browser never leaves the PWA.
 *
 * Why a BUFFER and not one sentinel: when the operator mashes the hardware Back button, the browser can
 * traverse several history entries in a single event-loop tick — *before* our popstate handler runs to
 * re-prime. A single sentinel absorbs only the first press; the rest pop straight through into the real
 * screens below and, with enough presses, out of the PWA entirely (the bug this is fixing). A deep
 * buffer means a whole burst only ever pops sentinels (all at the current URL ⇒ the screen never
 * changes), and because the browser caps session history at ~50 entries, flooding sentinels also evicts
 * the real screens out of the reachable window. SENTINEL_BUFFER is far more than any human can press in
 * one tick yet safely under the history cap; verified against synchronous-burst reproduction (see the
 * e2e spec deviceBackIsNoOp.spec.js — burst of dozens of presses stays a no-op, never escapes).
 *
 * The buffer is (re)built on mount and after every real navigation (history PUSH / REPLACE), and topped
 * up by one on each Back press (popstate) so it never depletes under sustained mashing. It is NOT
 * rebuilt on POP (POP is the Back press we are absorbing; connected-react-router also emits a POP when a
 * sentinel itself is popped).
 */
export const SENTINEL_BUFFER = 40;

export const useDeviceBackButton = () => {
  const history = useHistory();

  useEffect(() => {
    primeSentinels(SENTINEL_BUFFER);

    const unlisten = history.listen((location, action) => {
      // After a real navigation the new screen sits on top of the stack — rebuild the sentinel buffer
      // above it so Back keeps hitting only sentinels.
      if (action !== 'POP') {
        primeSentinels(SENTINEL_BUFFER);
      }
    });

    // Device / browser Back: refill the one sentinel just popped and do nothing else. The buffer stays
    // topped up across repeated presses, so a Back press can only ever pop a sentinel — never a real
    // entry → the screen never changes and the operator can never leave the PWA.
    const onPopState = () => {
      primeSentinel();
      // Field diagnostic for handheld debugging: proves every device/browser Back is a no-op. Gated
      // behind the app's diagnostics flag (set from the showAllErrorMessages SysConfig) so it is silent
      // in normal operation but can be switched on to capture the line from a device console.
      if (window.showAllErrorMessages) {
        console.log(`[deviceBack] Back pressed on ${window.location.pathname} → no-op (PWA unchanged)`);
      }
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
//
// The sentinel carries the CURRENT history state (the connected-react-router location state), NOT null.
// Why: when a sentinel is popped, the history library reads window.history.state to rebuild the
// location. A null state makes it synthesise a *new* location key for the same URL, so
// connected-react-router dispatches a LOCATION_CHANGE (action POP) — which remounts the active route
// and blanks screens whose content is async/websocket-loaded (the picking jobs list). Cloning the
// current state means a popped sentinel resolves to the *same* location key ⇒ no spurious location
// change ⇒ the screen is left untouched. (The buffer still protects against escape; state has no effect
// on history traversal.)
const primeSentinel = () => {
  window.history.pushState(window.history.state, '', window.location.href);
};

const primeSentinels = (count) => {
  for (let i = 0; i < count; i++) {
    primeSentinel();
  }
};
