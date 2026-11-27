import React from 'react';
import { createEvent, fireEvent, render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';

jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
  putContext: jest.fn(),
  traceLogWarn: jest.fn(),
}));

// The scanner input ends a scan with Enter. The hidden scanner of the quantity block sits in the same form as the OK
// button: its Enter must never submit the form, which would book the quantity.

const store = createStore(() => ({
  settings: { backend: { 'barcodeScanner.mode.hardware.input.inputMode': 'text' } },
}));

const keyDownOnScanner = (getByTestId, key) => {
  const event = createEvent.keyDown(getByTestId('scanner'), { key, bubbles: true, cancelable: true });
  fireEvent(getByTestId('scanner'), event);
  return event;
};

describe('HardwareModePanel: Enter of the scanner input', () => {
  const renderInForm = () =>
    render(
      <Provider store={store}>
        <form>
          <HardwareModePanel onBarcodeScanned={jest.fn()} testId="scanner" />
          <button type="submit">OK</button>
        </form>
      </Provider>
    );

  it('is cancelled on key-down: the form around the scanner is not submitted by it', () => {
    const { getByTestId } = renderInForm();
    expect(keyDownOnScanner(getByTestId, 'Enter').defaultPrevented).toBe(true);
  });

  // A printable key is not used here: on the release line the window-level scanner reader takes every printable key
  // into its scan buffer and cancels it (useKeyboardBarcodeReader), which has nothing to do with the Enter guard.
  it('other keys are not touched', () => {
    const { getByTestId } = renderInForm();
    expect(keyDownOnScanner(getByTestId, 'Escape').defaultPrevented).toBe(false);
  });
});
