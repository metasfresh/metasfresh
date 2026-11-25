import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';

import GetQuantityDialog from '../../../components/dialogs/GetQuantityDialog';
import { PickAttribute } from '../../../reducers/wfProcesses/picking/PickAttribute';

// Picking profile with "Capture Lot Number" / "Capture Best Before Date" off: the dialog does not show the field,
// so it must not send a value for it. PickLineScanScreen turns a sent value (even null) into setLotNo / setBestBeforeDate = true,
// and the server then overwrites the lot / best-before date copied from the scanned stock unit with that (empty) value.

// Skip qty validation: this test isolates the attribute payload.
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

jest.mock('../../../utils/toast');

jest.mock('../../../components/BarcodeScannerComponent', () => {
  const MockBarcodeScannerComponent = () => <div />;
  return MockBarcodeScannerComponent;
});

const baseProps = {
  qtyTarget: 3,
  qtyInitial: 3,
  uom: 'PCE',
  lotNo: 'LOT-A', // as resolved from the scanned stock unit's QR code
  bestBeforeDate: '2027-01-31',
  onCloseDialog: jest.fn(),
};

const confirmAndGetPayload = async (readAttributes) => {
  const onQtyChange = jest.fn(() => Promise.resolve());
  render(<GetQuantityDialog {...baseProps} onQtyChange={onQtyChange} readAttributes={readAttributes} />);
  fireEvent.click(screen.getByTestId('done-button'));
  await waitFor(() => expect(onQtyChange).toHaveBeenCalled());
  return onQtyChange.mock.calls[0][0];
};

describe('GetQuantityDialog - lot / best-before fields hidden by the picking profile', () => {
  it('sends no lot and no best-before date (undefined, not null) when neither field is shown', async () => {
    const payload = await confirmAndGetPayload([]);
    expect(payload.lotNo).toBeUndefined();
    expect(payload.bestBeforeDate).toBeUndefined();
  });

  it('sends the lot the picker left in the field when the lot field is shown', async () => {
    const payload = await confirmAndGetPayload([PickAttribute.LotNo]);
    expect(payload.lotNo).toBe('LOT-A');
    expect(payload.bestBeforeDate).toBeUndefined();
  });

  it('sends the best-before date when the best-before field is shown', async () => {
    const payload = await confirmAndGetPayload([PickAttribute.BestBeforeDate]);
    expect(payload.bestBeforeDate).toBe('2027-01-31');
    expect(payload.lotNo).toBeUndefined();
  });
});
