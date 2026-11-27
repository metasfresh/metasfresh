import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../../../reducers/settings';
import { toQtyEntryBehaviorProps } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';

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

const label = { lotNo: 'LOTA', bestBeforeDate: '2026-11-07', cuLabel: 'RAW-A' };

const renderBlock = async ({ nextTUActions, scannedBarcode, suggestedQty }) => {
  const resolveScannedBarcode = jest.fn().mockResolvedValue(label);
  render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <ScanHUAndGetQtyComponent
        qtyMax={12}
        qtyTarget={12}
        uom="PCE"
        {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
        scannedBarcode={scannedBarcode}
        suggestedQty={suggestedQty}
        nextTUActions={nextTUActions}
        resolveScannedBarcode={resolveScannedBarcode}
        onResult={jest.fn().mockResolvedValue()}
      />
    </Provider>
  );
  await act(async () => {});
  return { resolveScannedBarcode };
};

const NEXT_TU_BUTTON = <button data-testid="next-tu-button">TU</button>;

const countInput = () => document.querySelector('#qty-input');

describe('ScanHUAndGetQtyComponent: what "Weiteres TU" hands over to the screen (scan once)', () => {
  it('the button gets the scanned label and the count typed so far', async () => {
    const nextTUActions = jest.fn(() => <button data-testid="next-tu-button">TU</button>);
    await renderBlock({ nextTUActions });
    const input = screen.getByTestId('scanHUBarcode-input');
    input.value = 'RAW-A';
    await act(async () => {
      fireEvent.keyUp(input, { key: 'Enter' });
    });
    await act(async () => {});
    await act(async () => {
      fireEvent.change(countInput(), { target: { value: '4' } });
    });
    expect(nextTUActions).toHaveBeenLastCalledWith(
      expect.objectContaining({ pendingScan: { scannedBarcode: 'RAW-A', qtyEntered: '4' } })
    );
  });

  it('back from the TU list: the kept label is read again by itself and the kept count is in the field', async () => {
    const { resolveScannedBarcode } = await renderBlock({
      nextTUActions: NEXT_TU_BUTTON,
      scannedBarcode: 'RAW-A',
      suggestedQty: 4,
    });
    expect(resolveScannedBarcode).toHaveBeenCalledWith('RAW-A', undefined);
    expect(screen.queryByTestId('scanHUBarcode-input')).toBeNull(); // no new scan asked
    expect(countInput()).toHaveValue('4'); // the quantity field is a text input on the release line
  });
});
