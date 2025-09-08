import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

/**
 * @return {Promise<string>} latest server version
 */
export function getServerVersion() {
  return axios
    .get(`${apiBasePath}/version`, {
      headers: {
        'Cache-Control': 'no-cache',
      },
    })
    .then((response) => unboxAxiosResponse(response));
}
