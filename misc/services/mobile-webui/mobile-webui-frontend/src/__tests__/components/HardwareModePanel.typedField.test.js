import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';
import OpenNextTUButton from '../../apps/picking/cuQtyEntry/OpenNextTUButton';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';

jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
  putContext: jest.fn(),
  traceLogWarn: jest.fn(),
}));

// The hidden scanner of the quantity pop-up sits next to the quantity field the picker types into. A scan that the
// picker's scanner delivers as keystrokes (1 ms apart) while the quantity field has the focus is still read as a whole
// (the field is not the scanner's own input); the digits the picker types (120 ms apart) are not taken for a scan.
// Each test goes through one more layer of the chain: panel, scanner component, "Next TU" button of the pop-up.

const KEYSTROKE_WEDGE_CONFIG = {
  'barcodeScanner.mode.hardware.input.inputMode': 'none',
  'barcodeScanner.mode.hardware.input.readOnly': 'Y',
};
const CODE = '8003000000000000000001'; // longer than the minimum length of a scan

const createStoreOf = () => createStore(() => ({ settings: { backend: KEYSTROKE_WEDGE_CONFIG } }));

describe('the hidden scanner next to the quantity field', () => {
  let now;
  beforeEach(() => {
    now = 1000000;
    jest.spyOn(Date, 'now').mockImplementation(() => now);
    jest.spyOn(console, 'log').mockImplementation(() => {});
    jest.spyOn(console, 'debug').mockImplementation(() => {});
    // the keystrokes come from the browser, outside React's event system: React warns that the state changes are not wrapped in act()
    const originalConsoleError = console.error;
    jest.spyOn(console, 'error').mockImplementation((message, ...args) => {
      if (String(message).includes('not wrapped in act(')) return;
      originalConsoleError(message, ...args);
    });
  });
  afterEach(() => {
    jest.restoreAllMocks();
  });

  const press = (target, key, advanceMs) => {
    now += advanceMs;
    const event = new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true });
    target.dispatchEvent(event);
    return event;
  };
  const scanInto = (target) => {
    [...CODE].forEach((char, index) => press(target, char, index === 0 ? 500 : 1));
    press(target, 'Enter', 1);
  };
  const typeInto = (target, text) => {
    [...text].forEach((char) => press(target, char, 120));
    press(target, 'Enter', 120);
  };

  const cases = [
    [
      'HardwareModePanel',
      (onRead) => (
        <Provider store={createStoreOf()}>
          <input data-testid="qty" />
          <HardwareModePanel
            isCoexistingWithTypedInput
            onBarcodeScanned={({ scannedBarcode }) => onRead(scannedBarcode)}
          />
        </Provider>
      ),
    ],
    [
      'BarcodeScannerComponent',
      (onRead) => (
        <Provider store={createStoreOf()}>
          <input data-testid="qty" />
          <BarcodeScannerComponent
            invisible
            isCoexistingWithTypedInput
            onResolvedResult={({ scannedBarcode }) => onRead(scannedBarcode)}
          />
        </Provider>
      ),
    ],
    [
      'OpenNextTUButton of the pop-up',
      (onRead) => (
        <Provider store={createStoreOf()}>
          <input data-testid="qty" />
          <OpenNextTUButton
            onNextTU={jest.fn()}
            scanner={{ onResolvedResult: ({ scannedBarcode }) => onRead(scannedBarcode) }}
          />
        </Provider>
      ),
    ],
  ];

  it.each(cases)(
    '%s: a scan burst arriving while the quantity field has the focus is read as a whole',
    (name, build) => {
      const onRead = jest.fn();
      const { getByTestId } = render(build(onRead));

      scanInto(getByTestId('qty'));

      expect(onRead).toHaveBeenCalledTimes(1);
      expect(onRead).toHaveBeenCalledWith(CODE);
    }
  );

  it.each(cases)('%s: digits typed by the picker into the quantity field are not a scan', (name, build) => {
    const onRead = jest.fn();
    const { getByTestId } = render(build(onRead));

    typeInto(getByTestId('qty'), '12');

    expect(onRead).not.toHaveBeenCalled();
  });
});
