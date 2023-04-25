import axios from 'axios';
import { apiBasePath } from '../constants';

export function loginRequest(username, password) {
  return axios.post(`${apiBasePath}/auth`, {
    username,
    password,
  });
}
