import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../../../reducers/settings';
import { POLICY } from '../../../../testSupport/picking/testPolicies';

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));

const store = () => createStore(combineReducers({ settings }));

const labels = {
  'RAW-1': { lotNo: 'LOTA', bestBeforeDate: '2026-11-07', packId: '1', cuLabel: 'RAW-1' },
  'RAW-2': { lotNo: 'LOTA', bestBeforeDate: '2026-11-07', packId: '2', cuLabel: 'RAW-2' },
};

const renderComponent = ({ onResult, resolveScannedBarcode, qtyMax = 3, ...props }) =>
  render(
    <Provider store={store()}>
      <ScanHUAndGetQtyComponent
        qtyMax={qtyMax}
        qtyTarget={qtyMax}
        uom="PCE"
        cuQtyEntryPolicy={POLICY.SCAN_EACH_CU}
        resolveScannedBarcode={
          resolveScannedBarcode ??
          (async (code) => {
            if (!labels[code]) throw { messageKey: 'picking.cuLabel.noPackId' };
            return labels[code];
          })
        }
        onResult={onResult}
        nextBoxActions={<button type="button">Next box</button>}
        {...props}
      />
    </Provider>
  );

const scan = async (value) => {
  const input = screen.getByTestId('scanHUBarcode-input');
  input.value = value;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
  await act(async () => {});
};

describe('ScanHUAndGetQtyComponent: Scan each CU loop', () => {
  it('every label books one CU of 1 with its label and stays on the scan step (no quantity block, no OK)', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({ onResult });
    await act(async () => {});

    await scan('RAW-1');
    expect(onResult).toHaveBeenCalledTimes(1);
    expect(onResult).toHaveBeenLastCalledWith(
      expect.objectContaining({ qty: 1, cuLabel: 'RAW-1', lotNo: 'LOTA', bestBeforeDate: '2026-11-07', isDone: false })
    );
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
    expect(screen.queryByTestId('done-button')).toBeNull();
    expect(document.querySelector('#qty-input')).toBeNull();

    await scan('RAW-2');
    expect(onResult).toHaveBeenCalledTimes(2);
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ qty: 1, cuLabel: 'RAW-2' }));
  });

  it('the booking that reaches the open quantity reports "done"', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({ onResult, qtyMax: 1 });
    await act(async () => {});
    await scan('RAW-1');
    expect(onResult).toHaveBeenLastCalledWith(expect.objectContaining({ qty: 1, isDone: true }));
  });

  it('a refused label books nothing and the scan step stays', async () => {
    const onResult = jest.fn().mockResolvedValue();
    renderComponent({ onResult });
    await act(async () => {});
    await scan('NO-PACK');
    expect(onResult).not.toHaveBeenCalled();
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
  });

  it('shows "Next box" next to "Close"; Close leaves the line', async () => {
    const onResult = jest.fn().mockResolvedValue();
    const onClose = jest.fn();
    renderComponent({ onResult, onClose });
    await act(async () => {});
    expect(screen.getByText('Next box')).toBeInTheDocument();
    await act(async () => {
      fireEvent.click(screen.getByTestId('scan-each-close-button'));
    });
    expect(onClose).toHaveBeenCalledTimes(1);
  });

  it('a backend refusal (duplicate pack) is toasted and the scan step stays', async () => {
    const { toastErrorFromObj } = require('../../../../utils/toast');
    const onResult = jest.fn().mockRejectedValue({ message: 'already picked' });
    renderComponent({ onResult });
    await act(async () => {});
    await scan('RAW-1');
    expect(toastErrorFromObj).toHaveBeenCalled();
    expect(screen.getByTestId('scanHUBarcode-input')).toBeInTheDocument();
  });
});
