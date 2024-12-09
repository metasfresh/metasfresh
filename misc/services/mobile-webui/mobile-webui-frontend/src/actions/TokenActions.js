import { SET_TOKEN, CLEAR_TOKEN } from '../constants/TokenActionTypes';

/**
<<<<<<< HEAD
 * @method setToken
 * @summary sets user's authentication token
 */
export function setToken(token) {
  return {
    type: SET_TOKEN,
    payload: { token },
=======
 * @summary sets user's authentication token
 */
export function setToken({ token, userFullname }) {
  return {
    type: SET_TOKEN,
    payload: { token, userFullname },
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
