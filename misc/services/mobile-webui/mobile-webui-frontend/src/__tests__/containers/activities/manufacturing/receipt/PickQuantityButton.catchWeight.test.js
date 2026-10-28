import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { combineReducers, createStore } from 'redux';

import PickQuantityButton from '../../../../../containers/activities/manufacturing/receipt/PickQuantityButton';
import { reducer as settings } from '../../../../../reducers/settings';

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

// Catch-weight finished goods: the operator must scan the last block's L&M QR code. The scan fills
// quantity, weight, Lot-Nr and MHD into the dialog for review (overwriting the line's defaults); only the
// operator's confirm receives the goods.
const DEFAULT_LOT_NO = '26-0042';
const DEFAULT_BEST_BEFORE_DATE = '2026-12-31';
const LMQ_CODE = 'LMQ#1#0.101#08.11.2025#500';
// Captions render as their translation key in Jest (no messages loaded).
const CONFIRM_CAPTION = /activities\.picking\.confirmDone$/;

const renderCatchWeightButton = ({ isCatchWeightScanMandatory = true, editableAttributes } = {}) => {
  const onClick = jest.fn();
  const store = createStore(combineReducers({ settings }), {
    settings: { backend: { 'dateInput.isUseNativeComponent': true } },
  });
  render(
    <Provider store={store}>
      <PickQuantityButton
        qtyTarget={5}
        uom="PCE"
        catchWeightUom="kg"
        caption="Receive"
        editableAttributes={editableAttributes}
        defaultLotNo={DEFAULT_LOT_NO}
        defaultBestBeforeDate={DEFAULT_BEST_BEFORE_DATE}
        isCatchWeightScanMandatory={isCatchWeightScanMandatory}
        // weight captured at receipt (catchWeightUom set): the backend then also sends one piece per scan
        isOnePiecePerScan={isCatchWeightScanMandatory}
        onClick={onClick}
      />
    </Provider>
  );
  return { onClick };
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

const confirm = async () => {
  await act(async () => {
    fireEvent.click(screen.getByText(CONFIRM_CAPTION));
  });
};

it('offers no confirm and no manual-input escape before the L&M code is scanned', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();

  expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
  expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
  expect(screen.queryByTestId('switchToManualInput-button')).not.toBeInTheDocument();
  expect(onClick).not.toHaveBeenCalled();
});

it('fills the scanned quantity, weight, Lot-Nr and MHD into the fields without receiving', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();
  await scan(LMQ_CODE);

  expect(onClick).not.toHaveBeenCalled();
  expect(screen.getByTestId('lotNo')).toHaveValue('500');
  expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
  expect(document.getElementById('qty-input')).toHaveValue(1);
  expect(document.getElementById('catch-weight')).toHaveValue(0.101);
  expect(screen.getByText(CONFIRM_CAPTION)).toBeEnabled();
});

it('receives the scanned values when the operator confirms', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();
  await scan(LMQ_CODE);
  await confirm();

  expect(onClick.mock.calls[0][0]).toEqual(
    expect.objectContaining({
      qtyEnteredAndValidated: 1,
      catchWeight: '0.101',
      catchWeightUom: 'kg',
      lotNo: '500',
      bestBeforeDate: '2025-11-08',
      barcode: LMQ_CODE,
      isDone: true,
    })
  );
});

it('receives what the operator corrected after the scan', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();
  await scan(LMQ_CODE);
  await act(async () => {
    fireEvent.change(screen.getByTestId('lotNo'), { target: { value: '501' } });
  });
  await confirm();

  expect(onClick.mock.calls[0][0]).toEqual(expect.objectContaining({ lotNo: '501', barcode: LMQ_CODE }));
});

// Catch weight is received one at a time: the quantity is fixed at 1, the weight stays editable.
it('receives one unit with the weight the operator entered after the scan, with the scanned Lot-Nr and MHD', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();
  await scan(LMQ_CODE);
  expect(document.getElementById('qty-input')).toHaveValue(1);
  expect(document.getElementById('qty-input')).toBeDisabled();
  expect(document.getElementById('catch-weight')).toBeEnabled();
  await act(async () => {
    fireEvent.change(document.getElementById('catch-weight'), { target: { value: '0.303' } });
  });
  await confirm();

  expect(onClick).toHaveBeenCalledTimes(1);
  expect(onClick.mock.calls[0][0]).toEqual(
    expect.objectContaining({
      qtyEnteredAndValidated: 1,
      catchWeight: '0.303',
      catchWeightUom: 'kg',
      lotNo: '500',
      bestBeforeDate: '2025-11-08',
      barcode: LMQ_CODE,
    })
  );
});

// Lot-Nr, MHD and production date configured as editable attributes as well: with the mandatory scan they are shown once, in the
// scan-review rows, and sent once, through the dedicated fields; the other configured attributes stay as they are.
it('shows the scanned Lot-Nr and MHD once and sends them through the dedicated fields only', async () => {
  const { onClick } = renderCatchWeightButton({
    editableAttributes: [
      { code: 'HU_BestBeforeDate', caption: 'Best before', valueType: 'DATE' },
      { code: 'Lot-Nummer', caption: 'Lot', valueType: 'STRING' },
      { code: 'ProductionDate', caption: 'Production date', valueType: 'DATE' },
      { code: 'Color', caption: 'Color', valueType: 'STRING' },
    ],
  });
  await openDialog();
  await scan(LMQ_CODE);

  expect(screen.getByTestId('lotNo')).toHaveValue('500');
  expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
  expect(screen.queryByTestId('attr-Lot-Nummer-field')).not.toBeInTheDocument();
  expect(screen.queryByTestId('attr-HU_BestBeforeDate-field')).not.toBeInTheDocument();
  expect(screen.queryByTestId('attr-ProductionDate-field')).not.toBeInTheDocument();
  await act(async () => {
    fireEvent.change(screen.getByTestId('attr-Color-field'), { target: { value: 'red' } });
  });
  await confirm();

  const sent = onClick.mock.calls[0][0];
  expect(sent).toEqual(expect.objectContaining({ lotNo: '500', bestBeforeDate: '2025-11-08', barcode: LMQ_CODE }));
  expect(sent.attributeValues).toEqual({ Color: 'red' });
});

// Co-/by-products keep the previous behaviour: each scan receives one piece right away.
it('receives a co-product right on the scan, with the manual-input escape offered', async () => {
  const { onClick } = renderCatchWeightButton({ isCatchWeightScanMandatory: false });
  await openDialog();

  expect(screen.getByTestId('switchToManualInput-button')).toBeInTheDocument();
  await scan(LMQ_CODE);

  expect(onClick).toHaveBeenCalledWith(
    expect.objectContaining({ qtyEnteredAndValidated: 1, lotNo: '500', bestBeforeDate: '2025-11-08', isDone: false })
  );
});

// A code that cannot be used to receive is rejected with a clear error: nothing is filled (the defaults stay),
// there is still nothing to confirm, and the operator scans again.
describe.each([
  ['an unparseable code', 'this-is-not-a-qr-code', 'activities.picking.qrcode.invalidQRCode'],
  ['a code without a best-before date', 'LMQ#1#0.101', 'activities.picking.qrcode.missingBestBeforeDate'],
  ['a code with an empty best-before date', 'LMQ#1#0.101##500', 'activities.picking.qrcode.invalidBestBeforeDate'],
  ['a code without a lot', 'LMQ#1#0.101#08.11.2025', 'activities.picking.qrcode.missingLotNo'],
  ['a code with an empty lot', 'LMQ#1#0.101#08.11.2025#', 'activities.picking.qrcode.missingLotNo'],
  [
    'a code with best-before 00.00.0000',
    'LMQ#1#0.101#00.00.0000#500',
    'activities.picking.qrcode.invalidBestBeforeDate',
  ],
  [
    'a code with an impossible best-before date',
    'LMQ#1#0.101#31.02.2026#500',
    'activities.picking.qrcode.invalidBestBeforeDate',
  ],
])('scanning %s', (_description, code, expectedMessageKey) => {
  it('shows the error, fills nothing and keeps confirm blocked', async () => {
    const { onClick } = renderCatchWeightButton();
    await openDialog();
    mockToast.mockClear();
    await scan(code);

    expect(mockToast).toHaveBeenCalledWith({ messageKey: expectedMessageKey });
    expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
    expect(screen.queryByTestId('lotNo')).not.toBeInTheDocument();
    expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
    expect(onClick).not.toHaveBeenCalled();
  });

  it('accepts a valid scan afterwards, with only the valid code filled in', async () => {
    const { onClick } = renderCatchWeightButton();
    await openDialog();
    await scan(code);
    await scan(LMQ_CODE);

    expect(screen.getByTestId('lotNo')).toHaveValue('500');
    expect(screen.getByTestId('bestBeforeDate')).toHaveValue('2025-11-08');
    await confirm();
    expect(onClick.mock.calls[0][0]).toEqual(expect.objectContaining({ lotNo: '500', barcode: LMQ_CODE }));
  });
});

// After a good scan the operator may go back to the scanner; a rejected code there leaves the scanner open (the
// filled values are not visible then, nothing is received), and the next good scan replaces them.
it('rejects a bad rescan without receiving, and receives the next good scan', async () => {
  const { onClick } = renderCatchWeightButton();
  await openDialog();
  await scan(LMQ_CODE);
  await act(async () => {
    fireEvent.click(screen.getByText(/activities\.picking\.switchToQrCodeInput$/));
  });
  await scan('LMQ#1#0.101##500');

  expect(mockToast).toHaveBeenLastCalledWith({ messageKey: 'activities.picking.qrcode.invalidBestBeforeDate' });
  expect(screen.getByTestId('fake-scanner')).toBeInTheDocument();
  expect(screen.queryByText(CONFIRM_CAPTION)).not.toBeInTheDocument();
  expect(onClick).not.toHaveBeenCalled();

  await scan('LMQ#1#0.202#09.11.2025#600');
  await confirm();
  expect(onClick.mock.calls[0][0]).toEqual(expect.objectContaining({ lotNo: '600', bestBeforeDate: '2025-11-09' }));
});
