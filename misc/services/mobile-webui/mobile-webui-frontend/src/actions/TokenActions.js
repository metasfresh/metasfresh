import { SET_TOKEN, CLEAR_TOKEN } from '../constants/TokenActionTypes';

/**
 * @summary sets user's authentication token
 */
export function setToken({ token, userFullname }) {
  return {
    type: SET_TOKEN,
    payload: { token, userFullname },
  };
}

/**
 * @method clearToken
 * @summary clear user's authentication token
 */
export function clearToken() {
  return {
    type: CLEAR_TOKEN,
  };
}
