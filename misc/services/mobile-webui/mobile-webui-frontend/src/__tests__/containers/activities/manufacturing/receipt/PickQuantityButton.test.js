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
jest.mock('../../../../../utils/toast', () => ({
  toastError: jest.fn(),
  toastErrorFromObj: jest.fn(),
}));

// The finished-goods receive line carries the backend's suggested lot and best-before date; the receive
// quantity dialog shows them as initial values of the lot / best-before editable-attribute rows (the
// attributes are configured as editable), and the operator may still change them.
const DEFAULT_LOT_NO = '26-0042';
const DEFAULT_BEST_BEFORE_DATE = '2026-12-31';

const LOT_NO_FIELD = 'attr-Lot-Nummer-field';
const BEST_BEFORE_DATE_FIELD = 'attr-HU_BestBeforeDate-field';
const EDITABLE_ATTRIBUTES = [
  { code: 'HU_BestBeforeDate', caption: 'Best before', valueType: 'DATE' },
  { code: 'Lot-Nummer', caption: 'Lot', valueType: 'STRING' },
];

const renderButton = ({ onClick = jest.fn(), ...props } = {}) => {
  const store = createStore(combineReducers({ settings }), {
    settings: { backend: { 'dateInput.isUseNativeComponent': true } },
  });
  render(
    <Provider store={store}>
      <PickQuantityButton
        qtyTarget={5}
        uom="PCE"
        caption="Receive"
        editableAttributes={EDITABLE_ATTRIBUTES}
        onClick={onClick}
        {...props}
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

const type = async (testId, value) => {
  await act(async () => {
    fireEvent.change(screen.getByTestId(testId), { target: { value } });
  });
};

it('shows the sent default lot and best-before date as the initial values', async () => {
  renderButton({ defaultLotNo: DEFAULT_LOT_NO, defaultBestBeforeDate: DEFAULT_BEST_BEFORE_DATE });
  await openDialog();

  expect(screen.getByTestId(LOT_NO_FIELD)).toHaveValue(DEFAULT_LOT_NO);
  expect(screen.getByTestId(BEST_BEFORE_DATE_FIELD)).toHaveValue(DEFAULT_BEST_BEFORE_DATE);
});

it('shows empty fields when the line has no defaults', async () => {
  renderButton({ defaultLotNo: null, defaultBestBeforeDate: null });
  await openDialog();

  expect(screen.getByTestId(LOT_NO_FIELD)).toHaveValue('');
  expect(screen.getByTestId(BEST_BEFORE_DATE_FIELD)).toHaveValue('');
});

const confirm = async () => {
  await act(async () => {
    fireEvent.click(screen.getByTestId('done-button'));
  });
};

// Ruling: only a lot / best-before date the operator changed is sent; the backend fills the rest at
// receipt with the real receipt day.
it('keeps what the operator typed over the default and sends only that changed field', async () => {
  const { onClick } = renderButton({ defaultLotNo: DEFAULT_LOT_NO, defaultBestBeforeDate: DEFAULT_BEST_BEFORE_DATE });
  await openDialog();

  await type(LOT_NO_FIELD, 'MY-LOT');
  expect(screen.getByTestId(LOT_NO_FIELD)).toHaveValue('MY-LOT');
  expect(screen.getByTestId(BEST_BEFORE_DATE_FIELD)).toHaveValue(DEFAULT_BEST_BEFORE_DATE);
  await confirm();

  expect(onClick).toHaveBeenCalledTimes(1);
  const sent = onClick.mock.calls[0][0];
  expect(sent.attributeValues).toEqual({ 'Lot-Nummer': 'MY-LOT' });
  expect(sent.lotNo).toBeNull();
  expect(sent.bestBeforeDate).toBeNull();
});

it('sends neither field when the pre-filled defaults are unchanged', async () => {
  const { onClick } = renderButton({ defaultLotNo: DEFAULT_LOT_NO, defaultBestBeforeDate: DEFAULT_BEST_BEFORE_DATE });
  await openDialog();
  await confirm();

  const sent = onClick.mock.calls[0][0];
  expect(sent.attributeValues).toEqual({});
  expect(sent.lotNo).toBeNull();
  expect(sent.bestBeforeDate).toBeNull();
});

it('sends an edited best-before date', async () => {
  const { onClick } = renderButton({ defaultLotNo: DEFAULT_LOT_NO, defaultBestBeforeDate: DEFAULT_BEST_BEFORE_DATE });
  await openDialog();
  await type(BEST_BEFORE_DATE_FIELD, '2027-01-15');
  await confirm();

  const sent = onClick.mock.calls[0][0];
  expect(sent.attributeValues).toEqual({ HU_BestBeforeDate: '2027-01-15' });
  expect(sent.lotNo).toBeNull();
  expect(sent.bestBeforeDate).toBeNull();
});

it('sends a typed value when the line has no default', async () => {
  const { onClick } = renderButton({ defaultLotNo: null, defaultBestBeforeDate: null });
  await openDialog();
  await type(LOT_NO_FIELD, 'TYPED');
  await type(BEST_BEFORE_DATE_FIELD, '2027-02-01');
  await confirm();

  expect(onClick.mock.calls[0][0].attributeValues).toEqual({ 'Lot-Nummer': 'TYPED', HU_BestBeforeDate: '2027-02-01' });
});

it('shows no lot / best-before rows when they are not configured as editable attributes', async () => {
  renderButton({
    editableAttributes: [],
    defaultLotNo: DEFAULT_LOT_NO,
    defaultBestBeforeDate: DEFAULT_BEST_BEFORE_DATE,
  });
  await openDialog();

  expect(screen.queryByTestId(LOT_NO_FIELD)).not.toBeInTheDocument();
  expect(screen.queryByTestId(BEST_BEFORE_DATE_FIELD)).not.toBeInTheDocument();
  expect(screen.queryByTestId('lotNo')).not.toBeInTheDocument();
  expect(screen.queryByTestId('bestBeforeDate')).not.toBeInTheDocument();
});
