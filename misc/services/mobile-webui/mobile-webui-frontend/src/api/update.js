import axios from 'axios';
import { serverVersionUrl } from '../constants';

/**
 * @summary Get the information about the latest version. Note: Had to do that splitting due to the info endpoint that resides one level up (!)
 */
export function heartBeat() {
  return axios.get(serverVersionUrl);
}
