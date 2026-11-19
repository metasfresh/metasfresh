import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../components/dialogs/GetQuantityDialog';
import { reducer as settings } from '../../reducers/settings';

jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  toastNotification: jest.fn(),
}));

const renderManual = ({ onQtyChange, ...props } = {}) =>
  render(
    <Provider store={createStore(combineReducers({ settings }))}>
      <GetQuantityDialog
        qtyTarget={10}
        qtyInitial={5}
        uom="PCE"
        onQtyChange={onQtyChange}
        onCloseDialog={jest.fn()}
        {...props}
      />
    </Provider>
  );

describe('GetQuantityDialog (Manual): one OK books once', () => {
  it('a click on OK calls onQtyChange exactly once, although the button also submits the form', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    renderManual({ onQtyChange });
    await act(async () => {
      fireEvent.click(screen.getByTestId('done-button'));
    });
    expect(onQtyChange).toHaveBeenCalledTimes(1);
  });

  it('the cancel button is a plain button: it does not submit the form', () => {
    renderManual({ onQtyChange: jest.fn(() => Promise.resolve()) });
    expect(screen.getByTestId('cancel-button')).toHaveAttribute('type', 'button');
  });
});
