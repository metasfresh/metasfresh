import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

const DEFAULT_TIMEOUT_MILLIS = 20000;

/**
 * @method userConfirmation
 * @summary Post confirmation to the server after a dialog is shown (see ConfirmationButton/ConfirmActivity component)
 *
 * The Zebra scanner on flaky wifi regularly drops the TCP connection mid-roam; without an explicit timeout,
 * axios hangs forever and the operator is left with no signal that their "Fertigstellen" press didn't reach
 * the backend (me03#29027). The timeout ensures the promise eventually rejects so the UI can surface the
 * failure and let the operator retry.
 */
export function postUserConfirmation({ wfProcessId, activityId, timeoutMillis }) {
  const timeout = Number.isFinite(timeoutMillis) && timeoutMillis > 0 ? timeoutMillis : DEFAULT_TIMEOUT_MILLIS;
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/userConfirmation`, null, { timeout })
    .then((response) => unboxAxiosResponse(response));
}
