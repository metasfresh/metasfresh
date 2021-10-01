import * as types from '../constants/ActionTypes';

export const initialState = {
  active: false,
};

export default function scanner(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.START_SCANNING:
      return {
        ...state,
        active: payload.active,
      };
    case types.STOP_SCANNING:
      return {
        ...state,
        active: payload.active,
      };
    default:
      return state;
  }
}
