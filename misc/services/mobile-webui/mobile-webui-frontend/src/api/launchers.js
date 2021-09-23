import axios from 'axios';

export function getLaunchers({ token }) {
  return axios.get(`${process.env.API_URL}/app/api/v2/userWorkflows/launchers`, {
    headers: {
      'Authorization': token,
      'accept': 'application/json',
    }
  });
}
