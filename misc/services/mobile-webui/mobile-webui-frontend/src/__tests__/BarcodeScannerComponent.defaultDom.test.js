import React from 'react';
import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';
import BarcodeScannerComponent from '../components/BarcodeScannerComponent';
import { reducer as settingsReducer, putSettingsAction } from '../reducers/settings';

jest.mock('../utils/ui_trace', () => ({
  putContext: jest.fn(),
  trace: jest.fn(),
  traceLogWarn: jest.fn(),
  traceFunction: (fn) => fn,
}));
jest.mock('../utils/audio', () => ({ beep: jest.fn() }));
// the camera panel opens a camera stream (@zxing/browser), which jsdom does not have
jest.mock('../components/BarcodeScanner/CameraModePanel', () => () => <div data-testid="camera-mode-panel-stub" />);

// Pins the DOM the shared scanner renders for a caller that passes no layout, per settings set.
// Every other mobile screen uses the scanner this way, so the pinned markup must not change.
const renderDefaultDom = (settings) => {
  const store = createStore(combineReducers({ settings: settingsReducer }));
  store.dispatch(putSettingsAction(settings));
  const { container } = render(
    <Provider store={store}>
      <BarcodeScannerComponent onResolvedResult={jest.fn()} />
    </Provider>
  );
  return container.innerHTML;
};

describe('BarcodeScannerComponent default DOM (caller without layout)', () => {
  it('hardware default, manual enabled', () => {
    expect(
      renderDefaultDom({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'N',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'hardware',
      })
    ).toMatchSnapshot();
  });

  it('manual default', () => {
    expect(
      renderDefaultDom({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'N',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'manual',
      })
    ).toMatchSnapshot();
  });

  it('hardware default, camera enabled', () => {
    expect(
      renderDefaultDom({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'Y',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'hardware',
      })
    ).toMatchSnapshot();
  });
});
