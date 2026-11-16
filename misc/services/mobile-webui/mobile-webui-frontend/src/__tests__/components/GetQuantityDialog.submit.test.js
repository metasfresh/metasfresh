import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import GetQuantityDialog from '../../components/dialogs/GetQuantityDialog';
import { reducer as settings } from '../../reducers/settings';

// A recording uiTrace: which events were traced, and whether putContext ran inside a traced event.
const mockTracedEvents = [];
const mockPutContextCalls = [];
let mockInsideTracedEvent = false;
jest.mock('../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func, properties) => {
    if (!func) return func;
    return async (...args) => {
      mockTracedEvents.push(properties);
      mockInsideTracedEvent = true;
      try {
        return await func(...args);
      } finally {
        mockInsideTracedEvent = false;
      }
    };
  },
  traceLogWarn: jest.fn(),
  putContext: (context) => mockPutContextCalls.push({ context, isInsideTracedEvent: mockInsideTracedEvent }),
}));
jest.mock('../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
}));
jest.mock('../../utils/websocket', () => ({
  connectAndSubscribe: jest.fn(() => ({})),
  disconnectClient: jest.fn(),
}));
jest.mock('../../components/BarcodeScannerComponent', () => () => <div data-testid="fake-scanner" />);

// The dialog is shared (picking, distribution, manufacturing...). Its form must confirm exactly once, only
// through the Done button / Enter, and none of the other buttons may submit it.
beforeEach(() => {
  mockTracedEvents.length = 0;
  mockPutContextCalls.length = 0;
});

const renderDialog = (props = {}) => {
  const onQtyChange = jest.fn();
  const onCloseDialog = jest.fn();
  const store = createStore(combineReducers({ settings }), {
    settings: {
      backend: { 'qtyInput.DoNotValidate': true, 'qtyInput.AllowManualInputWhenScaleDeviceExists': true },
    },
  });
  render(
    <Provider store={store}>
      <GetQuantityDialog
        qtyTarget={5}
        qtyInitial={3}
        uom="PCE"
        onQtyChange={onQtyChange}
        onCloseDialog={onCloseDialog}
        {...props}
      />
    </Provider>
  );
  return { onQtyChange, onCloseDialog };
};

const click = async (element) => {
  await act(async () => {
    fireEvent.click(element);
  });
};

it('Done confirms exactly once', async () => {
  const { onQtyChange } = renderDialog();
  await click(screen.getByTestId('done-button'));
  expect(onQtyChange).toHaveBeenCalledTimes(1);
});

it('Enter in the quantity field confirms exactly once', async () => {
  const { onQtyChange } = renderDialog();
  await act(async () => {
    userEvent.type(document.getElementById('qty-input'), '{enter}');
  });
  expect(onQtyChange).toHaveBeenCalledTimes(1);
});

it('Cancel does not confirm', async () => {
  const { onQtyChange, onCloseDialog } = renderDialog();
  await click(screen.getByTestId('cancel-button'));
  expect(onCloseDialog).toHaveBeenCalledTimes(1);
  expect(onQtyChange).not.toHaveBeenCalled();
});

it('Scale and Manual buttons do not confirm', async () => {
  const { onQtyChange } = renderDialog({ scaleDevice: { caption: 'MyScale', websocketEndpoint: '/ws' } });
  await click(screen.getByText('Manual'));
  await click(screen.getByText('MyScale'));
  expect(onQtyChange).not.toHaveBeenCalled();
});

it('switching between the QR scanner and the form does not confirm', async () => {
  const { onQtyChange } = renderDialog({ catchWeightUom: 'kg' });
  await click(screen.getByTestId('switchToManualInput-button'));
  await click(screen.getByText(/activities\.picking\.switchToQrCodeInput$/));
  expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
  expect(onQtyChange).not.toHaveBeenCalled();
});

// The ui_trace <-> api_request_audit join needs the confirm to run inside a traced "dialogButtonClick" event.
describe.each([
  ['Done', async () => click(screen.getByTestId('done-button'))],
  [
    'Enter',
    async () =>
      act(async () => {
        userEvent.type(document.getElementById('qty-input'), '{enter}');
      }),
  ],
])('confirming with %s', (_name, confirmAction) => {
  it('is traced once as the Done dialog button click, and the confirm runs inside that event', async () => {
    renderDialog();
    await confirmAction();

    const doneEvents = mockTracedEvents.filter((e) => e?.eventName === 'dialogButtonClick');
    expect(doneEvents).toEqual([
      expect.objectContaining({
        captionKey: 'activities.picking.confirmDone',
        caption: expect.any(String),
        testId: 'done-button',
      }),
    ]);
    expect(mockPutContextCalls.length).toBeGreaterThan(0);
    expect(mockPutContextCalls.every((c) => c.isInsideTracedEvent)).toBe(true);
  });
});
