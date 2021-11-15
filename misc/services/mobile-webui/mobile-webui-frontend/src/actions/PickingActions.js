import { UPDATE_PICKING_STEP_QTY, UPDATE_ALT_PICKING_STEP_QTY } from '../constants/PickingActionTypes';

export function updatePickingStepQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  altStepId,
  scannedHUBarcode,
  qtyPicked,
  qtyRejectedReasonCode,
  qtyRejected,
}) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      scannedHUBarcode,
      qtyPicked,
      qtyRejectedReasonCode,
      qtyRejected,
    },
  };
}

export function updateAltPickingStepQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  altStepId,
  scannedHUBarcode,
  qtyPicked,
  qtyRejectedReasonCode,
}) {
  return {
    type: UPDATE_ALT_PICKING_STEP_QTY,
    payload: { wfProcessId, activityId, lineId, stepId, altStepId, scannedHUBarcode, qtyPicked, qtyRejectedReasonCode },
  };
}
