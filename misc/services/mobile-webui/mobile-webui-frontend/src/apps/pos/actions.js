import {
  ADD_ORDER_LINE,
  NEW_ORDER,
  ORDERS_LIST_INIT,
  REMOVE_ORDER,
  SET_SELECTED_ORDER_LINE,
  UPDATE_ORDER_FROM_BACKEND,
} from './actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useState } from 'react';
import { getApplicationState } from '../index';
import { APPLICATION_ID } from './constants';
import * as ordersAPI from './api/orders';

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
