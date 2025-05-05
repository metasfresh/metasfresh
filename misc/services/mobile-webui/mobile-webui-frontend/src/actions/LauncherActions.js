import {
  CLEAR_LAUNCHERS,
  POPULATE_LAUNCHERS_COMPLETE,
  POPULATE_LAUNCHERS_START,
  SET_ACTIVE_FILTERS,
} from '../constants/LaunchersActionTypes';
import { compareStringEmptyLast } from '../utils/stringUtils';

export const populateLaunchersStart = ({ applicationId, filterByQRCode }) => {
  return {
    type: POPULATE_LAUNCHERS_START,
    payload: { applicationId, filterByQRCode, timestamp: Date.now() },
  };
};

export const populateLaunchersComplete = ({ applicationId, applicationLaunchers }) => {
  //console.trace('populateLaunchersComplete', { applicationId, applicationLaunchers });
  if (applicationLaunchers && applicationLaunchers.length) {
    applicationLaunchers.sort((l1, l2) => compareStringEmptyLast(l1.caption, l2.caption));
  }
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
