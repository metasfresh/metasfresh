import axios from 'axios';
import { apiBasePath } from '../constants';

/**
 * @summary Get the information about the latest version. Note: Had to do that splitting due to the info endpoint that resides one level up (!)
 */
export function heartBeat() {
  const apiURLpart = apiBasePath.split('/');
  return axios.get(`${apiURLpart[0]}/${apiURLpart[2]}/app/info`);
}
