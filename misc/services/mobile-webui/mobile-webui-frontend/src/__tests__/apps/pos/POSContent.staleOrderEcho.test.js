import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';

import { posReducer } from '../../../apps/pos/reducers';
import { ADD_PAYMENT, POS_TERMINAL_LOAD_DONE, UPDATE_ORDER } from '../../../apps/pos/actionTypes';
import { POSContent } from '../../../apps/pos/containers/POSContent';
import * as ordersAPI from '../../../apps/pos/api/orders';

// No backend in jsdom: the order panel loads products on mount.
jest.mock('../../../apps/pos/api/products', () => ({
  getProducts: jest.fn(() => Promise.resolve({ list: [] })),
}));
// OK on the cash dialog POSTs the checkout (stubbed per test, because the CRA jest preset resets mocks before each test)
jest.mock('../../../apps/pos/api/orders', () => ({
  ...jest.requireActual('../../../apps/pos/api/orders'),
  checkoutPayment: jest.fn(),
}));

/*
 * Flaky case 207 (e2e pos_cash_sale.spec.js:45), a real cashier-facing defect.
 *
 * The backend sends every POS order save as a websocket "order changed" snapshot through a TRAILING 200 ms
 * debouncer (WebsocketSender). So all snapshots of a quick sequence (scan -> checkout -> tap CASH) arrive in one
 * batch, ~200 ms AFTER the add-payment REST response. Applied as they are, the stale ones drop the pending cash
 * payment for a render, so the cash dialog unmounts and re-mounts. Versioned snapshots that are not newer than the
 * order in state are ignored.
 *
 * Rendered from POSContent down, because a stale DRAFTED snapshot also swaps the whole payment panel for the
 * order panel, i.e. it unmounts everything below POSContent.
 */

const ORDER_UUID = 'order-1';
const POS_TERMINAL_ID = 1000000;
const LINE = { uuid: 'line-1', productId: 1, productName: 'P1', price: 2.5, qty: 1, amount: 2.5 };

const backendOrder = ({ status = 'WP', payments, version }) => ({
  uuid: ORDER_UUID,
  ...(version !== undefined ? { version } : {}),
  posTerminalId: POS_TERMINAL_ID,
  status,
  currencySymbol: '€',
  totalAmt: 2.5,
  paidAmt: payments.reduce((acc, p) => acc + p.amount, 0),
  lines: [LINE],
  payments,
  allowAddPayment: status === 'WP' && payments.length === 0,
});

const newStore = () =>
  configureStore({
    reducer: { 'applications/pos': posReducer },
    middleware: (getDefault) => getDefault({ serializableCheck: false, immutableCheck: false }),
  });

const applySnapshot = (store, order) => act(() => store.dispatch({ type: UPDATE_ORDER, payload: { order } }));

const tapNumpad = (digits) =>
  [...digits].forEach((digit) => fireEvent.click(screen.getByRole('button', { name: digit })));

const okButton = () => screen.getByTestId('pos-cash-payment-ok-button');
const tenderedAmountText = () => screen.getAllByText(/€/).map((element) => element.textContent);

/** Checkout + tap CASH + the add-payment REST response. Returns the add-payment snapshot. */
const checkoutAndTapCash = (store, { checkoutSnapshot, addPaymentVersion }) => {
  store.dispatch({
    type: POS_TERMINAL_LOAD_DONE,
    payload: {
      posTerminal: {
        id: POS_TERMINAL_ID,
        cashJournalOpen: true,
        currencyPrecision: 2,
        availablePaymentMethods: ['CASH'],
        products: [],
        openOrders: [checkoutSnapshot],
      },
    },
  });

  render(
    <Provider store={store}>
      <POSContent />
    </Provider>
  );
  expect(screen.getByTestId('pos-payment-panel')).toBeInTheDocument();

  act(() => {
    store.dispatch({ type: ADD_PAYMENT, payload: { order_uuid: ORDER_UUID, paymentMethod: 'CASH', amount: 2.5 } });
  });
  const payment = store.getState()['applications/pos'].orders.byUUID[ORDER_UUID].payments[0];
  const addPaymentSnapshot = backendOrder({
    payments: [{ ...payment, allowCheckout: true }],
    version: addPaymentVersion,
  });
  applySnapshot(store, addPaymentSnapshot);
  return addPaymentSnapshot;
};

/*
 * The debounced batch, with the backend's order versions (every save stamps a monotonic version).
 * The stale snapshots are older than the add-payment REST response already applied, so they must be ignored:
 * the cash dialog must not unmount even for one render, else a tap on OK in that render is lost
 * (CI trace: the OK tap landed in the flash and no checkout request was ever sent).
 */
describe('POSContent - cash dialog vs. the delayed websocket snapshots, versioned', () => {
  const draftedSnapshotV1 = backendOrder({ status: 'DR', payments: [], version: 1 });
  const checkoutSnapshotV2 = backendOrder({ payments: [], version: 2 });

  // never answered: the tests only check that the checkout request was sent
  beforeEach(() => ordersAPI.checkoutPayment.mockImplementation(() => new Promise(() => {})));

  it('keeps the cash dialog mounted when the stale checkout snapshot arrives, so OK is not lost', () => {
    const store = newStore();
    const addPaymentSnapshot = checkoutAndTapCash(store, {
      checkoutSnapshot: checkoutSnapshotV2,
      addPaymentVersion: 3,
    });
    tapNumpad('10');
    const okButtonBefore = okButton();
    expect(okButtonBefore).toBeEnabled();

    applySnapshot(store, checkoutSnapshotV2);
    expect(okButtonBefore).toBeInTheDocument(); // not detached
    applySnapshot(store, addPaymentSnapshot);
    expect(okButtonBefore).toBeInTheDocument();

    fireEvent.click(okButtonBefore);
    expect(ordersAPI.checkoutPayment).toHaveBeenCalledTimes(1);
    expect(ordersAPI.checkoutPayment).toHaveBeenCalledWith(expect.objectContaining({ cashTenderedAmount: 10 }));
  });

  // The add-payment save is slower than the 200 ms debounce: the checkout echo is sent alone and arrives while the
  // add-payment POST is still in flight, i.e. while the cash payment exists only locally.
  it('keeps the cash dialog mounted when the checkout echo arrives before the add-payment response', () => {
    const store = newStore();
    store.dispatch({
      type: POS_TERMINAL_LOAD_DONE,
      payload: {
        posTerminal: {
          id: POS_TERMINAL_ID,
          cashJournalOpen: true,
          currencyPrecision: 2,
          availablePaymentMethods: ['CASH'],
          products: [],
          openOrders: [checkoutSnapshotV2],
        },
      },
    });
    render(
      <Provider store={store}>
        <POSContent />
      </Provider>
    );
    act(() => {
      store.dispatch({ type: ADD_PAYMENT, payload: { order_uuid: ORDER_UUID, paymentMethod: 'CASH', amount: 2.5 } });
    });
    tapNumpad('10');
    const okButtonBefore = okButton();

    applySnapshot(store, checkoutSnapshotV2); // the echo of the checkout save, same version as the order in state
    expect(okButtonBefore).toBeInTheDocument(); // not detached

    const payment = store.getState()['applications/pos'].orders.byUUID[ORDER_UUID].payments[0];
    applySnapshot(store, backendOrder({ payments: [{ ...payment, allowCheckout: true }], version: 3 }));
    expect(okButtonBefore).toBeInTheDocument();

    fireEvent.click(okButtonBefore);
    expect(ordersAPI.checkoutPayment).toHaveBeenCalledWith(expect.objectContaining({ cashTenderedAmount: 10 }));
  });

  it('keeps the payment panel and the cash dialog mounted when a stale DRAFTED snapshot arrives', () => {
    const store = newStore();
    const addPaymentSnapshot = checkoutAndTapCash(store, {
      checkoutSnapshot: checkoutSnapshotV2,
      addPaymentVersion: 3,
    });
    tapNumpad('10');
    const okButtonBefore = okButton();

    applySnapshot(store, draftedSnapshotV1);
    applySnapshot(store, checkoutSnapshotV2);
    applySnapshot(store, addPaymentSnapshot);

    expect(okButtonBefore).toBeInTheDocument();
    expect(okButtonBefore).toBeEnabled();
    expect(tenderedAmountText()).toContain('10.00 €');
  });
});
