import { createSelector } from 'reselect';

import * as types from '../constants/LaunchersActionTypes';

export const initialState = {};

const getLaunchers = (state, appId) => state.launchers[appId] || {};

export const selectLaunchersFromState = createSelector(
  (state, appId) => getLaunchers(state, appId),
  (launchers) => launchers
);

export default function launchers(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.POPULATE_LAUNCHERS:
      return {
        ...state,
        [`${payload.applicationId}`]: { ...payload.launchers },
      };

    default:
      return state;
  }
}
