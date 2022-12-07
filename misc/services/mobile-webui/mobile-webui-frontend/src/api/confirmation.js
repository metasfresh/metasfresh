import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

/**
 * @method userConfirmation
 * @summary Post confirmation to the server after a dialog is shown (see ConfirmationButton/ConfirmActivity component)
 * @param {object} `token` - The token to use for authentication
 * @returns
 */
export function postUserConfirmation({ wfProcessId, activityId }) {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/userConfirmation`)
    .then((response) => unboxAxiosResponse(response));
}
