import {
  CLEAR_LAUNCHERS,
  POPULATE_LAUNCHERS_COMPLETE,
  POPULATE_LAUNCHERS_START,
  SET_ACTIVE_FILTERS,
} from '../constants/LaunchersActionTypes';

export const populateLaunchersStart = ({ applicationId, filterByQRCode }) => {
  return {
    type: POPULATE_LAUNCHERS_START,
    payload: { applicationId, filterByQRCode, timestamp: Date.now() },
  };
};

export const populateLaunchersComplete = ({ applicationId, applicationLaunchers }) => {
  //console.trace('populateLaunchersComplete', { applicationId, applicationLaunchers });
  return {
    type: POPULATE_LAUNCHERS_COMPLETE,
    payload: { applicationId, applicationLaunchers },
  };
};

export const clearLaunchers = ({ applicationId }) => {
  return {
    type: CLEAR_LAUNCHERS,
    payload: { applicationId },
  };
};

export const setActiveFilters = ({ applicationId, facets, filterByDocumentNo }) => {
  return {
    type: SET_ACTIVE_FILTERS,
    payload: { applicationId, facets, filterByDocumentNo },
  };
};
