import axios from 'axios';

export function postQtyPicked({ wfProcessId, activityId, stepId, qtyPicked, qtyRejectedReasonCode }) {
  return axios.post(`${window.config.SERVER_URL}/picking/events`, {
    events: [
      {
        wfProcessId,
        wfActivityId: activityId,
        pickingStepId: stepId,
        type: 'PICK',
        qtyPicked: qtyPicked,
        qtyRejectedReasonCode: qtyRejectedReasonCode,
      },
    ],
  });
}
