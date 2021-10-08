import axios from 'axios';

/**
 * @method getLaunchers
 * @summary Get the list of available launchers
 * @param {object} `token` - The token to use for authentication
 * @returns
 */
export function getLaunchers({ token }) {
  return axios.get(`${window.config.SERVER_URL}/userWorkflows/launchers`, {
    headers: {
      Authorization: token,
      accept: 'application/json',
    },
  });
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
export function continueWorkflowRequest(wfProcessId) {
  return axios.get(`${window.config.SERVER_URL}/userWorkflows/wfProcess/${wfProcessId}`);
}
