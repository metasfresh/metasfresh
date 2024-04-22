import axios from 'axios';

export function getUserSession() {
  return axios.get(`${config.API_URL}/userSession`, {
    validateStatus: (status) => {
      // returning true so that we can get the error status
      return (status >= 200 && status < 300) || status === 502;
    },
  });
}

export function getUserLang() {
  return axios.get(`${config.API_URL}/userSession/language`);
}
