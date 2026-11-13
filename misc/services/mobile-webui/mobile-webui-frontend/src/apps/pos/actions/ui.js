import { CLOSE_MODAL, CLOSE_PANEL, SET_CASH_TENDERED_DRAFT, SHOW_MODAL, SHOW_PANEL } from '../actionTypes';

export const MODAL_POSTerminalSelect = 'POSTerminalSelect';
export const MODAL_SelectOrders = 'SelectOrders';
export const MODAL_CashWithdrawal = 'CashWithdrawal';
export const MODAL_InvoiceSettlement = 'InvoiceSettlement';

export const showModalAction = ({ modal }) => {
  return {
    type: SHOW_MODAL,
    payload: { modal },
  };
};

export const closeModalAction = ({ ifModal } = {}) => {
  return {
    type: CLOSE_MODAL,
    payload: { ifModal },
  };
};

// The main content area's panel switch — independent of `modal` (an overlay on top of the content).
// `Return` takes over the whole content area (order panel <-> return panel), the same way the order's own
// status switches between order/payment/summary panels in POSContent.
export const PANEL_Return = 'Return';

export const showPanelAction = ({ panel }) => {
  return {
    type: SHOW_PANEL,
    payload: { panel },
  };
};

export const closePanelAction = () => {
  return {
    type: CLOSE_PANEL,
    payload: {},
  };
};

// The tendered amount the cashier is typing into the cash payment dialog. Kept in the store, not in the dialog,
// because the dialog (and the whole payment panel) is unmounted and re-mounted whenever a delayed websocket order
// snapshot briefly drops the pending payment; the re-mounted dialog restores the amount from here.
export const setCashTenderedDraftAction = ({ payment_uuid, tenderedEditingAmount }) => {
  return {
    type: SET_CASH_TENDERED_DRAFT,
    payload: { payment_uuid, tenderedEditingAmount },
  };
};
