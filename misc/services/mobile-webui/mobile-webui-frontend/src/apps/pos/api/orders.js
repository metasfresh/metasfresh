import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const getOpenOrdersArray = () => {
  return axios
    .get(`${apiBasePath}/pos/orders`)
    .then((response) => unboxAxiosResponse(response))
    .then((data) => data.list);
};

export const updateOrder = ({ order }) => {
  return axios.post(`${apiBasePath}/pos/orders`, order).then((response) => unboxAxiosResponse(response));
};

export const voidOrder = ({ order_uuid }) => {
  return axios.post(`${apiBasePath}/pos/orders/${order_uuid}/void`).then((response) => unboxAxiosResponse(response));
};
