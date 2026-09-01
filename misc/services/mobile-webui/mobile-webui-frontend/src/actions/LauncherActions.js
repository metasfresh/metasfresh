import {
  CLEAR_ACTIVE_FILTERS,
  CLEAR_LAUNCHERS,
  POPULATE_LAUNCHERS_COMPLETE,
  POPULATE_LAUNCHERS_PUSHED,
  POPULATE_LAUNCHERS_START,
  SET_ACTIVE_FILTERS,
} from '../constants/LaunchersActionTypes';

export const populateLaunchersStart = ({ applicationId, filterByQRCode }) => {
  return {
    type: POPULATE_LAUNCHERS_START,
    payload: { applicationId, filterByQRCode, timestamp: Date.now() },
  };
};

export const populateLaunchersComplete = ({ applicationId, applicationLaunchers, requestTimestamp }) => {
  //console.trace('populateLaunchersComplete', { applicationId, applicationLaunchers });
  return {
    type: POPULATE_LAUNCHERS_COMPLETE,
    // `requestTimestamp` is when this launchers snapshot was fetched (request issued). The
    // wfProcesses reducer uses it to avoid pruning a process started after the request went out.
    payload: { applicationId, applicationLaunchers, requestTimestamp },
  };
};

export const populateLaunchersPushed = ({ applicationId, applicationLaunchers }) => {
  return {
    type: POPULATE_LAUNCHERS_PUSHED,
    // NO requestTimestamp, on purpose: a pushed snapshot carries no request-issued time, so there is no
    // instant that bounds what it can know about. The wfProcesses reducer does not handle this type at
    // all, so a push cannot prune a workflow process.
    payload: { applicationId, applicationLaunchers },
  };
};

export const clearLaunchers = ({ applicationId }) => {
  return {
    type: CLEAR_LAUNCHERS,
    payload: { applicationId },
  };
};

export const setActiveFilters = ({ applicationId, facets, filters }) => {
  return {
    type: SET_ACTIVE_FILTERS,
    payload: { applicationId, facets, filters },
  };
};
export const clearActiveFilters = ({ applicationId }) => {
  return {
    type: CLEAR_ACTIVE_FILTERS,
    payload: { applicationId },
  };
};
