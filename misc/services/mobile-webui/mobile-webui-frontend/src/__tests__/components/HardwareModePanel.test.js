import React from 'react';
import { render } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import { reducer as settings } from '../../reducers/settings';
import HardwareModePanel from '../../components/BarcodeScanner/HardwareModePanel';

const renderPanel = () => {
  const onBarcodeScanned = jest.fn();
  const utils = render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <HardwareModePanel onBarcodeScanned={onBarcodeScanned} />
    </Provider>
  );
  return { onBarcodeScanned, input: utils.container.querySelector('#input-text'), ...utils };
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
});
