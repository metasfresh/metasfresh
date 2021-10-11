import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(`${config.SERVER_URL}/auth`, {
    username,
    password,
  });
}
