import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader } from '../useKeyboardBarcodeReader';

// The delivery window - first character to completion - is currently unrecorded: barcodeScanned is
// stamped only once the scan is already assembled, so a code that took 13 s to arrive and one that
// took 200 ms produce identical rows. These stats close that gap, riding on the SAME event.
//
// Production sysconfig, read from prod ui_trace eventdata: debounceMillis=1000, minLen=7.

const RATE_MS = 1000;
const MIN_LENGTH = 7;
const PLAIN = '1234567890123';

let now;

function mountReader() {
  const onReadDone = jest.fn();
  function TestComponent() {
    useKeyboardBarcodeReader({
      onReadDone,
      onReadInProgress: () => {},
      rateMs: RATE_MS,
      minLength: MIN_LENGTH,
    });
    return null;
  }
  render(<TestComponent />);
  return onReadDone;
}

function pressKey(key) {
  act(() => {
    window.dispatchEvent(new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true }));
  });
}

// Types `str`, pausing `gapMs` before the character at each index in `gapsAt`.
function typeString(str, { gapsAt = [], gapMs = 0, stepMs = 1 } = {}) {
  for (let i = 0; i < str.length; i += 1) {
    now += gapsAt.includes(i) ? gapMs : stepMs;
    pressKey(str[i]);
  }
}

function goIdleAndTick() {
  now += RATE_MS * 3;
  act(() => {
    jest.advanceTimersByTime(RATE_MS * 2);
  });
}

beforeEach(() => {
  now = 10_000;
  jest.spyOn(Date, 'now').mockImplementation(() => now);
  jest.useFakeTimers('legacy');
});

afterEach(() => {
  jest.useRealTimers();
  Date.now.mockRestore();
  jest.clearAllMocks();
});

describe('per-scan delivery stats on the completed scan', () => {
  it('reports the delivery window, length and a clean gap profile for a fast scan', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { stepMs: 1 });
    goIdleAndTick();

    expect(onReadDone).toHaveBeenCalledWith(PLAIN, expect.any(Object));
    const [, stats] = onReadDone.mock.calls[0];
    // 13 characters at 1 ms apart, measured from the FIRST character, then the idle wait.
    expect(stats.scanCharCount).toBe(13);
    expect(stats.scanMaxCharGapMs).toBeLessThan(RATE_MS);
    expect(stats.scanChunkCount).toBe(0);
    expect(stats.scanDurationMs).toBeGreaterThan(0);
  });

  it('counts chunks and reports the worst gap when a code arrives in pieces', () => {
    const onReadDone = mountReader();
    // Two pauses of 300 ms - chunked, but each well under rateMs so the scan is NOT split.
    typeString(PLAIN, { gapsAt: [4, 9], gapMs: 300 });
    goIdleAndTick();

    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(PLAIN); // non-vacuity: the scan really did survive intact
    expect(stats.scanChunkCount).toBe(2);
    expect(stats.scanMaxCharGapMs).toBe(300);
    expect(stats.scanDurationMs).toBeGreaterThanOrEqual(600);
  });

  it('shows a near-split: the worst gap approaching rateMs without crossing it', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { gapsAt: [6], gapMs: RATE_MS - 30 });
    goIdleAndTick();

    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(PLAIN); // not split
    expect(stats.scanMaxCharGapMs).toBe(RATE_MS - 30);
    expect(stats.scanMaxCharGapMs).toBeLessThan(RATE_MS);
  });

  it('starts a fresh measurement per scan rather than accumulating across scans', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { gapsAt: [4], gapMs: 300 });
    goIdleAndTick();
    typeString(PLAIN, { stepMs: 1 });
    goIdleAndTick();

    const [, first] = onReadDone.mock.calls[0];
    const [, second] = onReadDone.mock.calls[1];
    expect(first.scanChunkCount).toBe(1);
    expect(second.scanChunkCount).toBe(0); // the previous scan's chunk must not leak in
    expect(second.scanMaxCharGapMs).toBeLessThan(300);
  });
});
