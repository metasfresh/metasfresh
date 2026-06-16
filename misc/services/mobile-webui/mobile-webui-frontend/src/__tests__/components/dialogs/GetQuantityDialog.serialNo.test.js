import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';

import GetQuantityDialog from '../../../components/dialogs/GetQuantityDialog';
import { PickAttribute } from '../../../reducers/wfProcesses/picking/PickAttribute';

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

// Stub the scanner: renders a button that simulates a hardware/camera scan returning a serial.
jest.mock('../../../components/BarcodeScannerComponent', () => {
  // eslint-disable-next-line react/prop-types
  const MockBarcodeScannerComponent = ({ onResolvedResult }) => (
    <button data-testid="mock-scan" onClick={() => onResolvedResult({ scannedBarcode: 'SN-123' })}>
      mock-scan
    </button>
  );
  return MockBarcodeScannerComponent;
});

const baseProps = {
  qtyTarget: 1,
  qtyInitial: 1,
  uom: 'PCE',
  onQtyChange: jest.fn(() => Promise.resolve()),
  onCloseDialog: jest.fn(),
};

describe('GetQuantityDialog — SerialNo', () => {
  it('does not render the serial control when SerialNo is not in readAttributes', () => {
    render(<GetQuantityDialog {...baseProps} readAttributes={[]} />);
    expect(screen.queryByTestId('serialNo-scan-button')).toBeNull();
  });

  it('gates confirm until a serial is scanned, then carries it in the payload', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    render(<GetQuantityDialog {...baseProps} onQtyChange={onQtyChange} readAttributes={[PickAttribute.SerialNo]} />);

    // Required-but-missing → confirm disabled, scan button shown (no value yet).
    expect(screen.getByTestId('done-button')).toBeDisabled();
    expect(screen.getByTestId('serialNo-scan-button')).toBeInTheDocument();

    // Open the scan screen and simulate a scan.
    fireEvent.click(screen.getByTestId('serialNo-scan-button'));
    fireEvent.click(screen.getByTestId('mock-scan'));

    // Value shown, "Scan again" replaces the initial button, confirm enabled.
    expect(screen.getByTestId('serialNo-value')).toHaveTextContent('SN-123');
    expect(screen.getByTestId('serialNo-scan-again-button')).toBeInTheDocument();
    expect(screen.getByTestId('done-button')).not.toBeDisabled();

    // Confirm → payload carries the serial (onDialogYes is async).
    fireEvent.click(screen.getByTestId('done-button'));
    await waitFor(() => expect(onQtyChange).toHaveBeenCalled());
    expect(onQtyChange.mock.calls[0][0]).toMatchObject({ serialNo: 'SN-123' });
  });
});
