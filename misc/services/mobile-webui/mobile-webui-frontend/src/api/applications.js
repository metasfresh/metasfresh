import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';

const API = `${apiBasePath}/userWorkflows`;

/**
 * @summary Get the list of available applications
 */
export function getApplications() {
  return axios.get(`${API}/apps`).then((response) => unboxAxiosResponse(response));
}

export function getSettings() {
  return axios.get(`${API}/settings`).then((response) => unboxAxiosResponse(response));
}

export const logErrorToBackend = (error, info) => {
  console.log('Sending error to backend...', { error, info });

  let message = error?.message ?? `${error}`;

  axios
    .post(`${API}/errors`, {
      errors: [
        {
          message: message,
          stackTrace: info?.componentStack,
          issueCategory: 'MOBILEUI',
          frontendUrl: window?.location?.href,
        },
      ],
    })
    .catch((postError) => {
      console.warn('Got error while trying to send errors to backend', { postError, error, info });
    });
};
