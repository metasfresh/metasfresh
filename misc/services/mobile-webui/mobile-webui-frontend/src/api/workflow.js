import axios from 'axios';
import { toUrl, unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';

/**
 * @summary Get the current state of a workflow process (e.g. to show the real state after a failed action)
 */
export const getWFProcess = ({ wfProcessId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}`))
    .then((response) => unboxAxiosResponse(response));
};
