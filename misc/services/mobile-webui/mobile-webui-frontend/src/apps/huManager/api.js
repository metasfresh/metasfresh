import axios from 'axios';
import { apiBasePath } from '../../constants';
import { unboxAxiosResponse } from '../../utils';
import { toQRCodeString } from '../../utils/huQRCodes';

const huAPIBasePath = `${apiBasePath}/hu`;

export const getHUByQRCode = (qrCode) => {
  return axios
    .post(`${huAPIBasePath}/byQRCode`, { qrCode })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const disposeHU = ({ huId, reasonCode }) => {
  return axios.post(`${huAPIBasePath}/byId/${huId}/dispose?reasonCode=${reasonCode}`).then(unboxAxiosResponse);
};

export const getDisposalReasonsArray = () => {
  return axios
    .get(`${huAPIBasePath}/disposalReasons`)
    .then(unboxAxiosResponse)
    .then((response) => response.reasons);
};

/**
 * @returns {Promise<T>} handling unit info
 */
export const moveHU = ({ huId, huQRCode, targetQRCode }) => {
  return axios
    .post(`${huAPIBasePath}/move`, {
      huId,
      huQRCode: toQRCodeString(huQRCode),
      targetQRCode: toQRCodeString(targetQRCode),
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};
