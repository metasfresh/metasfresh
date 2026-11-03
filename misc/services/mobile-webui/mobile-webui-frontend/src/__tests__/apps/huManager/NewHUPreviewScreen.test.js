import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import NewHUPreviewScreen from '../../../apps/huManager/containers/NewHUPreviewScreen';
import { huManagerReducer } from '../../../apps/huManager/reducers';
import { putSettingsAction, reducer as settings } from '../../../reducers/settings';
import { toastErrorFromObj, toastNotification } from '../../../utils/toast';
import * as api from '../../../apps/huManager/api';
import { huManagerLocation } from '../../../apps/huManager/routes';
import axios from 'axios';

jest.mock('../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));
const mockHistory = { goBack: jest.fn(), push: jest.fn(), replace: jest.fn() };
jest.mock('../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: () => ({ history: mockHistory }),
}));
jest.mock('../../../apps/huManager/api');
jest.mock('axios');
const mockResolveLocatorResponse = (data) => axios.post.mockResolvedValue({ status: 200, data });

const SCANNER_INPUT = 'qrCode-input';
const LOCATOR_QR = 'LOC#1#{"warehouseId":540008,"locatorId":540009,"caption":"Hauptlager"}';

const scan = async (barcode) => {
  const input = screen.getByTestId(SCANNER_INPUT);
  input.value = barcode;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
};

// The case the handheld fixtures cannot produce (every fixture warehouse has an active locator): the location rule found
// no location, so the preview comes without one.
describe('NewHUPreviewScreen without a proposed location', () => {
  const renderPreviewWithoutLocation = () => {
    const proposal = { product: { productId: 1, value: 'P1', name: 'Product 1' }, qty: 1, uom: 'PCE', attributes: [] };
    const store = createStore(combineReducers({ settings, 'applications/huManager': huManagerReducer }), {
      'applications/huManager': { newHUPreview: { scannedCode: 'LABEL-1', proposal } },
    });
    return render(
      <Provider store={store}>
        <NewHUPreviewScreen />
      </Provider>
    );
  };

  it('no location → OK disabled until a location is scanned', async () => {
    renderPreviewWithoutLocation();
    await act(async () => {});
    expect(screen.getByTestId('ok-button')).toBeDisabled();

    // a product label is no location: refused through the scanner (error toast + error beep), still disabled
    mockResolveLocatorResponse({ error: 'not a locator' });
    await scan('LABEL-1');
    expect(toastErrorFromObj).toHaveBeenCalledWith(
      expect.stringContaining('huManager.newHU.notALocator'),
      expect.anything()
    );
    expect(screen.getByTestId('ok-button')).toBeDisabled();

    // a plain locator barcode is resolved by the server
    mockResolveLocatorResponse({ locator: { qrCode: LOCATOR_QR } });
    await scan('4711');
    expect(axios.post).toHaveBeenCalledWith(expect.stringContaining('/resolveLocator'), { scannedBarcode: '4711' });
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Hauptlager');
    expect(screen.getByTestId('ok-button')).toBeEnabled();
  });

  it('a location QR code is resolved without a server call', async () => {
    renderPreviewWithoutLocation();
    await act(async () => {});
    // a location QR code replaces the location at once
    await scan(LOCATOR_QR);
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Hauptlager');
    expect(screen.getByTestId('ok-button')).toBeEnabled();
  });
});

// Handheld with a hardware scanner (keyboard wedge): the scanner's window-level keydown reader cancels every printable key,
// so a quantity typed next to a live scanner never reaches the input. The quantity is therefore entered in a dialog that
// unmounts the scanner. The Playwright E2E types with real key events too; fill() sends no keydown and cannot see this.
describe('NewHUPreviewScreen quantity entry next to a hardware scanner', () => {
  const renderPreview = () => {
    const proposal = {
      product: { productId: 1, value: 'P1', name: 'Product 1' },
      qty: 1,
      uom: 'PCE',
      attributes: [],
      locatorQRCode: LOCATOR_QR,
      locatorCaption: 'Hauptlager',
    };
    const store = createStore(combineReducers({ settings, 'applications/huManager': huManagerReducer }), {
      'applications/huManager': { newHUPreview: { scannedCode: 'LABEL-1', proposal } },
    });
    store.dispatch(
      putSettingsAction({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'N',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'hardware',
      })
    );
    render(
      <Provider store={store}>
        <NewHUPreviewScreen />
      </Provider>
    );
  };

  it('a printable key pressed in the quantity field is not cancelled by the scanner', async () => {
    renderPreview();
    await act(async () => {});
    fireEvent.click(screen.getByTestId('qty-edit-button'));
    await act(async () => {});

    // fireEvent returns false when the keydown was cancelled (preventDefault): the browser would not insert the character
    const qtyField = screen.getByTestId('qty-input'); // the field of the dialog
    expect(fireEvent.keyDown(qtyField, { key: '5' })).toBe(true);
  });
});

// AC4: after OK the user is back on the scan screen with a success message — the new HU is NOT loaded into the HU info screen
// (the user opens it by typing the HU number from the message).
describe('NewHUPreviewScreen after OK', () => {
  it('shows the success message, keeps the new HU out of the HU info state and returns to the scan screen', async () => {
    const proposal = {
      product: { productId: 1, value: 'P1', name: 'Product 1' },
      qty: 1,
      uom: 'PCE',
      attributes: [],
      locatorQRCode: LOCATOR_QR,
      locatorCaption: 'Hauptlager',
    };
    const store = createStore(combineReducers({ settings, 'applications/huManager': huManagerReducer }), {
      'applications/huManager': { newHUPreview: { scannedCode: 'LABEL-1', proposal } },
    });
    api.createHUFromScannedCode.mockResolvedValue({ id: '1000001', displayName: '1000001 (CU)' });
    render(
      <Provider store={store}>
        <NewHUPreviewScreen />
      </Provider>
    );
    await act(async () => {});

    await act(async () => {
      fireEvent.click(screen.getByTestId('ok-button'));
    });

    expect(toastNotification).toHaveBeenCalledTimes(1); // the success message (its text is asserted in the E2E: HU number + quantity)
    expect(store.getState()['applications/huManager'].handlingUnitInfo ?? null).toBeNull(); // not loaded -> the scan screen is shown
    expect(mockHistory.replace).toHaveBeenCalledWith(huManagerLocation());
    expect(mockHistory.goBack).not.toHaveBeenCalled();
    expect(store.getState()['applications/huManager'].newHUPreview).toBeNull(); // not reachable again: a second OK would create a second HU
  });
});
