import { useEffect, useRef } from 'react';
import * as uiTrace from '../utils/ui_trace';
import { toastError } from '../utils/toast';
import { trl } from '../utils/translations';

export const useKeyboardBarcodeReader = ({
  onReadDone,
  onReadInProgress,
  rateMs = 50,
  minLength = 10,
  disabled = false,
}) => {
  // Use refs so values persist across rerenders but don't trigger state updates
  const bufferRef = useRef('');
  const lastKeyTimeRef = useRef(0);

  useEffect(() => {
    const handleKeyDown = async (event) => {
      // console.log('[scanner] keydown', {
      //   key: event.key,
      //   alt: event.altKey,
      //   ctrl: event.ctrlKey,
      //   meta: event.metaKey,
      //   len: event.key?.length,
      // });

      warnIMEInjectionMode({ event });

      if (event.key === 'Unidentified') {
        return;
      }

      //
      // Handle Ctrl+V (or Cmd+V on Mac)
      if ((event.ctrlKey || event.metaKey) && event.key === 'v') {
        try {
          const clipboardText = await navigator.clipboard.readText();
          if (clipboardText?.length < minLength) {
            return;
          }
          console.log('Pasted text:', clipboardText);

          event.preventDefault(); // Prevent default paste behavior
          onReadDone(clipboardText);
          bufferRef.current = '';
          lastKeyTimeRef.current = 0;
          return;
        } catch (error) {
          console.error('Failed to read clipboard:', error);
        }
      }

      // Ignore key events with Ctrl or Meta modifiers
      if (event.ctrlKey || event.metaKey) {
        return;
      }
      // Composition / IME events, some browser extensions and Chrome autofill dispatch
      // keydown-like events with event.key === undefined. Bail out before any .length access.
      if (event.key == null) {
        return;
      }
      // Allow altKey for printable characters (Zebra MC3300x firmware sets altKey=true on scanner keystrokes)
      if (event.altKey && event.key.length !== 1) {
        return;
      }

      //
      // Non-printable characters
      if (event.key.length !== 1) {
        // Optional: if your barcode uses Enter/Tab to finish, handle here
        if ((event.key === 'Enter' || event.key === 'Tab') && bufferRef.current) {
          onReadDone(bufferRef.current);
          bufferRef.current = '';
          lastKeyTimeRef.current = 0;
          event.preventDefault();
        }
      }
      //
      // Printable characters
      else {
        const now = Date.now();
        //
        // If the type rate is kept, collect the character
        if (now - lastKeyTimeRef.current < rateMs) {
          bufferRef.current += event.key;
          onReadInProgress?.(bufferRef.current);
          // Prevent the browser from also inserting the character into a focused input.
          // The hook handles value updates via onReadInProgress. Without this, the character
          // would be inserted twice: once by onReadInProgress and once by the browser's default action.
          // (Before the readOnly→inputMode="none" change, readOnly prevented browser insertion.)
          event.preventDefault();
        }
        //
        // Type rate dropped => send the collected string if any
        else {
          if (bufferRef.current && (!minLength || bufferRef.current.length >= minLength)) {
            onReadDone(bufferRef.current);
          }
          bufferRef.current = event.key;
        }
        lastKeyTimeRef.current = now;
      }
    };

    // console.log('Enabling keyboard barcode reader', { disabled });
    let intervalId;
    if (!disabled) {
      // Flush leftovers if needed, using interval
      intervalId = setInterval(() => {
        if (bufferRef.current && Date.now() - lastKeyTimeRef.current > rateMs) {
          onReadDone(bufferRef.current);
          bufferRef.current = '';
          lastKeyTimeRef.current = 0;
        }
      }, rateMs * 2);

      window.addEventListener('keydown', handleKeyDown);
      console.log('Enabled keyboard barcode reader', { rateMs, minLength });
    } else {
      bufferRef.current = '';
      lastKeyTimeRef.current = 0;
    }

    // Clean up on unmount
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
      clearInterval(intervalId);
      console.log('Disabled keyboard barcode reader');
    };
  }, [onReadDone, onReadInProgress, rateMs, minLength, disabled]);
};

//
//
//
//
//

// Module-level: report once per page-load when IME-injection mode is detected. Chrome puts
// the literal string 'Unidentified' on KeyboardEvent.key when text arrives via Android's
// InputConnection (DataWedge IME route / soft IME commit) instead of as discrete KeyEvents.
// The window-level keystroke reader CANNOT capture text delivered that way — the commit
// goes straight to the focused editable input, not as a keyboard event.
// Reported to both console.warn (DevTools) and uiTrace (backend ui_trace table — visible
// without attaching DevTools to the device).
let imeModeReported = false;

const warnIMEInjectionMode = ({ event }) => {
  // IME-injection-mode detection: 'Unidentified' = the scanner / IME committed text via
  // Android InputConnection, not via real KeyEvents. The hook can't see the actual text
  // (it lives in the focused <input>'s value, not on the event). This codebase deliberately
  // supports KEYSTROKE output only — IME-mode delivery is a misconfiguration the user must
  // fix on the device side (e.g. DataWedge → Keystroke output → Send chars as events: Yes).
  // We surface this to three sinks with different cadences:
  //   - toastError on EVERY misconfigured scan (loud user signal, can't be missed)
  //   - console.warn once per page-load (DevTools — keeps the console scannable)
  //   - uiTrace.trace once per page-load (backend ui_trace table — diagnosable remotely)
  if (event.key !== 'Unidentified') {
    return;
  }

  // Loud user signal — fires on every misconfigured scan so the user gets repeat exposure
  // until they fix the device profile. trl() resolves to the user's active language.
  toastError({ plainMessage: trl('components.BarcodeScannerComponent.imeModeError') });

  if (imeModeReported) {
    return;
  }
  imeModeReported = true;
  const inputEl = document.getElementById('input-text');
  const traceParams = {
    delivered_via: 'Android InputConnection (DataWedge IME / soft IME) — not as keystrokes',
    offscreenInputReadOnly: inputEl?.readOnly,
    offscreenInputInputMode: inputEl?.inputMode,
    effect: 'IME-routed text never reaches the window-level keystroke reader → scan lost',
    fix:
      'switch the scanner profile to Keystroke output (UI events) — e.g. DataWedge → ' +
      'Keystroke output → Send chars as events: Yes. See ' +
      'mobile-webui-frontend/CLAUDE.md § Barcode Scanning Modes → Zebra DataWedge.',
  };
  console.warn('[scanner] IME-injection mode detected', traceParams);
  uiTrace.trace({ ...traceParams, eventName: 'scannerImeModeDetected' });
};
