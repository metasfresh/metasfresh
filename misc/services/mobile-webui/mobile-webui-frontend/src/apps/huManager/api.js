import axios from 'axios';
import { apiBasePath } from '../../constants';
import { unboxAxiosResponse } from '../../utils';

export function getHUByQRCode(qrCode) {
  return axios
    .post(`${apiBasePath}/hu/byQRCode`, { qrCode })
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
