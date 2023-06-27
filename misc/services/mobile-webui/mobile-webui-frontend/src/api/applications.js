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
