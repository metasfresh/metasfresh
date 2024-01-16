import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';

/**
 * @summary Get the list of available applications
 */
export function getApplications() {
  return axios.get(`${apiBasePath}/userWorkflows/apps`).then((response) => unboxAxiosResponse(response));
}
