import axios from 'axios';
import { apiBasePath } from '../constants';

export function postStepDistributionMove({ wfProcessId, activityId, stepId, pickFrom, dropTo }) {
  const data = {
    wfProcessId,
    wfActivityId: activityId,
    distributionStepId: stepId,
  };

  if (pickFrom) {
    data.pickFrom = { ...pickFrom };
  } else if (dropTo) {
    data.dropTo = { ...dropTo };
  }
  return axios.post(`${apiBasePath}/distribution/event`, data);
}
