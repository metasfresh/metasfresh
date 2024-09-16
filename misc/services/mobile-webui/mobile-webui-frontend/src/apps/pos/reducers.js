import { ADD_ORDER_LINE, NEW_ORDER, REMOVE_ORDER } from './actionTypes';
import { v4 as uuidv4 } from 'uuid';

const initialState = {
  orders: {
    current_uuid: null,
    byUUID: {},
  },
};

export function posReducer(applicationState = initialState, action) {
  switch (action.type) {
    case NEW_ORDER: {
      return {
        ...applicationState,
        orders: addNewOrderAndSetCurrent(applicationState.orders),
      };
    }
    case REMOVE_ORDER: {
      const {
        payload: { order_uuid },
      } = action;

      return {
        ...applicationState,
        orders: removeOrderByUUID({ orders: applicationState.orders, order_uuid }),
      };
    }
    case ADD_ORDER_LINE: {
      const {
        payload: { productId, productName },
      } = action;

      return {
        ...applicationState,
        orders: addOrderLineToCurrentOrder(applicationState.orders, { productId, productName }),
      };
    }
    default: {
      return applicationState;
    }
  }
}

const addNewOrderAndSetCurrent = (orders) => {
  const newOrder = {
    uuid: uuidv4(),
    lines: [],
  };

  return {
    ...orders,
    current_uuid: newOrder.uuid,
    byUUID: { ...orders?.byUUID, [newOrder.uuid]: newOrder },
  };
};

const addOrderLineToCurrentOrder = (orders, { productId, productName }) => {
  const newOrderLine = {
    uuid: uuidv4(),
    productId,
    productName,
  };

  return changeCurrentOrder(orders, (order) => addOrderLineToOrder({ order, newOrderLine }));
};

const addOrderLineToOrder = ({ order, newOrderLine }) => {
  return {
    ...order,
    lines: [...order.lines, newOrderLine],
  };
};

const changeCurrentOrder = (orders, currentOrderMapper) => {
  const currentOrder = getCurrentOrder({ orders });

  const currentOrderChanged = currentOrderMapper(currentOrder);
  return {
    ...orders,
    byUUID: { ...orders.byUUID, [currentOrderChanged.uuid]: currentOrderChanged },
  };
};

const getCurrentOrder = ({ orders }) => {
  return orders.byUUID[orders.current_uuid];
};

const removeOrderByUUID = ({ orders, order_uuid }) => {
  const byUUID = { ...orders.byUUID };
  delete byUUID[order_uuid];

  const current_uuid = byUUID[orders.current_uuid] ? orders.current_uuid : Object.keys(byUUID).find(() => true);

  console.log('removeOrderByUUID', { new: { byUUID, current_uuid }, old: orders });

  return { ...orders, current_uuid, byUUID };
};
