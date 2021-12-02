import { createSelector } from 'reselect';

import * as types from '../constants/LaunchersActionTypes';

export const initialState = {};

const getApplicationLaunchers = (state, appId) => state.launchers[appId] || {};

export const selectApplicationLaunchersFromState = createSelector(
  (state, appId) => getApplicationLaunchers(state, appId),
  (applicationLaunchers) => applicationLaunchers
);

export default function launchers(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.POPULATE_LAUNCHERS:
      return {
        ...state,
        [`${payload.applicationId}`]: {
          list: payload.applicationLaunchers.launchers,
          scanBarcodeToStartJobSupport: payload.applicationLaunchers.scanBarcodeToStartJobSupport,
        },
      };

    default:
      return state;
  }
}
