import * as updateTypes from '../constants/UpdateTypes';

export const initialState = {
  version: null,
};

export default function appHandler(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case updateTypes.SET_VERSION:
      return {
        ...state,
        version: payload.version,
      };
    default:
      return state;
  }
}
