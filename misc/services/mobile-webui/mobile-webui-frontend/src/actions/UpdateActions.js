import { SET_VERSION } from '../constants/UpdateTypes';

/**
 * @method setVersion
 * @summary sets the version string as we get it from the server
 */
export function setVersion(serverVersion) {
  return {
    type: SET_VERSION,
    payload: { version: serverVersion },
  };
}
