import { SET_SCANNED_BARCODE } from '../constants/ScanActionTypes';

/**
 * @method setScannedBarcode
 * @summary update the scannedCode for a `common/scanBarcode` activity type with the scannedBarcode
 */
export function setScannedBarcode({ wfProcessId, activityId, scannedBarcode }) {
  return {
    type: SET_SCANNED_BARCODE,
    payload: { wfProcessId, activityId, scannedBarcode },
  };
}
