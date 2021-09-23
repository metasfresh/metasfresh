import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(`/app/api/v2/auth`, {
    username,
    password,
  });
}
