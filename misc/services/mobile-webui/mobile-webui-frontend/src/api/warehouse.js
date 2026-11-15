import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { isLocatorQRCodeString, parseLocatorQRCodeString } from '../utils/qrCode/locator';

const warehouseAPIBase = `${apiBasePath}/material/warehouses`;

const lookUpLocatorOnServer = ({ scannedBarcode }) => {
  return axios.post(`${warehouseAPIBase}/resolveLocator`, { scannedBarcode }).then(unboxAxiosResponse);
};

/** Scanned code -> locator QR code ({ code, displayable, warehouseId, locatorId }); rejects when the code is no locator. */
export const toLocatorQRCode = ({ scannedBarcode }) => {
  if (isLocatorQRCodeString(scannedBarcode)) {
    return Promise.resolve(parseLocatorQRCodeString(scannedBarcode));
  }

  return lookUpLocatorOnServer({ scannedBarcode }).then((response) => {
    if (response.error) {
      throw response.error;
    } else {
      return parseLocatorQRCodeString(response.locator.qrCode);
    }
  });
};
