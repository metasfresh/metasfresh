import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../components/dialogs/GetQuantityDialog';
import ScanHUAndGetQtyComponent from '../../components/ScanHUAndGetQtyComponent';
import { putSettingsAction, reducer as settings } from '../../reducers/settings';
import { CUQtyEntryMode } from '../../constants/CUQtyEntryMode';

jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));

const REJECT_REASONS = [{ key: 'NOT_FOUND', caption: 'Not found' }];

const newStore = ({ useZeroAsInitialValue = false } = {}) => {
  const store = createStore(combineReducers({ settings }));
  store.dispatch(putSettingsAction({ 'qtyInput.useZeroAsInitialValue': useZeroAsInitialValue ? 'Y' : 'N' }));
  return store;
};

const renderDialog = ({ cuQtyEntryMode, store = newStore(), onQtyChange = jest.fn().mockResolvedValue(), ...props }) =>
  render(
    <Provider store={store}>
      <GetQuantityDialog
        qtyTarget={10}
        qtyTargetCaption="To pick"
        uom="PCE"
        qtyRejectedReasons={REJECT_REASONS}
        cuQtyEntryMode={cuQtyEntryMode}
        onQtyChange={onQtyChange}
        onCloseDialog={jest.fn()}
        {...props}
      />
    </Provider>
  );

const typeQty = async (value) => {
  const input = document.querySelector('#qty-input');
  await act(async () => {
    fireEvent.change(input, { target: { value } });
  });
};

describe('GetQuantityDialog: scan once, enter quantity', () => {
  it('starts with an empty qty field and a disabled OK', async () => {
    renderDialog({ cuQtyEntryMode: CUQtyEntryMode.SCAN_ONCE_ENTER_QTY });
    await act(async () => {});
    expect(document.querySelector('#qty-input').value).toBe('');
    expect(screen.getByTestId('done-button')).toBeDisabled();
  });

  it('starts empty also when the initial value setting is zero', async () => {
    renderDialog({
      cuQtyEntryMode: CUQtyEntryMode.SCAN_ONCE_ENTER_QTY,
      store: newStore({ useZeroAsInitialValue: true }),
    });
    await act(async () => {});
    expect(document.querySelector('#qty-input').value).toBe('');
  });

  it('shows no reject block when fewer than the open qty are typed', async () => {
    renderDialog({ cuQtyEntryMode: CUQtyEntryMode.SCAN_ONCE_ENTER_QTY });
    await typeQty('3');
    expect(document.querySelector('#qty-rejected')).toBeNull();
    expect(screen.getByTestId('done-button')).not.toBeDisabled();
  });

  it('posts the typed count on OK', async () => {
    const onQtyChange = jest.fn().mockResolvedValue();
    renderDialog({ cuQtyEntryMode: CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, onQtyChange });
    await typeQty('3');
    await act(async () => {
      fireEvent.click(screen.getByTestId('done-button'));
    });
    expect(onQtyChange).toHaveBeenCalledWith(
      expect.objectContaining({ qtyEnteredAndValidated: '3', qtyRejected: 0, qtyRejectedReason: null })
    );
  });

  it('keeps the existing behaviour when the mode is absent (prefilled with the open qty, reject block shown)', async () => {
    renderDialog({ cuQtyEntryMode: undefined });
    await act(async () => {});
    expect(document.querySelector('#qty-input').value).toBe('10');
    await typeQty('3');
    expect(document.querySelector('#qty-rejected')).not.toBeNull();
  });

  it('keeps the existing behaviour for MANUAL', async () => {
    renderDialog({ cuQtyEntryMode: CUQtyEntryMode.MANUAL });
    await act(async () => {});
    expect(document.querySelector('#qty-input').value).toBe('10');
  });
});

describe('ScanHUAndGetQtyComponent: scan once passes the label', () => {
  it('reports the raw label with the typed count', async () => {
    const onResult = jest.fn().mockResolvedValue();
    render(
      <Provider store={newStore()}>
        <ScanHUAndGetQtyComponent
          qtyMax={10}
          qtyTarget={10}
          uom="PCE"
          cuQtyEntryMode={CUQtyEntryMode.SCAN_ONCE_ENTER_QTY}
          resolveScannedBarcode={async () => ({ lotNo: 'L1', cuLabel: 'RAW-LABEL' })}
          onResult={onResult}
        />
      </Provider>
    );
    await act(async () => {});
    const scanner = screen.getByTestId('scanHUBarcode-input');
    scanner.value = 'RAW-LABEL';
    await act(async () => {
      fireEvent.keyUp(scanner, { key: 'Enter' });
    });
    await act(async () => {
      fireEvent.change(await screen.findByTestId('lotNo'), { target: { value: 'L1' } });
    });
    await typeQty('4');
    await act(async () => {
      fireEvent.click(screen.getByTestId('done-button'));
    });
    expect(onResult).toHaveBeenCalledWith(expect.objectContaining({ qty: '4', cuLabel: 'RAW-LABEL' }));
  });
});
