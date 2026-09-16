import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader, IDLE_ABANDON_MS } from '../useKeyboardBarcodeReader';

// me03 31264 — does a long HU QR still FRAGMENT, or does it STALL, under the config
// production actually runs?  Client telemetry (10,948 scan events on intercheese prod):
//     debounce(rateMs)=1000   idleAbandonMillis=15000   minLen(triggerOnChange...)=7
// The customer video shows "Scan läuft…" (a held PARTIAL buffer) for ~8 s, and UI-Trace shows
// click->dialog gaps of 14 / 14 / 21 s — suspiciously close to the 15 s abandon window.
//
// Hypothesis under test: the reader no longer SPLITS a long QR (30625 fixed that), but a scan
// whose tail never arrives is now HELD for the full idleAbandonMs before anything happens —
// turning a fragmentation bug into a ~15 s stall.

const RATE_MS = 1000;      // production debounce
const MIN_LENGTH = 7;      // production triggerOnChangeIfLengthGreaterThan

const HU_QR =
  'HU#1#{"id":"6c25cb118489b9f4e52b15d44cd9-08081","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"EUR-Tauschpalette Holz"},"product":{"id":2005881,"code":"100333","name":"AdR Raclette carr Knoblauch, 1/4 Laib ca. 1.5kg"},"attributes":[{"code":"HU_BestBeforeDate","displayName":"Mindesthaltbarkeit","value":"2026-10-27"},{"code":"Lot-Nummer","displayName":"Lot-Nummer","value":"1M2608180066"},{"code":"WeightNet","displayName":"Gewicht Netto","value":"72.000"}]}';

let now;

function mountReader() {
  const onReadDone = jest.fn();
  const onReadInProgress = jest.fn();
  function C() {
    useKeyboardBarcodeReader({ onReadDone, onReadInProgress, rateMs: RATE_MS, minLength: MIN_LENGTH });
    return null;
  }
  render(<C />);
  return { onReadDone };
}

function pressKey(key) {
  act(() => {
    window.dispatchEvent(new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true }));
  });
}

function type(str, { gapAtIndex, stepMs = 10, gapMs } = {}) {
  for (let i = 0; i < str.length; i += 1) {
    now += i === gapAtIndex ? (gapMs ?? RATE_MS + 500) : stepMs;
    pressKey(str[i]);
  }
}

function advance(ms) {
  now += ms;
  act(() => { jest.advanceTimersByTime(ms); });
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

describe('me03 31264 — production config (rateMs=1000, minLen=7, idleAbandon=15000)', () => {
  it('a COMPLETE HU QR with a mid-scan stall is NOT split (30625 stays fixed)', () => {
    const { onReadDone } = mountReader();
    type(HU_QR, { gapAtIndex: 120, gapMs: RATE_MS + 2000 }); // 3 s stall mid-JSON
    advance(50);
    // eslint-disable-next-line no-console
    console.log('[31264] complete-with-stall -> calls=' + onReadDone.mock.calls.length +
                ' len=' + (onReadDone.mock.calls[0]?.[0]?.length ?? 0));
    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone.mock.calls[0][0]).toBe(HU_QR);   // whole code, not a fragment
  });

  it('a TRUNCATED HU QR is held for the FULL idleAbandon window before surfacing', () => {
    const { onReadDone } = mountReader();
    const truncated = HU_QR.slice(0, 200);             // tail never arrives
    type(truncated);

    advance(5000);
    const at5s = onReadDone.mock.calls.length;
    advance(5000);
    const at10s = onReadDone.mock.calls.length;
    advance(IDLE_ABANDON_MS);                          // past the abandon deadline
    const after = onReadDone.mock.calls.length;

    // eslint-disable-next-line no-console
    console.log(`[31264] truncated HU QR -> emitted after 5s=${at5s}, 10s=${at10s}, >15s=${after}` +
                ` (idleAbandonMs=${IDLE_ABANDON_MS})`);

    expect(at5s).toBe(0);      // nothing happens for the operator...
    expect(at10s).toBe(0);     // ...still nothing...
    expect(after).toBe(1);     // ...only after the abandon window does anything surface
  });

  it('a PLAIN (non-QR) barcode completes after rateMs, not after idleAbandon', () => {
    const { onReadDone } = mountReader();
    type('1743738');
    advance(RATE_MS * 3);
    // eslint-disable-next-line no-console
    console.log('[31264] plain barcode -> calls=' + onReadDone.mock.calls.length +
                ' value=' + JSON.stringify(onReadDone.mock.calls[0]?.[0]));
    expect(onReadDone).toHaveBeenCalledTimes(1);
  });
});
