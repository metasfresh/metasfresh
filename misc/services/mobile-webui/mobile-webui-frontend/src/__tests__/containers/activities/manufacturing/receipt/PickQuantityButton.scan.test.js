import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';

import PickQuantityButton from '../../../../../containers/activities/manufacturing/receipt/PickQuantityButton';

jest.mock('../../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../../components/dialogs/GetQuantityDialog', () => {
  // eslint-disable-next-line react/prop-types
  const Mock = ({ onQtyChange }) => (
    <button
      data-testid="fake-scan"
      onClick={() =>
        onQtyChange({
          qtyEnteredAndValidated: 1,
          catchWeight: 1.5,
          catchWeightUom: 'kg',
          bestBeforeDate: '2026-12-31',
          lotNo: '26-0042',
          barcode: 'QR',
          isDone: false,
        })
      }
    />
  );
  return Mock;
});

// A value that comes from a scanned QR code is always sent, even when it equals the line's default.
it('sends the lot and best-before date read from a scanned QR code', async () => {
  const onClick = jest.fn();
  render(
    <PickQuantityButton
      qtyTarget={5}
      uom="PCE"
      caption="Receive"
      defaultLotNo="26-0042"
      defaultBestBeforeDate="2026-12-31"
      onClick={onClick}
    />
  );
  await act(async () => {
    fireEvent.click(screen.getByTestId('receive-qty-button'));
  });
  await act(async () => {
    fireEvent.click(screen.getByTestId('fake-scan'));
  });

  expect(onClick).toHaveBeenCalledWith(
    expect.objectContaining({ lotNo: '26-0042', bestBeforeDate: '2026-12-31', barcode: 'QR' })
  );
});
