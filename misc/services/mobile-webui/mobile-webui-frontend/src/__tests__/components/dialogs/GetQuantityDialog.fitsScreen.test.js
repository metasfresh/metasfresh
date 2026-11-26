import React from 'react';
import '@testing-library/jest-dom';
import { render } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../../components/dialogs/GetQuantityDialog';
import { reducer as settings } from '../../../reducers/settings';

jest.mock('../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
jest.mock('../../../utils/audio', () => ({ beep: jest.fn() }));

// The quantity pop-up is never taller than the screen: `fits-screen` is the class that
// get-qty-dialog.scss keys the height limit and the scrolling table area on (the real heights are pinned by the layout
// E2E specs). Every screen that uses the dialog gets it, not only the ones of the label modes.

describe('GetQuantityDialog: fits the screen', () => {
  it('the dialog carries the fits-screen class, with and without the options of the label modes', () => {
    for (const props of [
      {},
      { isRepeatedBooking: true, isSubmitOnEnter: false, isLotAndBestBeforeShownReadOnly: true },
    ]) {
      const { container, unmount } = render(
        <Provider store={createStore(combineReducers({ settings }))}>
          <GetQuantityDialog qtyTarget={10} uom="PCE" onQtyChange={jest.fn()} onCloseDialog={jest.fn()} {...props} />
        </Provider>
      );
      expect(container.querySelector('.prompt-dialog.get-qty-dialog.fits-screen')).not.toBeNull();
      unmount();
    }
  });
});
