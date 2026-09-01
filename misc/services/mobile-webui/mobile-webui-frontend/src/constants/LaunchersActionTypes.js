/**
 * @constant
 * @type {string} populate launchers
 */
export const POPULATE_LAUNCHERS_START = 'launchers/loadStart';
export const POPULATE_LAUNCHERS_COMPLETE = 'launchers/loadComplete';

/**
 * A launchers snapshot PUSHED over the websocket. Deliberately a DIFFERENT type from
 * POPULATE_LAUNCHERS_COMPLETE: a push carries no request-issued time, so it can never prove a workflow
 * process is gone, and reducers/wfProcesses/workflow.js must not handle it. See the comment there.
 * @constant
 * @type {string}
 */
export const POPULATE_LAUNCHERS_PUSHED = 'launchers/loadPushed';

export const CLEAR_LAUNCHERS = 'launchers/clear';
export const SET_ACTIVE_FILTERS = 'launchers/activeFilters/set';
export const CLEAR_ACTIVE_FILTERS = 'launchers/activeFilters/clear';
