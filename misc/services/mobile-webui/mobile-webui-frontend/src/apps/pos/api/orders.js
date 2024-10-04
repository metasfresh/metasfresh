import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { toUrl, unboxAxiosResponse } from '../../../utils';
import { useEffect } from 'react';
import * as ws from '../../../utils/websocket';

export const getOpenOrders = ({ ids } = {}) => {
  const queryParams = {};
  if (ids?.length > 0) {
    queryParams.ids = ids.join(',');
  }

  return axios.get(toUrl(`${apiBasePath}/pos/orders`, queryParams)).then((response) => unboxAxiosResponse(response));
};

export const updateOrder = ({ order }) => {
  return axios.post(`${apiBasePath}/pos/orders`, order).then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToDraft = ({ order_uuid }) => {
  return axios.post(`${apiBasePath}/pos/orders/${order_uuid}/draft`).then((response) => unboxAxiosResponse(response));
};
export const changeOrderStatusToWaitingPayment = ({ order_uuid }) => {
  return axios
    .post(`${apiBasePath}/pos/orders/${order_uuid}/waitingPayment`)
    .then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToVoid = ({ order_uuid }) => {
  return axios.post(`${apiBasePath}/pos/orders/${order_uuid}/void`).then((response) => unboxAxiosResponse(response));
};

export const changeOrderStatusToComplete = ({ order_uuid }) => {
  return axios
    .post(`${apiBasePath}/pos/orders/${order_uuid}/complete`)
    .then((response) => unboxAxiosResponse(response));
};

export const useOrdersWebsocket = ({ terminalId, onWebsocketMessage }) => {
  useEffect(() => {
    let client;
    const topic = `/pos/orders/${terminalId}`;

    client = ws.connectAndSubscribe({
      topic,
      debug: !!window?.debug_ws,
      onWebsocketMessage: (message) => {
        onWebsocketMessage(JSON.parse(message.body));
      },
    });
    console.log(`WS connected to ${topic}`, { client });

    return () => {
      if (client) {
        ws.disconnectClient(client);
        client = null;
        console.log(`WS disconnected from ${topic}`);
      }
    };
  }, [terminalId]);
};
