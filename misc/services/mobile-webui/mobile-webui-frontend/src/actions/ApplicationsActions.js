import {
  POPULATE_APPLICATIONS,
  SET_ACTIVE_APPLICATION,
  CLEAR_ACTIVE_APPLICATION,
} from '../constants/ApplicationsActionTypes';

/**
 * @method populateApplications
 * @summary populate the applications branch in the redux store
 */
export function populateApplications({ applications }) {
  return {
    type: POPULATE_APPLICATIONS,
    payload: { applications },
  };
}

/**
 * @method setActiveApplication
 * @summary set the current active application name
 */
export function setActiveApplication(applicationName) {
  return {
    type: SET_ACTIVE_APPLICATION,
    payload: { applicationName },
  };
}

/**
 * @method clearActiveApplication
 * @summary set the current active application name
 */
export function clearActiveApplication() {
  return {
    type: CLEAR_ACTIVE_APPLICATION,
  };
}
