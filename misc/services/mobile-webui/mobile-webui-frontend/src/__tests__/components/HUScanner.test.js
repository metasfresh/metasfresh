import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import HUScanner from '../../components/huSelector/HUScanner';
import { reducer as settings } from '../../reducers/settings';
import * as huManagerApi from '../../apps/huManager/api';
import { toastErrorFromObj } from '../../utils/toast';

jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/toast', () => ({
  ...jest.requireActual('../../utils/toast'),
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));
jest.mock('../../apps/huManager/api');

const SCANNER_INPUT = 'qrCode-input';

const scan = async (barcode) => {
  const input = screen.getByTestId(SCANNER_INPUT);
  input.value = barcode;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
};

// Every scanner user other than the HU Manager (e.g. the manufacturing raw-material
// issue) keeps today's behaviour for a scanned code that is no HU: the not-found error goes to the user, nothing resolves.
describe('HUScanner not-found handling', () => {
  it('without onHUNotFound rethrows not-found', async () => {
    const notFound = { response: { status: 404, data: {} } };
    huManagerApi.getHUByQRCode.mockRejectedValue(notFound);
    const onResolvedBarcode = jest.fn();

    render(
      <Provider store={createStore(combineReducers({ settings }))}>
        <HUScanner onResolvedBarcode={onResolvedBarcode} />
      </Provider>
    );
    await act(async () => {});

    await scan('NOT-AN-HU');

    expect(huManagerApi.getHUByQRCode).toHaveBeenCalledWith('NOT-AN-HU');
    expect(toastErrorFromObj).toHaveBeenCalledWith(notFound, expect.anything());
    expect(onResolvedBarcode).not.toHaveBeenCalled();
  });
});
