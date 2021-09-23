import * as types from '../constants/ActionTypes';

export const initialState = {
    network: true,
    token: null,
};

export default function appHandler(state = initialState, action) {
    const { payload } = action;
    switch (action.type) {
      case types.SET_NETWORK_OFFLINE:
        return {
          ...state,
          network: payload.network,
        };
      case types.SET_NETWORK_ONLINE:
        return {
          ...state,
          network: payload.network,
        };  
      case types.SET_TOKEN:
        return {
          ...state,
          token: payload.token,
        };
      case types.CLEAR_TOKEN:
        return {
          ...state,
          token: null,
        };
      default:
        return state;
    }
  }