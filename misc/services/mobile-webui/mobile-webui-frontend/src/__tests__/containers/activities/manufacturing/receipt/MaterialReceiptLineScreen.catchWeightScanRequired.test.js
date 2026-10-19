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
jest.mock('../../../../../reducers/wfProcesses/picking/getReadAttributesFromActivity', () => ({
  getReadAttributesFromActivity: () => ['BestBeforeDate', 'LotNo'],
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

// With the setting on, the backend still sends catchWeightScanRequired=false for a co-/by-product line.
it('keeps receive-on-scan and manual input for a co-product line (backend sends no scan requirement)', async () => {
  renderScreen(catchWeightMainFinishedGood({ coproduct: true, catchWeightScanRequired: false }));
  await openDialog();

  expect(screen.getByTestId('switchToManualInput-button')).toBeInTheDocument();

  await scan(LMQ_CODE_WITHOUT_LOT);

  expect(mockToast).not.toHaveBeenCalled();
  expect(postManufacturingReceiveEventThunk).toHaveBeenCalledTimes(1);
});
