import axios from 'axios';

import {
    getLaunchers
} from './launchers';

function loginRequest(username, password) {
  return axios.post(`/app/api/v2/auth`, {
    username,
    password,
  });
}

export { getLaunchers, loginRequest };
