import { UPDATE_PICKING_STEP_QTY } from '../constants/PickingActionTypes';

export function updatePickingStepQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  scannedHUBarcode,
  qtyPicked,
  qtyRejectedReasonCode,
}) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: { wfProcessId, activityId, lineId, stepId, scannedHUBarcode, qtyPicked, qtyRejectedReasonCode },
  };
}
