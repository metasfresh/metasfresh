import axios from 'axios';

/**
 * @summary Get the list of available launchers
 */
export function getLaunchers() {
  return axios.get(`${window.config.SERVER_URL}/userWorkflows/launchers`);
}

/**
 * @method startWorkflowRequest
 * @summary Start a workflow from the launchers list
 */
export function startWorkflowRequest({ wfParameters }) {
  return axios.post(`${window.config.SERVER_URL}/userWorkflows/wfProcess/start`, { wfParameters });
}

/**
 * @method startWorkflow
 * @summary Continue a workflow from the launchers list
 */
export function getWorkflowRequest(wfProcessId) {
  return axios.get(`${window.config.SERVER_URL}/userWorkflows/wfProcess/${wfProcessId}`);
}
