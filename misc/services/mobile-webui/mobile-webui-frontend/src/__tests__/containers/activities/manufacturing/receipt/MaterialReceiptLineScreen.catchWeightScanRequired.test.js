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
// read-only attributes (readOnlyAttributes) as the backend sends them; set per test.
// The editable ones come per line (editableAttributes).
let mockReadOnlyAttributes = [];
jest.mock('../../../../../reducers/wfProcesses/picking/getReadAttributesFromActivity', () => ({
  getReadOnlyAttributesFromActivity: () => mockReadOnlyAttributes,
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
  mockReadOnlyAttributes = [];
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
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true, receiveOnePiecePerScan: true }));
    await openDialog();

    expect(screen.queryByTestId('switchToManualInput-button')).not.toBeInTheDocument();

    await scan(LMQ_CODE);

    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(document.getElementById('catch-weight')).toHaveValue(0.101);
    expect(screen.getByText(CONFIRM_CAPTION)).toBeEnabled();
  });

  it('rejects a code without a lot number', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true, receiveOnePiecePerScan: true }));
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
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true, receiveOnePiecePerScan: true }));
    await openDialog();
    await scan(LMQ_CODE);

    expect(qtyInput()).toHaveValue(1);
    expect(qtyInput()).toBeDisabled();
    expect(screen.getByTestId('lotNo')).toBeEnabled();
    expect(screen.getByTestId('bestBeforeDate')).toBeEnabled();
    expect(document.getElementById('catch-weight')).toBeEnabled();
  });

  it('setting on: the receipt is posted with quantity 1, even if a quantity change reaches the field', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightScanRequired: true, receiveOnePiecePerScan: true }));
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

// One piece per scan is the backend's decision (weight captured at receipt), not re-derived from the weight UOM:
// e.g. with a scale device the dialog shows no weight field, yet the backend still accepts only one piece per receipt.
it('fixes the quantity at 1 when the backend sends receiveOnePiecePerScan, even without a weight UOM', async () => {
  renderScreen(
    catchWeightMainFinishedGood({
      catchWeightUomSymbol: undefined,
      catchWeightScanRequired: true,
      receiveOnePiecePerScan: true,
    })
  );
  await openDialog();
  await scan(LMQ_CODE);

  expect(document.getElementById('qty-input')).toHaveValue(1);
  expect(document.getElementById('qty-input')).toBeDisabled();
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
// The operator enters the number of blocks as in a normal receipt and scans the label of the last block: its Lot-Nr and
// MHD overwrite the defaults for the whole receipt. No weight is shown or sent.
describe('setting on, weight not captured at receipt (no catchWeightUomSymbol)', () => {
  const lineWithoutWeight = () =>
    catchWeightMainFinishedGood({ catchWeightUomSymbol: undefined, catchWeightScanRequired: true });
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

  it('opens on the scanner, with no manual-input escape and nothing to confirm', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();

    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
    expect(screen.queryByTestId('switchToManualInput-button')).not.toBeInTheDocument();
    expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
  });

  it('fills Lot-Nr and MHD for review, shows no weight, and leaves the quantity editable with the open quantity', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);

    expect(postManufacturingReceiveEventThunk).not.toHaveBeenCalled();
    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
    expect(document.getElementById('catch-weight')).not.toBeInTheDocument();
    expect(qtyInput()).toHaveValue(5);
    expect(qtyInput()).toBeEnabled();
  });

  it('receives the entered number of blocks with the scanned Lot-Nr and MHD and no weight', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);
    await typeQty(3);
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    const posted = postManufacturingReceiveEventThunk.mock.calls[0][0];
    expect(posted).toEqual(
      expect.objectContaining({ qtyReceived: 3, lotNo: '500', bestBeforeDate: '2025-11-08', barcode: LMQ_CODE })
    );
    expect(posted.catchWeight).toBeFalsy();
    expect(posted.catchWeightUom).toBeFalsy();
  });

  it('keeps Lot-Nr and MHD editable after the scan; the edited values are received', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);

    expect(screen.getByTestId('lotNo')).toBeEnabled();
    expect(screen.getByTestId('bestBeforeDate')).toBeEnabled();
    await act(async () => {
      fireEvent.change(screen.getByTestId('lotNo'), { target: { value: '777' } });
    });
    await typeQty(2);
    await confirm();

    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 2, lotNo: '777', bestBeforeDate: '2025-11-08' })
    );
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
  it('offers a rescan; the next scan replaces Lot-Nr and MHD and keeps the entered quantity', async () => {
    renderScreen(lineWithoutWeight());
    await openDialog();
    await scan(LMQ_CODE);
    await typeQty(3);
    await act(async () => {
      fireEvent.click(screen.getByText(/activities\.picking\.switchToQrCodeInput$/));
    });
    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();

    await scan('LMQ#1##15.11.2025#501');
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 3, lotNo: '501', bestBeforeDate: '2025-11-15' })
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

// Lot number not editable (backend: in readOnlyAttributes, Lot-Nummer not one of the line's editableAttributes),
// best-before date editable.
describe('lot number not editable', () => {
  const BEST_BEFORE_DATE_ATTRIBUTE = { code: 'HU_BestBeforeDate', caption: 'Best before', valueType: 'DATE' };
  const confirm = async () => {
    await act(async () => {
      fireEvent.click(screen.getByText(CONFIRM_CAPTION));
    });
  };
  beforeEach(() => {
    mockReadOnlyAttributes = ['LotNo'];
  });

  // piece goods: the lot is shown read-only and generated by the receipt; the best-before date stays editable
  it('piece goods: shows the lot disabled, keeps the best-before date editable, and lets the receipt generate the lot', async () => {
    renderScreen(
      catchWeightMainFinishedGood({
        catchWeightUomSymbol: undefined,
        catchWeightScanRequired: false,
        editableAttributes: [BEST_BEFORE_DATE_ATTRIBUTE],
      })
    );
    await openDialog();

    expect(screen.getByTestId('lotNo')).toHaveValue('26-0042');
    expect(screen.getByTestId('lotNo')).toBeDisabled();
    expect(screen.getByTestId('attr-HU_BestBeforeDate-field')).toBeEnabled();

    await act(async () => {
      fireEvent.change(screen.getByTestId('attr-HU_BestBeforeDate-field'), { target: { value: '2027-01-15' } });
    });
    await confirm();

    expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ lotNo: null, attributes: [{ code: 'HU_BestBeforeDate', value: '2027-01-15' }] })
    );
  });

  it('piece goods without a lot preview: no lot row', async () => {
    renderScreen(
      catchWeightMainFinishedGood({
        catchWeightUomSymbol: undefined,
        catchWeightScanRequired: false,
        defaultLotNo: null,
      })
    );
    await openDialog();

    expect(screen.queryByTestId('lotNo')).not.toBeInTheDocument();
  });

  // the scanned label's lot is taken over and stays editable, whatever the lot-number setting
  it('catch weight with the mandatory scan: shows the scanned lot editable and sends it', async () => {
    renderScreen(catchWeightMainFinishedGood({ catchWeightUomSymbol: undefined, catchWeightScanRequired: true }));
    await openDialog();
    await scan(LMQ_CODE);

    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(screen.getByTestId('lotNo')).toBeEnabled();

    await act(async () => {
      fireEvent.change(document.getElementById('qty-input'), { target: { value: '3' } });
    });
    await confirm();

    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 3, lotNo: '500', bestBeforeDate: '2025-11-08' })
    );
  });

  it('catch weight with the mandatory scan, lot not even read-only: the scanned lot is still shown and sent', async () => {
    mockReadOnlyAttributes = [];
    renderScreen(
      catchWeightMainFinishedGood({ catchWeightScanRequired: true, receiveOnePiecePerScan: true, defaultLotNo: null })
    );
    await openDialog();
    await scan(LMQ_CODE);

    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    await confirm();

    expect(postManufacturingReceiveEventThunk.mock.calls[0][0]).toEqual(
      expect.objectContaining({ qtyReceived: 1, lotNo: '500' })
    );
  });
});
