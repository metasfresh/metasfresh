import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(`localhost:3000/app/api/v2/auth`, {
    username,
    password,
  });
}
