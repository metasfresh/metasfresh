import fs from 'fs';
import path from 'path';
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

// The operator scans a whole TU holding 3 pieces onto a line with 2 still to pick, so the component
// raises the over-delivery confirmation, and answering "Ja" fires the pick.
const QTY_REMAINING = 2;
const HU_QTY = 3;

const SCANNER_INPUT = 'scanHUBarcode-input';
const DONE_BUTTON = 'done-button';
const GRAI_SAVE_BUTTON = 'grai-save-button';
const YES_BUTTON = '#yes-button';
const SPINNER = '.loading';
const A_GRAI = '1234567.12345.SERIAL1';

const COMPONENT_PATH = path.join(__dirname, '../../components/ScanHUAndGetQtyComponent.jsx');

/** Feeds a barcode to the HU scanner, the way a hardware scan does. */
const scanWholeTU = async (barcode) => {
  const input = screen.getByTestId(SCANNER_INPUT);
  input.value = barcode;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
};

const confirmTheOverDelivery = async () => {
  await act(async () => {
    fireEvent.click(document.querySelector(YES_BUTTON));
  });
};

const renderForOverDelivery = ({ onResult }) =>
  render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <ScanHUAndGetQtyComponent
        qtyMax={QTY_REMAINING}
        qtyTarget={QTY_REMAINING}
        uom="PCE"
        resolveScannedBarcode={async (scannedBarcode) => ({
          qrCode: { code: scannedBarcode, isUnique: false, isTUToBePickedAsWhole: true },
          isTUToBePickedAsWhole: true,
          qtyInitial: HU_QTY,
        })}
        onResult={onResult}
        getConfirmationPromptForQty={(qty) => (Number(qty) > QTY_REMAINING ? 'Pack more than ordered?' : undefined)}
      />
    </Provider>
  );

describe('ScanHUAndGetQtyComponent over-delivery confirmation', () => {
  // The happy path of the in-flight guard - confirming the over-delivery shows the pick running, takes the
  // scan step away, and books exactly once however often the operator scans meanwhile - is driven end to end
  // by e2e/mobile-webui/tests/spec/picking/overPickingPrompt_wholeHU.spec.js. What no E2E drives is a pick
  // that FAILS: the running state has to be cleared then too, or the operator is left on the spinner with
  // nothing to scan into and no way out.
  it('lets the operator scan again after a confirmed pick failed', async () => {
    let failThePick;
    const pick = new Promise((resolve, reject) => (failThePick = reject));
    const onResult = jest.fn(() => pick);

    renderForOverDelivery({ onResult });
    await act(async () => {});

    await scanWholeTU('WHOLE-TU-LABEL-1');
    await confirmTheOverDelivery();

    // The pick is running: no scan step, so the operator cannot start a second one.
    expect(document.querySelector(SPINNER)).not.toBeNull();
    expect(screen.queryByTestId(SCANNER_INPUT)).toBeNull();

    await act(async () => {
      failThePick({ message: 'pick rejected by the server' });
    });

    // It failed, so the operator gets the scan step back and can pick the HU again.
    expect(document.querySelector(SPINNER)).toBeNull();
    expect(screen.getByTestId(SCANNER_INPUT)).toBeInTheDocument();

    await scanWholeTU('WHOLE-TU-LABEL-2');
    await confirmTheOverDelivery();
    expect(onResult).toHaveBeenCalledTimes(2);
  });

  // Both paths that book a qty - the typed CU qty and the whole scanned TU - must apply the same
  // over-delivery ceiling. A second copy of that ceiling would behave identically, so no behavioural test
  // could ever see it; what a copy cannot avoid is producing the same "above max" message.
  it('builds the "above max" message in exactly one place, so there is only one ceiling', () => {
    const componentSource = fs.readFileSync(COMPONENT_PATH, 'utf8');

    // The constant itself, plus the single place that turns it into the message.
    expect(componentSource.match(/DEFAULT_MSG_qtyAboveMax/g)).toHaveLength(2);
  });
});

const renderForGraiScan = ({ onResult }) =>
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

    renderForGraiScan({ onResult });
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

    renderForGraiScan({ onResult });
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
