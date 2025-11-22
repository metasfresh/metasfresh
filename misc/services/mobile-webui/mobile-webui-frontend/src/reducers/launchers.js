import * as types from '../constants/LaunchersActionTypes';
import { toQRCodeObject } from '../utils/qrCode/hu';
import { useSelector } from 'react-redux';

export const initialState = {};

const EMPTY_ARRAY = [];

export const useApplicationLaunchers = ({ applicationId }) => {
  const {
    list: launchers,
    filterByQRCode,
    requestTimestamp,
  } = useSelector((state) => getApplicationLaunchers(state, applicationId));
  return { launchers, filterByQRCode, requestTimestamp };
};
const getApplicationLaunchers = (state, applicationId) => state.launchers[applicationId] || {};

export const useFacetIds = ({ applicationId }) => {
  return useSelector((state) => getApplicationLaunchersFacetIds(state, applicationId));
};

export const useFacets = ({ applicationId }) => {
  return useSelector((state) => getApplicationLaunchersFacets(state, applicationId));
};

const getApplicationLaunchersFacetIds = (state, applicationId) => {
  const facets = getApplicationLaunchersFacets(state, applicationId);
  return facets?.length > 0 ? extractFacetIdsFromFacetsArray(facets) : EMPTY_ARRAY;
};

const getApplicationLaunchersFacets = (state, applicationId) => {
  let activeFacets = getApplicationLaunchers(state, applicationId).activeFacets;
  return activeFacets?.length > 0 ? activeFacets : EMPTY_ARRAY;
};

export const useFilters = ({ applicationId }) => {
  return useSelector((state) => getApplicationLaunchersFilters(state, applicationId));
};

const getApplicationLaunchersFilters = (state, applicationId) => {
  const launchers = getApplicationLaunchers(state, applicationId);
  return {
    filterByDocumentNo: launchers.filterByDocumentNo,
    filterByQtyAvailableAtPickFromLocator: launchers.filterByQtyAvailableAtPickFromLocator ?? false,
  };
};

const extractFacetIdsFromFacetsArray = (facets) => {
  return facets?.map((facet) => facet.facetId) ?? [];
};

//
//
//
//
//

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
      const { applicationId, facets, filters } = payload;
      return copyAndMergeToState(state, applicationId, {
        filterByDocumentNo: filters?.filterByDocumentNo,
        filterByQtyAvailableAtPickFromLocator: filters?.filterByQtyAvailableAtPickFromLocator,
        activeFacets: facets,
      });
    }
    case types.CLEAR_ACTIVE_FILTERS: {
      const { applicationId } = payload;
      return copyAndMergeToState(state, applicationId, {
        filterByDocumentNo: null,
        filterByQtyAvailableAtPickFromLocator: false,
        activeFacets: [],
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
