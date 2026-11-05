import React from 'react';
import '@testing-library/jest-dom';
import { act, fireEvent, render, screen } from '@testing-library/react';
import { Provider } from 'react-redux';
import { configureStore } from '@reduxjs/toolkit';

import { posReducer } from '../../../apps/pos/reducers';
import { ADD_PAYMENT, POS_TERMINAL_LOAD_DONE, REMOVE_PAYMENT, UPDATE_ORDER } from '../../../apps/pos/actionTypes';
import { POSContent } from '../../../apps/pos/containers/POSContent';

// No backend in jsdom: the order panel loads products on mount.
jest.mock('../../../apps/pos/api/products', () => ({
  getProducts: jest.fn(() => Promise.resolve({ list: [] })),
}));

/*
 * Flaky case 207 (e2e pos_cash_sale.spec.js:45), a real cashier-facing defect.
 *
 * The backend sends every POS order save as a websocket "order changed" snapshot through a TRAILING 200 ms
 * debouncer (WebsocketSender). So all snapshots of a quick sequence (scan -> checkout -> tap CASH) arrive in one
 * batch, ~200 ms AFTER the add-payment REST response, and the frontend applies each one (UPDATE_ORDER has no
 * staleness check). The stale ones drop the pending cash payment for a render, so the cash dialog unmounts and
 * re-mounts. Whatever the cashier typed into it in between must survive that.
 *
 * Rendered from POSContent down, because a stale DRAFTED snapshot also swaps the whole payment panel for the
 * order panel, i.e. it unmounts everything below POSContent.
 */

const ORDER_UUID = 'order-1';
const POS_TERMINAL_ID = 1000000;
const LINE = { uuid: 'line-1', productId: 1, productName: 'P1', price: 2.5, qty: 1, amount: 2.5 };

const backendOrder = ({ status = 'WP', payments }) => ({
  uuid: ORDER_UUID,
  posTerminalId: POS_TERMINAL_ID,
  status,
  currencySymbol: '€',
  totalAmt: 2.5,
  paidAmt: payments.reduce((acc, p) => acc + p.amount, 0),
  lines: [LINE],
  payments,
  allowAddPayment: status === 'WP' && payments.length === 0,
});

const draftedSnapshot = backendOrder({ status: 'DR', payments: [] });
const checkoutSnapshot = backendOrder({ payments: [] });

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
const checkoutAndTapCash = (store) => {
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
  const addPaymentSnapshot = backendOrder({ payments: [{ ...payment, allowCheckout: true }] });
  applySnapshot(store, addPaymentSnapshot);
  return addPaymentSnapshot;
};

describe('POSContent - cash dialog vs. the delayed websocket snapshots', () => {
  it('keeps the tendered amount when the stale checkout snapshot re-mounts the cash dialog', () => {
    const store = newStore();
    const addPaymentSnapshot = checkoutAndTapCash(store);

    tapNumpad('10');
    expect(okButton()).toBeEnabled();

    // the debounced batch: checkout snapshot (no payment), then add-payment snapshot
    applySnapshot(store, checkoutSnapshot);
    expect(screen.queryByTestId('pos-cash-payment-modal')).not.toBeInTheDocument();
    applySnapshot(store, addPaymentSnapshot);

    expect(okButton()).toBeEnabled();
    expect(tenderedAmountText()).toContain('10.00 €');
  });

  it('keeps the tendered amount when a stale DRAFTED snapshot also unmounts the payment panel', () => {
    const store = newStore();
    const addPaymentSnapshot = checkoutAndTapCash(store);

    tapNumpad('10');
    expect(okButton()).toBeEnabled();

    // the scan's save landed within the debounce window too: drafted snapshot, checkout snapshot, add-payment snapshot
    applySnapshot(store, draftedSnapshot);
    expect(screen.queryByTestId('pos-payment-panel')).not.toBeInTheDocument();
    applySnapshot(store, checkoutSnapshot);
    applySnapshot(store, addPaymentSnapshot);

    expect(okButton()).toBeEnabled();
    expect(tenderedAmountText()).toContain('10.00 €');
  });

  // Regression guard for keying the draft by payment uuid (it passes without the fix, too).
  it('does not carry a tendered amount over to another cash payment', () => {
    const store = newStore();
    checkoutAndTapCash(store);
    tapNumpad('10');
    expect(okButton()).toBeEnabled();

    // the cashier cancels (the payment is removed), then taps CASH again: a new payment with a new uuid
    const payment_uuid = store.getState()['applications/pos'].orders.byUUID[ORDER_UUID].payments[0].uuid;
    act(() => {
      store.dispatch({ type: REMOVE_PAYMENT, payload: { order_uuid: ORDER_UUID, payment_uuid } });
    });
    expect(screen.queryByTestId('pos-cash-payment-modal')).not.toBeInTheDocument();
    act(() => {
      store.dispatch({ type: ADD_PAYMENT, payload: { order_uuid: ORDER_UUID, paymentMethod: 'CASH', amount: 2.5 } });
    });

    expect(okButton()).toBeDisabled();
  });
});
