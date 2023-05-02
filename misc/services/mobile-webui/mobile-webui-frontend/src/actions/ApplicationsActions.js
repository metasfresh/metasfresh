import { POPULATE_APPLICATIONS } from '../constants/ApplicationsActionTypes';

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
