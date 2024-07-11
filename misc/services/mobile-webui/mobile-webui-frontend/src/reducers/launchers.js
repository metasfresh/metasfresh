import * as types from '../constants/LaunchersActionTypes';

export const initialState = {};

export const getApplicationLaunchers = (state, applicationId) => state.launchers[applicationId] || {};

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
