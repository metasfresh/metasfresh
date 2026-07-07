import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader, IDLE_ABANDON_MS } from '../useKeyboardBarcodeReader';

// These tests cover the HOOK MECHANIC (gap-independence, content-complete flush, Enter/Tab
// terminator, idle fallback, abandon-on-stuck). The classification of a code as
// NOT_APPLICABLE / PARTIAL_SCAN / COMPLETE_SCAN is exhaustively covered by the util tests
// (src/__tests__/utils/qrCode/{hu,common}.test.js) and is NOT re-derived here — we only use a
// real HU QR code and a plain barcode as representative inputs.

// Prod SysConfig debounceMillis sits in the 300-1000 ms range; use the low end.
const RATE_MS = 300;
const MIN_LENGTH = 10;
// IDLE_ABANDON_MS (the hook's fixed long-idle "abandon"/"stuck partial" deadline, decoupled from
// rateMs) is imported from the hook so a rename/retune can't silently desync this test.

// A real, complete HU global QR code (with nested JSON objects) — parses successfully.
const HU_QR =
  'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';

// A recognised HU QR whose JSON payload has not fully arrived (opening braces never closed).
const PARTIAL_HU_QR = 'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU"';

// Manually-controlled monotonic clock so the inter-keystroke gap logic is deterministic.
let now;

function mountReader(props = {}) {
  const onReadDone = jest.fn();
  const onReadInProgress = jest.fn();

  function TestComponent() {
    useKeyboardBarcodeReader({
      onReadDone,
      onReadInProgress,
      rateMs: RATE_MS,
      minLength: MIN_LENGTH,
      ...props,
    });
    return null;
  }

  const utils = render(<TestComponent />);
  return { onReadDone, onReadInProgress, ...utils };
}

function pressKey(key, opts = {}) {
  act(() => {
    const event = new KeyboardEvent('keydown', {
      key,
      bubbles: true,
      cancelable: true,
      ...opts,
    });
    window.dispatchEvent(event);
  });
}

// Type a string as window keydown events. `gapAtIndex` inserts ONE long inter-keystroke
// gap (>= rateMs) before that character; every other character arrives fast (< rateMs).
function typeString(str, { gapAtIndex, stepMs = 10, gapMs = RATE_MS + 100 } = {}) {
  for (let i = 0; i < str.length; i += 1) {
    now += i === gapAtIndex ? gapMs : stepMs;
    pressKey(str[i]);
  }
}

// Advance past the idle (rateMs) threshold and let the flush interval fire.
function goIdleAndTick() {
  now += RATE_MS * 3;
  act(() => {
    jest.advanceTimersByTime(RATE_MS * 2);
  });
}

beforeEach(() => {
  now = 10_000;
  jest.spyOn(Date, 'now').mockImplementation(() => now);
  // Legacy fake timers so the flush setInterval only runs when we explicitly advance,
  // and never auto-fires mid-scan.
  jest.useFakeTimers('legacy');
});

afterEach(() => {
  jest.useRealTimers();
  Date.now.mockRestore();
  jest.clearAllMocks();
});

describe('useKeyboardBarcodeReader', () => {
  it('force-completes a complete HU QR on JSON-close WITHOUT waiting for the idle timer, despite a mid-scan gap', () => {
    const { onReadDone } = mountReader();

    // One long gap in the middle of the JSON payload — the real-world symptom (a long QR arrives
    // in chunks spread over seconds). The old gap-based reader split it into fragments here.
    typeString(HU_QR, { gapAtIndex: Math.floor(HU_QR.length / 2) });

    // The closing '}' makes the buffer a complete, terminal HU QR → force-completed immediately,
    // with NO idle-timer advance (no goIdleAndTick), exactly once, with the full code.
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(HU_QR);
  });

  it('holds a partial HU QR back across an idle tick (mid-scan gap), then force-completes when the final chunk closes the JSON', () => {
    const { onReadDone } = mountReader();

    const splitAt = Math.floor(HU_QR.length / 2);
    const firstChunk = HU_QR.substring(0, splitAt); // JSON still open => PARTIAL_SCAN
    const secondChunk = HU_QR.substring(splitAt);

    // First chunk arrives, then a real inter-chunk gap that crosses an idle tick.
    typeString(firstChunk);
    now += RATE_MS + 50;
    act(() => {
      jest.advanceTimersByTime(RATE_MS * 2);
    });
    // Held back — a still-incomplete recognised QR must NOT be flushed as a fragment.
    expect(onReadDone).not.toHaveBeenCalled();

    // The remaining chunk arrives and closes the JSON → force-completed on content (no idle wait).
    typeString(secondChunk);
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(HU_QR);
  });

  it('separates back-to-back scans: a complete HU QR force-completes at its close even if another code follows immediately', () => {
    const { onReadDone } = mountReader();

    // A second (non-HU) code delivered right after the HU QR must NOT merge into it: the HU QR
    // force-completes at its terminal '}', resetting the buffer so the next code accumulates clean.
    const NEXT_CODE = 'LMQ#1#12.5';
    typeString(HU_QR + NEXT_CODE);

    // Exactly the HU QR was emitted (the trailing code did not merge into it).
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(HU_QR);

    // The trailing (NOT_APPLICABLE) code then completes via the idle-flush, on its own.
    goIdleAndTick();
    expect(onReadDone).toHaveBeenCalledTimes(2);
    expect(onReadDone).toHaveBeenNthCalledWith(2, NEXT_CODE);
  });

  it('separates two back-to-back plain codes split by a real gap shorter than one interval tick (must NOT merge)', () => {
    const { onReadDone } = mountReader();

    // Two independent non-QR scans. The gap between them (typeString default = rateMs+100) is
    // >= rateMs (a NEW scan is starting) but < 2*rateMs (before the idle interval would tick) — the
    // exact window in which a wholesale unconditional-append reader merges them into one garbage
    // string. The gap-flush must separate them because the first buffer is NOT_APPLICABLE (not a
    // still-arriving PARTIAL_SCAN).
    const CODE1 = '1111111111';
    const CODE2 = '2222222222';
    typeString(CODE1 + CODE2, { gapAtIndex: CODE1.length });

    // The gap flushed CODE1 as its own completed scan (not merged with CODE2).
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(CODE1);

    // CODE2 then completes on its own via the idle fallback — two distinct scans, never merged.
    goIdleAndTick();
    expect(onReadDone).toHaveBeenCalledTimes(2);
    expect(onReadDone).toHaveBeenNthCalledWith(2, CODE2);
  });

  it('completes on an Enter keydown with the full buffer (reacts to the device terminator)', () => {
    const { onReadDone } = mountReader();

    // Plain (non-QR, NOT_APPLICABLE) code: completion here comes from the Enter terminator.
    const CODE = '1234567890ABCDEF';
    typeString(CODE);
    expect(onReadDone).not.toHaveBeenCalled();

    now += 10;
    pressKey('Enter');

    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(CODE);
  });

  it('completes a Tab-terminated scan with the full buffer', () => {
    const { onReadDone } = mountReader();

    const CODE = 'ABCDEFGHIJ12345';
    typeString(CODE);
    expect(onReadDone).not.toHaveBeenCalled();

    now += 10;
    pressKey('Tab');

    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(CODE);
  });

  it('a plain (non-QR) barcode completes via the idle-timer fallback', () => {
    const { onReadDone } = mountReader();

    const CODE = '2100001234567'; // EAN-like weight label, not a recognised streamed QR code
    typeString(CODE);
    expect(onReadDone).not.toHaveBeenCalled();

    goIdleAndTick();

    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(CODE);
  });

  it('the FAST idle tier does NOT flush a recognised, still-incomplete HU QR (no mid-scan truncation)', () => {
    const { onReadDone } = mountReader();

    typeString(PARTIAL_HU_QR);
    goIdleAndTick();

    expect(onReadDone).not.toHaveBeenCalled();
  });

  it('does NOT merge a genuinely-stuck partial HU QR with a later re-scan: the stuck partial is abandoned on the new-scan gap and the new code completes on its own', () => {
    const { onReadDone } = mountReader();

    // A truncated HU QR whose JSON never closes: it stays PARTIAL_SCAN and, with no further
    // keystrokes and no idle tick here, sits buffered.
    typeString(PARTIAL_HU_QR);
    expect(onReadDone).not.toHaveBeenCalled();

    // The operator waits longer than any legit inter-chunk gap, then re-scans a fresh, DISTINCT
    // (different id), complete HU QR. The stuck partial must be flushed (abandoned) on this new-scan
    // gap — NOT merged with the re-scan — so the new code assembles and completes cleanly on its own.
    const HU_QR_RESCAN =
      'HU#1#{"id":"11111111111111111111111111111-99999","packingInfo":{"huUnitType":"TU"},"product":{"id":2000002,"code":"9999","name":"Re-scan"},"attributes":[]}';
    now += IDLE_ABANDON_MS + RATE_MS;
    typeString(HU_QR_RESCAN);

    // The new scan completed as itself; nothing was emitted as "stuck-partial + new" merged garbage.
    expect(onReadDone).toHaveBeenCalledWith(HU_QR_RESCAN);
    const emittedCodes = onReadDone.mock.calls.map((call) => call[0]);
    expect(emittedCodes).not.toContain(PARTIAL_HU_QR + HU_QR_RESCAN);
    // No emitted code is longer than the re-scan (a merge would carry the stuck partial's chars too).
    expect(emittedCodes.every((code) => code.length <= HU_QR_RESCAN.length)).toBe(true);
  });

  it('surfaces a SHORT (< minLength) stuck partial HU QR abandoned on a new-scan gap, instead of silently dropping it', () => {
    const { onReadDone } = mountReader();

    // A truncated partial shorter than minLength that still classifies as PARTIAL_SCAN. If the
    // new-scan-gap abandon enforced minLength (the pre-fix behaviour) this would be silently
    // dropped; it must instead be surfaced (matching the interval-based abandon path) so the app
    // shows its "QR not recognised" error rather than swallowing the code.
    const SHORT_PARTIAL = 'HU#1#{'; // PARTIAL_SCAN, length 6 < MIN_LENGTH (10)
    typeString(SHORT_PARTIAL);
    expect(onReadDone).not.toHaveBeenCalled();

    // Operator gives up and re-scans after longer than any legit inter-chunk gap: the new-scan gap
    // (NOT the interval poll — no timer advance here) abandons the short partial, surfacing it.
    now += IDLE_ABANDON_MS + RATE_MS;
    pressKey('X');

    expect(onReadDone).toHaveBeenCalledWith(SHORT_PARTIAL);
  });

  it('eventually ABANDONS and flushes a genuinely stuck / truncated HU QR after the long idle deadline (surfaces the app error instead of hanging)', () => {
    const { onReadDone } = mountReader();

    typeString(PARTIAL_HU_QR);

    // Within the fast idle window it must stay buffered (in-flight QR is protected)...
    now += RATE_MS * 2;
    act(() => {
      jest.advanceTimersByTime(RATE_MS * 2);
    });
    expect(onReadDone).not.toHaveBeenCalled();

    // ...but past the abandon deadline it is flushed unconditionally so the app can react.
    now += IDLE_ABANDON_MS + RATE_MS;
    act(() => {
      jest.advanceTimersByTime(RATE_MS * 2);
    });
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(PARTIAL_HU_QR);
  });

  it('honours an explicit idleAbandonMs param: a stuck partial is abandoned after the CONFIGURED window, not IDLE_ABANDON_MS', () => {
    // Wire a SHORT configured window (as the sysconfig barcodeScanner.inputText.idleAbandonMillis
    // would, via HardwareModePanel → useKeyboardBarcodeReader's idleAbandonMs param). The stuck
    // partial must surface after THIS window, and it must NOT still be waiting on the 15000 ms default.
    const CONFIGURED_ABANDON_MS = 1000;
    expect(CONFIGURED_ABANDON_MS).toBeLessThan(IDLE_ABANDON_MS); // guard: the override is genuinely shorter
    const { onReadDone } = mountReader({ idleAbandonMs: CONFIGURED_ABANDON_MS });

    typeString(PARTIAL_HU_QR);

    // Just past the CONFIGURED window (but far below the 15000 ms default) the partial is abandoned
    // and flushed — proving the param, not the module default, drives the deadline.
    now += CONFIGURED_ABANDON_MS + RATE_MS;
    act(() => {
      jest.advanceTimersByTime(RATE_MS * 2);
    });
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith(PARTIAL_HU_QR);
  });
});
