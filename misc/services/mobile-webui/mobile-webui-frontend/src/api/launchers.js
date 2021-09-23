import axios from 'axios';

export function getLaunchers() {
  return axios.get(`${config.API_URL}/app/api/v2/userWorkflows/launchers`);
}
