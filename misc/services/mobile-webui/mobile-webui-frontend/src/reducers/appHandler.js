import * as networkTypes from '../constants/NetworkActionTypes';
import * as tokenTypes from '../constants/TokenActionTypes';
import { SET_ACTIVE_APPLICATION, CLEAR_ACTIVE_APPLICATION } from '../constants/ApplicationsActionTypes';

export const initialState = {
  network: true,
  token: null,
  activeApplication: null,
};

export default function appHandler(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case networkTypes.SET_NETWORK_OFFLINE:
      return {
        ...state,
        network: payload.network,
      };
    case networkTypes.SET_NETWORK_ONLINE:
      return {
        ...state,
        network: payload.network,
      };
    case tokenTypes.SET_TOKEN:
      return {
        ...state,
        token: payload.token,
      };
    case tokenTypes.CLEAR_TOKEN:
      return {
        ...state,
        token: null,
      };
    case SET_ACTIVE_APPLICATION:
      return {
        ...state,
        activeApplication: payload.applicationName,
      };
    case CLEAR_ACTIVE_APPLICATION:
      return {
        ...state,
        activeApplication: null,
      };
    default:
      return state;
  }
}
