import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';

import GetQuantityDialog from '../../../components/dialogs/GetQuantityDialog';
import { PickAttribute } from '../../../reducers/wfProcesses/picking/PickAttribute';

// NOTE: this unit test asserts the i18n-INDEPENDENT gating logic (chip counts, confirm gating,
// payload shape, dedup). The exact "X of N" count TEXT depends on counterpart being bootstrapped
// (only happens in the live app), so it's asserted by the Playwright E2E against the running app,
// not here.

// Skip qty validation so the test isolates the SerialNo gating.
jest.mock('../../../reducers/settings', () => ({
  useBooleanSetting: (name) => name === 'qtyInput.DoNotValidate',
  useNumber: () => 0,
  usePositiveNumberSetting: () => 0,
}));

// ui_trace uses IndexedDB which is absent in jsdom.
jest.mock('../../../utils/ui_trace', () => ({
  putContext: jest.fn(),
  trace: jest.fn(),
  traceFunction: (fn) => fn,
}));

// Stub the scanner: a button that simulates a hardware/camera scan returning `mockNextScan`
// (jest allows out-of-scope refs prefixed with `mock` inside the factory).
let mockNextScan = 'SN-1';
jest.mock('../../../components/BarcodeScannerComponent', () => {
  // eslint-disable-next-line react/prop-types
  const MockBarcodeScannerComponent = ({ onResolvedResult }) => (
    <button data-testid="mock-scan" onClick={() => onResolvedResult({ scannedBarcode: mockNextScan })}>
      mock-scan
    </button>
  );
  return MockBarcodeScannerComponent;
});

const baseProps = {
  qtyTarget: 2,
  qtyInitial: 2,
  uom: 'PCE',
  onQtyChange: jest.fn(() => Promise.resolve()),
  onCloseDialog: jest.fn(),
};

const scan = (serial) => {
  mockNextScan = serial;
  fireEvent.click(screen.getByTestId('mock-scan'));
};

describe('GetQuantityDialog — SerialNo (multi-serial)', () => {
  it('does not render the serial control when SerialNo is not in readAttributes', () => {
    render(<GetQuantityDialog {...baseProps} readAttributes={[]} />);
    expect(screen.queryByTestId('serialNo-scan-button')).toBeNull();
    expect(screen.queryByTestId('serialNo-count')).toBeNull();
  });

  it('gates confirm until N distinct serials (= qty) are scanned, dedups, carries them as serialNos[]', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    render(<GetQuantityDialog {...baseProps} onQtyChange={onQtyChange} readAttributes={[PickAttribute.SerialNo]} />);

    // qty = 2 → need 2 serials. Required-but-missing → confirm disabled, count shown, scan button shown.
    expect(screen.getByTestId('done-button')).toBeDisabled();
    expect(screen.getByTestId('serialNo-count')).toBeInTheDocument();
    expect(screen.getByTestId('serialNo-scan-button')).toBeInTheDocument();

    // Open the live scan view and scan one serial → 1 chip, still gated (1 of 2).
    fireEvent.click(screen.getByTestId('serialNo-scan-button'));
    scan('SN-1');
    expect(screen.getAllByTestId('serialNo-chip')).toHaveLength(1);

    // Scanning the same serial again is silently deduped → still 1 chip.
    scan('SN-1');
    expect(screen.getAllByTestId('serialNo-chip')).toHaveLength(1);

    // Scan a 2nd distinct serial → 2 chips.
    scan('SN-2');
    expect(screen.getAllByTestId('serialNo-chip')).toHaveLength(2);

    // Return to the qty form → 2 of 2 distinct scanned, confirm enabled.
    fireEvent.click(screen.getByTestId('serialNo-scan-done-button'));
    expect(screen.getByTestId('done-button')).not.toBeDisabled();

    // Confirm → payload carries the serials as an array (onDialogYes is async).
    fireEvent.click(screen.getByTestId('done-button'));
    await waitFor(() => expect(onQtyChange).toHaveBeenCalled());
    expect(onQtyChange.mock.calls[0][0]).toMatchObject({ serialNos: ['SN-1', 'SN-2'] });
  });
});
