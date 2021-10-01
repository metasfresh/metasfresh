import axios from 'axios';

/**
 * @method scannedBarcode
 * @summary Post the scanned barcode
 * @param {object} `token` - The token to use for authentication, `barcode` - The barcode to post
 * @returns
 */
export function postScannedBarcode({ token, detectedCode, wfProcessId, activityId }) {
  return axios.post(
    `${window.config.SERVER_URL}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/scannedBarcode`,
    { barcode: detectedCode },
    {
      headers: {
        Authorization: token,
        accept: 'application/json',
      },
    }
  );
}
