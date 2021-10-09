import {
  UPDATE_PICKING_STEP_QTY,
  SET_SCANNED_BARCODE,
  UPDATE_PICKING_STEP_SCANNED_HU_BARCODE,
} from '../constants/ActionTypes';

/**
 * @method switchoffLinesVisibility
 * @summary sets the lines visibility to `false` and by doing this the steps will be visible
 */
export function updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qty }) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: { wfProcessId, activityId, lineId, stepId, qty },
  };
}

export function updatePickingStepScannedHUBarcode({ wfProcessId, activityId, lineId, stepId, scannedHUBarcode }) {
  return {
    type: UPDATE_PICKING_STEP_SCANNED_HU_BARCODE,
    payload: { wfProcessId, activityId, lineId, stepId, scannedHUBarcode },
  };
}

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
