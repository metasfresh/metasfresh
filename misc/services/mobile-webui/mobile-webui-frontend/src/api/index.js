import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(`https://terheggendev.metasfresh.com/app/api/v2/auth`, {
    username,
    password,
  });
}
