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

/**
 * @constant
 * @type {integer} Used to indicate the row position from where the dropup should be shown.
 */
export const DROPUP_START = 10;

/**
 * @constant
 * @type {integer} Used to indicate the offset position for resizing the table container
 */
export const DROPDOWN_OFFSET_BIG = 250;

/**
 * @constant
 * @type {integer} Used to indicate the offset position for resizing the table container
 */
export const DROPUP_OFFSET_SMALL = 150;
export const LOCAL_LANG = 'metasfreshLanguage';

/**
 * @constant
 * @type {integer} Used to indicate the code of the F2 key. Visualy is easier to see the constant name and reason about
 */
export const F2_KEY = 113;

/**
 * @constant
 * @type {string} Used to indicate the max height for the TableContextMenu from where the autoscroll will start
 */
export const TBL_CONTEXT_MENU_HEIGHT = '300px';

/**
 * @constant
 * @type {integer} Used to indicate the max Y mouse position from where the routine to scroll the table and readjust position begins
 *                 case when there are many OL and you click on one line at the bottom.
 */
export const TBL_CONTEXT_MENU_MAX_Y = 706;

/**
 * @constant
 * @type {integer} Used to indicate the debounce time when performing a search
 */
export const DEBOUNCE_TIME_SEARCH = 150;

/**
 * @constant
 * @type {integer} Used to indicate the number of characters from where we show read more link in the notification toastr
 */
export const SHOW_READ_MORE_FROM = 100;

/**
 * @constant
 * @type {integer} Used to indicate the max number of items to show when user clicks on the `Home` icon (`childrenLimit` param in api/menu/root call)
 */
export const HOME_MENU_USER_MAX_ITEMS = 10;

/**
 * @constant
 * @type {integer} Used to indicate the number of rows from where we display the `Show more...` button
 */
export const INLINE_TAB_SHOW_MORE_FROM = 5;
