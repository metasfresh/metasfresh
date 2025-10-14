import axios from 'axios';
import { toUrl, unboxAxiosResponse } from '../../utils';
import { apiBasePath } from '../../constants';

const inventoryBasePath = `${apiBasePath}/mobile/inventory`;

export const resolveLocator = ({ scannedBarcode, wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${inventoryBasePath}/resolveLocator`), { scannedCode: scannedBarcode, wfProcessId, lineId })
    .then((response) => unboxAxiosResponse(response));
};

export const resolveHU = ({ scannedCode, wfProcessId, lineId, locatorQRCode }) => {
  return axios
    .post(toUrl(`${inventoryBasePath}/resolveHU`), { scannedCode, wfProcessId, lineId, locatorQRCode })
    .then((response) => unboxAxiosResponse(response));
};

export const reportInventoryCounting = ({
  wfProcessId,
  lineId,
  scannedCode,
  huId,
  qtyCount,
  lineCountingDone,
  attributes,
}) => {
  return axios
    .post(toUrl(`${inventoryBasePath}/count`), {
      wfProcessId,
      lineId,
      scannedCode,
      huId,
      qtyCount,
      lineCountingDone,
      attributes,
    })
    .then((response) => unboxAxiosResponse(response));
};
