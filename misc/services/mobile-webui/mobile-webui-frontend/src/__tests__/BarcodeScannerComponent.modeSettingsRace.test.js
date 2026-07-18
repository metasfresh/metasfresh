import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, act } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';
import BarcodeScannerComponent from '../components/BarcodeScannerComponent';
import { reducer as settingsReducer, putSettingsAction } from '../reducers/settings';

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
});
