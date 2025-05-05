/**
 * @constant
 * @type {string} Type of `connectionErrorType` to be used in the redux store when we receive 502 error from the server.
 */
export const BAD_GATEWAY_ERROR = 'badGateway';

/**
 * @constant
 * @type {string} Type of connectionErrorType when no error is present (internet down).
 * Note: do not change this to another value because this matches the key in the translation messages (as it comes from the BE).
 *       (see ErrorScreen component for more details)
 */
export const NO_CONNECTION_ERROR = 'noStatus';

/**
 * @type {integer} The number of milliseconds to wait before retrying to ping the server
 */
export const CONNECTION_ERROR_RETRY_INTERVAL_MILLIS = 5000;

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
 * @type {string} - the event string fired when down arrow is pressed
 */
export const ARROW_DOWN_KEY = 'ArrowDown';

/**
 * @constant
 * @type {string} - the event string fired when up arrow is pressed
 */
export const ARROW_UP_KEY = 'ArrowUp';

/**
 * @constant
 * @type {string} ToDo: Description for the constant.
 */
export const AMOUNT_FIELD_TYPES = ['Amount', 'CostPrice', 'Quantity'];

export const NUMERIC_FIELD_TYPES = [...AMOUNT_FIELD_TYPES, 'Integer', 'Number'];

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
export const DEBOUNCE_TIME_SEARCH = 450;

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

/**
 * @constant
 * @type {string} Used to identify the type of filter passed to checkClearedFilters
 */
export const FILTERS_TYPE_NOT_INCLUDED = 'NotIncluded';

/**
 * @constant
 * @type {integer} Used to indicate the number of px until we apply left offset
 */
export const LOOKUP_SHOW_MORE_PIXEL_NO = 250;

/**
 * @constant
 * @type {integer} Used to indicate the max number of character for a label before chopping it
 *                 Note: it has to be in sync with `.table-cell-label-container` size
 */
export const TBL_CELL_LABEL_MAX = 10;

/**
 * @constant
 * @type {integer} Used to indicate the pos from where we apply diff with the offset on Y axis
 */
export const TBL_CONTEXT_MENU_X_MAX = 620;

/**
 * @constant
 * @type {integer} Used to indicate the pos from where we apply diff with the offset on X axis
 */
export const TBL_CONTEXT_MENU_Y_MAX = 1430;

/**
 * @constant
 * @type {integer} Used to indicate the offset to apply when exceeds TBL_CONTEXT_MENU_X_MAX
 */
export const TBL_CONTEXT_X_OFFSET = 145;

/**
 * @constant
 * @type {integer} Used to indicate the offset to apply when exceeds TBL_CONTEXT_MENU_Y_MAX
 */
export const TBL_CONTEXT_Y_OFFSET = 65;

/**
 * @constant
 * @type {integer} Used to indicate the popup pre-defined height
 */
export const TBL_CONTEXT_POPUP_HEIGHT = '215px';
