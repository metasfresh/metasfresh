import * as types from '../constants/ActionTypes';

export const initialState = {
    network: true,
};

export default function appHandler(state = initialState, action) {
    switch (action.type) {
      case types.SET_NETWORK_OFFLINE:
        return {
          ...state,
          network: action.network,
        };
      case types.SET_NETWORK_ONLINE:
        return {
          ...state,
          network: action.network,
        };  
  
      default:
        return state;
    }
  }