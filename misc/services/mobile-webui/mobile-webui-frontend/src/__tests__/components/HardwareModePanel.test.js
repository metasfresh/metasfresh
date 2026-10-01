import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { reducer as settings } from '../../reducers/settings';
import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';

const store = createStore(combineReducers({ settings }));

const panel = ({ isProcessing = false, onBarcodeScanned }) => (
  <Provider store={store}>
    <HardwareModePanel isProcessing={isProcessing} onBarcodeScanned={onBarcodeScanned} />
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

describe('HardwareModePanel', () => {
  it('writes only the first character of a scan into the hidden input', () => {
    // The first character flips the input off :placeholder-shown, which drives the "scan in progress"
    // caption (BarcodeScannerComponent.scss). Writing every further character forces a style recalc +
    // layout per keystroke, which on a slow handheld adds up to seconds per long QR code.
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

    expect(valueWrites).toEqual(['H']);
    expect(input.value).toBe('H');
  });

  it('still shows the scan in progress when the input was not mounted at the first character', () => {
    // While the previous scan is processing, the hidden input is not rendered. If the next scan's first
    // character arrives then, the caption must still appear once the input is back.
    const { rerenderPanel, container } = renderPanel({ isProcessing: true });
    expect(container.querySelector('#input-text')).toBeNull();

    pressKeys('H');
    rerenderPanel({ isProcessing: false });
    pressKeys('U#1#');

    expect(container.querySelector('#input-text').value).not.toBe('');
  });
});
