import {
  ADD_ORDER_LINE,
  NEW_ORDER,
  ORDERS_LIST_INIT,
  REMOVE_ORDER,
  SET_SELECTED_ORDER_LINE,
  UPDATE_ORDER_FROM_BACKEND,
} from './actionTypes';
import { v4 as uuidv4 } from 'uuid';

const initialState = {
  orders: {
    current_uuid: null,
    byUUID: {},
  },
};

export function posReducer(applicationState = initialState, action) {
  switch (action.type) {
    case ORDERS_LIST_INIT: {
      const {
        payload: { ordersArray },
      } = action;

      return {
        ...applicationState,
        orders: setOrders({ orders: applicationState.orders, ordersArray }),
      };
    }
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
    case UPDATE_ORDER_FROM_BACKEND: {
      const {
        payload: { order },
      } = action;

      return {
        ...applicationState,
        orders: updateOrderFromBackend({ orders: applicationState.orders, order }),
      };
    }
    case ADD_ORDER_LINE: {
      const {
        payload: { order_uuid, productId, productName, currencySymbol, price, qty, uomId, uomSymbol },
      } = action;

      return {
        ...applicationState,
        orders: addOrderLineToCurrentOrder(applicationState.orders, {
          order_uuid,
          productId,
          productName,
          currencySymbol,
          price,
          qty,
          uomId,
          uomSymbol,
        }),
      };
    }
    case SET_SELECTED_ORDER_LINE: {
      const {
        payload: { order_uuid, selectedLineUUID },
      } = action;
      return {
        ...applicationState,
        orders: setSelectedOrderLine({ orders: applicationState.orders, order_uuid, selectedLineUUID }),
      };
    }
    default: {
      return applicationState;
    }
  }
}

//
//
//

const setOrders = ({ orders, ordersArray }) => {
  const byUUID = {};
  ordersArray.forEach((order) => (byUUID[order.uuid] = recomputeOrderDetails({ order })));

  // Reset current_uuid if is no longer present in the orders array
  let current_uuid = orders.current_uuid;
  if (current_uuid && !byUUID[current_uuid]) {
    current_uuid = null;
  }

  if (!current_uuid) {
    if (ordersArray.length > 0) {
      current_uuid = ordersArray[0].uuid;
    } else {
      const newOrder = {
        uuid: uuidv4(),
        lines: [],
      };
      current_uuid = newOrder.uuid;
      byUUID[newOrder.uuid] = newOrder;
    }
  }

  return {
    ...orders,
    current_uuid,
    byUUID,
  };
};

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

const addOrderLineToCurrentOrder = (
  orders,
  { order_uuid, productId, productName, currencySymbol, price, qty, uomId, uomSymbol }
) => {
  const newOrderLine = {
    uuid: uuidv4(),
    productId,
    productName,
    currencySymbol,
    price,
    qty,
    uomId,
    uomSymbol,
  };

  return changeOrder({
    orders,
    order_uuid,
    mapper: (order) => addOrderLineToOrder({ order, newOrderLine }),
  });
};

const addOrderLineToOrder = ({ order, newOrderLine }) => {
  return {
    ...order,
    lines: [...order.lines, newOrderLine],
  };
};

const changeOrder = ({ orders, order_uuid, mapper }) => {
  const order = orders.byUUID[order_uuid];
  if (!order) {
    throw 'No order found for ' + order_uuid;
  }

  const orderChanged = mapper(order);
  if (order === orderChanged) {
    return orders;
  } else {
    return {
      ...orders,
      byUUID: { ...orders.byUUID, [orderChanged.uuid]: orderChanged },
    };
  }
};

const removeOrderByUUID = ({ orders, order_uuid }) => {
  const byUUID = { ...orders.byUUID };
  delete byUUID[order_uuid];

  const current_uuid = byUUID[orders.current_uuid] ? orders.current_uuid : Object.keys(byUUID).find(() => true);

  console.log('removeOrderByUUID', { new: { byUUID, current_uuid }, old: orders });

  return { ...orders, current_uuid, byUUID };
};

const updateOrderFromBackend = ({ orders, order }) => {
  const byUUID = { ...orders.byUUID };
  byUUID[order.uuid] = recomputeOrderDetails({ order });
  return { ...orders, byUUID };
};

const recomputeOrderDetails = ({ order }) => {
  let orderChanged = order;

  if (!orderChanged.selectedLineUUID || !orderChanged.lines.some((line) => line.uuid === order.selectedLineUUID)) {
    const lines = orderChanged.lines;
    const selectedLineUUID = lines.length > 0 ? lines[lines.length - 1].uuid : null;
    orderChanged = { ...orderChanged, selectedLineUUID };
  }

  return orderChanged;
};

const setSelectedOrderLine = ({ orders, order_uuid, selectedLineUUID }) => {
  return changeOrder({
    orders,
    order_uuid,
    mapper: (order) => {
      if (order.selectedLineUUID === selectedLineUUID) {
        return order;
      } else {
        return { ...order, selectedLineUUID };
      }
    },
  });
};
