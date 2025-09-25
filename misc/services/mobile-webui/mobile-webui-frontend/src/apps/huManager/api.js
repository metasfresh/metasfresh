import axios from 'axios';
import { apiBasePath } from '../../constants';
import { toUrl, unboxAxiosResponse } from '../../utils';
import { toQRCodeString } from '../../utils/qrCode/hu';
import { toLocatorQRCodeString } from '../../utils/qrCode/locator';

const huAPIBasePath = `${apiBasePath}/material/handlingunits`;
const huManagerAPIBasePath = `${apiBasePath}/material/handlingunits/hu-manager`;

export const getHUByQRCode = (qrCode) => {
  return axios
    .post(`${huManagerAPIBasePath}/byQRCode`, { qrCode })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const disposeHU = ({ huId, reasonCode }) => {
  return axios.post(toUrl(`${huManagerAPIBasePath}/dispose`, { huId, reasonCode })).then(unboxAxiosResponse);
};

export const getDisposalReasonsArray = () => {
  return axios
    .get(`${huManagerAPIBasePath}/disposalReasons`)
    .then(unboxAxiosResponse)
    .then((response) => response.reasons);
};

/**
 * @returns {Promise<T>} handling unit info
 */
export const moveHU = ({ huId, huQRCode, targetQRCode, numberOfTUs }) => {
  return axios
    .post(`${huManagerAPIBasePath}/move`, {
      huId,
      huQRCode: toQRCodeString(huQRCode),
      targetQRCode: toQRCodeString(targetQRCode),
      numberOfTUs: numberOfTUs,
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

/**
 * @returns {Promise<T>} handling unit info
 */
export function getAllowedClearanceStatusesRequest({ huId }) {
  return axios
    .get(toUrl(`${huManagerAPIBasePath}/allowedClearanceStatuses`, { huId }))
    .then(unboxAxiosResponse)
    .then((response) => response.clearanceStatuses);
}

export function setClearanceStatusRequest({ huId, clearanceNote = null, clearanceStatus }) {
  return axios.put(`${huManagerAPIBasePath}/clearance`, {
    huIdentifier: { metasfreshId: huId },
    clearanceStatus,
    clearanceNote,
  });
}

export const moveBulkHUs = ({ huQRCodes, targetQRCode }) => {
  return axios
    .post(`${huManagerAPIBasePath}/bulk/move`, {
      huQRCodes: huQRCodes,
      targetQRCode: toQRCodeString(targetQRCode),
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const changeQty = ({
  huId,
  huQRCode,
  description,
  qty,
  locatorQRCode,
  setBestBeforeDate,
  bestBeforeDate,
  setLotNo,
  lotNo,
}) => {
  return axios
    .post(`${huManagerAPIBasePath}/changeQty`, {
      huId,
      huQRCode: toQRCodeString(huQRCode),
      qty,
      description,
      locatorQRCode: locatorQRCode ? toLocatorQRCodeString(locatorQRCode) : null,
      splitOneIfAggregated: true,
      setBestBeforeDate,
      bestBeforeDate: setBestBeforeDate ? bestBeforeDate : null,
      setLotNo,
      lotNo: setLotNo ? lotNo : null,
    })
    .then(unboxAxiosResponse)
    .then((response) => response.result);
};

export const printHULabels = ({ huId, huLabelProcessId, nrOfCopies }) => {
  return axios
    .post(`${huManagerAPIBasePath}/huLabels/print`, {
      huId: huId,
      huLabelProcessId: huLabelProcessId,
      nrOfCopies: nrOfCopies,
    })
    .then(unboxAxiosResponse);
};

export const getPrintingOptions = () => {
  return axios.get(`${huManagerAPIBasePath}/huLabels/printingOptions`).then(unboxAxiosResponse);
};

export const getHUsByDisplayableQRCode = (displayableQRCode) => {
  return axios.get(`${huAPIBasePath}/byDisplayableQrCode/${displayableQRCode}`).then(unboxAxiosResponse);
};

export const listHUsByQRCode = ({ qrCode, upperLevelLocatingQrCode }) => {
  return axios.post(`${huAPIBasePath}/list/byQRCode`, { qrCode, upperLevelLocatingQrCode }).then(unboxAxiosResponse);
};
