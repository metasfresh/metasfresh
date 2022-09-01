import * as updateTypes from '../constants/UpdateTypes';

export const initialState = {
  version: null,
};

export const getVersionFromState = (state) => state.update.version;

export default function reducer(state = initialState, action) {
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
