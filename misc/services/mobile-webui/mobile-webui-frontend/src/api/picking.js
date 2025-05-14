import axios from 'axios';
import { apiBasePath } from '../constants';
import { toUrl, unboxAxiosResponse } from '../utils';
import { QTY_REJECTED_REASON_TO_IGNORE_KEY } from '../reducers/wfProcesses';
import { useQuery } from '../hooks/useQuery';
import { PickingTargetType } from '../constants/PickingTargetType';

export const useAvailablePickingTargets = ({ wfProcessId, lineId, type }) => {
  const isTU = type === PickingTargetType.TU;
  const { isPending: isTargetsLoading, data: targets } = useQuery({
    queryKey: [wfProcessId, lineId, type],
    queryFn: () => {
      return getAvailablePickingTargets({ wfProcessId, lineId }).then((data) => (isTU ? data.tuTargets : data.targets));
    },
  });

  return {
    isTargetsLoading,
    targets,
    setPickingTarget: ({ target }) => {
      return isTU
        ? setTUPickingTarget({ wfProcessId, lineId, target })
        : setLUPickingTarget({ wfProcessId, lineId, target });
    },
  };
};

const getAvailablePickingTargets = ({ wfProcessId, lineId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/available`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

export const setLUPickingTarget = ({ wfProcessId, lineId, target }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target`, { lineId }), target)
    .then((response) => unboxAxiosResponse(response));
};

const setTUPickingTarget = ({ wfProcessId, lineId, target }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/tu`, { lineId }), target)
    .then((response) => unboxAxiosResponse(response));
};

export const closePickingTarget = ({ wfProcessId, lineId, type }) => {
  return type === PickingTargetType.TU
    ? closeTUPickingTarget({ wfProcessId, lineId })
    : closeLUPickingTarget({ wfProcessId, lineId });
};

const closeLUPickingTarget = ({ wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/close`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

const closeTUPickingTarget = ({ wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/tu/close`, { lineId }))
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
  const realRejectedQtyReason =
    qtyRejectedReasonCode === QTY_REJECTED_REASON_TO_IGNORE_KEY ? null : qtyRejectedReasonCode;

  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'PICK',
    huQRCode,
    qtyPicked,
    qtyRejectedReasonCode: realRejectedQtyReason,
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

export const getClosedLUs = ({ wfProcessId, lineId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/closed-lu`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};
