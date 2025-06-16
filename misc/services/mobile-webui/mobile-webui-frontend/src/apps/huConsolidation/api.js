import axios from 'axios';
import { toUrl, unboxAxiosResponse } from '../../utils';
import { apiBasePath } from '../../constants';

export const getAvailableTargets = ({ wfProcessId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/target/available`))
    .then((response) => unboxAxiosResponse(response));
};

export const setTarget = ({ wfProcessId, target }) => {
  return axios
    .post(toUrl(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/target`), target)
    .then((response) => unboxAxiosResponse(response));
};

export const closeTarget = ({ wfProcessId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/target/close`))
    .then((response) => unboxAxiosResponse(response));
};

export const consolidate = ({ wfProcessId, fromPickingSlotQRCode, huId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/consolidate`), {
      wfProcessId,
      fromPickingSlotQRCode,
      huId,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const getPickingSlotContent = ({ wfProcessId, pickingSlotId }) => {
  return axios
    .get(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/pickingSlot/${pickingSlotId}`)
    .then((response) => unboxAxiosResponse(response));
};
