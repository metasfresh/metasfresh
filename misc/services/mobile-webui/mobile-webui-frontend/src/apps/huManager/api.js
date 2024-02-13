import axios from 'axios';
import { apiBasePath } from '../../constants';
import { unboxAxiosResponse } from '../../utils';
import { toQRCodeString } from '../../utils/qrCode/hu';

const huAPIBasePath = `${apiBasePath}/material/handlingunits`;

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

/**
 * @returns {Promise<T>} handling unit info
 */
export function getAllowedClearanceStatusesRequest({ huId }) {
  return axios
    .get(`${huAPIBasePath}/byId/${huId}/allowedClearanceStatuses`)
    .then(unboxAxiosResponse)
    .then((response) => response.clearanceStatuses);
}

export function setClearanceStatusRequest({ huId, clearanceNote = null, clearanceStatus }) {
  return axios.put(`${huAPIBasePath}/clearance`, {
    huIdentifier: { metasfreshId: huId },
    clearanceStatus,
    clearanceNote,
  });
}

export async function assignExternalLotNumber({ huId, qrCode }) {
  return axios
    .put(`${huAPIBasePath}/byId/${huId}/externalLotNumber`, {
      qrCode,
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
}

export const moveBulkHUs = ({ huQRCodes, targetQRCode }) => {
  return axios
    .post(`${huAPIBasePath}/bulk/move`, {
      huQRCodes: huQRCodes,
      targetQRCode: toQRCodeString(targetQRCode),
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const changeQty = ({ huQRCode, description, qty }) => {
  return axios
    .put(`${huAPIBasePath}/qty`, {
      huQRCode: toQRCodeString(huQRCode),
      qty: qty,
      description: description,
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const printHULabels = ({ huQRCode, huLabelProcessId, nrOfCopies }) => {
  return axios
    .post(`${huAPIBasePath}/huLabels/print`, {
      huQRCode: toQRCodeString(huQRCode),
      huLabelProcessId: huLabelProcessId,
      nrOfCopies: nrOfCopies,
    })
    .then(unboxAxiosResponse);
};

export const getPrintingOptions = () => {
  return axios.get(`${huAPIBasePath}/huLabels/printingOptions`).then(unboxAxiosResponse);
};

export const getHUsByDisplayableQRCode = (displayableQRCode) => {
  return axios.get(`${huAPIBasePath}/byDisplayableQrCode/${displayableQRCode}`).then(unboxAxiosResponse);
};
