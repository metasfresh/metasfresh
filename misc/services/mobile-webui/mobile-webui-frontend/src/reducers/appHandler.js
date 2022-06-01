import * as networkTypes from '../constants/NetworkActionTypes';
import * as tokenTypes from '../constants/TokenActionTypes';

export const getTokenFromState = (state) => state.appHandler.token;
export const getIsLoggedInFromState = (state) => !!getTokenFromState(state);

export const initialState = {
  network: true,
  token: null,
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
    default:
      return state;
  }
}
