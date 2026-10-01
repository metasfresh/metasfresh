import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader } from '../useKeyboardBarcodeReader';

// What a blocked main thread does to a scan, per code type. The gap is derived from Date.now(),
// i.e. when the HANDLER RAN, so queued keystrokes look late and the reader sees a gap the scanner
// never sent - measured in a bare browser as 1506 ms against 6 ms of e.timeStamp for the same
// events. What blocks the thread is not established, and this helps only when the gap is an
// artifact of delayed processing, never when the scanner genuinely paused.
//
// Modelled that way below: Date.now() jumps, the events' timestamps keep their cadence.
// Config values are production's.

const RATE_MS = 1000;
const MIN_LENGTH = 7;
const IDLE_ABANDON = 15000;

const HU_QR =
  'HU#1#{"id":"0de63cbd34708add7a9afbb423d0-05650","packingInfo":{"huUnitType":"LU","packingInstructionsId":1000006,"caption":"Euro Palette"},"product":{"id":1000001,"code":"2680","name":"Sternflow 11 Raps"},"attributes":[]}';
const LMQ = 'LMQ#1#{"lotNo":"1M2608280001"}';
const PICKING_SLOT = 'PICKING_SLOT#1#{"pickingSlotId":1000169,"caption":"slot1"}';
const PLAIN_DIGITS = '2968132037562';

let now; // mocked Date.now()  - jumps by the stall
let eventTs; // event.timeStamp    - never jumps; the hardware did not pause

function mountReader() {
  const onReadDone = jest.fn();
  // A split is a buffer RESTART, not an emission: a fragment below minLength is dropped silently
  // (completeScan resets before the length gate), and a full HU QR's content-completion is a
  // legitimate emission. A shrinking in-progress buffer catches both.
  const restarts = [];
  let prev = '';
  const onReadInProgress = (buf) => {
    if (buf.length <= prev.length) restarts.push(prev);
    prev = buf;
  };
  function TestComponent() {
    useKeyboardBarcodeReader({
      onReadDone,
      onReadInProgress,
      rateMs: RATE_MS,
      minLength: MIN_LENGTH,
      idleAbandonMs: IDLE_ABANDON,
    });
    return null;
  }
  const { unmount } = render(<TestComponent />);
  return { onReadDone, restarts, unmount };
}

function pressKey(key, { withEventTime = true } = {}) {
  act(() => {
    const event = new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true });
    // timeStamp is readonly; override so the test controls it. withEventTime:false drives the
    // hook's wall-clock fallback, otherwise uncovered.
    Object.defineProperty(event, 'timeStamp', { value: withEventTime ? eventTs : 0, configurable: true });
    window.dispatchEvent(event);
  });
}

// The stall advances the WALL CLOCK only: a 0 ms-delay wedge had already queued the characters,
// so their own timestamps keep their cadence.
function typeWithStall(code, { stallAtIndex, stallMs }) {
  for (let i = 0; i < code.length; i += 1) {
    if (i === stallAtIndex) now += stallMs; // handler resumes late...
    now += 1;
    eventTs += 1; // ...but the event was created on time
    pressKey(code[i]);
  }
}

// Advance past the idle threshold and let the flush interval fire.
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

describe('a main-thread stall must not split a scan', () => {
  const CASES = [
    { name: 'LMQ#          (NOT_APPLICABLE, unprotected)', code: LMQ },
    { name: 'PICKING_SLOT# (NOT_APPLICABLE, unprotected)', code: PICKING_SLOT },
    { name: 'plain digits  (NOT_APPLICABLE, unprotected)', code: PLAIN_DIGITS },
    { name: 'HU#1# QR      (PARTIAL, exempt to 15000ms)', code: HU_QR },
  ];

  // Characterisation matrix: prints what CURRENT code does, so before/after is directly comparable.
  it('MATRIX: how each code type survives stalls of 1.2s / 2s / 16s', () => {
    const rows = [];
    for (const { name, code } of CASES) {
      for (const stallMs of [1200, 2000, 16000]) {
        now = 10_000;
        eventTs = 10_000;
        jest.clearAllMocks();
        const { onReadDone, restarts, unmount } = mountReader();
        typeWithStall(code, { stallAtIndex: Math.floor(code.length / 2), stallMs });
        const emissions = onReadDone.mock.calls.map(([c]) => c);
        const split = restarts.length > 0;
        const intact = emissions.some((e) => e === code);
        unmount(); // each iteration mounts its own reader; leaving 12 alive would cross-talk
        rows.push(
          `  ${name}  stall=${String(stallMs).padStart(5)}ms  ` +
            `SPLIT=${split ? 'YES' : 'no '}  ` +
            `delivered_intact=${intact ? 'yes' : 'NO '}  ` +
            `emitted=${emissions.length}` +
            (split ? `  lost_fragment_len=${restarts[0].length}` : '')
        );
      }
    }
    console.log('\n=== stall vs code type (CURRENT code, Date.now() gap) ===');
    console.log('A SPLIT means the reader emitted a FRAGMENT mid-scan: the operator sees');
    console.log('"QR not recognised", re-scans, and it works - the reported symptom.');
    console.log(rows.join('\n'));
    console.log('=== end ===');
  });

  // The actual requirement. RED on current code for the unprotected types.
  it.each([
    ['LMQ#', LMQ],
    ['PICKING_SLOT#', PICKING_SLOT],
    ['plain digits', PLAIN_DIGITS],
  ])('%s is not split by a 1.2s main-thread stall the scanner never caused', (_label, code) => {
    const { onReadDone, restarts } = mountReader();
    typeWithStall(code, { stallAtIndex: Math.floor(code.length / 2), stallMs: 1200 });
    // A NOT_APPLICABLE code has no content-completion signal and these devices send no terminator,
    // so it completes only on the idle flush - advance past it before asserting delivery.
    goIdleAndTick();
    expect(restarts).toEqual([]);
    expect(onReadDone).toHaveBeenCalledWith(code, expect.any(Object));
  });

  // Crossing the clock source breaks the gap in BOTH directions, so both are pinned: event time ->
  // fallback yields ~1.7e12 ms and splits a protected partial; the reverse goes negative and merges
  // two scans. The epochs must be seeded realistically different or neither can appear.
  it('does not invent a gap when a keystroke falls back to the wall clock mid-scan', () => {
    now = 1_700_000_000_000;
    eventTs = 50_000;
    const { onReadDone, restarts } = mountReader();

    const head = HU_QR.slice(0, 60); // recognised and incomplete => isPartial, normally exempt
    for (let i = 0; i < head.length; i += 1) {
      now += 1;
      eventTs += 1;
      pressKey(head[i], { withEventTime: i < head.length - 1 });
    }

    expect(restarts).toEqual([]);
    expect(onReadDone).not.toHaveBeenCalled();
  });

  // fallback -> event time: the gap goes NEGATIVE, so a real pause between two scans is never seen
  // and the next scan is merged into the previous buffer instead of being separated.
  it('still separates two scans when the clock source changes between them', () => {
    now = 1_700_000_000_000;
    eventTs = 50_000;
    const { onReadDone, restarts } = mountReader();

    // First scan arrives with no usable event time, so the hook stores a wall-clock value.
    for (let i = 0; i < PLAIN_DIGITS.length; i += 1) {
      now += 1;
      eventTs += 1;
      pressKey(PLAIN_DIGITS[i], { withEventTime: false });
    }

    // A genuine operator pause, well beyond rateMs, then a NEW scan carrying an event time.
    now += RATE_MS * 2;
    eventTs += RATE_MS * 2;
    pressKey('9', { withEventTime: true });

    // The pause must have closed the first scan rather than merging '9' into it.
    expect(onReadDone).toHaveBeenCalledWith(PLAIN_DIGITS, expect.any(Object));
    expect(restarts.length).toBeGreaterThan(0);
  });

  it('HU QR is shielded from the same stall by the isPartial exemption', () => {
    const { onReadDone, restarts } = mountReader();
    typeWithStall(HU_QR, { stallAtIndex: Math.floor(HU_QR.length / 2), stallMs: 1200 });
    expect(restarts).toEqual([]);
    expect(onReadDone).toHaveBeenCalledWith(HU_QR, expect.any(Object));
  });
});
