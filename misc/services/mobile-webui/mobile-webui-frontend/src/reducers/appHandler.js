import * as types from '../constants/ActionTypes';

export const initialState = {
    network: true,
};

export default function appHandler(state = initialState, action) {
    switch (action.type) {
      case types.SET_NETWORK_STATE:
        return {
          ...state,
          network: action.network,
        };
  
      default:
        return state;
    }
  }