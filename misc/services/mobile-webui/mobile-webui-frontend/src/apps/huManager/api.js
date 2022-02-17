import axios from 'axios';
import { apiBasePath } from '../../constants';
import { unboxAxiosResponse } from '../../utils';

export const getHUByQRCode = (qrCode) => {
  return axios
    .post(`${apiBasePath}/hu/byQRCode`, { qrCode })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const disposeHU = ({ huId, reasonCode }) => {
  return axios.post(`${apiBasePath}/hu/byId/${huId}/dispose?reasonCode=${reasonCode}`).then(unboxAxiosResponse);
};

export const getDisposalReasonsArray = () => {
  return axios
    .get(`${apiBasePath}/hu/disposalReasons`)
    .then(unboxAxiosResponse)
    .then((response) => response.reasons);
};

/**
 * @returns {Promise<T>} handling unit info
 */
export const moveHU = ({ huId, targetQRCode }) => {
  return axios
    .post(`${apiBasePath}/hu/byId/${huId}/moveTo`, { targetQRCode })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};
