import { toQtyEntryBehaviorProps } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';
import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../../../components/dialogs/GetQuantityDialog';
import { reducer as settings } from '../../../../reducers/settings';

jest.mock('../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../../utils/audio', () => ({ beep: jest.fn() }));
jest.mock('../../../../utils/toast', () => ({
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

const payloads = (fn) => fn.mock.calls.map(([arg]) => arg.isCloseTarget);

describe('GetQuantityDialog (Manual): submit and default buttons', () => {
  it('the form submit (Enter in the quantity field) books with isCloseTarget:false', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    const { container } = renderManual({ onQtyChange });
    await act(async () => {
      fireEvent.submit(container.querySelector('form'));
    });
    expect(payloads(onQtyChange)).toEqual([false]);
  });

  // On the release line DialogButton is a plain button unless it opts in to submit (#26569), so in Manual only Done
  // submits the form: Enter books without closing the target.
  it('with an LU target, only the Done button is a submit button: Enter books without closing the target', () => {
    renderManual({ onQtyChange: jest.fn(() => Promise.resolve()), isShowCloseTargetButton: true });
    expect(screen.getByTestId('confirmDoneAndCloseTarget-button')).toHaveAttribute('type', 'button');
    expect(screen.getByTestId('done-button')).toHaveAttribute('type', 'submit');
    expect(screen.getByTestId('cancel-button')).toHaveAttribute('type', 'button');
  });
});

describe('GetQuantityDialog (label-required CU quantity entry mode): one OK books once', () => {
  it('a click on OK calls onQtyChange exactly once', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    renderManual({ onQtyChange, ...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY) });
    await act(async () => {
      fireEvent.click(screen.getByTestId('done-button'));
    });
    expect(onQtyChange).toHaveBeenCalledTimes(1);
  });

  it('the form submit does not book; secondary buttons are plain buttons', async () => {
    const onQtyChange = jest.fn(() => Promise.resolve());
    const { container } = renderManual({
      onQtyChange,
      ...toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY),
      isShowCloseTargetButton: true,
    });
    await act(async () => {
      fireEvent.submit(container.querySelector('form'));
    });
    expect(onQtyChange).not.toHaveBeenCalled();
    expect(screen.getByTestId('cancel-button')).toHaveAttribute('type', 'button');
    expect(screen.getByTestId('confirmDoneAndCloseTarget-button')).toHaveAttribute('type', 'button');
  });
});
