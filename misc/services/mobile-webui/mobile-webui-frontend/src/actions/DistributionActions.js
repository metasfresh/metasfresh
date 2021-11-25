import { UPDATE_DISTRIBUTION_PICK_FROM, UPDATE_DISTRIBUTION_DROP_TO } from '../constants/DistributionActionTypes';

export function updateDistributionPickFrom({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  qtyPicked,
  qtyRejectedReasonCode,
}) {
  return {
    type: UPDATE_DISTRIBUTION_PICK_FROM,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      qtyPicked,
      qtyRejectedReasonCode,
    },
  };
}

export function updateDistributionDropTo({ wfProcessId, activityId, lineId, stepId }) {
  return {
    type: UPDATE_DISTRIBUTION_DROP_TO,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
    },
  };
}
