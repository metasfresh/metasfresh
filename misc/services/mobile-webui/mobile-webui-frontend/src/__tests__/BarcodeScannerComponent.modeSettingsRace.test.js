import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, act, fireEvent } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';
import BarcodeScannerComponent from '../components/BarcodeScannerComponent';
import { reducer as settingsReducer, putSettingsAction } from '../reducers/settings';

// ui_trace persists to IndexedDB / posts traces over the network, neither of which exists in
// jsdom — a button click would otherwise leave an unhandled promise rejection. Mock it out
// (same pattern as GetQuantityDialog.serialNo.test.js); traceFunction must stay pass-through.
jest.mock('../utils/ui_trace', () => ({
  putContext: jest.fn(),
  trace: jest.fn(),
  traceLogWarn: jest.fn(),
  traceFunction: (fn) => fn,
}));

// beep() news up an AudioContext and calls navigator.vibrate — neither exists in jsdom, so a
// completed scan would throw. Mock it (the scan-processing tests below drive a full scan cycle).
jest.mock('../utils/audio', () => ({ beep: jest.fn() }));

// Reproduces the async-settings race exercised by barcode_scanner_modes.spec.js
// ("manual mode — visible editable input rendered…").
//
// ApplicationRoot fetches the backend settings fire-and-forget in a useEffect AFTER login
// (getSettings().then(putSettingsAction)); there is NO ordering guarantee that this resolves
// before a scanner screen (e.g. HU Manager) mounts BarcodeScannerComponent. When the component
// mounts BEFORE settings arrive, useBarcodeScannerModes returns its hook defaults → defaultMode
// resolves to HARDWARE, and activeMode (a useState initialised from defaultMode) freezes there.
// When settings later arrive with defaultMode=manual, activeMode must adopt the configured
// default so the visible manual-entry input renders. Before the fix it stayed HARDWARE forever
// → manual-entry-input never attached → SLOW_ACTION_TIMEOUT flake.

const MANUAL_MODE_SETTINGS = {
  'barcodeScanner.mode.hardware.enabled': 'Y',
  'barcodeScanner.mode.camera.enabled': 'N',
  'barcodeScanner.mode.manual.enabled': 'Y',
  'barcodeScanner.defaultMode': 'manual',
};

const HARDWARE_MODE_SETTINGS = {
  'barcodeScanner.mode.hardware.enabled': 'Y',
  'barcodeScanner.mode.camera.enabled': 'N',
  'barcodeScanner.mode.manual.enabled': 'Y',
  'barcodeScanner.defaultMode': 'hardware',
};

const renderWithEmptySettings = () => {
  const store = createStore(combineReducers({ settings: settingsReducer }));
  render(
    <Provider store={store}>
      <BarcodeScannerComponent onResolvedResult={jest.fn()} />
    </Provider>
  );
  return store;
};

describe('BarcodeScannerComponent — activeMode adoption on async settings load', () => {
  it('adopts the configured manual defaultMode when settings arrive AFTER mount', () => {
    const store = renderWithEmptySettings();

    // Settings not loaded yet at mount → hook defaults → HARDWARE → no visible manual input.
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();

    // Settings resolve late (ApplicationRoot's async getSettings) → defaultMode becomes manual.
    act(() => {
      store.dispatch(putSettingsAction(MANUAL_MODE_SETTINGS));
    });

    // The scanner must now switch to the configured manual mode and render the editable input.
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();
  });

  it('does NOT revert an operator mode choice made during the settings-load window', () => {
    // Mounts before settings arrive → boots in the default HARDWARE mode. The footer's "enter
    // manually" button is live (gated only on mode.manual.enabled, default true), so the operator
    // can switch to manual DURING the load window — a real path on a slow handheld network.
    const store = renderWithEmptySettings();
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();

    act(() => {
      fireEvent.click(screen.getByTestId('barcode-scanner-enter-manually'));
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();

    // Settings now arrive with a DIFFERENT configured default (hardware). The operator's explicit
    // manual choice must survive — adopting the settings default here would silently yank them out
    // of the manual input they are typing into.
    act(() => {
      store.dispatch(putSettingsAction(HARDWARE_MODE_SETTINGS));
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();
  });

  it('does NOT override the operator even when they return to a mode equal to the pre-settings default', () => {
    // Edge the flag guards but a value-equality check could not: operator goes manual and back to
    // hardware (which equals the mount-time default) during the load window, then settings arrive
    // with a DIFFERENT default (manual). The operator explicitly chose hardware — it must stick.
    const store = renderWithEmptySettings();

    act(() => {
      fireEvent.click(screen.getByTestId('barcode-scanner-enter-manually'));
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();

    act(() => {
      fireEvent.click(screen.getByTestId('barcode-scanner-back-to-scanner'));
    });
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();

    // Settings arrive with defaultMode=manual — but the operator deliberately went back to
    // hardware, so no auto-switch to manual may happen.
    act(() => {
      store.dispatch(putSettingsAction(MANUAL_MODE_SETTINGS));
    });
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();
  });

  it('defers adoption while a scan is processing, then adopts once processing ends', async () => {
    // Mounts before settings → boots HARDWARE. A scan is fired and is still in flight (slow
    // network) when the settings finally arrive carrying a DIFFERENT default (manual). Flipping
    // activeMode mid-scan would flicker the visible panel once; adoption must wait for a render
    // where isProcessing is false, then apply.
    let resolveScan;
    const resolveScannedBarcode = jest.fn(() => new Promise((resolve) => (resolveScan = resolve)));
    const store = createStore(combineReducers({ settings: settingsReducer }));
    render(
      <Provider store={store}>
        <BarcodeScannerComponent onResolvedResult={jest.fn()} resolveScannedBarcode={resolveScannedBarcode} />
      </Provider>
    );

    // Boots in HARDWARE mode: the offscreen scan input is present, no manual input.
    const hardwareInput = screen.getByTestId('qrCode-input');
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();

    // Fire a hardware scan → isProcessing becomes true and stays true (resolveScannedBarcode's
    // promise is left pending). The offscreen input unmounts while processing.
    act(() => {
      hardwareInput.value = '12345';
      fireEvent.keyUp(hardwareInput, { key: 'Enter' });
    });
    expect(resolveScannedBarcode).toHaveBeenCalled();
    expect(screen.queryByTestId('qrCode-input')).not.toBeInTheDocument();

    // Settings arrive mid-scan with defaultMode=manual. The guard must hold the mode on HARDWARE
    // (no manual input) until the scan finishes — no mid-scan flip.
    act(() => {
      store.dispatch(putSettingsAction(MANUAL_MODE_SETTINGS));
    });
    expect(screen.queryByTestId('manual-entry-input')).not.toBeInTheDocument();

    // Scan resolves → isProcessing flips false → the deferred adoption runs → manual mode shows.
    await act(async () => {
      resolveScan({ scannedBarcode: '12345', error: null });
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();
  });
});
