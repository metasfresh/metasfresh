import axios from 'axios';
import { apiBasePath } from '../constants';

/**
 * @summary Get the list of available launchers
 */
export function getLaunchers() {
  return axios.get(`${apiBasePath}/userWorkflows/launchers`);
}

/**
 * @method startWorkflowRequest
 * @summary Start a workflow from the launchers list
 */
export function startWorkflowRequest({ wfParameters }) {
  return axios.post(`${apiBasePath}/userWorkflows/wfProcess/start`, { wfParameters });
}

/**
 * @method startWorkflow
 * @summary Continue a workflow from the launchers list
 */
export function getWorkflowRequest(wfProcessId) {
  return axios.get(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}`);
}
