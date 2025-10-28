import { useEffect, useRef } from 'react';

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

      // Ignore key events with Ctrl, Alt or Meta modifiers
      if (event.ctrlKey || event.altKey || event.metaKey) {
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
