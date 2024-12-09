import * as networkTypes from '../constants/NetworkActionTypes';
import * as tokenTypes from '../constants/TokenActionTypes';

<<<<<<< HEAD
export const getTokenFromState = (state) => state.appHandler.token;
export const getIsLoggedInFromState = (state) => !!getTokenFromState(state);
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

export const initialState = {
  network: true,
  token: null,
};

export default function appHandler(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
<<<<<<< HEAD
    case networkTypes.SET_NETWORK_OFFLINE:
=======
    case networkTypes.SET_NETWORK_OFFLINE: {
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
      return {
        ...state,
        network: payload.network,
      };
<<<<<<< HEAD
    case networkTypes.SET_NETWORK_ONLINE:
=======
    }
    case networkTypes.SET_NETWORK_ONLINE: {
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
      return {
        ...state,
        network: payload.network,
      };
<<<<<<< HEAD
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
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
  }
}
