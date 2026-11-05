import { ordersReducer } from '../../../apps/pos/reducers/orders';
import { POS_TERMINAL_LOAD_DONE, UPDATE_ORDER } from '../../../apps/pos/actionTypes';

/*
 * The backend stamps every saved POS order with a monotonic `version`. Order snapshots reach the frontend through two
 * channels: REST responses (immediately) and websocket events (through a trailing debouncer, so possibly AFTER a newer
 * REST response). A snapshot older than the order we already hold must not overwrite it.
 */

const ORDER_UUID = 'order-1';

const order = ({ version, status = 'WP', payments = [] }) => ({
  uuid: ORDER_UUID,
  posTerminalId: 1,
  status,
  lines: [],
  payments,
  ...(version !== undefined ? { version } : {}),
});

const CASH_PAYMENT = { uuid: 'pay-1', paymentMethod: 'CASH', amount: 2.5, status: 'NEW' };

const stateWith = (existingOrder) => ({
  orders: { current_uuid: ORDER_UUID, byUUID: { [ORDER_UUID]: existingOrder } },
});

const applySnapshot = (state, snapshot) => ordersReducer(state, { type: UPDATE_ORDER, payload: { order: snapshot } });

const currentOrder = (state) => state.orders.byUUID[ORDER_UUID];

describe('POS orders reducer - UPDATE_ORDER vs. out-of-order snapshots', () => {
  it('ignores a snapshot older than the order already held', () => {
    const state = stateWith(order({ version: 6, payments: [CASH_PAYMENT] }));

    const stateAfter = applySnapshot(state, order({ version: 5, payments: [] }));

    expect(stateAfter).toBe(state);
    expect(currentOrder(stateAfter).payments).toEqual([CASH_PAYMENT]);
  });

  it('ignores an older snapshot that would remove the order (e.g. a stale DRAFTED one)', () => {
    const state = stateWith(order({ version: 6, payments: [CASH_PAYMENT] }));

    expect(applySnapshot(state, order({ version: 4, status: 'DR' }))).toBe(state);
  });

  it('applies a newer snapshot', () => {
    const state = stateWith(order({ version: 5 }));

    const stateAfter = applySnapshot(state, order({ version: 6, payments: [CASH_PAYMENT] }));

    expect(currentOrder(stateAfter).version).toBe(6);
    expect(currentOrder(stateAfter).payments).toEqual([CASH_PAYMENT]);
  });

  // Same version = the save the frontend already applied (e.g. the websocket echo of a REST response). Applying it again
  // can only drop local changes which are not saved yet, e.g. the cash payment just added while its POST is in flight.
  it('ignores a snapshot of the same version, keeping the not yet saved local changes', () => {
    const state = stateWith(order({ version: 6, payments: [CASH_PAYMENT] }));

    const stateAfter = applySnapshot(state, order({ version: 6, payments: [] }));

    expect(stateAfter).toBe(state);
    expect(currentOrder(stateAfter).payments).toEqual([CASH_PAYMENT]);
  });

  it('applies a snapshot when either side has no version', () => {
    const versionless = stateWith(order({ payments: [] }));
    expect(currentOrder(applySnapshot(versionless, order({ version: 1, payments: [CASH_PAYMENT] }))).payments).toEqual([
      CASH_PAYMENT,
    ]);

    const versioned = stateWith(order({ version: 6, payments: [] }));
    expect(currentOrder(applySnapshot(versioned, order({ payments: [CASH_PAYMENT] }))).payments).toEqual([
      CASH_PAYMENT,
    ]);
  });
});

describe('POS orders reducer - UPDATE_ORDER vs. delayed snapshots of an order already removed', () => {
  const removedState = () => applySnapshot(stateWith(order({ version: 6 })), order({ version: 7, status: 'VO' }));

  it('removes the order on a voided snapshot', () => {
    expect(removedState().orders.byUUID[ORDER_UUID]).toBeUndefined();
  });

  it('does not bring the order back on an older snapshot', () => {
    const state = removedState();

    const stateAfter = applySnapshot(state, order({ version: 6, status: 'WP' }));

    expect(stateAfter.orders.byUUID[ORDER_UUID]).toBeUndefined();
  });

  it('applies a newer snapshot of the removed order', () => {
    const stateAfter = applySnapshot(removedState(), order({ version: 8, status: 'WP' }));

    expect(currentOrder(stateAfter).version).toBe(8);
  });

  it('forgets the removed orders when the whole orders list is reloaded', () => {
    const reloaded = ordersReducer(removedState(), {
      type: POS_TERMINAL_LOAD_DONE,
      payload: { posTerminal: { id: 1, openOrders: [] } },
    });

    const stateAfter = applySnapshot(reloaded, order({ version: 2, status: 'WP' }));

    expect(currentOrder(stateAfter).version).toBe(2);
  });
});
