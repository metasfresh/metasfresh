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

// Reproduces the async-settings race behind flaky-test case 19
// (barcode_scanner_modes.spec.js "manual mode — visible editable input rendered…").
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

describe('BarcodeScannerComponent — async settings race (flaky case 19)', () => {
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
});
