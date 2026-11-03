import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../components/dialogs/GetQuantityDialog';
import { reducer as settings } from '../../reducers/settings';

jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
}));
jest.mock('../../components/BarcodeScannerComponent', () => {
  // eslint-disable-next-line react/prop-types
  const FakeScanner = ({ onResolvedResult }) => (
    <input
      data-testid="fake-scanner"
      onChange={(e) => onResolvedResult({ scannedBarcode: e.target.value, error: null })}
    />
  );
  return FakeScanner;
});

// The forced scan with review is for the manufacturing receive only. Every other caller (e.g. picking a
// catch-weight product) keeps receiving the scanned item right away and may switch to manual input.
it('without the mandatory-scan flag a catch-weight scan is confirmed right away', async () => {
  const onQtyChange = jest.fn();
  const store = createStore(combineReducers({ settings }), { settings: { backend: {} } });
  render(
    <Provider store={store}>
      <GetQuantityDialog qtyTarget={5} uom="PCE" catchWeightUom="kg" onQtyChange={onQtyChange} />
    </Provider>
  );

  expect(screen.getByTestId('switchToManualInput-button')).toBeInTheDocument();

  await act(async () => {
    fireEvent.change(screen.getByTestId('fake-scanner'), { target: { value: 'LMQ#1#0.101#08.11.2025#500' } });
  });

  expect(onQtyChange).toHaveBeenCalledWith(
    expect.objectContaining({
      qtyEnteredAndValidated: 1,
      catchWeight: 0.101,
      lotNo: '500',
      bestBeforeDate: '2025-11-08',
      barcode: 'LMQ#1#0.101#08.11.2025#500',
      isDone: false,
    })
  );
});
