import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { useKeyboardBarcodeReader } from '../useKeyboardBarcodeReader';

// CHARACTERISATION: what a main-thread stall does to a scan, per code type.
//
// The gap is derived from Date.now(), i.e. the delta between when the HANDLER RAN, so a blocked
// main thread makes queued keystrokes look late and the reader sees a gap the scanner never sent.
// Measured in a bare browser: across a 1500 ms block, Date.now() reports 1506 ms while the same
// events' e.timeStamp reports 6 ms. What BLOCKS the thread is not established - garbage collection
// is plausible but unmeasured, and this fix helps only when the gap is an artifact of delayed
// processing, never when the scanner genuinely paused.
//
// Modelled exactly that way: Date.now() jumps, the events' timestamps keep their 1 ms cadence.
//
// The two code classes differ because of the isPartial exemption, whatever causes the gap:
// NOT_APPLICABLE buffers flush at rateMs (1000 ms); a recognised-but-incomplete HU QR is exempt
// until idleAbandonMs (15000 ms).
//
// Production sysconfig (read from prod ui_trace eventdata): debounceMillis=1000, minLen=7,
// idleAbandonMillis=15000.

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
  // A SPLIT is a buffer RESTART, not an emission. Emissions miss two cases: the legitimate
  // content-completion of a full HU QR looks like an emission but is correct, and a fragment below
  // minLength is dropped SILENTLY (completeScan resets the buffer before the length gate), so the
  // scan is corrupted with nothing emitted at all. Watching onReadInProgress catches both: if the
  // in-progress buffer ever gets SHORTER than the previous one, the buffer was reset mid-scan.
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
    // timeStamp is readonly and stamped at construction; override so the test controls it.
    // withEventTime:false models an event carrying no usable creation time, which drives the
    // hook's wall-clock fallback - otherwise an entirely uncovered branch.
    Object.defineProperty(event, 'timeStamp', { value: withEventTime ? eventTs : 0, configurable: true });
    window.dispatchEvent(event);
  });
}

// Types `code`, inserting a main-thread stall of `stallMs` before the character at `stallAtIndex`.
// The stall advances the WALL CLOCK only - the events' own timestamps keep their 1 ms cadence,
// because a DataWedge wedge at 0 ms inter-character delay had already queued them.
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
    expect(onReadDone).toHaveBeenCalledWith(code);
  });

  // event.timeStamp counts from the time origin; Date.now() counts from the Unix epoch. If one
  // keystroke supplies an event time and the next falls back to the wall clock, subtracting one
  // from the other yields a ~1.7e12 ms gap - clearing not just rateMs but idleAbandonMs, so it
  // would flush even a partial HU QR that the exemption exists to protect. That is a worse failure
  // than the one this hook is being fixed for, so pin both crossing directions.
  // event.timeStamp counts from the time origin; Date.now() counts from the Unix epoch. Mixing
  // them across a clock-source change breaks the gap in BOTH directions, so pin both - they fail
  // differently and one test cannot cover the pair.
  //
  // Seeding realistic, DIFFERENT epochs is what makes these tests real: with both clocks started
  // from the same small number the mismatch cannot appear and they pass against the broken code.

  // event time -> fallback: the gap becomes ~1.7e12 ms, clearing not just rateMs but idleAbandonMs,
  // so it flushes even a partial HU QR the exemption exists to protect - a worse split than the one
  // this hook is being fixed for.
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
    expect(onReadDone).toHaveBeenCalledWith(PLAIN_DIGITS);
    expect(restarts.length).toBeGreaterThan(0);
  });

  it('HU QR is shielded from the same stall by the isPartial exemption', () => {
    const { onReadDone, restarts } = mountReader();
    typeWithStall(HU_QR, { stallAtIndex: Math.floor(HU_QR.length / 2), stallMs: 1200 });
    expect(restarts).toEqual([]);
    expect(onReadDone).toHaveBeenCalledWith(HU_QR);
  });
});
