// Locale-robust reading of a decimal number typed by the user, for the mobile-webui frontend.
// A mobile quantity input must accept the decimal separator the operator types regardless of the device
// browser language -- an HTML type="number" input ties the accepted separator to that language, so a typed
// point or comma can be rejected and the entry wiped. These helpers normalize a typed value (comma or dot as
// the decimal separator) to the dot-decimal form the backend expects. Tested in decimalInput.test.js.

const BLANKS = /[\s\u00A0\u202F]/g; // any whitespace, incl. no-break and narrow no-break space (e.g. a pasted grouped number)

/**
 * Reads a number the user typed and normalizes it to the dot-decimal form the backend expects.
 * Both the comma and the dot are read as the decimal separator; there is NO thousands grouping on input.
 * When both separators occur, the LAST one is the decimal point and the earlier one(s) are stripped.
 * A trailing separator is dropped. Blanks are dropped.
 *
 * @param {string} text the raw text from the input
 * @returns {string|null} the dot-decimal number; '' when the text holds no digit but only characters a number may
 *          begin with (a lone '-', ',' or '.', or blanks); null when the text is no number (a letter, a non-ASCII
 *          digit, "Infinity", ...) or would overflow to Infinity
 */
// internal: normalize/isValid below are the module's public surface; this is not exported
const parseDecimalNumberString = (text) => {
  const trimmed = text.trim();
  const sign = trimmed.startsWith('-') ? '-' : '';
  const unsigned = trimmed.substring(sign.length).replace(BLANKS, '');

  if (!/^[0-9.,]*$/.test(unsigned)) {
    return null;
  }
  if (!/[0-9]/.test(unsigned)) {
    return ''; // nothing typed yet
  }

  // a trailing separator has no digit after it, so it is not the decimal point: drop it ('1.5.' -> '1.5')
  const digitsAndSeparators = unsigned.replace(/[.,]+$/, '');
  const lastSeparator = Math.max(digitsAndSeparators.lastIndexOf('.'), digitsAndSeparators.lastIndexOf(','));
  let normalized;
  if (lastSeparator < 0) {
    normalized = digitsAndSeparators;
  } else {
    const integerDigits = digitsAndSeparators.substring(0, lastSeparator).replace(/[.,]/g, '');
    const fractionDigits = digitsAndSeparators.substring(lastSeparator + 1);
    normalized = fractionDigits ? `${integerDigits}.${fractionDigits}` : integerDigits;
  }

  return normalized !== '' && Number.isFinite(Number(normalized)) ? sign + normalized : null;
};

/**
 * @param {string} text the raw text from the input
 * @returns {string} the normalized number; an empty or missing value is returned as is; an unparseable text is
 *          returned as typed (trimmed), for the backend to reject it
 */
export const normalizeDecimalNumberString = (text) => {
  if (typeof text !== 'string' || !text) {
    return text;
  }
  const parsed = parseDecimalNumberString(text);
  return parsed === null ? text.trim() : parsed;
};

/**
 * @param {string} text
 * @returns {boolean} true for an empty value or a text without digit yet (e.g. '-', '1,'); false only for a
 *          genuinely unparseable text (letter, non-ASCII digit, overflow)
 */
export const isValidDecimalNumberString = (text) =>
  typeof text !== 'string' || !text || parseDecimalNumberString(text) !== null;
