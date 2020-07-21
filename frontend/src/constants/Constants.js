/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const VIEW_EDITOR_RENDER_MODES_NEVER = 'never';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const VIEW_EDITOR_RENDER_MODES_ON_DEMAND = 'on-demand';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const VIEW_EDITOR_RENDER_MODES_ALWAYS = 'always';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INITIALLY_CLOSED = 'INITIALLY_CLOSED';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const INITIALLY_OPEN = 'INITIALLY_OPEN';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const AMOUNT_FIELD_TYPES = ['Amount', 'CostPrice', 'Quantity'];

/**
 * @constant
 * @type {array} ToDo: Description for the constant.
 */
export const AMOUNT_FIELD_FORMATS_BY_PRECISION = [
  '0,0.[00000]',
  '0,0.0[0000]',
  '0,0.00[000]',
  '0,0.000[00]',
  '0,0.0000[0]',
  '0,0.00000',
];
export const SPECIAL_FIELD_TYPES = ['Color'];

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DATE_FORMAT = 'YYYY-MM-DD';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const TIME_FORMAT = `HH:mm`;

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DATE_TIMEZONE_FORMAT = `YYYY-MM-DDTHH:mm:ss.SSSZ`;

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const DATE_FIELDS = ['Date', 'DateTime'];

/**
 * @constant
 * @type {array} Used as argument for Moment.format() function when handling date formats.
 *  Format strings available for 'Date', 'DateTime', 'ZonedDateTime', 'Timestamp'.
 */
export const DATE_FIELD_TYPES = [
  'Date',
  'DateTime',
  'ZonedDateTime',
  'Timestamp',
];

/**
 * @constant
 * @type {object} ToDo: Description for the constant.
 */
export const DATE_FIELD_FORMATS = {
  Date: 'L',
  ZonedDateTime: 'L LTS',
  DateTime: 'L LTS',
  Time: 'LT',
  Timestamp: 'L HH:mm:ss',
};

/**
 * @constant
 * @type {array} ToDo: Description for the constant.
 */
export const TIME_FIELD_TYPES = ['Time'];

export const TIME_REGEX_TEST = /^\d\d:\d\d$/;

export const PROCESS_NAME = 'process';

export const LOCATION_SEARCH_NAME = 'location-area-search';

export const LOCAL_LANG = 'metasfreshLanguage';
