import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import MaterialReceiptLineScreen from '../../../../../containers/activities/manufacturing/receipt/MaterialReceiptLineScreen';
import { reducer as settings } from '../../../../../reducers/settings';
import { postManufacturingReceiveEventThunk } from '../../../../../actions/ManufacturingActions';

jest.mock('../../../../../utils/ui_trace', () => ({
  trace: jest.fn(),
  traceFunction: (func) => func,
  traceLogWarn: jest.fn(),
  putContext: jest.fn(),
}));
const mockToast = jest.fn();
jest.mock('../../../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
  errorToString: jest.requireActual('../../../../../utils/toast').errorToString,
}));
// The scanner itself is not under test: a fake one hands the typed code straight to the dialog and, like the
// real one, shows the error of a rejected code as a toast.
jest.mock('../../../../../components/BarcodeScannerComponent', () => {
  // eslint-disable-next-line react/prop-types
  const FakeScanner = ({ onResolvedResult }) => (
    <input
      data-testid="fake-scanner"
      onChange={async (e) => {
        try {
          await onResolvedResult({ scannedBarcode: e.target.value, error: null });
        } catch (error) {
          mockToast(error);
        }
      }}
    />
  );
  return FakeScanner;
});
jest.mock('../../../../../hooks/useScreenDefinition', () => ({
  useScreenDefinition: () => ({
    history: { goBack: jest.fn(), push: jest.fn() },
    url: '/mfg/wf1/a1/l1',
    applicationId: 'mfg',
    wfProcessId: 'wf1',
    activityId: 'a1',
    lineId: 'l1',
  }),
}));
// The line as the backend sends it is the input under test; the selectors just hand it over.
let mockLine = null;
jest.mock('../../../../../reducers/wfProcesses', () => ({
  getWfProcess: () => ({ id: 'wf1' }),
  getActivityByIdFromWFProcess: () => ({ caption: 'Receive', userInstructions: null }),
  getLineByIdFromActivity: () => mockLine,
  getCustomQRCodeFormats: () => undefined,
}));
jest.mock('../../../../../actions/ManufacturingActions', () => ({
  postManufacturingReceiveEventThunk: jest.fn(),
  updateManufacturingLUReceiptTarget: jest.fn(() => ({ type: 'test/updateManufacturingLUReceiptTarget' })),
}));

const LMQ_CODE = 'LMQ#1#0.101#08.11.2025#500';
const LMQ_CODE_WITHOUT_LOT = 'LMQ#1#0.101#08.11.2025';
const CONFIRM_CAPTION = /activities\.picking\.confirmDone$/;

// A catch-weight MAIN finished good with a receiving target, i.e. the line the flag is about.
const catchWeightMainFinishedGood = (extra = {}) => ({
  id: 'l1',
  coproduct: false,
  productName: 'Product',
  uom: 'PCE',
  catchWeightUomSymbol: 'kg',
  qtyToReceive: 5,
  qtyReceived: 0,
  aggregateToLU: { newLU: { luCaption: 'LU', tuCaption: 'TU' } },
  availableReceivingTargets: { values: [] },
  availableReceivingTUTargets: { values: [] },
  defaultLotNo: '26-0042',
  defaultBestBeforeDate: '2026-12-31',
  ...extra,
});

const renderScreen = (line) => {
  mockLine = line;
  const store = createStore(combineReducers({ settings }), {
    settings: { backend: { 'dateInput.isUseNativeComponent': true } },
  });
  const plainDispatch = store.dispatch;
  store.dispatch = (action) =>
    typeof action === 'function' ? action(store.dispatch, store.getState) : plainDispatch(action);
  render(
    <Provider store={store}>
      <MaterialReceiptLineScreen />
    </Provider>
  );
};

const openDialog = async () => {
  await act(async () => {
    fireEvent.click(screen.getByTestId('receive-qty-button'));
  });
};

const scan = async (code) => {
  await act(async () => {
    fireEvent.change(screen.getByTestId('fake-scanner'), { target: { value: code } });
  });
};

beforeEach(() => {
  // set here, not in the jest.mock factory: the CRA jest config resets mock implementations before each test
  postManufacturingReceiveEventThunk.mockImplementation(() => () => Promise.resolve());
  mockToast.mockClear();
});

describe('setting off (the backend does not send catchWeightScanRequired)', () => {
  it('offers manual input and receives the scanned code right away', async () => {
    renderScreen(catchWeightMainFinishedGood());
    await openDialog();

    expect(screen.getByTestId('switchToManualInput-button')).toBeInTheDocument();

    await scan(LMQ_CODE);

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 1, catchWeight: 0.101, lotNo: '500', barcode: LMQ_CODE })
    );
  });

  it('accepts a code without a lot number', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: false }));
    await openDialog();
    await scan(LMQ_CODE_WITHOUT_LOT);

    expect(mockToast).not.toHaveBeenCalled();
    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
  });
});

describe('setting on (catchWeightScanRequired = true)', () => {
  it('hides manual input and only fills the scanned values in for review', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true }));
    await openDialog();

    expect(screen.queryByTestId('switchToManualInput-button')).not.toBeInTheDocument();

    await scan(LMQ_CODE);

    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(document.getElementById('catch-weight')).toHaveValue(0.101);
    expect(screen.getByText(CONFIRM_CAPTION)).toBeEnabled();
  });

  it('rejects a code without a lot number', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true }));
    await openDialog();
    await scan(LMQ_CODE_WITHOUT_LOT);

    expect(mockToast).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
  });
});

// Catch weight is received one at a time: one receipt = one block = one scanned weight.
describe('quantity per receipt', () => {
  const qtyInput = () => document.getElementById('qty-input');
  const typeQty = async (qty) => {
    await act(async () => {
      fireEvent.change(qtyInput(), { target: { value: `${qty}` } });
    });
  };
  const confirm = async () => {
    await act(async () => {
      fireEvent.click(screen.getByText(CONFIRM_CAPTION));
    });
  };

  it('setting on: the quantity is fixed at 1 and read-only, while lot, best-before date and weight stay editable', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true }));
    await openDialog();
    await scan(LMQ_CODE);

    expect(qtyInput()).toHaveValue(1);
    expect(qtyInput()).toBeDisabled();
    expect(screen.getByTestId('lotNo')).toBeEnabled();
    expect(screen.getByTestId('bestBeforeDate')).toBeEnabled();
    expect(document.getElementById('catch-weight')).toBeEnabled();
  });

  it('setting on: the receipt is posted with quantity 1, even if a quantity change reaches the field', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true }));
    await openDialog();
    await scan(LMQ_CODE);

    await typeQty(3); // a disabled input cannot be typed into by the operator; this guards against the event anyway
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    // confirmed from the review form, so the weight comes as the field's string value
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 1, catchWeight: '0.101', lotNo: '500', barcode: LMQ_CODE })
    );
  });

  it('setting off: the quantity stays editable and the entered quantity is received', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: false }));
    await openDialog();
    await act(async () => {
      fireEvent.click(screen.getByTestId('switchToManualInput-button'));
    });

    expect(qtyInput()).toBeEnabled();

    await typeQty(3);
    await act(async () => {
      fireEvent.change(document.getElementById('catch-weight'), { target: { value: '0.3' } });
    });
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 3, catchWeight: '0.3' })
    );
  });
});

// With the setting on, the backend still sends catchWeightScanRequired=false for a co-/by-product line.
it('keeps receive-on-scan and manual input for a co-product line (backend sends no scan requirement)', async () => {
  renderScreen(catchWeightMainFinishedGood({ coproduct: true, catchWeightScanRequired: false }));
  await openDialog();

  expect(screen.getByTestId('switchToManualInput-button')).toBeInTheDocument();

  await scan(LMQ_CODE_WITHOUT_LOT);

  expect(mockToast).not.toHaveBeenCalled();
  expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
});

// Weight captured at picking, not at receipt: the backend sends no catch-weight UOM, but the scan is still required.
// The scan takes over the Lot-Nr and MHD only; no weight is shown or sent, and each receipt is still one piece.
describe('setting on, weight not captured at receipt (no catchWeightUomSymbol)', () => {
  const lineWithoutWeight = () =>
    catchWeightMainFinishedGood({ catchWeightUomSymbol: undefined, catchWeightScanRequired: true });
  const confirm = async () => {
    await act(async () => {
      fireEvent.click(screen.getByText(CONFIRM_CAPTION));
    });
  };

  it('opens on the scanner, with no manual-input escape and nothing to confirm', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();

    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
    expect(screen.queryByTestId('switchToManualInput-button')).not.toBeInTheDocument();
    expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
  });

  it('fills Lot-Nr and MHD for review, shows no weight, and fixes the quantity at 1', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);

    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
    expect(document.getElementById('catch-weight')).not.toBeInTheDocument();
    expect(document.getElementById('qty-input')).toHaveValue(1);
    expect(document.getElementById('qty-input')).toBeDisabled();
  });

  it('receives one piece with the scanned Lot-Nr and MHD and no weight', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    const posted = postManufacturingReceiveEventThunk.mock.calls[0][0];
    expect(posted).toEqual(
      expect.objectContaining({ qtyReceived: 1, lotNo: '500', bestBeforeDate: '2025-11-08', barcode: LMQ_CODE })
    );
    expect(posted.catchWeight).toBeFalsy();
    expect(posted.catchWeightUom).toBeFalsy();
  });

  it('accepts a label with an empty weight segment, since no weight is needed', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan('LMQ#1##08.11.2025#500');

    expect(mockToast).not.toHaveBeenCalled();
    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
  });

  // Without a weight row the rescan control must still be offered: a wrong label can be replaced by scanning again.
  it('offers a rescan after a scan, and the next scan replaces Lot-Nr and MHD', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);
    await act(async () => {
      fireEvent.click(screen.getByText(/activities\.picking\.switchToQrCodeInput$/));
    });
    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();

    await scan('LMQ#1##15.11.2025#501');
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 1, lotNo: '501', bestBeforeDate: '2025-11-15' })
    );
  });

  it.each([
    ['an unparseable code', 'this-is-not-a-qr-code', 'activities.picking.qrcode.invalidQRCode'],
    ['a code without a best-before date', 'LMQ#1#0.101', 'activities.picking.qrcode.missingBestBeforeDate'],
    ['a code without a lot', LMQ_CODE_WITHOUT_LOT, 'activities.picking.qrcode.missingLotNo'],
    [
      'a code with best-before 00.00.0000',
      'LMQ#1#0.101#00.00.0000#500',
      'activities.picking.qrcode.invalidBestBeforeDate',
    ],
  ])('rejects %s and receives nothing', async (_description, code, expectedMessageKey) => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(code);

    expect(mockToast).toHaveBeenCalledWith({ messageKey: expectedMessageKey });
    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
    expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
  });
});
