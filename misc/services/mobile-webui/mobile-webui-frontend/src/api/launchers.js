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
export function startWorkflowRequest({ wfProviderId, wfParameters }) {
  return axios.post(`${window.config.API_URL}/userWorkflows/wfProcess/start`, { wfProviderId, wfParameters });
}

/**
 * @method startWorkflow
 * @summary Continue a workflow from the launchers list
 */
export function continueWorkflowRequest(wfProviderId) {
  return axios.get(`${window.config.API_URL}/userWorkflows/wfProcess/${wfProviderId}`);
}
