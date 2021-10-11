import axios from 'axios';
import { apiBasePath } from '../constants';

export function postQtyPicked({ wfProcessId, activityId, stepId, qtyPicked, qtyRejectedReasonCode }) {
  return axios.post(`${apiBasePath}/picking/events`, {
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
