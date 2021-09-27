import axios from 'axios';

/**
 * @method userConfirmation
 * @summary Post confirmation to the server after a dialog is shown (see ConfirmationButton/ConfirmActivity component)
 * @param {object} `token` - The token to use for authentication
 * @returns
 */
export function userConfirmation({ token }) {
  return axios.post(`${window.config.API_URL}/userWorkflows/wfProcess/wfProcessId/wfActivityId/userConfirmation`, {
    headers: {
      Authorization: token,
      accept: 'application/json',
    },
  });
}
