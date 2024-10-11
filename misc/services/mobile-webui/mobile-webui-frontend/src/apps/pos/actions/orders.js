import * as ordersAPI from '../api/orders';
import {
  ADD_ORDER_LINE,
  ADD_PAYMENT,
  NEW_ORDER,
  ORDERS_LIST_UPDATE,
  REMOVE_ORDER_LINE,
  REMOVE_PAYMENT,
  SET_CURRENT_ORDER,
  SET_SELECTED_ORDER_LINE,
  UPDATE_ORDER,
} from '../actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { getCurrentOrderFromGlobalState, getOrderByUUID } from '../reducers/orderUtils';

export const useCurrentOrderOrNew = ({ posTerminalId }) => {
  const dispatch = useDispatch();
  const [loadingStatus, setLoadingStatus] = useState('to-load');
  const currentOrder = useSelector(getCurrentOrderFromGlobalState);

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
  const currentOrder = useSelector(getCurrentOrderFromGlobalState);

  const isLoading = !posTerminalId;
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

export const addNewOrderAction = ({ posTerminalId }) => {
  return {
    type: NEW_ORDER,
    payload: { posTerminalId },
  };
};
export const setCurrentOrder = ({ order_uuid }) => {
  return (dispatch) => dispatch(setCurrentOrderAction({ order_uuid }));
};
const setCurrentOrderAction = ({ order_uuid }) => {
  return {
    type: SET_CURRENT_ORDER,
    payload: { order_uuid },
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
    const order = getOrderByUUID({ globalState, order_uuid });
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
    type: UPDATE_ORDER,
    payload: { order },
  };
};
export const setSelectedOrderLineAction = ({ order_uuid, selectedLineUUID }) => {
  return {
    type: SET_SELECTED_ORDER_LINE,
    payload: { order_uuid, selectedLineUUID },
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
const removePaymentAction = ({ order_uuid, payment_uuid }) => {
  return { type: REMOVE_PAYMENT, payload: { order_uuid, payment_uuid } };
};
export const checkoutPayment = ({ posTerminalId, order_uuid, payment_uuid, cardPayAmount, cashTenderedAmount }) => {
  return async (dispatch) => {
    const order = await ordersAPI.checkoutPayment({
      posTerminalId,
      order_uuid,
      payment_uuid,
      cardPayAmount,
      cashTenderedAmount,
    });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const refundPayment = ({ posTerminalId, order_uuid, payment_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.refundPayment({ posTerminalId, order_uuid, payment_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
