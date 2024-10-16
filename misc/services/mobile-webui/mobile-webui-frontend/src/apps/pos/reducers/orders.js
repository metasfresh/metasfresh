import {
  ADD_ORDER_LINE,
  ADD_PAYMENT,
  NEW_ORDER,
  ORDERS_LIST_UPDATE,
  POS_TERMINAL_LOAD_DONE,
  REMOVE_ORDER_LINE,
  REMOVE_PAYMENT,
  SET_CURRENT_ORDER,
  SET_SELECTED_ORDER_LINE,
  UPDATE_ORDER,
} from '../actionTypes';
import { v4 as uuidv4 } from 'uuid';
import { isOpenOrderStatus, ORDER_STATUS_DRAFTED } from '../constants/orderStatus';
import { PAYMENT_STATUS_NEW } from '../constants/paymentStatus';

export function ordersReducer(applicationState, action) {
  switch (action.type) {
    case POS_TERMINAL_LOAD_DONE: {
      const {
        posTerminal: { id: posTerminalId, openOrders },
      } = action.payload;
      if (openOrders != null) {
        return syncOrdersFromSource({
          applicationState,
          posTerminalId,
          fromOrdersArray: openOrders,
          isUpdateOnly: false,
        });
      } else {
        return applicationState;
      }
    }
    case ORDERS_LIST_UPDATE: {
      const { ordersArray, posTerminalId, missingIds, isUpdateOnly } = action.payload;
      return syncOrdersFromSource({
        applicationState,
        posTerminalId,
        fromOrdersArray: ordersArray,
        missingIds,
        isUpdateOnly,
      });
    }
    case UPDATE_ORDER: {
      const { order } = action.payload;

      let isOpenOrder = isOpenOrderStatus(order.status);
      return syncOrdersFromSource({
        applicationState,
        posTerminalId: order.posTerminalId,
        fromOrdersArray: isOpenOrder ? [order] : [],
        missingIds: isOpenOrder ? [] : [order.uuid],
        isUpdateOnly: true,
      });
    }
    case NEW_ORDER: {
      const { posTerminalId } = action.payload;
      return addNewOrderAndSetCurrent({ applicationState, posTerminalId });
    }
    case SET_CURRENT_ORDER: {
      const { order_uuid } = action.payload;
      return setCurrentOrder({ applicationState, order_uuid });
    }
    case ADD_ORDER_LINE: {
      return addOrderLine({ applicationState, newOrderLineRequest: action.payload });
    }
    case REMOVE_ORDER_LINE: {
      const { order_uuid, line_uuid } = action.payload;
      return removeOrderLine({ applicationState, order_uuid, line_uuid });
    }
    case SET_SELECTED_ORDER_LINE: {
      const { order_uuid, selectedLineUUID } = action.payload;
      return setSelectedOrderLine({ applicationState, order_uuid, selectedLineUUID });
    }

    //
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
  }

  return applicationState;
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

const changeOrdersStateInApplicationState = ({ applicationState, mapper }) => {
  const ordersState = applicationState.orders;
  const ordersStateChanged = mapper(ordersState);
  if (ordersStateChanged === undefined || ordersState === ordersStateChanged) {
    return applicationState;
  } else {
    return { ...applicationState, orders: ordersStateChanged };
  }
};

const changeOrderInApplicationState = ({ applicationState, order_uuid, mapper }) => {
  return changeOrdersStateInApplicationState({
    applicationState,
    mapper: (ordersState) => {
      return changeOrder({ ordersState, order_uuid, mapper });
    },
  });
};

const changeOrder = ({ ordersState, order_uuid, mapper }) => {
  const order = ordersState.byUUID[order_uuid];
  if (!order) {
    throw 'No order found for ' + order_uuid;
  }

  const orderChanged = mapper(order);
  if (order === orderChanged) {
    return ordersState;
  } else {
    return {
      ...ordersState,
      byUUID: { ...ordersState.byUUID, [orderChanged.uuid]: orderChanged },
    };
  }
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

const syncOrdersFromSource = ({ applicationState, posTerminalId, fromOrdersArray, missingIds, isUpdateOnly }) => {
  return changeOrdersStateInApplicationState({
    applicationState,
    mapper: (ordersState) =>
      syncOrdersStateFromSource({
        ordersState,
        posTerminalId,
        fromOrdersArray,
        missingIds,
        isUpdateOnly,
      }),
  });
};

const syncOrdersStateFromSource = ({ ordersState, posTerminalId, fromOrdersArray, missingIds, isUpdateOnly }) => {
  const byUUID = isUpdateOnly ? { ...ordersState.byUUID } : {};

  if (fromOrdersArray?.length > 0) {
    fromOrdersArray.forEach((order) => (byUUID[order.uuid] = recomputeOrderDetails({ order })));
  }

  if (missingIds?.length > 0) {
    missingIds.forEach((missingId) => {
      delete byUUID[missingId];
    });
  }

  // Reset current_uuid if is no longer present in the orders array
  let current_uuid = ordersState.current_uuid;
  if (current_uuid && !byUUID[current_uuid]) {
    current_uuid = null;
  }

  if (!current_uuid) {
    const allOrderIds = Object.keys(byUUID);
    if (allOrderIds.length > 0) {
      current_uuid = allOrderIds[0];
    } else {
      const order = newOrder({ posTerminalId });
      current_uuid = order.uuid;
      byUUID[order.uuid] = order;
    }
  }

  return {
    ...ordersState,
    current_uuid,
    byUUID,
  };
};

const newOrder = ({ posTerminalId }) => {
  return {
    uuid: uuidv4(),
    posTerminalId,
    status: ORDER_STATUS_DRAFTED,
    lines: [],
    payments: [],
  };
};

const addNewOrderAndSetCurrent = ({ applicationState, posTerminalId }) => {
  const order = newOrder({ posTerminalId });

  return changeOrdersStateInApplicationState({
    applicationState,
    mapper: (ordersState) => {
      return {
        ...ordersState,
        current_uuid: order.uuid,
        byUUID: { ...ordersState?.byUUID, [order.uuid]: order },
      };
    },
  });
};

const setCurrentOrder = ({ applicationState, order_uuid }) => {
  return changeOrdersStateInApplicationState({
    applicationState,
    mapper: (ordersState) => {
      if (ordersState.current_uuid === order_uuid) {
        // no change
        return ordersState;
      } else if (!ordersState.byUUID?.[order_uuid]) {
        console.warn(`cannot set order_uuid '${order_uuid}' as current because there no such order`, {
          byUUID: ordersState.byUUID,
        });
        return ordersState;
      } else {
        return {
          ...ordersState,
          current_uuid: order_uuid,
        };
      }
    },
  });
};

const addOrderLine = ({ applicationState, newOrderLineRequest }) => {
  const {
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
  } = newOrderLineRequest;

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
    isNew: true,
  };

  return changeOrderInApplicationState({
    applicationState,
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

const removeOrderLine = ({ applicationState, order_uuid, line_uuid }) => {
  return changeOrderInApplicationState({
    applicationState,
    order_uuid,
    mapper: (order) => remoteOrderLineFromOrder({ order, line_uuid }),
  });
};
const remoteOrderLineFromOrder = ({ order, line_uuid }) => {
  const lines = order.lines.filter((line) => line.uuid !== line_uuid);
  const selectedLineUUID = computeSelectedLineUUID({ selectedLineUUID: order.selectedLineUUID, lines });

  return { ...order, lines, selectedLineUUID };
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

const setSelectedOrderLine = ({ applicationState, order_uuid, selectedLineUUID }) => {
  return changeOrderInApplicationState({
    applicationState,
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

const changePayment = ({ applicationState, order_uuid, payment_uuid, mapper }) => {
  return changeOrderInApplicationState({
    applicationState,
    order_uuid,
    mapper: (order) => {
      const orderPayments = order.payments ?? [];
      const paymentBeforeChange = orderPayments.find((payment) => payment.uuid === payment_uuid);
      const paymentChanged = mapper(paymentBeforeChange);

      let orderPaymentsChanged;
      if (paymentBeforeChange === paymentChanged) {
        // no change:
        return order;
      } else if (paymentBeforeChange == null) {
        if (paymentChanged == null) {
          // no change:
          return order;
        } else {
          // new payment:
          orderPaymentsChanged = [...orderPayments, paymentChanged];
        }
      } else {
        if (paymentChanged == null) {
          // delete payment
          orderPaymentsChanged = orderPayments.filter((payment) => payment.uuid !== payment_uuid);
        } else {
          // replace payment
          orderPaymentsChanged = orderPayments.map((payment) => {
            return payment.uuid === payment_uuid ? paymentChanged : payment;
          });
        }
      }

      let orderChanged = { ...order, payments: orderPaymentsChanged };
      orderChanged = updatePaymentAmounts(orderChanged);
      return orderChanged;
    },
  });
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

const addPayment = ({ applicationState, order_uuid, paymentMethod, amount }) => {
  const newPayment = {
    uuid: uuidv4(),
    paymentMethod,
    amount,
    cashTenderedAmount: 0,
    status: PAYMENT_STATUS_NEW,
    allowCheckout: false,
    allowDelete: true,
    allowRefund: false,
  };
  return changePayment({
    applicationState,
    order_uuid,
    payment_uuid: newPayment.uuid,
    mapper: () => newPayment,
  });
};

const removePayment = ({ applicationState, order_uuid, payment_uuid }) => {
  return changePayment({
    applicationState,
    order_uuid,
    payment_uuid,
    mapper: () => null,
  });
};
