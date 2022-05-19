export const apiBasePath = `${config.SERVER_URL}/api/v2`;
export const stompUrl = `${config.SERVER_URL}/stomp`;

/**
 * @constant
 * @type {int} used normally to check every min if there is a new version, can be customised as needed
 */
export const UPDATE_CHECK_INTERVAL = 60000;

/**
 * @constant
 * @type {boolean} used to set or not the service worker registration
 */
export const REGISTER_SERVICE_WORKER = false;
