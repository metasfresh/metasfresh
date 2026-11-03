import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import NewHUPreviewScreen from '../../../apps/huManager/containers/NewHUPreviewScreen';
import { huManagerReducer } from '../../../apps/huManager/reducers';
import { putSettingsAction, reducer as settings } from '../../../reducers/settings';
import { toastNotification } from '../../../utils/toast';
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
const OTHER_LOCATOR_QR = 'LOC#1#{"warehouseId":540008,"locatorId":540010,"caption":"Nebenlager"}';

const scan = async (barcode) => {
  const input = screen.getByTestId(SCANNER_INPUT);
  input.value = barcode;
  await act(async () => {
    fireEvent.keyUp(input, { key: 'Enter' });
  });
};

const renderPreview = ({ locator = { locatorQRCode: LOCATOR_QR, locatorCaption: 'Hauptlager' } } = {}) => {
  const proposal = {
    product: { productId: 1, value: 'P1', name: 'Product 1' },
    qty: 1,
    uom: 'PCE',
    attributes: [],
    ...locator,
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
  return store;
};

// The location is changed with the "Change locator" button: it opens the scan dialog of the HU Manager and the scanned location
// replaces the proposed one (the preview itself has no scanner).
describe('NewHUPreviewScreen change locator', () => {
  const openDialog = async () => {
    await act(async () => {});
    fireEvent.click(screen.getByTestId('change-locator-button'));
    await act(async () => {});
  };

  it('has no scanner of its own; the button opens the scan dialog; a scanned location QR code is applied', async () => {
    renderPreview();
    await act(async () => {});
    expect(screen.queryByTestId(SCANNER_INPUT)).not.toBeInTheDocument();
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Hauptlager');

    await openDialog();
    expect(screen.getByTestId('ChangeCurrentLocatorDialog')).toBeInTheDocument();
    await scan(OTHER_LOCATOR_QR);

    expect(screen.queryByTestId('ChangeCurrentLocatorDialog')).not.toBeInTheDocument();
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Nebenlager');
  });

  it('a plain locator barcode is resolved by the server', async () => {
    renderPreview();
    await openDialog();
    mockResolveLocatorResponse({ locator: { qrCode: OTHER_LOCATOR_QR } });
    await scan('4711');
    expect(axios.post).toHaveBeenCalledWith(expect.stringContaining('/resolveLocator'), { scannedBarcode: '4711' });
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Nebenlager');
  });

  it('Cancel in the dialog keeps the proposed location', async () => {
    renderPreview();
    await openDialog();
    fireEvent.click(screen.getByTestId('cancelText-button'));
    await act(async () => {});
    expect(screen.queryByTestId('ChangeCurrentLocatorDialog')).not.toBeInTheDocument();
    expect(screen.getByTestId('locator-value')).toHaveTextContent('Hauptlager');
  });

  it('no proposed location -> OK disabled until a location is scanned in the dialog', async () => {
    renderPreview({ locator: {} });
    await act(async () => {});
    expect(screen.getByTestId('ok-button')).toBeDisabled();
    await openDialog();
    await scan(OTHER_LOCATOR_QR);
    expect(screen.getByTestId('ok-button')).toBeEnabled();
  });
});

// Quantity: an inline field. The preview has no scanner, so no window-level key reader can swallow typed digits.
describe('NewHUPreviewScreen inline quantity field', () => {
  it('(i) typed digits stay in the field (no scanner on the screen, the key is not cancelled)', async () => {
    renderPreview();
    await act(async () => {});
    const qtyField = screen.getByTestId('qty-input');
    expect(screen.queryByTestId(SCANNER_INPUT)).not.toBeInTheDocument();
    // fireEvent returns false when the keydown was cancelled (preventDefault): the browser would not insert the character
    expect(fireEvent.keyDown(qtyField, { key: '5' })).toBe(true);
    fireEvent.change(qtyField, { target: { value: '57' } });
    expect(qtyField).toHaveValue('57');
  });

  it('(ii) an emptied field disables OK; typed again it is enabled', async () => {
    renderPreview();
    await act(async () => {});
    expect(screen.getByTestId('ok-button')).toBeEnabled();
    fireEvent.change(screen.getByTestId('qty-input'), { target: { value: '' } });
    expect(screen.getByTestId('ok-button')).toBeDisabled();
    fireEvent.change(screen.getByTestId('qty-input'), { target: { value: '4' } });
    expect(screen.getByTestId('ok-button')).toBeEnabled();
  });

  it('(iii) typed quantity + changed location -> OK creates with both', async () => {
    renderPreview();
    await act(async () => {});
    api.createHUFromScannedCode.mockResolvedValue({ id: '1000001', displayName: '1000001 (CU)' });
    fireEvent.change(screen.getByTestId('qty-input'), { target: { value: '3' } });
    fireEvent.click(screen.getByTestId('change-locator-button'));
    await act(async () => {});
    await scan(OTHER_LOCATOR_QR);
    await act(async () => {
      fireEvent.click(screen.getByTestId('ok-button'));
    });
    expect(api.createHUFromScannedCode).toHaveBeenCalledWith({
      scannedCode: 'LABEL-1',
      qty: 3,
      locatorQRCode: OTHER_LOCATOR_QR,
    });
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
