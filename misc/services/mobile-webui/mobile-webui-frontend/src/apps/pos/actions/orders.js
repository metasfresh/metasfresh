import * as ordersAPI from '../api/orders';
import {
  ADD_ORDER_LINE,
  ADD_PAYMENT,
  NEW_ORDER,
  ORDERS_LIST_UPDATE,
  REMOVE_ORDER_LINE,
  REMOVE_PAYMENT,
  SET_SELECTED_ORDER_LINE,
} from '../actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { getPOSApplicationState } from './common';

export const useCurrentOrderOrNew = ({ posTerminalId }) => {
  const dispatch = useDispatch();
  const [loadingStatus, setLoadingStatus] = useState('to-load');
  const currentOrder = useSelector(getCurrentOrderFromState);

  useEffect(() => {
    if (!currentOrder && posTerminalId) {
      if (loadingStatus === 'to-load') {
        setLoadingStatus('loading');
        dispatch(addNewOrderAction({ posTerminalId }));
        console.log('No current order => creating new order');
      }
    } else {
      if (loadingStatus !== 'load-done') {
        setLoadingStatus('load-done');
      }
    }
  }, [posTerminalId]);

  return {
    isCurrentOrderLoading: loadingStatus !== 'load-done',
    currentOrder,
  };
};
export const useCurrentOrder = ({ posTerminalId }) => {
  const dispatch = useDispatch();
  const [isProcessing, setProcessing] = useState(false);
  const { isOpenOrdersLoading } = useOpenOrders({ posTerminalId });
  const currentOrder = useSelector(getCurrentOrderFromState);

  const isLoading = isOpenOrdersLoading;
  return {
    ...(currentOrder ?? {}),
    isLoading,
    isProcessing,
    addOrderLine: (orderLine) => {
      if (isProcessing) {
        console.log('Skip adding order line because order is currently processing', { orderLine, currentOrder });
        return;
      }
      setProcessing(true);
      dispatch(
        addOrderLine({
          qty: 1,
          ...orderLine,
          order_uuid: currentOrder?.uuid,
        })
      );
      dispatch(() => setProcessing(false));
    },
    removeOrderLine: (line_uuid) => {
      if (isProcessing) {
        console.log('Skip removing order line because order is currently processing', { line_uuid, currentOrder });
        return;
      }
      setProcessing(true);

      dispatch(
        removeOrderLine({
          order_uuid: currentOrder?.uuid,
          line_uuid,
        })
      );

      dispatch(() => setProcessing(false));
    },
  };
};
const getCurrentOrderFromState = (globalState) => {
  const applicationState = getPOSApplicationState(globalState);
  const current_uuid = getCurrentOrderUUID({ applicationState });
  if (!current_uuid) {
    return null;
  }

  return getOrderByUUID({ applicationState, order_uuid: current_uuid });
};
export const useOpenOrders = ({ posTerminalId }) => {
  const dispatch = useDispatch();
  const [loadStatus, setLoadStatus] = useState('new');
  const openOrders = useSelector(getOpenOrdersArrayFromState);

  useEffect(() => {
    if (loadStatus === 'new' && posTerminalId) {
      setLoadStatus('loading');
      ordersAPI
        .getOpenOrders({ posTerminalId })
        .then(({ list }) => dispatch(updateOrdersArrayFromBackendAction({ posTerminalId, list, isUpdateOnly: false })))
        .finally(() => setLoadStatus('load-done'));
    }
  }, [posTerminalId]);

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
export const addNewOrderAction = ({ posTerminalId }) => {
  return {
    type: NEW_ORDER,
    payload: { posTerminalId },
  };
};
export const changeOrderStatusToDraft = ({ posTerminalId, order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToDraft({ posTerminalId, order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const changeOrderStatusToVoid = ({ posTerminalId, order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToVoid({ posTerminalId, order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const changeOrderStatusToWaitingPayment = ({ posTerminalId, order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToWaitingPayment({ posTerminalId, order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const changeOrderStatusToComplete = ({ posTerminalId, order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToComplete({ posTerminalId, order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
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
  catchWeightUomId,
  catchWeightUomSymbol,
  catchWeight,
  ...otherOrderLineFields
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
        catchWeightUomId,
        catchWeightUomSymbol,
        catchWeight,
        ...otherOrderLineFields,
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
  catchWeightUomId,
  catchWeightUomSymbol,
  catchWeight,
  ...otherOrderLineFields
}) => {
  return {
    type: ADD_ORDER_LINE,
    payload: {
      order_uuid,
      productId,
      productName,
      taxCategoryId,
      currencySymbol,
      price,
      qty,
      uomId,
      uomSymbol,
      catchWeightUomId,
      catchWeightUomSymbol,
      catchWeight,
      ...otherOrderLineFields,
    },
  };
};

const removeOrderLine = ({ order_uuid, line_uuid }) => {
  return (dispatch) => {
    dispatch(removeOrderLineAction({ order_uuid, line_uuid }));
    dispatch(syncOrderToBackend({ order_uuid }));
  };
};

const removeOrderLineAction = ({ order_uuid, line_uuid }) => {
  return {
    type: REMOVE_ORDER_LINE,
    payload: {
      order_uuid,
      line_uuid,
    },
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
export const updateOrderFromBackend = ({ posTerminalId, order_uuid }) => {
  return (dispatch) => {
    ordersAPI
      .getOpenOrders({ posTerminalId, ids: [order_uuid] })
      .then(({ list, missingIds }) =>
        dispatch(updateOrdersArrayFromBackendAction({ posTerminalId, list, missingIds, isUpdateOnly: true }))
      );
  };
};
const updateOrdersArrayFromBackendAction = ({ posTerminalId, list, missingIds, isUpdateOnly }) => {
  return {
    type: ORDERS_LIST_UPDATE,
    payload: { posTerminalId, ordersArray: list, missingIds, isUpdateOnly },
  };
};
export const updateOrderFromBackendAction = ({ order }) => {
  return {
    type: ORDERS_LIST_UPDATE,
    payload: { posTerminalId: order.posTerminalId, ordersArray: [order], isUpdateOnly: true },
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
export const checkoutPayment = ({ posTerminalId, order_uuid, payment_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.checkoutPayment({ posTerminalId, order_uuid, payment_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const refundPayment = ({ posTerminalId, order_uuid, payment_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.refundPayment({ posTerminalId, order_uuid, payment_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
