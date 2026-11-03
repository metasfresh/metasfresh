// Keystroke-level filter for decimal inputs; mirrors the desktop CostPrice.handleBeforeInput.
// The allowed set is exactly what decimalInput.parseDecimalNumberString accepts: digits, '.', ',', a '-' (sign) and the
// blanks it strips. JS '\s' already covers NBSP (U+00A0) and narrow NBSP (U+202F), same as BLANKS in decimalInput.js.
const ALLOWED_INSERT = /^[0-9.,\-\s]+$/;

/**
 * @param {string|null|undefined} data the text about to be inserted (beforeinput `data`)
 * @returns {boolean} false only if the insertion holds a character the decimal parser would reject.
 *          Deletion/navigation have no data and are always allowed.
 */
export const isAllowedDecimalInput = (data) => data == null || data === '' || ALLOWED_INSERT.test(data);
