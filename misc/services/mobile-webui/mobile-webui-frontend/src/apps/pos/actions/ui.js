import { CLOSE_MODAL, CLOSE_PANEL, SHOW_MODAL, SHOW_PANEL } from '../actionTypes';

export const MODAL_POSTerminalSelect = 'POSTerminalSelect';
export const MODAL_SelectOrders = 'SelectOrders';
export const MODAL_CashWithdrawal = 'CashWithdrawal';

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
