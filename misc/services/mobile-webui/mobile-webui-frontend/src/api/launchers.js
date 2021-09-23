import axios from 'axios';

/**
 * @method getLaunchers
 * @summary Get the list of available launchers
 * @param {object} `token` - The token to use for authentication 
 * @returns 
 */
export function getLaunchers({ token }) {
  return axios.get(`${process.env.API_URL}/app/api/v2/userWorkflows/launchers`, {
    headers: {
      'Authorization': token,
      'accept': 'application/json',
    }
  });
}
