import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, act, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';
import { toast } from 'react-toastify';
import BarcodeScannerComponent from '../components/BarcodeScannerComponent';
import { toastError, toastErrorFromObj } from '../utils/toast';
import { reducer as settingsReducer } from '../reducers/settings';

jest.mock('../utils/ui_trace', () => ({
  putContext: jest.fn(),
  trace: jest.fn(),
  traceLogWarn: jest.fn(),
  traceFunction: (fn) => fn,
}));
jest.mock('../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../utils/toast', () => ({ toastError: jest.fn(), toastErrorFromObj: jest.fn() }));
jest.mock('react-toastify', () => ({ toast: { isActive: jest.fn() } }));

// A scan error toast must be seen on every scan screen: one error toast at a time (a repeated error must not stack), but
// every toast gets its own id — a toast shown right after the previous one was closed must not reuse the previous id,
// else it flashes and vanishes (the 2nd consecutive error was never seen).
describe('BarcodeScannerComponent — scan error toast', () => {
  const scan = async (value) => {
    const input = screen.getByTestId('qrCode-input');
    await act(async () => {
      input.value = value;
      fireEvent.keyUp(input, { key: 'Enter' });
    });
  };

  const renderScanner = (resolveScannedBarcode = jest.fn(() => Promise.reject(new Error('rejected')))) => {
    const store = createStore(combineReducers({ settings: settingsReducer }));
    render(
      <Provider store={store}>
        <BarcodeScannerComponent onResolvedResult={jest.fn()} resolveScannedBarcode={resolveScannedBarcode} />
      </Provider>
    );
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('gives a new toast its own id once the previous toast was closed', async () => {
    renderScanner();
    toast.isActive.mockReturnValue(false);

    await scan('code-1');
    await scan('code-2');

    expect(toastErrorFromObj).toHaveBeenCalledTimes(2);
    const [firstId, secondId] = toastErrorFromObj.mock.calls.map((call) => call[1]);
    expect(firstId).toBeTruthy();
    expect(secondId).toBeTruthy();
    expect(secondId).not.toEqual(firstId);
  });

  it('shows no second toast while the previous one is still open', async () => {
    renderScanner();
    // a toast of this test is open as soon as the first one was shown (the module keeps the last id across tests)
    toast.isActive.mockImplementation(() => toastErrorFromObj.mock.calls.length > 0);

    await scan('code-1');
    await scan('code-2');

    // the first scan has no previous toast; the second finds the first still active
    expect(toastErrorFromObj).toHaveBeenCalledTimes(1);
  });

  // the second way a scan fails: the resolver answers with an error message instead of throwing
  it('gives the toast of a resolver error its own id, and shows none while the previous one is still open', async () => {
    renderScanner(jest.fn(({ scannedBarcode }) => Promise.resolve({ scannedBarcode, error: 'not a label' })));

    toast.isActive.mockReturnValue(false);
    await scan('code-1');
    await scan('code-2');
    expect(toastError).toHaveBeenCalledTimes(2);
    const [firstId, secondId] = toastError.mock.calls.map((call) => call[0].toastId);
    expect(firstId).toBeTruthy();
    expect(secondId).toBeTruthy();
    expect(secondId).not.toEqual(firstId);

    toast.isActive.mockReturnValue(true);
    await scan('code-3');
    expect(toastError).toHaveBeenCalledTimes(2);
  });
});
