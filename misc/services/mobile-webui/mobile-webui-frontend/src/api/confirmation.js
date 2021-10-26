import axios from 'axios';
import { apiBasePath } from '../constants';

/**
 * @method userConfirmation
 * @summary Post confirmation to the server after a dialog is shown (see ConfirmationButton/ConfirmActivity component)
 * @param {object} `token` - The token to use for authentication
 * @returns
 */
export function userConfirmation({ wfProcessId, activityId }) {
  return axios.post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/userConfirmation`);
}
