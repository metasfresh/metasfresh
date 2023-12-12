import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export function postManufacturingReceiveEvent({ wfProcessId, activityId, receiveFrom }) {
  return axios
    .post(`${apiBasePath}/manufacturing/event`, {
      wfProcessId,
      wfActivityId: activityId,
      receiveFrom,
    })
    .then((response) => unboxAxiosResponse(response));
}

export function postManufacturingIssueEvent({ wfProcessId, activityId, issueTo }) {
  return axios
    .post(`${apiBasePath}/manufacturing/event`, {
      wfProcessId,
      wfActivityId: activityId,
      issueTo,
    })
    .then((response) => unboxAxiosResponse(response));
}
