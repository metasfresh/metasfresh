import React from 'react';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';

jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
  putContext: jest.fn(),
  traceLogWarn: jest.fn(),
}));

// In an IME-style scanner config (editable input) the OS commits the scanned text into the FOCUSED element.
// Next to a field the operator types into (the quantity block), the scanner input must therefore hold the focus
// whenever the operator is not typing, and must never take it away from the field being typed into.

const store = (backend) => createStore(() => ({ settings: { backend } }));
const IME_CONFIG = { 'barcodeScanner.mode.hardware.input.inputMode': 'text' };
const KEYSTROKE_WEDGE_CONFIG = {
  'barcodeScanner.mode.hardware.input.inputMode': 'none',
  'barcodeScanner.mode.hardware.input.readOnly': 'Y',
};

const renderPanel = (backend, { withQtyField = false } = {}) =>
  render(
    <Provider store={store(backend)}>
      {withQtyField && <input data-testid="qty" />}
      <HardwareModePanel isCoexistingWithTypedInput onBarcodeScanned={jest.fn()} testId="scanner" />
    </Provider>
  );

describe('HardwareModePanel next to a typed-in field', () => {
  it('IME config, operator not typing: the scanner input holds the focus', () => {
    const { getByTestId } = renderPanel(IME_CONFIG, { withQtyField: true });
    expect(document.activeElement).toBe(getByTestId('scanner'));
  });

  it('IME config, operator typing in the field: the focus stays in the field', () => {
    const { getByTestId, rerender } = renderPanel(IME_CONFIG, { withQtyField: true });
    getByTestId('qty').focus();
    rerender(
      <Provider store={store(IME_CONFIG)}>
        <input data-testid="qty" />
        <HardwareModePanel isCoexistingWithTypedInput onBarcodeScanned={jest.fn()} testId="scanner" />
      </Provider>
    );
    expect(document.activeElement).toBe(getByTestId('qty'));
  });

  it('keystroke-wedge config: the scanner input is never focused (window-level reader)', () => {
    const { getByTestId } = renderPanel(KEYSTROKE_WEDGE_CONFIG, { withQtyField: true });
    expect(document.activeElement).not.toBe(getByTestId('scanner'));
  });
});
