import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { isLocatorQRCodeString, parseLocatorQRCodeString } from '../utils/qrCode/locator';

const warehouseAPIBase = `${apiBasePath}/material/warehouses`;

export const resolveLocatorScannedCode = ({ scannedBarcode }) => {
  return axios.post(`${warehouseAPIBase}/resolveLocator`, { scannedBarcode }).then(unboxAxiosResponse);
};

/**
 * Resolves a scanned code to a locator QR code ({ code, displayable, warehouseId, locatorId }): a locator QR code string is
 * parsed as is, any other code (e.g. a plain locator barcode) is resolved by the server.
 * Rejects with the server's error text when the code is no locator.
 */
export const resolveLocatorQRCode = ({ scannedBarcode }) => {
  if (isLocatorQRCodeString(scannedBarcode)) {
    return Promise.resolve(parseLocatorQRCodeString(scannedBarcode));
  }

  return resolveLocatorScannedCode({ scannedBarcode }).then((response) => {
    if (response.error) {
      throw response.error;
    } else {
      return parseLocatorQRCodeString(response.locator.qrCode);
    }
  });
};
