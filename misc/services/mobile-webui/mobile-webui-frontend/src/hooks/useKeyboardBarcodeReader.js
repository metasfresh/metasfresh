import { useEffect, useLayoutEffect, useRef } from 'react';

export const useKeyboardBarcodeReader = ({
  onReadDone,
  onReadInProgress,
  rateMs = 50,
  minLength = 10,
  disabled = false,
}) => {
  // The callbacks are read through refs, so that a new callback (e.g. an inline function of a re-rendered
  // consumer) does not remove and re-add the window listener. A re-render can happen while a keydown is
  // being dispatched (another window listener changing state on the same key), and a listener removed at
  // that moment never sees the key: the scan's Enter was lost, its code stayed buffered and was delivered
  // again later or glued to the next scan. The refs are updated after each commit, not during render.
  const onReadDoneRef = useRef(onReadDone);
  const onReadInProgressRef = useRef(onReadInProgress);
  useLayoutEffect(() => {
    onReadDoneRef.current = onReadDone;
    onReadInProgressRef.current = onReadInProgress;
  });

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

          event.preventDefault(); // Prevent default paste behavior
          onReadDoneRef.current(clipboardText);
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
          onReadDoneRef.current(bufferRef.current);
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
          onReadInProgressRef.current?.(bufferRef.current);
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
            onReadDoneRef.current(bufferRef.current);
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
          onReadDoneRef.current(bufferRef.current);
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
  }, [rateMs, minLength, disabled]);
};
