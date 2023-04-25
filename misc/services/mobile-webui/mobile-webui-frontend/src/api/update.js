import axios from 'axios';
import { apiBasePath } from '../constants';

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
    .then((response) => response.data);
}
