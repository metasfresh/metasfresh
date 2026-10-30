import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import ScanHUAndGetQtyComponent from '../../components/ScanHUAndGetQtyComponent';
import { reducer as settings } from '../../reducers/settings';

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

const SCANNER_INPUT = 'scanHUBarcode-input';
const DONE_BUTTON = 'done-button';
const GRAI_SAVE_BUTTON = 'grai-save-button';
const SPINNER = '.loading';
const A_GRAI = '1234567.12345.SERIAL1';

const renderComponent = ({ onResult }) =>
  render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <ScanHUAndGetQtyComponent
        qtyMax={1}
        qtyTarget={1}
        uom="PCE"
        graiScanEnabled
        resolveScannedBarcode={async (scannedBarcode) => ({
          qrCode: { code: scannedBarcode, isUnique: false, isTUToBePickedAsWhole: false },
          isTUToBePickedAsWhole: false,
          qtyInitial: 1,
        })}
        onResult={onResult}
      />
    </Provider>
  );

/** Scans a CU label (1 pc), confirms the qty and scans the one GRAI, so the GRAI "Speichern" button is enabled. */
const reachTheEnabledGraiSaveButton = async () => {
  await act(async () => {});
  const input = screen.getByTestId(SCANNER_INPUT);
  input.value = 'CU-LABEL-1';
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });

  const doneButton = await screen.findByTestId(DONE_BUTTON);
  await act(async () => {
    fireEvent.click(doneButton);
  });

  // GRAI scan, the way a hardware reader delivers it: keystrokes + Enter
  await act(async () => {
    [...A_GRAI, 'Enter'].forEach((key) => window.dispatchEvent(new KeyboardEvent('keydown', { key })));
  });

  expect(screen.getByTestId(GRAI_SAVE_BUTTON)).not.toBeDisabled();
};

describe('ScanHUAndGetQtyComponent GRAI save', () => {
  it('books the pick once when the GRAI Speichern button is fired twice while the pick is in flight', async () => {
    let finishThePick;
    const onResult = jest.fn(() => new Promise((resolve) => (finishThePick = resolve)));

    renderComponent({ onResult });
    await reachTheEnabledGraiSaveButton();

    const saveButton = screen.getByTestId(GRAI_SAVE_BUTTON);
    // two taps in the same tick: the second one hits the still-mounted button before React re-rendered
    await act(async () => {
      fireEvent.click(saveButton);
      fireEvent.click(saveButton);
    });

    expect(onResult).toHaveBeenCalledTimes(1);
    expect(document.querySelector(SPINNER)).not.toBeNull();
    expect(screen.queryByTestId(GRAI_SAVE_BUTTON)).toBeNull();

    await act(async () => finishThePick());
  });

  it('gives the operator the GRAI screen back when the pick failed', async () => {
    let failThePick;
    const onResult = jest.fn(() => new Promise((resolve, reject) => (failThePick = reject)));

    renderComponent({ onResult });
    await reachTheEnabledGraiSaveButton();

    await act(async () => {
      fireEvent.click(screen.getByTestId(GRAI_SAVE_BUTTON));
    });
    expect(document.querySelector(SPINNER)).not.toBeNull();

    await act(async () => failThePick({ message: 'pick rejected by the server' }));

    expect(document.querySelector(SPINNER)).toBeNull();
    expect(screen.getByTestId(GRAI_SAVE_BUTTON)).toBeInTheDocument();
  });
});
