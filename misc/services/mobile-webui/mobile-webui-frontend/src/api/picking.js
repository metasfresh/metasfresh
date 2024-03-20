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
  catchWeight,
  pickWholeTU,
  checkIfAlreadyPacked,
  setBestBeforeDate,
  bestBeforeDate,
  setLotNo,
  lotNo,
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
    catchWeight,
    pickWholeTU,
    checkIfAlreadyPacked,
    setBestBeforeDate,
    bestBeforeDate,
    setLotNo,
    lotNo,
  });
};

export const postStepUnPicked = ({ wfProcessId, activityId, lineId, stepId, huQRCode, unpickToTargetQRCode }) => {
  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'UNPICK',
    huQRCode,
    unpickToTargetQRCode,
  });
};

const postEvent = (event) => {
  return axios.post(`${apiBasePath}/picking/event`, event).then((response) => unboxAxiosResponse(response));
};

export const closePickingJobLine = ({ wfProcessId, lineId }) => {
  return axios
    .post(`${apiBasePath}/picking/closeLine`, { wfProcessId, pickingLineId: lineId })
    .then((response) => unboxAxiosResponse(response));
};

export const openPickingJobLine = ({ wfProcessId, lineId }) => {
  return axios
    .post(`${apiBasePath}/picking/openLine`, { wfProcessId, pickingLineId: lineId })
    .then((response) => unboxAxiosResponse(response));
};
