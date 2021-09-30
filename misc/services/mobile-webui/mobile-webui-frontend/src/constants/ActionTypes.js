export * from './NetworkTypes';
export * from './LaunchersTypes';
export * from './PickingTypes';

/**
 * @constant
 * @type {string} sets the authentication token
 */
export const SET_TOKEN = 'SET_TOKEN';

/**
 * @constant
 * @type {string} clears the authentication token
 */
export const CLEAR_TOKEN = 'CLEAR_TOKEN';

/**
 * @constant
 * @type {string} clears the authentication token
 */
export const START_WORKFLOW_PROCESS = 'START_WORKFLOW_PROCESS';

/**
 * @constant
 * @type {string} clears the authentication token
 */
export const CONTINUE_WORKFLOW_PROCESS = 'CONTINUE_WORKFLOW_PROCESS';

/**
 * @constant
 * @type {string} Adds started workflow's activities to a temporary structure in the tree
 */
export const ADD_WORKFLOW_STATUS = 'ADD_WORKFLOW_STATUS';
