import { ADD_ORDER_LINE, NEW_ORDER, REMOVE_ORDER } from './actionTypes';
import { useDispatch, useSelector } from 'react-redux';
import { useState } from 'react';
import { getApplicationState } from '../index';
import { APPLICATION_ID } from './constants';

export const useCurrentOrder = () => {
  return useSelector(getCurrentOrderFromState);
};

export const useCurrentOrderOrNew = () => {
  const dispatch = useDispatch();
  const [isLoading, setLoading] = useState();
  const currentOrder = useSelector(getCurrentOrderFromState);

  if (!currentOrder) {
    if (!isLoading) {
      setLoading(true);
      dispatch(addNewOrderAction());

      console.log('useCurrentOrderOrNew - new order');
    } else {
      console.log('useCurrentOrderOrNew - still loading', { currentOrder });
    }
    return { isCurrentOrderLoading: true, currentOrder };
  } else {
    if (isLoading) {
      setLoading(false);
    }
    console.log('useCurrentOrderOrNew - existing', { currentOrder });
    return { isCurrentOrderLoading: false, currentOrder };
  }
};

const getCurrentOrderFromState = (globalState) => {
  const applicationState = getApplicationState(globalState, APPLICATION_ID);
  const current_uuid = applicationState?.orders?.current_uuid;
  if (!current_uuid) {
    console.log('getCurrentOrderFromState - no current_uuid', { applicationState, current_uuid });
    return null;
  }

  const currentOrder = applicationState?.orders?.byUUID?.[current_uuid];
  console.log('getCurrentOrderFromState - currentOrder', { applicationState, currentOrder, current_uuid });
  return currentOrder;
};

export const addNewOrderAction = () => {
  return {
    type: NEW_ORDER,
  };
};

export const removeOrderAction = ({ order_uuid }) => {
  return {
    type: REMOVE_ORDER,
    payload: { order_uuid },
  };
};

export const addOrderLineAction = ({ productId, productName }) => {
  return {
    type: ADD_ORDER_LINE,
    payload: { productId, productName },
  };
};
