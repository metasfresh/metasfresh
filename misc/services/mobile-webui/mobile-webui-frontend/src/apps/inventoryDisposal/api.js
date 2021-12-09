import axios from 'axios';
import { apiBasePath } from '../../constants';
import { unboxAxiosResponse } from '../../utils';

export function getHUByBarcode(huBarcode) {
  return axios
    .get(`${apiBasePath}/hu/byBarcode/${huBarcode}`)
    .then(unboxAxiosResponse)
    .then((response) => response.result);
}

export function disposeHU({ huId, reasonCode }) {
  return axios.post(`${apiBasePath}/hu/byId/${huId}/dispose?reasonCode=${reasonCode}`).then(unboxAxiosResponse);
}

export function getDisposalReasonsArray() {
  return axios
    .get(`${apiBasePath}/hu/disposalReasons`)
    .then(unboxAxiosResponse)
    .then((response) => response.reasons);
}
