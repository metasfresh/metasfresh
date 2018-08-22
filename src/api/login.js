import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(`${config.API_URL}/login/authenticate`, {
    username,
    password,
  });
}

export function localLoginRequest() {
  return axios.get(`${config.API_URL}/login/isLoggedIn`);
}

export function loginCompletionRequest(role) {
  return axios.post(`${config.API_URL}/login/loginComplete`, role);
}

export function logoutRequest() {
  return axios.get(`${config.API_URL}/login/logout`);
}

export function getUserLang() {
  return axios.get(`${config.API_URL}/userSession/language`);
}

// TODO: Looks like this is not being used
export function setUserLang(payload) {
  return axios.put(`${config.API_URL}/userSession/language`, payload);
}

export function getAvailableLang() {
  return axios.get(`${config.API_URL}/login/availableLanguages`);
}

export function getAvatar(id) {
  return `${config.API_URL}/image/${id}?maxWidth=200&maxHeight=200`;
}

export function getUserSession() {
  return axios.get(`${config.API_URL}/userSession`);
}

export function resetPasswordRequest(form) {
  return axios.post(`${config.API_URL}/login/resetPassword`, {
    ...form,
  });
}

export function getResetPasswordInfo(token) {
  return axios.get(`${config.API_URL}/login/resetPassword/${token}`);
}

export function resetPasswordComplete(token, form) {
  return axios.post(`${config.API_URL}/login/resetPassword/${token}`, {
    ...form,
  });
}

export function resetPasswordGetAvatar(token) {
  return `${config.API_URL}/login/resetPassword/${token}/avatar`;
}
