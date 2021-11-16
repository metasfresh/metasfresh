import * as types from '../constants/ApplicationsActionTypes';

const initialState = {};

export default function applications(state = initialState, action) {
  const { payload } = action;
  switch (action.type) {
    case types.POPULATE_APPLICATIONS:
      return { ...payload.applications };

    default:
      return state;
  }
}
