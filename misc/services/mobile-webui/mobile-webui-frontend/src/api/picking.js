import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';
import { useEffect, useState } from 'react';

export const usePickTargets = ({ wfProcessId }) => {
  const [loading, setLoading] = useState(false);
  const [targets, setTargets] = useState([]);

  useEffect(() => {
    setLoading(true);
    getPickTargets({ wfProcessId })
      .then(setTargets)
      .finally(() => setLoading(false));
  }, [wfProcessId]);

  return {
    isTargetsLoading: loading,
    targets,
  };
};

const getPickTargets = ({ wfProcessId }) => {
  return axios
    .get(`${apiBasePath}/picking/job/${wfProcessId}/target/available`)
    .then((response) => unboxAxiosResponse(response))
    .then((data) => data.targets);
};

export const setPickTarget = ({ wfProcessId, target }) => {
  return axios
    .post(`${apiBasePath}/picking/job/${wfProcessId}/target`, target)
    .then((response) => unboxAxiosResponse(response));
};

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
  isCloseTarget = false,
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
    isCloseTarget,
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
