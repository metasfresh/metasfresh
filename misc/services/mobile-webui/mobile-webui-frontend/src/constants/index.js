export const apiBasePath = `${config.SERVER_URL}/api/v2`;
export const stompUrl = `${config.SERVER_URL}/stomp`;
export const serverVersionUrl = `${config.SERVER_URL}/info`;

/**
 * @constant
 * @type {int} used normally to check every min if there is a new version, can be customised as needed
 */
export const UPDATE_CHECK_INTERVAL = 60000;
