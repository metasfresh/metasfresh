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
}));

// The operator scans a whole TU holding 3 pieces onto a line with 2 still to pick, so the component
// raises the over-delivery confirmation, and answering "Ja" fires the pick.
const QTY_REMAINING = 2;
const HU_QTY = 3;

const SCANNER_INPUT = 'scanHUBarcode-input';
const YES_BUTTON = '#yes-button';
const SPINNER = '.loading';

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

const renderComponent = ({ onResult }) =>
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

// The happy path of the in-flight guard - confirming the over-delivery shows the pick running, takes the
// scan step away, and books exactly once however often the operator scans meanwhile - is driven end to end
// by e2e/mobile-webui/tests/spec/picking/overPickingPrompt_wholeHU.spec.js. What no E2E drives is a pick
// that FAILS: the running state has to be cleared then too, or the operator is left on the spinner with
// nothing to scan into and no way out.
it('lets the operator scan again after a confirmed pick failed', async () => {
  let failThePick;
  const pick = new Promise((resolve, reject) => (failThePick = reject));
  const onResult = jest.fn(() => pick);

  renderComponent({ onResult });
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
