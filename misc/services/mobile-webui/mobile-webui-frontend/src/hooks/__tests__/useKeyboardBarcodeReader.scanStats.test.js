import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader } from '../useKeyboardBarcodeReader';

// barcodeScanned is stamped once the scan is already assembled, so the delivery window is
// otherwise unrecorded. Config values below are production's.

const RATE_MS = 1000;
const MIN_LENGTH = 7;
const PLAIN = '1234567890123';
// A real, complete HU global QR code - parses, so it force-completes on its final character.
const HU_QR =
  'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU",' +
  '"packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,' +
  '"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';

let now;
let eventTs;

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
    const event = new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true });
    // timeStamp is readonly and stamped at construction; override so the test drives it separately
    // from the mocked wall clock.
    Object.defineProperty(event, 'timeStamp', { value: eventTs, configurable: true });
    window.dispatchEvent(event);
  });
}

// Types `str`, pausing `gapMs` before the character at each index in `gapsAt`.
function typeString(str, { gapsAt = [], gapMs = 0, stepMs = 1 } = {}) {
  for (let i = 0; i < str.length; i += 1) {
    const step = gapsAt.includes(i) ? gapMs : stepMs;
    now += step;
    eventTs += step;
    pressKey(str[i]);
  }
}

function goIdleAndTick() {
  now += RATE_MS * 3;
  eventTs += RATE_MS * 3;
  act(() => {
    jest.advanceTimersByTime(RATE_MS * 2);
  });
}

beforeEach(() => {
  now = 10_000;
  eventTs = 10_000;
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
    expect(stats.scanCharCount).toBe(13);
    expect(stats.scanMaxCharGapMs).toBeLessThan(RATE_MS);
    expect(stats.scanChunkCount).toBe(0);
    // Delivery only: 13 characters 1 ms apart. Must NOT include the idle wait that follows.
    expect(stats.scanDurationMs).toBe(12);
  });

  it('counts chunks and reports the worst gap when a code arrives in pieces', () => {
    const onReadDone = mountReader();
    // Past the chunk threshold (rateMs/2) but under rateMs: counted as chunked, not split.
    typeString(PLAIN, { gapsAt: [4, 9], gapMs: 600 });
    goIdleAndTick();

    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(PLAIN); // non-vacuity: the scan really did survive intact
    expect(stats.scanChunkCount).toBe(2);
    expect(stats.scanMaxCharGapMs).toBe(600);
    expect(stats.scanDurationMs).toBe(1210); // 10 x 1 ms steps between chars + two 600 ms pauses
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

  // gapMs is stall-immune post-fix, so it alone cannot show a blocked thread. The wall-clock
  // counterpart can: the two diverge by exactly the block, which is what says whether the
  // event-clock fix is saving scans in the field.
  it('separates a blocked thread from a real pause', () => {
    const onReadDone = mountReader();
    // Characters keep their 1 ms cadence; the WALL clock jumps 1200 ms mid-scan, i.e. the thread
    // was blocked and the queued keystrokes were processed late.
    for (let i = 0; i < PLAIN.length; i += 1) {
      if (i === 6) now += 1200;
      now += 1;
      eventTs += 1;
      pressKey(PLAIN[i]);
    }
    goIdleAndTick();

    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(PLAIN); // not split - the event clock saw no gap
    expect(stats.scanMaxCharGapMs).toBeLessThan(10); // hardware cadence was fine
    expect(stats.scanMaxProcessingGapMs).toBeGreaterThanOrEqual(1200); // the block is visible here
  });

  // Boundary: without this, a mutation from >= to > survives.
  it('counts a gap of exactly the chunk threshold, not only gaps beyond it', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { gapsAt: [5], gapMs: RATE_MS / 2 });
    goIdleAndTick();

    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(PLAIN); // non-vacuity: the scan survived, so the gap really was under rateMs
    expect(stats.scanChunkCount).toBe(1);
  });

  it('does not count a gap one millisecond below the threshold', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { gapsAt: [5], gapMs: RATE_MS / 2 - 1 });
    goIdleAndTick();

    const [, stats] = onReadDone.mock.calls[0];
    expect(stats.scanChunkCount).toBe(0);
  });

  // Every other case completes via the idle flush; this one takes the content-completion path.
  it('reports stats for a scan that content-completes instead of going idle', () => {
    const onReadDone = mountReader();
    typeString(HU_QR, { stepMs: 2 });
    // No goIdleAndTick: a parseable HU QR force-completes on its closing character.

    expect(onReadDone).toHaveBeenCalledTimes(1);
    const [code, stats] = onReadDone.mock.calls[0];
    expect(code).toBe(HU_QR);
    expect(stats.scanCharCount).toBe(HU_QR.length);
    expect(stats.scanDurationMs).toBeGreaterThan(0);
    expect(stats.scanChunkCount).toBe(0);
  });

  // A paste has no per-character delivery, so timings must be null rather than zero.
  it('reports null timings for a clipboard paste, with the length still recorded', async () => {
    const onReadDone = mountReader();
    const pasted = 'PASTED1234567';
    Object.defineProperty(navigator, 'clipboard', {
      value: { readText: () => Promise.resolve(pasted) },
      configurable: true,
    });

    await act(async () => {
      window.dispatchEvent(new KeyboardEvent('keydown', { key: 'v', ctrlKey: true, bubbles: true, cancelable: true }));
      await Promise.resolve();
    });

    expect(onReadDone).toHaveBeenCalledWith(pasted, expect.any(Object));
    const [, stats] = onReadDone.mock.calls[0];
    expect(stats.scanCharCount).toBe(pasted.length);
    expect(stats.scanDurationMs).toBeNull();
    expect(stats.scanMaxCharGapMs).toBeNull();
  });

  it('starts a fresh measurement per scan rather than accumulating across scans', () => {
    const onReadDone = mountReader();
    typeString(PLAIN, { gapsAt: [4], gapMs: 600 });
    goIdleAndTick();
    typeString(PLAIN, { stepMs: 1 });
    goIdleAndTick();

    const [, first] = onReadDone.mock.calls[0];
    const [, second] = onReadDone.mock.calls[1];
    expect(first.scanChunkCount).toBe(1);
    expect(second.scanChunkCount).toBe(0); // the previous scan's chunk must not leak in
    expect(second.scanMaxCharGapMs).toBeLessThan(600);
  });
});
