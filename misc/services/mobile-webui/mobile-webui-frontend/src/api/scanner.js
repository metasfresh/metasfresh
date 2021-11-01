import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

/**
 * @method scannedBarcode
 * @summary Post the scanned barcode
 * @param {object} `token` - The token to use for authentication
 * @param wfProcessId
 * @param activityId
 * @param scannedBarcode
 * @returns wfProcess
 */
export function postScannedBarcode({ wfProcessId, activityId, scannedBarcode }) {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/scannedBarcode`, {
      barcode: scannedBarcode,
    })
    .then((response) => unboxAxiosResponse(response));
}
