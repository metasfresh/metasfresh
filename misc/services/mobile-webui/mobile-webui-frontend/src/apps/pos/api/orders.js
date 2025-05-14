import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { toUrl, unboxAxiosResponse } from '../../../utils';
import { useEffect } from 'react';
import * as ws from '../../../utils/websocket';

const posApiBase = `${apiBasePath}/pos`;

export const getOpenOrders = ({ posTerminalId, ids } = {}) => {
  const queryParams = { posTerminalId };
  if (ids?.length > 0) {
    queryParams.ids = ids.join(',');
  }

  return axios.get(toUrl(`${posApiBase}/orders`, queryParams)).then((response) => unboxAxiosResponse(response));
};

export const updateOrder = ({ order }) => {
  return axios.post(`${posApiBase}/orders`, order).then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToDraft = ({ posTerminalId, order_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/draft`, { posTerminalId, order_uuid })
    .then((response) => unboxAxiosResponse(response));
};
export const changeOrderStatusToWaitingPayment = ({ posTerminalId, order_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/waitingPayment`, { posTerminalId, order_uuid })
    .then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToVoid = ({ posTerminalId, order_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/void`, { posTerminalId, order_uuid })
    .then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToComplete = ({ posTerminalId, order_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/complete`, { posTerminalId, order_uuid })
    .then((response) => unboxAxiosResponse(response));
};
export const changeOrderStatusToClosed = ({ posTerminalId, order_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/close`, { posTerminalId, order_uuid })
    .then((response) => unboxAxiosResponse(response));
};

export const useOrdersWebsocket = ({ posTerminalId, onWebsocketMessage }) => {
  useEffect(() => {
    let client;
    const topic = `/pos/orders/${posTerminalId}`;

    if (posTerminalId) {
      client = ws.connectAndSubscribe({
        topic,
        debug: !!window?.debug_ws,
        onWebsocketMessage: (message) => {
          onWebsocketMessage(JSON.parse(message.body));
        },
      });
      console.log(`WS connected to ${topic}`, { client });
    } else {
      console.log('Skip connecting to WS because terminalId is not valid', { posTerminalId });
    }

    return () => {
      if (client) {
        ws.disconnectClient(client);
        client = null;
        console.log(`WS disconnected from ${topic}`);
      }
    };
  }, [posTerminalId]);
};

export const checkoutPayment = ({ posTerminalId, order_uuid, payment_uuid, cardPayAmount, cashTenderedAmount }) => {
  return axios
    .post(`${posApiBase}/orders/checkoutPayment`, {
      posTerminalId,
      order_uuid,
      payment_uuid,
      cardPayAmount,
      cashTenderedAmount,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const refundPayment = ({ posTerminalId, order_uuid, payment_uuid }) => {
  return axios
    .post(`${posApiBase}/orders/refundPayment`, { posTerminalId, order_uuid, payment_uuid })
    .then((response) => unboxAxiosResponse(response));
};

export const getReceiptPdf = ({ order_uuid }) => {
  return axios.get(toUrl(`${posApiBase}/orders/receipt/receipt.pdf`, { id: order_uuid }), {
    responseType: 'arraybuffer',
    validateStatus: false,
  });
};
