import axios from 'axios';

/**
 * @method scannedBarcode
 * @summary Post the scanned barcode
 * @param {object} `token` - The token to use for authentication, `barcode` - The barcode to post
 * @returns
 */
export function postScannedBarcode({ token, barcode, wfProcessId, activityId }) {
  return axios.get(
    `${window.config.SERVER_URL}/app/api/v2/userWorkflows/wfProcess/${wfProcessId}/${activityId}/scannedBarcode`,
    {
      headers: {
        Authorization: token,
        accept: 'application/json',
      },
      barcode,
    }
  );
}
