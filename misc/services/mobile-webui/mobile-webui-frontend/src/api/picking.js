import axios from 'axios';
import { apiBasePath } from '../constants';

export function postStepPicked({ wfProcessId, activityId, stepId, huBarcode, qtyPicked, qtyRejectedReasonCode }) {
  return axios.post(`${apiBasePath}/picking/events`, {
    events: [
      {
        wfProcessId,
        wfActivityId: activityId,
        pickingStepId: stepId,
        type: 'PICK',
        huBarcode: huBarcode,
        qtyPicked: qtyPicked,
        qtyRejectedReasonCode: qtyRejectedReasonCode,
      },
    ],
  });
}

export function postStepUnPicked({ wfProcessId, activityId, stepId, huBarcode }) {
  return axios.post(`${apiBasePath}/picking/events`, {
    events: [
      {
        wfProcessId,
        wfActivityId: activityId,
        pickingStepId: stepId,
        type: 'UNPICK',
        huBarcode: huBarcode,
      },
    ],
  });
}
