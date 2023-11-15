import { UPDATE_PICKING_STEP_QTY } from '../constants/PickingActionTypes';

// TODO remote this function
export function updatePickingStepQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  altStepId,
  qtyPicked,
  qtyRejected,
  qtyRejectedReasonCode,
}) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
      qtyPicked,
      qtyRejected,
      qtyRejectedReasonCode,
    },
  };
}
