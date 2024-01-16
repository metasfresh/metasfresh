import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export function postDistributionPickFrom({ wfProcessId, activityId, stepId, pickFrom }) {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      distributionStepId: stepId,
      pickFrom: { ...pickFrom },
    })
    .then((response) => unboxAxiosResponse(response));
}

export function postDistributionDropTo({ wfProcessId, activityId, stepId }) {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      distributionStepId: stepId,
      dropTo: {},
    })
    .then((response) => unboxAxiosResponse(response));
}
