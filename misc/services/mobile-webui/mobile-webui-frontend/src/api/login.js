import axios from 'axios';
import { apiBasePath } from '../constants';

export const loginRequest = (username, password) => {
  return axios.post(`${apiBasePath}/auth`, {
    username,
    password,
  });
};

export const logoutRequest = () => {
  return axios.post(`${apiBasePath}/userWorkflows/logout`);
};
