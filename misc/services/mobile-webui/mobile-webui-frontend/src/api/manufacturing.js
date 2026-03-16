import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export function postManufacturingReceiveEvent({ wfProcessId, activityId, receiveFrom, pickTo }) {
  return axios
    .post(`${apiBasePath}/manufacturing/event`, {
      wfProcessId,
      wfActivityId: activityId,
      receiveFrom,
      pickTo,
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

export function postManufacturingIssueToLineEvent({ wfProcessId, activityId, issueToLine }) {
  return axios
    .post(`${apiBasePath}/manufacturing/event`, {
      wfProcessId,
      wfActivityId: activityId,
      issueToLine,
    })
    .then((response) => unboxAxiosResponse(response));
}
