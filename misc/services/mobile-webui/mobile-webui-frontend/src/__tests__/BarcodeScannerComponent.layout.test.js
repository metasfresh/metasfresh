import React from 'react';
import '@testing-library/jest-dom';
import { render, screen, fireEvent, act } from '@testing-library/react';
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

const SETTINGS = (overrides) => ({
  'barcodeScanner.mode.hardware.enabled': 'Y',
  'barcodeScanner.mode.camera.enabled': 'N',
  'barcodeScanner.mode.manual.enabled': 'Y',
  'barcodeScanner.defaultMode': 'hardware',
  ...overrides,
});

const renderScanner = ({ settings, props = {} }) => {
  const store = createStore(combineReducers({ settings: settingsReducer }));
  store.dispatch(putSettingsAction(settings));
  return render(
    <Provider store={store}>
      <BarcodeScannerComponent onResolvedResult={jest.fn()} {...props} />
    </Provider>
  );
};

const pressKey = (key) => {
  fireEvent.keyDown(window, { key });
  fireEvent.keyUp(window, { key });
};

// Pins the DOM the shared scanner renders for a caller that passes no layout, per settings set.
// Every other mobile screen uses the scanner this way, so the pinned markup must not change.
const renderDomOf = (settings) => renderScanner({ settings }).container.innerHTML;

describe('BarcodeScannerComponent default DOM (caller without layout)', () => {
  it('hardware default, manual enabled', () => {
    expect(
      renderDomOf({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'N',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'hardware',
      })
    ).toMatchSnapshot();
  });

  it('manual default', () => {
    expect(
      renderDomOf({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'N',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'manual',
      })
    ).toMatchSnapshot();
  });

  it('hardware default, camera enabled', () => {
    expect(
      renderDomOf({
        'barcodeScanner.mode.hardware.enabled': 'Y',
        'barcodeScanner.mode.camera.enabled': 'Y',
        'barcodeScanner.mode.manual.enabled': 'Y',
        'barcodeScanner.defaultMode': 'hardware',
      })
    ).toMatchSnapshot();
  });
});

describe('BarcodeScannerComponent layout="strip"', () => {
  it('adds the strip class to the root; the default root class stays exactly "barcode-scanner"', () => {
    const strip = renderScanner({ settings: SETTINGS(), props: { layout: 'strip' } });
    expect(strip.container.firstChild).toHaveClass('barcode-scanner', 'barcode-scanner-strip');
    strip.unmount();

    const plain = renderScanner({ settings: SETTINGS() });
    expect(plain.container.firstChild.className).toBe('barcode-scanner');
  });

  it('keeps the panel and footer DOM of the default layout (same test ids)', () => {
    renderScanner({ settings: SETTINGS(), props: { layout: 'strip', testId: 'grai-scanner' } });
    expect(screen.getByTestId('grai-scanner')).toBeInTheDocument();
    expect(screen.getByTestId('barcode-scanner-enter-manually')).toBeInTheDocument();
  });

  it('manual entry: typed keys on window are not taken as a scan, only the manual input submits (exactly once)', async () => {
    const resolveScannedBarcode = jest.fn(() => Promise.resolve({ scannedBarcode: 'x', error: null }));
    renderScanner({ settings: SETTINGS(), props: { layout: 'strip', testId: 'grai-scanner', resolveScannedBarcode } });

    act(() => {
      fireEvent.click(screen.getByTestId('barcode-scanner-enter-manually'));
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();
    // the hardware input stays mounted (DataWedge / E2E contract)
    expect(screen.getByTestId('grai-scanner')).toBeInTheDocument();

    // key-by-key typing, fast burst + Enter, as a person typing would deliver to window
    'GRAI123'.split('').forEach(pressKey);
    pressKey('Enter');
    expect(resolveScannedBarcode).not.toHaveBeenCalled();

    const manualInput = screen.getByTestId('manual-entry-input');
    manualInput.value = 'GRAI123';
    await act(async () => {
      fireEvent.keyDown(manualInput, { key: 'Enter' });
      fireEvent.keyUp(manualInput, { key: 'Enter' });
    });
    expect(resolveScannedBarcode).toHaveBeenCalledTimes(1);
    expect(resolveScannedBarcode).toHaveBeenCalledWith({ scannedBarcode: 'GRAI123' });
  });

  it('manual entry: a key burst does not move the focus away from the manual input', () => {
    jest.useFakeTimers();
    try {
      renderScanner({ settings: SETTINGS(), props: { layout: 'strip', testId: 'grai-scanner' } });
      act(() => {
        fireEvent.click(screen.getByTestId('barcode-scanner-enter-manually'));
      });
      const manualInput = screen.getByTestId('manual-entry-input');
      manualInput.focus();
      'ABCDEFGH'.split('').forEach(pressKey);
      act(() => {
        jest.advanceTimersByTime(2500);
      });
      expect(document.activeElement).toBe(manualInput);
    } finally {
      jest.useRealTimers();
    }
  });

  it('hardware off, manual on: starts in manual, the window key listener is off (a key burst + Enter scans nothing)', () => {
    const resolveScannedBarcode = jest.fn(() => Promise.resolve({ scannedBarcode: 'x', error: null }));
    renderScanner({
      settings: SETTINGS({ 'barcodeScanner.mode.hardware.enabled': 'N', 'barcodeScanner.defaultMode': 'manual' }),
      props: { layout: 'strip', testId: 'grai-scanner', resolveScannedBarcode },
    });
    expect(screen.getByTestId('manual-entry-input')).toBeInTheDocument();

    'GRAI123456'.split('').forEach(pressKey);
    pressKey('Enter');
    expect(resolveScannedBarcode).not.toHaveBeenCalled();
  });

  it('hardware + camera: the footer offers the camera toggle; defaultMode=camera starts in the camera panel', () => {
    const hw = renderScanner({
      settings: SETTINGS({ 'barcodeScanner.mode.camera.enabled': 'Y' }),
      props: { layout: 'strip' },
    });
    expect(screen.getByTestId('barcode-scanner-toggle-hw-camera')).toBeInTheDocument();
    hw.unmount();

    renderScanner({
      settings: SETTINGS({ 'barcodeScanner.mode.camera.enabled': 'Y', 'barcodeScanner.defaultMode': 'camera' }),
      props: { layout: 'strip' },
    });
    expect(screen.getByTestId('camera-mode-panel-stub')).toBeInTheDocument();
  });
});
