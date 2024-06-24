import axios from 'axios';

export function loginRequest(username, password) {
  return axios.post(
    `${config.API_URL}/login/authenticate`,
    {
      username,
      password,
    },
    {
      validateStatus: () => {
        // returning true so that we can get the error message
        return true;
      },
    }
  );
}

export function checkLoginRequest() {
  return axios.get(`${config.API_URL}/login/isLoggedIn`);
}

export function loginCompletionRequest(role) {
  return axios.post(`${config.API_URL}/login/loginComplete`, role);
}

/**
 * POST method
 * REST -> /rest/api/login/authenticate
 * @method loginWithToken
 * @summary - Allows authenticating with a given token, returns a promise (Note: `  type: "token" ` - passed by default
 *            to trigger the token authentication routine.)
 * @param {string} tokenId
 */
export function loginWithToken(token) {
  return axios.post(`${config.API_URL}/login/authenticate`, {
    type: 'token',
    token,
  });
}

export function logoutRequest() {
  return axios.get(`${config.API_URL}/login/logout`);
}

export function getAvailableLang() {
  return axios.get(`${config.API_URL}/login/availableLanguages`);
}

export function getAvatar(id) {
  return `${config.API_URL}/image/${id}?maxWidth=200&maxHeight=200`;
}

export function resetPasswordRequest(form) {
  return axios.post(`${config.API_URL}/login/resetPassword`, {
    ...form,
  });
}

export function getResetPasswordInfo(token) {
  return axios.post(`${config.API_URL}/login/resetPassword/${token}/init`);
}

export function resetPasswordComplete(token, form) {
  return axios.post(`${config.API_URL}/login/resetPassword/${token}`, {
    ...form,
  });
}

export function resetPasswordGetAvatar(token) {
  return `${config.API_URL}/login/resetPassword/${token}/avatar`;
}
