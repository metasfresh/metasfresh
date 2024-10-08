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

export const useCurrentOrderOrNew = () => {
  const dispatch = useDispatch();
  const [loadingStatus, setLoadingStatus] = useState('to-load');
  const currentOrder = useSelector(getCurrentOrderFromState);

  useEffect(() => {
    if (!currentOrder) {
      if (loadingStatus === 'to-load') {
        setLoadingStatus('loading');
        dispatch(addNewOrderAction());
        console.log('No current order => creating new order', { currentOrder, loadingStatus });
      }
    } else {
      if (loadingStatus !== 'load-done') {
        setLoadingStatus('load-done');
      }
    }
  }, []);

  return {
    isCurrentOrderLoading: loadingStatus !== 'load-done',
    currentOrder,
  };
};
export const useCurrentOrder = () => {
  const dispatch = useDispatch();
  const [isProcessing, setProcessing] = useState(false);
  const { isOpenOrdersLoading } = useOpenOrders();
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
export const useOpenOrders = () => {
  const dispatch = useDispatch();
  const [loadStatus, setLoadStatus] = useState('new');
  const openOrders = useSelector(getOpenOrdersArrayFromState);

  useEffect(() => {
    if (loadStatus === 'new') {
      setLoadStatus('loading');
      ordersAPI
        .getOpenOrders()
        .then(({ list }) => dispatch(updateOrdersArrayFromBackendAction({ list, isUpdateOnly: false })))
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
export const changeOrderStatusToVoid = ({ order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToVoid({ order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const changeOrderStatusToWaitingPayment = ({ order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToWaitingPayment({ order_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const changeOrderStatusToComplete = ({ order_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.changeOrderStatusToComplete({ order_uuid });
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
export const updateOrderFromBackend = ({ order_uuid }) => {
  return (dispatch) => {
    ordersAPI
      .getOpenOrders({ ids: [order_uuid] })
      .then(({ list, missingIds }) =>
        dispatch(updateOrdersArrayFromBackendAction({ list, missingIds, isUpdateOnly: true }))
      );
  };
};
const updateOrdersArrayFromBackendAction = ({ list, missingIds, isUpdateOnly }) => {
  return {
    type: ORDERS_LIST_UPDATE,
    payload: { ordersArray: list, missingIds, isUpdateOnly },
  };
};
export const updateOrderFromBackendAction = ({ order }) => {
  return {
    type: ORDERS_LIST_UPDATE,
    payload: { ordersArray: [order], isUpdateOnly: true },
  };
  // return {
  //   type: UPDATE_ORDER_FROM_BACKEND,
  //   payload: { order },
  // };
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
export const checkoutPayment = ({ order_uuid, payment_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.checkoutPayment({ order_uuid, payment_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
export const refundPayment = ({ order_uuid, payment_uuid }) => {
  return async (dispatch) => {
    const order = await ordersAPI.refundPayment({ order_uuid, payment_uuid });
    dispatch(updateOrderFromBackendAction({ order }));
  };
};
