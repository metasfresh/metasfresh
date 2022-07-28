import { POPULATE_LAUNCHERS } from '../constants/LaunchersActionTypes';

/**
 * @method populateLaunchers
 * @summary populate launchers in the redux store
 */
export function populateLaunchers({ applicationId, launchers }) {
  return {
    type: POPULATE_LAUNCHERS,
    payload: { applicationId, launchers },
  };
}
