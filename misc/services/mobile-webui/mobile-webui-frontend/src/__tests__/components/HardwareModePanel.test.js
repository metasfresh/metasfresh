import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { reducer as settings } from '../../reducers/settings';
import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';

const store = createStore(combineReducers({ settings }));

const panel = ({ isProcessing = false, disabled = false, onBarcodeScanned }) => (
  <Provider store={store}>
    <HardwareModePanel isProcessing={isProcessing} disabled={disabled} onBarcodeScanned={onBarcodeScanned} />
  </Provider>
);

const renderPanel = ({ isProcessing } = {}) => {
  const onBarcodeScanned = jest.fn();
  const utils = render(panel({ isProcessing, onBarcodeScanned }));
  const rerenderPanel = (props) => utils.rerender(panel({ onBarcodeScanned, ...props }));
  return { onBarcodeScanned, rerenderPanel, input: utils.container.querySelector('#input-text'), ...utils };
};

const pressKeys = (text) => {
  for (const key of text) {
    act(() => {
      window.dispatchEvent(new KeyboardEvent('keydown', { key, bubbles: true, cancelable: true }));
    });
  }
};

const isScanInProgressShown = (container) =>
  container.querySelector('.scan-prompt').classList.contains('scan-in-progress');

describe('HardwareModePanel', () => {
  it('does not write the scanned characters into the hidden input', () => {
    // The "scan in progress" caption is driven by an explicit state, not by the input's content: every
    // write into the input forces a style recalc + layout, which on a slow handheld adds up to seconds
    // per long QR code. The scanned code itself comes from the reader hook's buffer.
    const { input } = renderPanel();
    const valueWrites = [];
    const setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;
    Object.defineProperty(input, 'value', {
      configurable: true,
      get: () => valueWrites[valueWrites.length - 1] ?? '',
      set: (value) => {
        valueWrites.push(value);
        setter.call(input, value);
      },
    });

    pressKeys('HU#1#{"id":"abc"');

    expect(valueWrites).toEqual([]);
  });

  it('shows the scan in progress while a scan is being read and hides it when the scan is done', () => {
    const { container, onBarcodeScanned } = renderPanel();
    expect(isScanInProgressShown(container)).toBe(false);

    pressKeys('HU#1#');
    expect(isScanInProgressShown(container)).toBe(true);

    pressKeys(['Enter']);
    expect(onBarcodeScanned).toHaveBeenCalledTimes(1);
    expect(isScanInProgressShown(container)).toBe(false);
  });

  it('still shows the scan in progress when the input was not mounted at the first character', () => {
    // While the previous scan is processing, the hidden input is not rendered. If the next scan's first
    // character arrives then, the caption must still appear once the input is back.
    const { rerenderPanel, container } = renderPanel({ isProcessing: true });
    expect(container.querySelector('#input-text')).toBeNull();

    pressKeys('H');
    rerenderPanel({ isProcessing: false });
    pressKeys('U#1#');

    expect(isScanInProgressShown(container)).toBe(true);
  });

  it('hides the scan in progress when the reader is disabled mid-scan', () => {
    // E.g. switching HARDWARE -> MANUAL mid-scan: the reader drops its buffer without onReadDone, while
    // the hidden input stays mounted. The caption must not stay on "scanning" after switching back.
    const { rerenderPanel, container } = renderPanel();
    pressKeys('HU#1#');
    expect(isScanInProgressShown(container)).toBe(true);

    rerenderPanel({ disabled: true });
    rerenderPanel({ disabled: false });

    expect(isScanInProgressShown(container)).toBe(false);
  });

  it('keeps the same keydown listener for the whole scan', () => {
    // The in-progress state re-renders the panel mid-scan; that must not tear down and re-attach the
    // reader's window listener (and its idle timer) for every scan.
    const { rerenderPanel } = renderPanel();
    const addSpy = jest.spyOn(window, 'addEventListener');
    const removeSpy = jest.spyOn(window, 'removeEventListener');

    pressKeys('HU#1#');
    pressKeys(['Enter']);
    rerenderPanel({});

    expect(addSpy.mock.calls.filter(([type]) => type === 'keydown')).toHaveLength(0);
    expect(removeSpy.mock.calls.filter(([type]) => type === 'keydown')).toHaveLength(0);
    addSpy.mockRestore();
    removeSpy.mockRestore();
  });
});
