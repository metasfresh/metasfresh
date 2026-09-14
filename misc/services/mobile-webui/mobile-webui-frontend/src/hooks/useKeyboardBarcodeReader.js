import { useLayoutEffect, useRef } from 'react';
import { checkPartialScannedCode, ScanCompleteness } from '../utils/qrCode/common';

// Abandon window for a stuck/truncated streamed QR partial (see the note in the effect below).
// FIXED, and deliberately DECOUPLED from rateMs: it must sit safely ABOVE the largest legit
// inter-chunk gap (observed 3-8 s) so a slow-but-completing chunked scan is never re-split, yet
// it must NOT scale with the debounce — with the old Math.max(3000, rateMs * 10) a debounce of
// 300 ms shrank this to 3 s, i.e. INTO the real inter-chunk range, which would abandon a genuine
// chunked scan mid-stream. 15 s clears the max real gap with margin and is independent of debounce.
// Exported so the test asserts against the real value instead of mirroring a magic number.
export const IDLE_ABANDON_MS = 15000;

export const useKeyboardBarcodeReader = ({
  onReadDone,
  onReadInProgress,
  rateMs = 50,
  minLength = 10,
  idleAbandonMs = IDLE_ABANDON_MS,
  disabled = false,
}) => {
  // Use refs so values persist across rerenders but don't trigger state updates
  const bufferRef = useRef('');
  const lastKeyTimeRef = useRef(0);

  // useLayoutEffect (not useEffect): the keydown listener must attach/detach synchronously in the
  // commit phase, BEFORE the browser paints — not in a post-paint passive effect. This closes two
  // sub-frame gaps that would otherwise silently drop a scan:
  //  - mount: a scanner screen renders its offscreen input during commit, but a passive useEffect
  //    runs a task later, so a scan fired between "input painted" and "listener attached" is lost
  //    (repro: e2e scanWorkstation/scanWorkplace timed out ~15% of runs without the removed retry guard);
  //  - switch: moving between screens, the outgoing scanner's detach and the incoming scanner's attach
  //    happen in the same commit, so a scan fired in the gap isn't caught by the WRONG (outgoing) scanner.
  // The reader owns document-level keydown, so attaching one frame earlier has no layout/paint cost
  // and no behavioural change for any consumer.
  useLayoutEffect(() => {
    // A recognised-but-incomplete streamed QR code (e.g. a long HU QR arriving in chunks over
    // several seconds) is kept buffered across inter-keystroke gaps instead of being flushed as a
    // fragment. If it never completes (genuinely truncated: device disconnect / out-of-range) we
    // abandon and flush it once this window elapses — either while idle (below) or when a brand-new
    // scan arrives after this long a gap (the pre-append flush below). Surfacing the app's "QR not
    // recognised" error and, crucially, preventing a stuck partial from swallowing the next scan.
    // The window itself (idleAbandonMs, decoupled from rateMs) is a hook param defaulting to
    // IDLE_ABANDON_MS (defined and explained at module scope above); a caller may override it via a
    // sysconfig (barcodeScanner.inputText.idleAbandonMillis) the same way rateMs is wired from
    // barcodeScanner.inputText.debounceMillis.

    const resetBuffer = () => {
      bufferRef.current = '';
      lastKeyTimeRef.current = 0;
    };

    // Emit the assembled buffer as a completed scan and reset for the next one.
    // Capture-then-reset before firing onReadDone: the callback may re-render / unmount this
    // component, so the refs must already be in their next-scan state.
    const completeScan = ({ shouldEnforceMinLength }) => {
      const code = bufferRef.current;
      resetBuffer();
      if (code && (!shouldEnforceMinLength || !minLength || code.length >= minLength)) {
        onReadDone(code);
      }
    };

    const handleKeyDown = async (event) => {
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
          onReadDone(clipboardText);
          resetBuffer();
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
        // Enter/Tab terminator: a device configured to send Enter/Tab as a KeyEvent finishes the
        // current scan immediately. Kept as a belt-and-suspenders path — production Zebra devices
        // currently send NO terminator, but one may be enabled later. An explicit terminator is an
        // end-of-scan signal, so do not gate on minLength; fires regardless of the timing.
        if ((event.key === 'Enter' || event.key === 'Tab') && bufferRef.current) {
          completeScan({ shouldEnforceMinLength: false });
          event.preventDefault();
        }
      }
      //
      // Printable characters
      else {
        const now = Date.now();

        // A real gap since the last keystroke means a NEW scan is starting: flush the current buffer
        // FIRST so two back-to-back codes are separated, not merged into one garbage string. A
        // still-arriving recognised format (PARTIAL_SCAN) is normally EXEMPT from this flush — a long
        // QR reaches the browser in chunks over several seconds and a mid-stream gap must NOT split
        // it — which keeps chunked-QR assembly working while preserving the deterministic back-to-back
        // separation for NOT_APPLICABLE / already-COMPLETE buffers.
        //
        // BUT a genuinely-stuck PARTIAL (truncated scan whose JSON never closes) must not swallow the
        // NEXT scan forever: once the gap exceeds idleAbandonMs — well beyond the largest legit
        // inter-chunk gap — the partial is abandoned here too, so the incoming keystroke starts a
        // clean new scan instead of merging into the stuck buffer. (A re-scan WITHIN that window is
        // NOT isolated immediately: its keystrokes still append to the stuck partial and merely
        // restart the abandon window from the re-scan's last keystroke, so the combined buffer is
        // flushed by the idle-abandon fallback below rather than here. The guarantee here is only
        // that a deliberate re-scan after the gap has exceeded idleAbandonMs — the operator has
        // clearly given up — is never merged.)
        const gapMs = now - lastKeyTimeRef.current;
        const isPartial = checkPartialScannedCode(bufferRef.current) === ScanCompleteness.PARTIAL_SCAN;
        if (bufferRef.current && gapMs >= rateMs && (!isPartial || gapMs >= idleAbandonMs)) {
          // A normal back-to-back scan (non-partial) is length-gated exactly as before; a partial we
          // abandon here is surfaced unconditionally (shouldEnforceMinLength:false) — matching the
          // interval-based abandon path below — so a genuinely-stuck code reaches the app as its
          // "QR not recognised" error instead of being silently dropped.
          completeScan({ shouldEnforceMinLength: !isPartial });
        }

        bufferRef.current += event.key;
        onReadInProgress?.(bufferRef.current);
        // Prevent the browser from also inserting the character into a focused input.
        // The hook handles value updates via onReadInProgress. Without this, the character
        // would be inserted twice: once by onReadInProgress and once by the browser's default action.
        // (Before the readOnly→inputMode="none" change, readOnly prevented browser insertion.)
        event.preventDefault();
        lastKeyTimeRef.current = now;

        // Content-based completion: force-complete immediately (no idle wait) once the buffer is a
        // COMPLETE, TERMINAL recognised code. Safe ONLY because COMPLETE_SCAN is invariant-bound to
        // terminal codes (no continuation could yield a different valid code — see the
        // checkPartialScannedCode contract), and it correctly separates back-to-back scans instead
        // of merging them. shouldEnforceMinLength:false — a content-verified terminal code has
        // proven itself by content, so length is irrelevant (and the buffer is already cleared, so a
        // minLength drop would be silently unrecoverable). PARTIAL_SCAN holds the idle flush back; a
        // NOT_APPLICABLE (plain) code completes via the gap-flush above, Enter/Tab, or the idle-flush.
        if (checkPartialScannedCode(bufferRef.current) === ScanCompleteness.COMPLETE_SCAN) {
          completeScan({ shouldEnforceMinLength: false });
        }
      }
    };

    let intervalId;
    if (!disabled) {
      // Idle-timer fallback for codes without a content-completion signal (plain brace-less
      // barcodes: EAN / weight labels) and for genuinely stuck scans.
      intervalId = setInterval(() => {
        if (!bufferRef.current) {
          return;
        }
        const idleMs = Date.now() - lastKeyTimeRef.current;
        if (checkPartialScannedCode(bufferRef.current) === ScanCompleteness.PARTIAL_SCAN) {
          // A recognised QR code that is still incomplete: protect it from the normal idle flush so
          // a chunked scan is never truncated. Only the long abandon deadline flushes it, so a
          // genuinely truncated scan still reaches the app (as an error) instead of hanging.
          if (idleMs > idleAbandonMs) {
            completeScan({ shouldEnforceMinLength: false });
          }
        } else if (idleMs > rateMs) {
          // NOT_APPLICABLE (plain barcode) or COMPLETE_SCAN: normal debounce flush.
          completeScan({ shouldEnforceMinLength: false });
        }
      }, rateMs * 2);

      window.addEventListener('keydown', handleKeyDown);
      console.log('Enabled keyboard barcode reader', { rateMs, minLength });
    } else {
      resetBuffer();
    }

    // Clean up on unmount
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
      clearInterval(intervalId);
      console.log('Disabled keyboard barcode reader');
    };
  }, [onReadDone, onReadInProgress, rateMs, minLength, idleAbandonMs, disabled]);
};
