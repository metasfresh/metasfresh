export * from './NetworkActions';
export * from './LauncherActions';
export * from './PickingActions';

import { SET_TOKEN, CLEAR_TOKEN } from '../constants/ActionTypes';

/**
 * @method setToken
 * @summary sets user's authentication token
 */
export function setToken(token) {
  return {
    type: SET_TOKEN,
    payload: { token },
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
