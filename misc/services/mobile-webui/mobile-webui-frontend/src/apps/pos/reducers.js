import {
  ADD_ORDER_LINE,
  ADD_PAYMENT,
  NEW_ORDER,
  ORDERS_LIST_UPDATE,
  POS_TERMINAL_CLOSING,
  POS_TERMINAL_CLOSING_CANCEL,
  POS_TERMINAL_LOAD_DONE,
  POS_TERMINAL_LOAD_START,
  REMOVE_ORDER_LINE,
  REMOVE_PAYMENT,
  SET_SELECTED_ORDER_LINE,
} from './actionTypes';
import { v4 as uuidv4 } from 'uuid';

const initialState = {
  terminal: null,
  orders: {
    current_uuid: null,
    byUUID: {},
  },
};

export function posReducer(applicationState = initialState, action) {
  switch (action.type) {
    case POS_TERMINAL_LOAD_START: {
      return {
        ...applicationState,
        terminal: {
          ...applicationState?.terminal,
          isLoading: true,
        },
      };
    }
    case POS_TERMINAL_LOAD_DONE: {
      const { terminal } = action.payload;

      // preserve closing flag.
      // also, closing makes sense only if the journal is already open.
      const isCashJournalClosing = applicationState?.terminal?.isCashJournalClosing && terminal?.cashJournalOpen;

      return {
        ...applicationState,
        terminal: {
          ...terminal,
          isCashJournalClosing,
          isLoading: false,
          isLoaded: true,
        },
      };
    }
    case POS_TERMINAL_CLOSING: {
      const terminal = applicationState?.terminal ?? {};
      const isCashJournalClosing = !!terminal?.cashJournalOpen; // closing makes sense only if the journal is already open

      return {
        ...applicationState,
        terminal: {
          ...terminal,
          isCashJournalClosing,
        },
      };
    }
    case POS_TERMINAL_CLOSING_CANCEL: {
      return {
        ...applicationState,
        terminal: {
          ...applicationState?.terminal,
          isCashJournalClosing: false,
        },
      };
    }
    case ORDERS_LIST_UPDATE: {
      const {
        payload: { ordersArray, missingIds, isUpdateOnly },
      } = action;

      return {
        ...applicationState,
        orders: updateOrders({
          orders: applicationState.orders,
          fromOrdersArray: ordersArray,
          missingIds,
          isUpdateOnly,
        }),
      };
    }
    case NEW_ORDER: {
      return {
        ...applicationState,
        orders: addNewOrderAndSetCurrent(applicationState.orders),
      };
    }
    case ADD_ORDER_LINE: {
      return {
        ...applicationState,
        orders: addOrderLineToCurrentOrder(applicationState.orders, action.payload),
      };
    }
    case REMOVE_ORDER_LINE: {
      return {
        ...applicationState,
        orders: removeOrderLine(applicationState.orders, action.payload),
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
    //
    //
    case ADD_PAYMENT: {
      const { order_uuid, paymentMethod, amount } = action.payload;
      return addPayment({ applicationState, order_uuid, paymentMethod, amount });
    }
    case REMOVE_PAYMENT: {
      const { order_uuid, payment_uuid } = action.payload;
      return removePayment({ applicationState, order_uuid, payment_uuid });
    }
    //
    //

    default: {
      return applicationState;
    }
  }
}

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
//
//
//
//
//

const updateOrders = ({ orders, fromOrdersArray, missingIds, isUpdateOnly }) => {
  const byUUID = isUpdateOnly ? { ...orders.byUUID } : {};
  fromOrdersArray.forEach((order) => (byUUID[order.uuid] = recomputeOrderDetails({ order })));
  if (missingIds?.length > 0) {
    missingIds.forEach((missingId) => {
      delete byUUID[missingId];
    });
  }

  // Reset current_uuid if is no longer present in the orders array
  let current_uuid = orders.current_uuid;
  if (current_uuid && !byUUID[current_uuid]) {
    current_uuid = null;
  }

  if (!current_uuid) {
    const allOrderIds = Object.keys(byUUID);
    if (allOrderIds.length > 0) {
      current_uuid = allOrderIds[0];
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
  {
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
  }
) => {
  const newOrderLine = {
    uuid: uuidv4(),
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

const removeOrderLine = (ordersState, { order_uuid, line_uuid }) => {
  return changeOrder({
    orders: ordersState,
    order_uuid,
    mapper: (order) => remoteOrderLineFromOrder({ order, line_uuid }),
  });
};
const remoteOrderLineFromOrder = ({ order, line_uuid }) => {
  const lines = order.lines.filter((line) => line.uuid !== line_uuid);
  const selectedLineUUID = computeSelectedLineUUID({ selectedLineUUID: order.selectedLineUUID, lines });

  return { ...order, lines, selectedLineUUID };
};

const changeOrderInApplicationState = ({ applicationState, order_uuid, mapper }) => {
  const orders = applicationState.orders;
  const ordersChanged = changeOrder({ orders, order_uuid, mapper });
  if (orders === ordersChanged) {
    return applicationState;
  } else {
    return { ...applicationState, orders: ordersChanged };
  }
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

const recomputeOrderDetails = ({ order }) => {
  let orderChanged = order;

  const selectedLineUUID = computeSelectedLineUUID({ selectedLineUUID: order.selectedLineUUID, lines: order.lines });
  if (selectedLineUUID !== orderChanged.selectedLineUUID) {
    orderChanged = { ...orderChanged, selectedLineUUID };
  }

  return orderChanged;
};

const computeSelectedLineUUID = ({ selectedLineUUID, lines }) => {
  if (!selectedLineUUID || !lines.some((line) => line.uuid === selectedLineUUID)) {
    return lines.length > 0 ? lines[lines.length - 1].uuid : null;
  } else {
    return selectedLineUUID;
  }
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

//
//
//
//
//
//
//
//
//

const addPayment = ({ applicationState, order_uuid, paymentMethod, amount }) => {
  return changeOrderInApplicationState({
    applicationState,
    order_uuid,
    mapper: (order) => addPaymentToOrder({ order, paymentMethod, amount }),
  });
};

const addPaymentToOrder = ({ order, paymentMethod, amount }) => {
  const newPayment = {
    uuid: uuidv4(),
    paymentMethod,
    amount,
  };

  const paymentsPrev = order?.payments ?? [];
  const payments = [...paymentsPrev, newPayment];

  let orderChanged = { ...order, payments };
  orderChanged = updatePaymentAmounts(orderChanged);
  return orderChanged;
};

const removePayment = ({ applicationState, order_uuid, payment_uuid }) => {
  return changeOrderInApplicationState({
    applicationState,
    order_uuid,
    mapper: (order) => removePaymentFromOrder({ order, payment_uuid }),
  });
};

const removePaymentFromOrder = ({ order, payment_uuid }) => {
  if (!order.payments) {
    return order;
  }

  const paymentsPrev = order?.payments ?? [];
  const payments = order.payments.filter((payment) => payment.uuid !== payment_uuid);
  if (payments.length === paymentsPrev.length) {
    return order;
  }

  let orderChanged = { ...order, payments };
  orderChanged = updatePaymentAmounts(orderChanged);
  return orderChanged;
};

const updatePaymentAmounts = (order) => {
  let paidAmt = 0;
  const payments = order.payments ?? [];

  payments.forEach((payment) => {
    paidAmt += payment.amount ?? 0;
  });

  let openAmt = order.totalAmt - paidAmt;
  return { ...order, paidAmt, openAmt };
};
