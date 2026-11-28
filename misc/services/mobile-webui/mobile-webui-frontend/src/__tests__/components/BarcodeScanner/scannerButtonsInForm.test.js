import React from 'react';
import '@testing-library/jest-dom';
import { fireEvent, render, screen } from '@testing-library/react';

import BarcodeScannerFooter from '../../../components/BarcodeScanner/BarcodeScannerFooter';
import ManualModePanel from '../../../components/BarcodeScanner/ManualModePanel';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { MODE } from '../../../components/BarcodeScanner/useBarcodeScannerModes';

jest.mock('../../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
  putContext: jest.fn(),
  traceLogWarn: jest.fn(),
}));
jest.mock('../../../utils/translations', () => ({ trl: (key) => `T(${key})` }));

// The scanner buttons sit inside the quantity pop-up's form (next to the OK button): a tap on one of them must never
// submit that form, because the submit books the quantity.

const ALL_MODES = { [MODE.HARDWARE]: true, [MODE.CAMERA]: true, [MODE.MANUAL]: true };

const renderInForm = (children) => {
  const onSubmit = jest.fn((event) => event.preventDefault());
  render(<form onSubmit={onSubmit}>{children}</form>);
  return onSubmit;
};

describe('ButtonWithIndicator: the type', () => {
  it('is passed to the <button>', () => {
    render(<ButtonWithIndicator testId="b" caption="x" type="button" onClick={jest.fn()} />);
    expect(screen.getByTestId('b')).toHaveAttribute('type', 'button');
  });

  it('is not set when not given: the browser default (a submit button inside a form) stays as before', () => {
    render(<ButtonWithIndicator testId="b" caption="x" onClick={jest.fn()} />);
    expect(screen.getByTestId('b')).not.toHaveAttribute('type');
  });
});

describe('scanner buttons inside a form never submit it', () => {
  it('the footer buttons: camera / scanner toggle and "enter manually"', () => {
    const onModeSelected = jest.fn();
    const onSubmit = renderInForm(
      <BarcodeScannerFooter activeMode={MODE.HARDWARE} enabledModes={ALL_MODES} onModeSelected={onModeSelected} />
    );

    for (const testId of ['barcode-scanner-toggle-hw-camera', 'barcode-scanner-enter-manually']) {
      expect(screen.getByTestId(testId)).toHaveAttribute('type', 'button');
      fireEvent.click(screen.getByTestId(testId));
    }

    expect(onModeSelected).toHaveBeenCalledTimes(2);
    expect(onSubmit).not.toHaveBeenCalled();
  });

  it('the manual panel buttons: submit of the typed code and "back to scanner"', () => {
    const onBarcodeScanned = jest.fn().mockResolvedValue({});
    const onModeSelected = jest.fn();
    const onSubmit = renderInForm(
      <ManualModePanel
        isProcessing={false}
        onBarcodeScanned={onBarcodeScanned}
        enabledModes={ALL_MODES}
        onModeSelected={onModeSelected}
      />
    );

    for (const testId of ['manual-entry-submit', 'barcode-scanner-back-to-scanner']) {
      expect(screen.getByTestId(testId)).toHaveAttribute('type', 'button');
      fireEvent.click(screen.getByTestId(testId));
    }

    expect(onModeSelected).toHaveBeenCalledWith(MODE.HARDWARE);
    expect(onSubmit).not.toHaveBeenCalled();
  });
});
