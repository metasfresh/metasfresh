import { POPULATE_APPLICATIONS } from '../constants/ApplicationsActionTypes';

/**
 * @summary populate the applications branch in the redux store
 */
export const populateApplications = ({ applications }) => {
  return {
    type: POPULATE_APPLICATIONS,
    payload: { applications },
  };
};
