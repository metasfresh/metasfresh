import axios from 'axios';
import { apiBasePath } from '../constants';

export function postStepPicked({
  wfProcessId,
  activityId,
  stepId,
  huQRCode,
  qtyPicked,
  qtyRejected,
  qtyRejectedReasonCode,
}) {
  return axios.post(`${apiBasePath}/picking/events`, {
    events: [
      {
        wfProcessId,
        wfActivityId: activityId,
        pickingStepId: stepId,
        type: 'PICK',
        huQRCode,
        qtyPicked,
        qtyRejectedReasonCode,
        qtyRejected,
      },
    ],
  });
}

export function postStepUnPicked({ wfProcessId, activityId, stepId, huQRCode }) {
  return axios.post(`${apiBasePath}/picking/events`, {
    events: [
      {
        wfProcessId,
        wfActivityId: activityId,
        pickingStepId: stepId,
        type: 'UNPICK',
        huQRCode,
      },
    ],
  });
}
