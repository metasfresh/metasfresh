import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../../../reducers/settings';
import { toQtyEntryBehaviorProps } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';
import OpenNextTUButton from '../../../../apps/picking/cuQtyEntry/OpenNextTUButton';

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

// the real wiring (PickLineScanScreen): the "next TU" button owns the hidden scanner of the quantity block
const nextTUActions = ({ scanner }) => <OpenNextTUButton onNextTU={jest.fn()} scanner={scanner} />;

const label = { lotNo: 'LOTA', bestBeforeDate: '2026-11-07', cuLabel: 'RAW-A' };

const renderOpenQuantityBlock = async ({ resolveScannedBarcode, isShowCloseTargetButton = false }) => {
  render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <ScanHUAndGetQtyComponent
        qtyMax={12}
        qtyTarget={12}
        uom="PCE"
        {...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)}
        isShowCloseTargetButton={isShowCloseTargetButton}
        nextTUActions={nextTUActions}
        resolveScannedBarcode={resolveScannedBarcode}
        onResult={jest.fn().mockResolvedValue()}
      />
    </Provider>
  );
  await act(async () => {});
  const input = screen.getByTestId('scanHUBarcode-input');
  input.value = 'RAW-A';
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
  await act(async () => {});
};

describe('ScanHUAndGetQtyComponent: scan once, the count field is typed into', () => {
  let logSpy;
  beforeEach(() => {
    jest.useFakeTimers('modern');
    logSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
  });
  afterEach(() => {
    jest.useRealTimers();
    logSpy.mockRestore();
  });

  it('the label rescan scanner does not take the focus from the count field, not on open and not 2 s after a tap', async () => {
    await renderOpenQuantityBlock({ resolveScannedBarcode: jest.fn().mockResolvedValue(label) });
    const count = document.querySelector('#qty-input');
    expect(screen.getByTestId('rescanCULabel-input')).toBeInTheDocument();
    expect(document.activeElement).toBe(count);

    // the picker taps another field (lot) and back into the count field
    act(() => {
      count.blur();
    });
    act(() => {
      count.focus();
    });
    await act(async () => {
      jest.advanceTimersByTime(2500);
    });
    expect(document.activeElement).toBe(count);
  });

  it('digits typed into the count field (120 ms apart) are not read as a label scan', async () => {
    const resolveScannedBarcode = jest.fn().mockResolvedValue(label);
    await renderOpenQuantityBlock({ resolveScannedBarcode });
    resolveScannedBarcode.mockClear();
    const count = document.querySelector('#qty-input');
    let now = Date.now();
    const nowSpy = jest.spyOn(Date, 'now').mockImplementation(() => now);

    for (const k of ['1', '2']) {
      now += 120;
      fireEvent.keyDown(count, { key: k });
    }
    now += 120;
    const enter = fireEvent.keyDown(count, { key: 'Enter' });
    await act(async () => {
      jest.advanceTimersByTime(2000);
    });

    nowSpy.mockRestore();
    expect(resolveScannedBarcode).not.toHaveBeenCalled();
    expect(enter).toBe(true); // Enter not consumed: it stays the form's OK
  });

  it('Enter in the count field reaches OK: no other button before it in the form can submit', async () => {
    await renderOpenQuantityBlock({
      resolveScannedBarcode: jest.fn().mockResolvedValue(label),
      isShowCloseTargetButton: true,
    });
    const form = document.querySelector('form');
    const firstSubmitter = form.querySelector('button:not([type="button"])');
    expect(firstSubmitter).toBe(screen.getByTestId('done-button'));
  });

  it('the label rescan scanner shows nothing (no icon, no caption, no manual entry) and its Enter cannot submit the quantity form', async () => {
    await renderOpenQuantityBlock({ resolveScannedBarcode: jest.fn().mockResolvedValue(label) });
    expect(screen.getByTestId('rescanCULabel-input')).toBeInTheDocument();
    expect(document.querySelector('.scan-prompt')).toBeNull();
    expect(document.querySelector('.barcode-scanner-footer')).toBeNull();
    expect(screen.queryByTestId('barcode-scanner-enter-manually')).toBeNull();
    expect(screen.queryByTestId('manual-entry-input')).toBeNull();
    const enterNotPrevented = fireEvent.keyDown(screen.getByTestId('rescanCULabel-input'), { key: 'Enter' });
    expect(enterNotPrevented).toBe(false); // default prevented: no implicit form submit
  });
});
