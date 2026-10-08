import counterpart from 'counterpart';
import axios from 'axios';
import Moment from 'moment';
import numeral from 'numeral';

import { LOCAL_LANG } from '../constants/Constants';

/** Just a shortcut & abstraction of counterpart's translate function */
export const trl = (key, args = {}) => {
  return counterpart.translate(key, args);
};

export function initCurrentActiveLocale() {
  const lang = getCurrentActiveLocale();

  if (lang) {
    // calling it because that method will set the language in all other places
    setCurrentActiveLocale(lang);
  }
}

/**
 * @method getCurrentActiveLocale
 * @summary Retrieves the active locale from the local store
 */
export function getCurrentActiveLocale() {
  return localStorage.getItem(LOCAL_LANG);
}

/**
 * @param {string} locale in form of 'en_US', 'de_DE
 * @returns {string} extracted language (e.g. 'en', 'de')
 */
export function extractLanguageFromLocale(locale) {
  if (!locale) return 'de';
  const idx = locale.indexOf('_');
  if (idx > 0) {
    return locale.substr(0, idx);
  } else {
    return locale;
  }
}

export function getCurrentActiveLanguage() {
  return extractLanguageFromLocale(getCurrentActiveLocale());
}

export function setCurrentActiveLocale(lang) {
  localStorage.setItem(LOCAL_LANG, lang);

  Moment.locale(lang);
  axios.defaults.headers.common['Accept-Language'] = lang;
}

/**
 * @method isGermanLanguage
 * @summary Returns boolean value if the language is german or not
 */
export function isGermanLanguage(languageObj) {
  return languageObj && languageObj.key
    ? languageObj.key.includes('de')
    : false;
}

/** The number separators of the current user session, see {@link initNumeralLocales} */
let sessionNumberDelimiters = { decimal: '.', thousands: ',' };

export function initNumeralLocales(lang, locale) {
  sessionNumberDelimiters = {
    decimal: locale.numberDecimalSeparator || '.',
    thousands: locale.numberGroupingSeparator || ',',
  };

  const language = lang.toLowerCase();
  const LOCAL_NUMERAL_FORMAT = {
    defaultFormat: '0,0.00[000]',
    delimiters: {
      thousands: locale.numberGroupingSeparator || ',',
      decimal: locale.numberDecimalSeparator || '.',
    },
  };

  if (typeof numeral.locales[language] === 'undefined') {
    numeral.register('locale', language, LOCAL_NUMERAL_FORMAT);
  }

  if (typeof numeral.locales[language] !== 'undefined') {
    numeral.locale(language);

    if (LOCAL_NUMERAL_FORMAT.defaultFormat) {
      numeral.defaultFormat(LOCAL_NUMERAL_FORMAT.defaultFormat);
    }
  }
}

/**
 * @returns {{decimal: string, thousands: string}} the number separators of the current user session's locale.
 *          Read from the session locale itself and not from numeral, which keeps the separators of the first
 *          registration of a language for the whole tab.
 */
export const getSessionNumberDelimiters = () => sessionNumberDelimiters;

const BLANKS = /[\s\u00A0\u202F]/g; // any whitespace, incl. no-break and narrow no-break space (e.g. a pasted grouped number)

/**
 * @summary Reads a number the user typed and normalizes it to the dot-decimal form the backend expects
 *          (NumberUtils.asBigDecimal, which reads a dot-decimal with no grouping and throws on a comma). Both the comma
 *          and the dot are read as the decimal separator (so '3,57' and '3.57' are both 3.57), and there is NO thousands
 *          grouping on input - users do not type grouping separators. When a value holds BOTH separators (e.g. a pasted
 *          '1.234,56' or '1,234.56'), the LAST-occurring separator is the decimal point and the earlier one(s) are
 *          stripped. A trailing separator has no digit after it, so it is not the decimal point and is dropped
 *          ('1.5.' is 1.5). Blanks (also no-break and narrow no-break spaces) are dropped.
 * @param {string} text the raw text from the input
 * @returns {string|null} the dot-decimal number the backend expects, '' when the text holds no digit but only the
 *          characters a number may begin with (a lone '-', ',' or '.', or blanks: "nothing typed yet"), or null when
 *          the text holds any other character (a letter, a non-ASCII digit, "Infinity", ...) or more digits than a
 *          number can hold (it would overflow to Infinity) - i.e. is no number
 */
const parseDecimalNumberString = (text) => {
  const trimmed = text.trim();
  const sign = trimmed.startsWith('-') ? '-' : '';
  const unsigned = trimmed.substring(sign.length).replace(BLANKS, '');

  if (!/^[0-9.,]*$/.test(unsigned)) {
    return null; // a letter, a non-ASCII digit or any other character a number cannot hold
  }
  if (!/[0-9]/.test(unsigned)) {
    return ''; // e.g. a lone '-', ',' or '.' (or blanks only): nothing typed yet
  }

  // a trailing separator has no digit after it, so it is not the decimal point: drop it ('1.5.' -> '1.5')
  const digitsAndSeparators = unsigned.replace(/[.,]+$/, '');
  const lastSeparator = Math.max(
    digitsAndSeparators.lastIndexOf('.'),
    digitsAndSeparators.lastIndexOf(',')
  );
  let normalized;
  if (lastSeparator < 0) {
    normalized = digitsAndSeparators; // no separator: whole-number digits
  } else {
    // the last separator is the decimal point; any earlier separator is not, so it is dropped (no grouping on input)
    const integerDigits = digitsAndSeparators
      .substring(0, lastSeparator)
      .replace(/[.,]/g, '');
    const fractionDigits = digitsAndSeparators.substring(lastSeparator + 1);
    normalized = fractionDigits
      ? `${integerDigits}.${fractionDigits}`
      : integerDigits;
  }

  return normalized !== '' && Number.isFinite(Number(normalized))
    ? sign + normalized
    : null;
};

/**
 * @summary Converts a number the user typed into the dot-decimal form the backend expects, see
 *          {@link parseDecimalNumberString}. A text without any digit is read as empty; an unparseable text (e.g. one
 *          holding a letter) is returned as typed, for the backend to reject it.
 * @param {string} text the raw text from the input
 * @returns {string} the normalized number; an empty or missing value is returned as is
 */
export function normalizeDecimalNumberString(text) {
  if (typeof text !== 'string' || !text) {
    return text;
  }
  return parseDecimalNumberString(text) ?? text.trim();
}

/**
 * @summary Tells whether a text the user typed is a number that can be read - both the comma and the dot count as the
 *          decimal separator, see {@link parseDecimalNumberString}. Only genuinely unparseable input (e.g. a letter) is
 *          invalid; an empty value, or a text without any digit (e.g. '-' or '1,' on its way to a number), is valid.
 *          A decimal number input takes only a valid text, see RawWidget.handleChange.
 * @param {string} text
 */
export function isValidDecimalNumberString(text) {
  return (
    typeof text !== 'string' || !text || parseDecimalNumberString(text) !== null
  );
}

/**
 * @summary Shows a stored (dot-decimal) number for editing, with the session's decimal separator and no grouping:
 *          de 3.57 -> '3,57', 1234.5 -> '1234,5'. What it returns is read back by {@link normalizeDecimalNumberString}
 *          as the same stored value. Any other text is returned untouched.
 * @param {string|number} value
 * @param {{decimal: string, thousands: string}} [delimiters] defaults to the session's separators
 */
export function formatDecimalNumberForEditing(
  value,
  delimiters = getSessionNumberDelimiters()
) {
  const text = typeof value === 'number' ? String(value) : value;
  if (typeof text !== 'string' || !/^-?\d+(\.\d+)?$/.test(text)) {
    return text;
  }
  return text.replace('.', delimiters.decimal);
}

/**
 * @summary The notification that tells the user why a pasted or dropped text was not taken over as a number. A number
 *          may be typed with either "," or "." as the decimal separator; only a text that cannot be read as a number
 *          (e.g. one holding a letter) is refused.
 * @param {string} refusedText
 * @returns {{title: string, message: string}}
 */
export const getRefusedNumberNotification = (refusedText) => {
  const { decimal } = getSessionNumberDelimiters();
  const params = {
    text: refusedText,
    decimal,
    example: `1234${decimal}56`,
  };
  return {
    title: counterpart.translate('window.error.invalidNumber.title', {
      fallback: 'Invalid number',
    }),
    message: counterpart.translate('window.error.invalidNumber.description', {
      ...params,
      fallback: `"${refusedText}" is not a valid number: enter digits with "," or "." as the decimal separator (e.g. ${params.example}).`,
    }),
  };
};
