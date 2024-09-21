import {
  ADD_ORDER_LINE,
  ADD_PAYMENT,
  NEW_ORDER,
  ORDERS_LIST_INIT,
  POS_TERMINAL_CLOSING,
  POS_TERMINAL_CLOSING_CANCEL,
  POS_TERMINAL_LOAD_DONE,
  POS_TERMINAL_LOAD_START,
  REMOVE_ORDER,
  REMOVE_PAYMENT,
  SET_SELECTED_ORDER_LINE,
  UPDATE_ORDER_FROM_BACKEND,
} from './actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { getApplicationState } from '../index';
import { APPLICATION_ID } from './constants';
import * as ordersAPI from './api/orders';
import * as posTerminalAPI from './api/posTerminal';
import * as posJournalAPI from './api/posJournal';

export const usePOSTerminal = ({ refresh } = { refresh: false }) => {
  const dispatch = useDispatch();
  const posTerminal = useSelector(getPOSTerminalFromGlobalState);

  useEffect(() => {
    if (refresh) {
      updatePOSTerminalFromBackend({ dispatch });
    }
  }, []);

  if (!posTerminal) return { isLoading: true };

  return {
    ...posTerminal,
    updateFromBackend: () => updatePOSTerminalFromBackend({ dispatch }),
    changeStatusToClosing: () => dispatch(posTerminalClosingAction()),
    cancelClosing: () => dispatch(posTerminalClosingCancelAction()),
    openJournal: ({ cashBeginningBalance, openingNote }) => {
      dispatch(openJournal({ cashBeginningBalance, openingNote }));
    },
    closeJournal: ({ cashClosingBalance, closingNote }) => {
      dispatch(closeJournal({ cashClosingBalance, closingNote }));
    },
  };
};

const getPOSTerminalFromGlobalState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  return applicationState?.terminal;
};

const updatePOSTerminalFromBackend = ({ dispatch }) => {
  dispatch(posTerminalLoadStartAction());
  posTerminalAPI.getPOSTerminal().then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
};

const posTerminalLoadStartAction = () => {
  return {
    type: POS_TERMINAL_LOAD_START,
  };
};
const posTerminalLoadDoneAction = ({ terminal }) => {
  return {
    type: POS_TERMINAL_LOAD_DONE,
    payload: { terminal },
  };
};

const posTerminalClosingAction = () => {
  return {
    type: POS_TERMINAL_CLOSING,
  };
};

const posTerminalClosingCancelAction = () => {
  return {
    type: POS_TERMINAL_CLOSING_CANCEL,
  };
};

const openJournal = ({ cashBeginningBalance, openingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .openJournal({ cashBeginningBalance, openingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};

const closeJournal = ({ cashClosingBalance, closingNote }) => {
  return (dispatch) => {
    return posJournalAPI
      .closeJournal({ cashClosingBalance, closingNote })
      .then((terminal) => dispatch(posTerminalLoadDoneAction({ terminal })));
  };
};

//
//
//
//
//
//
//
//
//
//

export const useCurrentOrderOrNew = () => {
  const dispatch = useDispatch();
  const [isLoading, setLoading] = useState();
  const currentOrder = useSelector(getCurrentOrderFromState);

  if (!currentOrder) {
    if (!isLoading) {
      setLoading(true);
      dispatch(addNewOrderAction());
    }
    return { isCurrentOrderLoading: true, currentOrder };
  } else {
    if (isLoading) {
      setLoading(false);
    }
    return { isCurrentOrderLoading: false, currentOrder };
  }
};

export const useCurrentOrder = () => {
  const { isOpenOrdersLoading } = useOpenOrders();
  const currentOrder = useSelector(getCurrentOrderFromState);

  if (isOpenOrdersLoading) {
    return { isCurrentOrderLoading: true, currentOrder };
  }

  return { isCurrentOrderLoading: false, currentOrder };
};

const getCurrentOrderFromState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  const current_uuid = getCurrentOrderUUID({ applicationState });
  if (!current_uuid) {
    return null;
  }

  return getOrderByUUID({ applicationState, order_uuid: current_uuid });
};

export const useOpenOrders = () => {
  const dispatch = useDispatch();
  const [loadStatus, setLoadStatus] = useState('new');
  const openOrders = useSelector(getOpenOrdersArrayFromState);

  useEffect(() => {
    if (loadStatus === 'new') {
      setLoadStatus('loading');
      ordersAPI
        .getOpenOrdersArray()
        .then((ordersArray) =>
          dispatch({
            type: ORDERS_LIST_INIT,
            payload: { ordersArray },
          })
        )
        .finally(() => setLoadStatus('load-done'));
    }
  }, []);

  return {
    isOpenOrdersLoading: loadStatus === 'loading',
    openOrders,
  };
};

const getOpenOrdersArrayFromState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  const orders = applicationState?.orders?.byUUID ?? {};
  return Object.values(orders);
};

const getPOSApplicationState = (globalState) => getApplicationState(globalState, APPLICATION_ID);

const getOrderByUUID = ({ applicationState, order_uuid }) => applicationState?.orders?.byUUID?.[order_uuid];

const getCurrentOrderUUID = ({ globalState, applicationState }) => {
  let applicationStateEff = applicationState;
  if (applicationState == null && globalState != null) {
    applicationStateEff = getPOSApplicationState(globalState);
  }
  if (applicationStateEff == null) {
    throw 'Cannot determine applicationState';
  }

  return applicationStateEff?.orders?.current_uuid;
};

export const addNewOrderAction = () => {
  return {
    type: NEW_ORDER,
  };
};

export const changeOrderStatusToDraft = ({ order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToDraft({ order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};

export const changeOrderStatusToWaitingPayment = ({ order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToWaitingPayment({ order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};

export const changeOrderStatusToVoid = ({ order_uuid }) => {
  return async (dispatch) => {
    await ordersAPI.changeOrderStatusToVoid({ order_uuid });
    dispatch(removeOrderAction({ order_uuid }));
  };
};

export const changeOrderStatusToComplete = ({ order_uuid }) => {
  return async (dispatch) => {
    await ordersAPI.changeOrderStatusToComplete({ order_uuid });
    dispatch(removeOrderAction({ order_uuid }));
  };
};

export const removeOrderAction = ({ order_uuid }) => {
  return {
    type: REMOVE_ORDER,
    payload: { order_uuid },
  };
};

export const addOrderLine = ({
  order_uuid,
  productId,
  productName,
  taxCategoryId,
  currencySymbol,
  price,
  qty,
  uomId,
  uomSymbol,
}) => {
  return (dispatch) => {
    dispatch(
      addOrderLineAction({
        order_uuid,
        productId,
        productName,
        taxCategoryId,
        currencySymbol,
        price,
        qty,
        uomId,
        uomSymbol,
      })
    );
    dispatch(syncOrderToBackend({ order_uuid }));
  };
};

const addOrderLineAction = ({
  order_uuid,
  productId,
  productName,
  taxCategoryId,
  currencySymbol,
  price,
  qty,
  uomId,
  uomSymbol,
}) => {
  return {
    type: ADD_ORDER_LINE,
    payload: { order_uuid, productId, productName, taxCategoryId, currencySymbol, price, qty, uomId, uomSymbol },
  };
};

const syncOrderToBackend = ({ order_uuid }) => {
  return (dispatch, getState) => {
    const globalState = getState();
    const applicationState = getPOSApplicationState(globalState);
    const order = getOrderByUUID({ applicationState, order_uuid });
    ordersAPI.updateOrder({ order }).then((order) => dispatch(updateOrderFromBackendAction({ order })));
  };
};

const updateOrderFromBackendAction = ({ order }) => {
  return {
    type: UPDATE_ORDER_FROM_BACKEND,
    payload: { order },
  };
};

export const setSelectedOrderLineAction = ({ order_uuid, selectedLineUUID }) => {
  return {
    type: SET_SELECTED_ORDER_LINE,
    payload: { order_uuid, selectedLineUUID },
  };
};

export const addPayment = ({ order_uuid, paymentMethod, amount }) => {
  return (dispatch) => {
    dispatch(addPaymentAction({ order_uuid, paymentMethod, amount }));
    dispatch(syncOrderToBackend({ order_uuid }));
  };
};

const addPaymentAction = ({ order_uuid, paymentMethod, amount }) => {
  return { type: ADD_PAYMENT, payload: { order_uuid, paymentMethod, amount } };
};

export const removePayment = ({ order_uuid, payment_uuid }) => {
  return (dispatch) => {
    dispatch(removePaymentAction({ order_uuid, payment_uuid }));
    dispatch(syncOrderToBackend({ order_uuid }));
  };
};
export const removePaymentAction = ({ order_uuid, payment_uuid }) => {
  return { type: REMOVE_PAYMENT, payload: { order_uuid, payment_uuid } };
};

//
//
//
//
//
//
//
//
//
//
