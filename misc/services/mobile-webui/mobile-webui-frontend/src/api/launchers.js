import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';

/**
 * @summary Get the list of available launchers
 */
export function getLaunchers(applicationId) {
  return axios
    .get(`${apiBasePath}/userWorkflows/launchers`, { params: { applicationId } })
    .then((response) => unboxAxiosResponse(response));
}

/**
 * @method startWorkflowRequest
 * @summary Start a workflow from the launchers list
 * @returns wfProcess
 */
export function startWorkflowRequest({ wfParameters }) {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/start`, { wfParameters })
    .then((response) => unboxAxiosResponse(response));
}

/**
 * @method startWorkflow
 * @summary Continue a workflow from the launchers list
 * @returns wfProcess
 */
export function getWorkflowRequest(wfProcessId) {
  return axios
    .get(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}`)
    .then((response) => unboxAxiosResponse(response));
}

/**
 * @method abortWorkflow
 * @summary Abort a workflow
 * @returns wfProcess
 */
export function abortWorkflowRequest(wfProcessId) {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/abort`)
    .then((response) => unboxAxiosResponse(response));
}
