import axios from 'axios';

export function postQtyPicked({ wfProcessId, activityId, stepId, qtyPicked }) {
  return axios.post(`${config.SERVER_URL}/picking/events`, {
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
