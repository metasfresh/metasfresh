/**
 * @constant
 * @type {string} populate launchers
 */
export const POPULATE_LAUNCHERS_START = 'launchers/loadStart';
export const POPULATE_LAUNCHERS_COMPLETE = 'launchers/loadComplete';

/**
 * A launchers snapshot the server pushed over the websocket, as opposed to one this client requested.
 * A separate type from POPULATE_LAUNCHERS_COMPLETE so that no reducer can prune on it.
 * @constant
 * @type {string}
 */
export const POPULATE_LAUNCHERS_PUSHED = 'launchers/pushedByServer';

export const CLEAR_LAUNCHERS = 'launchers/clear';
export const SET_ACTIVE_FILTERS = 'launchers/activeFilters/set';
export const CLEAR_ACTIVE_FILTERS = 'launchers/activeFilters/clear';
