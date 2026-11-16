import React, { useCallback, useState } from 'react';
import PropTypes from 'prop-types';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';

import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { useKeyboardBarcodeReader } from '../../hooks/useKeyboardBarcodeReader';

// The scanner component must deliver every scan exactly once, also when the screen around it runs its
// own keyboard reader that re-renders the screen on the same keystroke (the GRAI screens do this).
//
// The screen's reader handles the scan's Enter first and changes state twice (like useGrais: the list,
// then `dirty`). Outside a React event handler React 17 renders each change synchronously, and the
// second render first runs the pending effects of the first one. If the scanner's reader re-subscribes
// its window keydown listener in such an effect, it is removed while that Enter is still being
// dispatched, so it never sees the Enter: the code stays in its buffer and is either delivered again
// later or glued to the next scan.

jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/ui_trace', () => ({
  traceFunction: (fn) => fn,
  putContext: jest.fn(),
  traceLogWarn: jest.fn(),
}));

const createSettingsStore = () => createStore(() => ({ settings: { backend: {} } }));

const ScreenWithOwnReader = ({ onScannerResult }) => {
  const [, setCodes] = useState([]);
  const [, setDirty] = useState(false);
  const onReadDone = useCallback((code) => {
    setCodes((prev) => [...prev, code]);
    setDirty(true);
  }, []);
  useKeyboardBarcodeReader({ onReadDone });

  return <BarcodeScannerComponent onResolvedResult={({ scannedBarcode }) => onScannerResult(scannedBarcode)} />;
};

ScreenWithOwnReader.propTypes = {
  onScannerResult: PropTypes.func.isRequired,
};

// Real browser keystrokes are dispatched outside React's event system, so they are NOT wrapped in act()
// (fireEvent would batch the screen's two state changes and hide the problem).
const typeScan = (code) => {
  [...code, 'Enter'].forEach((key) => window.dispatchEvent(new KeyboardEvent('keydown', { key })));
};

const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

describe('BarcodeScannerComponent next to a screen with its own keyboard reader', () => {
  let consoleLogSpy;
  let consoleErrorSpy;
  beforeEach(() => {
    consoleLogSpy = jest.spyOn(console, 'log').mockImplementation(() => {});
    // React warns that the keystroke updates are not wrapped in act(); they are not, on purpose.
    // Any other error is still printed.
    const originalConsoleError = console.error;
    consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation((message, ...args) => {
      if (String(message).includes('not wrapped in act(')) return;
      originalConsoleError(message, ...args);
    });
  });
  afterEach(() => {
    consoleLogSpy.mockRestore();
    consoleErrorSpy.mockRestore();
  });

  it('delivers each scan exactly once, not repeated or glued to the next one', async () => {
    const scannerResults = [];
    render(
      <Provider store={createSettingsStore()}>
        <ScreenWithOwnReader onScannerResult={(code) => scannerResults.push(code)} />
      </Provider>
    );

    const codes = ['8003000000000000000001', '8003000000000000000002', '8003000000000000000003'];
    for (const code of codes) {
      typeScan(code);
      // let the scanner finish processing, but stay well within its 300 ms reading window
      await sleep(20);
    }
    // longer than the reader's idle flush (2 x 300 ms), so a code left in its buffer shows up
    await sleep(1000);

    expect(scannerResults).toEqual(codes);
  });
});
