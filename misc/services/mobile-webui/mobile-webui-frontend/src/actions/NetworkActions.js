import { SET_NETWORK_OFFLINE, SET_NETWORK_ONLINE } from '../constants/NetworkActionTypes';

/**
 * @method networkStatusOffline
 * @summary sets the network flag to `false` in the store indicating that we are in the offline state
 */
export function networkStatusOffline() {
  return {
    type: SET_NETWORK_OFFLINE,
    payload: { network: false },
  };
}

/**
 * @method networkStatusOnline
 * @summary sets the network flag to `true` in the store indicating that we are in the online state
 */
export function networkStatusOnline() {
  return {
    type: SET_NETWORK_ONLINE,
    payload: { network: true },
  };
}
