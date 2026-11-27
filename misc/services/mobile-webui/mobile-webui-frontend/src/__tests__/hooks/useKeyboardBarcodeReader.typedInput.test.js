import React from 'react';
import { render } from '@testing-library/react';

import { useKeyboardBarcodeReader } from '../../hooks/useKeyboardBarcodeReader';

const Harness = ({ onReadDone, rateMs = 300 }) => {
  useKeyboardBarcodeReader({
    onReadDone,
    rateMs,
    minLength: 0,
    isTypedInputTarget: (target) => target?.id === 'count',
  });
  return (
    <div>
      <input id="count" />
      <input id="own-scanner-input" />
    </div>
  );
};

describe('useKeyboardBarcodeReader: next to a typed input', () => {
  let now;
  let nowSpy;
  let logSpy;
  beforeEach(() => {
    now = 1000000;
    nowSpy = jest.spyOn(Date, 'now').mockImplementation(() => now);
    logSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
  });
  afterEach(() => {
    nowSpy.mockRestore();
    logSpy.mockRestore();
  });

  const key = (target, k, advanceMs) => {
    now += advanceMs;
    const event = new KeyboardEvent('keydown', { key: k, bubbles: true, cancelable: true });
    // A real KeyboardEvent carries the time it was created; the reader measures key gaps on it (as in
    // hooks/__tests__/useKeyboardBarcodeReader.test.js), so stamp it with this suite's mocked clock.
    Object.defineProperty(event, 'timeStamp', { value: now, configurable: true });
    target.dispatchEvent(event);
    return event;
  };

  it('does not take digits typed by a person (120 ms apart) in the typed input, nor the Enter after them', () => {
    const onReadDone = jest.fn();
    const { container } = render(<Harness onReadDone={onReadDone} />);
    const count = container.querySelector('#count');

    const events = [key(count, '1', 500), key(count, '2', 120), key(count, 'Enter', 120)];

    expect(events.every((e) => !e.defaultPrevented)).toBe(true);
    expect(onReadDone).not.toHaveBeenCalled();
  });

  it('still reads a scanner burst (1 ms apart) with its Enter, also when the typed input has the focus', () => {
    const onReadDone = jest.fn();
    const { container } = render(<Harness onReadDone={onReadDone} />);
    const count = container.querySelector('#count');

    key(count, 'A', 500);
    key(count, 'B', 1);
    key(count, 'C', 1);
    key(count, 'Enter', 1);

    expect(onReadDone).toHaveBeenCalledTimes(1);
    expect(onReadDone).toHaveBeenCalledWith('ABC', expect.any(Object));
  });

  it('keeps the buffering for other targets (a slow pair is buffered)', () => {
    const onReadDone = jest.fn();
    const { container } = render(<Harness onReadDone={onReadDone} />);
    const other = container.querySelector('#own-scanner-input');

    key(other, '1', 500);
    key(other, '2', 120);
    key(other, 'Enter', 120);

    expect(onReadDone).toHaveBeenCalledWith('12', expect.any(Object));
  });

  it('the scan speed of the instance (a scanner debounce of 20 ms) is the limit: keys 30 ms apart are typing', () => {
    const onReadDone = jest.fn();
    const { container } = render(<Harness onReadDone={onReadDone} rateMs={20} />);
    const count = container.querySelector('#count');

    const events = [key(count, '1', 500), key(count, '2', 30), key(count, 'Enter', 30)];

    expect(events.every((e) => !e.defaultPrevented)).toBe(true);
    expect(onReadDone).not.toHaveBeenCalled();
  });

  // On the release line the reader closes the burst on the slow key and then treats that key like any first key typed
  // into the field: it is left to the field, and the Enter after it is the field's (no read).
  it('a key typed after a scanner burst is not glued to the burst, also with a slow scan speed of the instance: the burst is read as AB, the later key is left to the typed input', () => {
    const onReadDone = jest.fn();
    const { container } = render(<Harness onReadDone={onReadDone} rateMs={300} />);
    const count = container.querySelector('#count');

    key(count, 'A', 500);
    key(count, 'B', 1);
    const typed = key(count, 'C', 120); // 120 ms later: typed, although the instance allows 300 ms between the keys of a scan
    const enter = key(count, 'Enter', 1);

    expect(onReadDone.mock.calls.map(([code]) => code)).toEqual(['AB']); // 2nd argument: the scan statistics
    expect(typed.defaultPrevented).toBe(false);
    expect(enter.defaultPrevented).toBe(false);
  });
});
