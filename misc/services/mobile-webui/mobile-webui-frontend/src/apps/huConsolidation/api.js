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

export const consolidate = ({ wfProcessId, fromPickingSlotQRCode }) => {
  return axios
    .post(toUrl(`${apiBasePath}/mobile/huConsolidation/job/${wfProcessId}/consolidate`), {
      wfProcessId,
      fromPickingSlotQRCode,
    })
    .then((response) => unboxAxiosResponse(response));
};
