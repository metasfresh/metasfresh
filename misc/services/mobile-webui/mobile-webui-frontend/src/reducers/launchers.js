import * as types from '../constants/LaunchersActionTypes';
import { toQRCodeObject } from '../utils/qrCode/hu';

export const initialState = {};

const NO_ACTIVE_FACETS = [];

export const getApplicationLaunchers = (state, applicationId) => state.launchers[applicationId] || {};

const getApplicationLaunchersFacets = (state, applicationId) =>
  getApplicationLaunchers(state, applicationId).activeFacets ?? NO_ACTIVE_FACETS;

export const getApplicationLaunchersFacetIds = (state, applicationId) =>
  getApplicationLaunchersFacets(state, applicationId).map((facet) => facet.facetId);

export const getApplicationLaunchersFilters = (state, applicationId) => {
  const launchers = getApplicationLaunchers(state, applicationId);
  return {
    filterByDocumentNo: launchers.filterByDocumentNo,
    facets: launchers.activeFacets ?? NO_ACTIVE_FACETS,
  };
};

export const getApplicationLaunchersFilterByDocumentNo = (state, applicationId) => {
  return getApplicationLaunchers(state, applicationId).filterByDocumentNo;
};

export default function launchers(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.POPULATE_LAUNCHERS_START: {
      const { applicationId, filterByQRCode, timestamp } = payload;
      return copyAndMergeToState(state, applicationId, {
        isLoading: true,
        filterByQRCode: toQRCodeObject(filterByQRCode),
        requestTimestamp: timestamp,
      });
    }
    case types.POPULATE_LAUNCHERS_COMPLETE: {
      const { applicationId, applicationLaunchers } = payload;
      return copyAndMergeToState(state, applicationId, {
        isLoading: false,
        filterByQRCode: applicationLaunchers.filterByQRCode,
        list: applicationLaunchers.launchers,
      });
    }
    case types.CLEAR_LAUNCHERS: {
      const { applicationId } = payload;
      return copyAndMergeToState(state, applicationId, {
        isLoading: false,
        list: [],
        requestTimestamp: null,
      });
    }
    case types.SET_ACTIVE_FILTERS: {
      const { applicationId, facets, filterByDocumentNo } = payload;
      return copyAndMergeToState(state, applicationId, {
        filterByDocumentNo,
        activeFacets: facets,
      });
    }
    default: {
      return state;
    }
  }
}

const copyAndMergeToState = (state, applicationId, applicationStateToMerge) => {
  const applicationState = state?.[`${applicationId}`] || {};
  return {
    ...state,
    [`${applicationId}`]: {
      ...applicationState,
      ...applicationStateToMerge,
    },
  };
};
