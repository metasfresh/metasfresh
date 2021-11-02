import { UPDATE_PICKING_STEP_QTY, UPDATE_PICKING_STEP_SCANNED_HU_BARCODE } from '../constants/PickingActionTypes';

/**
 * @method switchoffLinesVisibility
 * @summary sets the lines visibility to `false` and by doing this the steps will be visible
 */
export function updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode }) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: { wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode },
  };
}

export function updatePickingStepScannedHUBarcode({ wfProcessId, activityId, lineId, stepId, scannedHUBarcode }) {
  return {
    type: UPDATE_PICKING_STEP_SCANNED_HU_BARCODE,
    payload: { wfProcessId, activityId, lineId, stepId, scannedHUBarcode },
  };
}
