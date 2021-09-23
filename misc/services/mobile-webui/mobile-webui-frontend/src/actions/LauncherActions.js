import { POPULATE_LAUNCHERS } from '../constants/LaunchersTypes';

/**
 * @method populateLaunchers
 * @summary populate launchers in the redux store
 */
 export function populateLaunchers(launchers) {
   return {
     type: POPULATE_LAUNCHERS,
     payload: { launchers },
   };
}