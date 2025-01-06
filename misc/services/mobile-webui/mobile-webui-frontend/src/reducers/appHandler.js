import * as networkTypes from '../constants/NetworkActionTypes';
import * as tokenTypes from '../constants/TokenActionTypes';

const getAppHandlerState = (globalState) => {
  return globalState.appHandler ?? {};
};
export const getTokenFromState = (globalState) => {
  return getAppHandlerState(globalState).token;
};
export const getIsLoggedInFromState = (globalState) => {
  return !!getTokenFromState(globalState);
};
export const getUserFullnameFromState = (globalState) => {
  return getAppHandlerState(globalState).userFullname;
};

export const initialState = {
  network: true,
  token: null,
};

export default function appHandler(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case networkTypes.SET_NETWORK_OFFLINE: {
      return {
        ...state,
        network: payload.network,
      };
    }
    case networkTypes.SET_NETWORK_ONLINE: {
      return {
        ...state,
        network: payload.network,
      };
    }
    case tokenTypes.SET_TOKEN: {
      const token = action.payload.token;
      const userFullname = action.payload.userFullname ? action.payload.userFullname : state.userFullname;
      return {
        ...state,
        token,
        userFullname,
      };
    }
    case tokenTypes.CLEAR_TOKEN: {
      return {
        ...state,
        token: null,
        userFullname: null,
      };
    }
    default: {
      return state;
    }
  }
}
