import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postStepPicked = ({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  huQRCode,
  qtyPicked,
  qtyRejected,
  qtyRejectedReasonCode,
}) => {
  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'PICK',
    huQRCode,
    qtyPicked,
    qtyRejectedReasonCode,
    qtyRejected,
  });
};

export const postStepUnPicked = ({ wfProcessId, activityId, lineId, stepId, huQRCode }) => {
  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'UNPICK',
    huQRCode,
  });
};

const postEvent = (event) => {
  return axios.post(`${apiBasePath}/picking/event`, event).then((response) => unboxAxiosResponse(response));
};
