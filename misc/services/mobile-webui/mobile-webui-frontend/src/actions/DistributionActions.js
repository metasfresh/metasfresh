import { UPDATE_DISTRIBUTION_STEP_QTY } from '../constants/DistributionActionTypes';

export function updateDistributionStepQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  qtyPicked,
  qtyRejectedReasonCode,
  droppedToLocator,
}) {
  return {
    type: UPDATE_DISTRIBUTION_STEP_QTY,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      qtyPicked,
      qtyRejectedReasonCode,
      droppedToLocator,
    },
  };
}
