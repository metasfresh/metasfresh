import * as types from '../constants/ActionTypes';

export const initialState = {};

export default function launcher(state = initialState, action) {
    const { payload } = action;
    switch (action.type) {
      case types.POPULATE_LAUNCHERS:
        return {
          ...state,
          ...payload.launchers,
        };
  
      default:
        return state;
    }
  }