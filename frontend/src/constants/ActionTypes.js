export * from './actions/TableTypes';
export * from './actions/ViewTypes';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const NO_CONNECTION = 'NO_CONNECTION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const OPEN_MODAL = 'OPEN_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLOSE_MODAL = 'CLOSE_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLOSE_PROCESS_MODAL = 'CLOSE_PROCESS_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_MODAL = 'UPDATE_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const USER_SESSION_INIT = 'USER_SESSION_INIT';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const USER_SESSION_UPDATE = 'USER_SESSION_UPDATE';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const SET_PROCESS_STATE_SAVED = 'SET_PROCESS_STATE_SAVED';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const SET_PROCESS_STATE_PENDING = 'SET_PROCESS_STATE_PENDING';

// SCOPED ACTIONS

/**
 * @constant
 * Window is being initialized, data requests are triggered
 * @type {string}
 */
export const INIT_WINDOW = 'INIT_WINDOW';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INIT_DATA_SUCCESS = 'INIT_DATA_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INIT_LAYOUT_SUCCESS = 'INIT_LAYOUT_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const SORT_TAB = 'SORT_TAB';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const ACTIVATE_TAB = 'ACTIVATE_TAB';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UNSELECT_TAB = 'UNSELECT_TAB';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_DATA_PROPERTY = 'UPDATE_DATA_PROPERTY';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_DATA_FIELD_PROPERTY = 'UPDATE_DATA_FIELD_PROPERTY';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_MASTER_DATA = 'UPDATE_MASTER_DATA';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLEAR_MASTER_DATA = 'CLEAR_MASTER_DATA';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_DATA_VALID_STATUS = 'UPDATE_DATA_VALID_STATUS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_DATA_SAVE_STATUS = 'UPDATE_DATA_SAVE_STATUS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_DATA_INCLUDED_TABS_INFO = 'UPDATE_DATA_INCLUDED_TABS_INFO';

// END OF SCOPED ACTIONS

// INDICATOR ACTIONS
/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CHANGE_INDICATOR_STATE = 'CHANGE_INDICATOR_STATE';
// END OF INDICATOR ACTIONS

// NOTIFICATION ACTIONS
/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const ADD_NOTIFICATION = 'ADD_NOTIFICATION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const SET_NOTIFICATION_PROGRESS = 'SET_NOTIFICATION_PROGRESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DELETE_NOTIFICATION = 'DELETE_NOTIFICATION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLEAR_NOTIFICATIONS = 'CLEAR_NOTIFICATIONS';
// END OF NOTIFICATION ACTIONS

//AUTH ACTIONS
/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const LOGOUT_SUCCESS = 'LOGOUT_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const ENABLE_TUTORIAL = 'ENABLE_TUTORIAL';
//END OF AUTH ACTIONS

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INIT_FILTERS_PARAMETERS = 'INIT_FILTERS_PARAMETERS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DELETE_FILTERS_PARAMETERS = 'DELETE_FILTERS_PARAMETERS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_FILTERS_PARAMETERS = 'UPDATE_FILTERS_PARAMETERS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DRAGGABLE_CARD = 'DRAGGABLE_CARD';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const NEW_PROCESS_SUCCESS = 'NEW_PROCESS_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const GET_NOTIFICATIONS_SUCCESS = 'GET_NOTIFICATIONS_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const NEW_NOTIFICATION = 'NEW_NOTIFICATION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const REMOVE_NOTIFICATION = 'REMOVE_NOTIFICATION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const REMOVE_ALL_NOTIFICATIONS = 'REMOVE_ALL_NOTIFICATIONS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const READ_NOTIFICATION = 'READ_NOTIFICATION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const READ_ALL_NOTIFICATIONS = 'READ_ALL_NOTIFICATIONS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const OPEN_PLUGIN_MODAL = 'OPEN_PLUGIN_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLOSE_PLUGIN_MODAL = 'CLOSE_PLUGIN_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const OPEN_RAW_MODAL = 'OPEN_RAW_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_RAW_MODAL = 'UPDATE_RAW_MODAL';

/**
 * @constant
 * @type {string}
 */
export const SET_RAW_MODAL_TITLE = 'SET_RAW_MODAL_TITLE';

/**
 * @constant
 * @type {string}
 */
export const SET_RAW_MODAL_DESCRIPTION = 'SET_RAW_MODAL_DESCRIPTION';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLOSE_RAW_MODAL = 'CLOSE_RAW_MODAL';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const TOGGLE_OVERLAY = 'TOGGLE_OVERLAY';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const OPEN_FILTER_BOX = 'OPEN_FILTER_BOX';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const CLOSE_FILTER_BOX = 'CLOSE_FILTER_BOX';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const SET_LATEST_NEW_DOCUMENT = 'SET_LATEST_NEW_DOCUMENT';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const ALLOW_SHORTCUT = 'ALLOW_SHORTCUT';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DISABLE_SHORTCUT = 'DISABLE_SHORTCUT';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const PATCH_REQUEST = 'PATCH_REQUEST';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const PATCH_SUCCESS = 'PATCH_SUCCESS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const PATCH_FAILURE = 'PATCH_FAILURE';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const PATCH_RESET = 'PATCH_RESET';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const ALLOW_OUTSIDE_CLICK = 'ALLOW_OUTSIDE_CLICK';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DISABLE_OUTSIDE_CLICK = 'DISABLE_OUTSIDE_CLICK';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INIT_KEYMAP = 'INIT_KEYMAP';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_KEYMAP = 'UPDATE_KEYMAP';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INIT_HOTKEYS = 'INIT_HOTKEYS';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const UPDATE_HOTKEYS = 'UPDATE_HOTKEYS';

/**
 * @constant
 * @type {string} fetches panel's quick actions.
 */
export const FETCHED_QUICK_ACTIONS = 'FETCHED_QUICK_ACTIONS';

/**
 * @constant
 * @type {string} removes panel's quick actions when it's unmounted.
 */
export const DELETE_QUICK_ACTIONS = 'DELETE_QUICK_ACTIONS';

/**
 * @constant
 * @type {string} fetches tab's quick actions.
 */
export const FETCH_TOP_ACTIONS = 'FETCH_TOP_ACTIONS';

/**
 * @constant
 * @type {string} called on successful response.
 */
export const FETCH_TOP_ACTIONS_SUCCESS = 'FETCH_TOP_ACTIONS_SUCCESS';

/**
 * @constant
 * @type {string} called on failed response.
 */
export const FETCH_TOP_ACTIONS_FAILURE = 'FETCH_TOP_ACTIONS_FAILURE';

/**
 * @constant
 * @type {string} removes tab's quick actions on unmount.
 */
export const DELETE_TOP_ACTIONS = 'DELETE_TOP_ACTIONS';

/**
 * @constant
 * @type {string} set languages in the app handler
 */
export const SET_LANGUAGES = 'SET_LANGUAGES';
