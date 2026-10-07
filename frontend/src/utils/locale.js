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

const escapeRegExp = (text) => text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');

const BLANKS = ' \u00A0\u202F'; // space, no-break space, narrow no-break space
const APOSTROPHES = "'\u2019"; // ASCII and typographic apostrophe, e.g. de_CH grouping

/** The characters that may group thousands: the session's grouping separator (both apostrophes for an apostrophe), and blanks */
const getGroupingCharacters = (thousands) =>
  (APOSTROPHES.includes(thousands) ? APOSTROPHES : thousands) + BLANKS;

/**
 * @summary Reads a number the user typed with the separators of the session locale. Valid are only:
 *          - a number with the session's decimal separator: de '3,57', en '3.57';
 *          - a number grouped in valid groups of three digits: de '1.000' / '1.234,56', en '1,000' / '1,234.56'; a blank
 *            (also no-break or narrow no-break space) may group too ('1 234,56'), and the first group has no leading zero.
 *          So in a German session a dot is a grouping separator only: '3.57', '1.2', '0.500' or '3 57' are invalid;
 *          English mirrors it.
 * @param {string} text the raw text from the input
 * @param {{decimal: string, thousands: string}} [delimiters] defaults to the session's separators
 * @returns {string|null} the dot-decimal number the backend expects, '' when the text holds no digit (e.g. '-'),
 *          or null when the text is no valid number
 */

const parseDecimalNumberString = (text, delimiters) => {
  const { decimal, thousands } = delimiters;
  const trimmed = text.trim();
  const sign = trimmed.startsWith('-') ? '-' : '';
  const unsigned = trimmed.substring(sign.length);
  const decimalPattern = escapeRegExp(decimal);
  const groupingClass = `[${escapeRegExp(getGroupingCharacters(thousands))}]`;

  if (!/[0-9]/.test(trimmed)) {
    return ''; // e.g. a lone '-' or ',': nothing typed yet
  } else if (new RegExp(`^\\d*(${decimalPattern}\\d*)?$`).test(unsigned)) {
    return sign + unsigned.replace(decimal, '.');
  } else if (
    new RegExp(
      `^[1-9]\\d{0,2}(${groupingClass}\\d{3})+(${decimalPattern}\\d*)?$`
    ).test(unsigned)
  ) {
    return (
      sign +
      unsigned.replace(new RegExp(groupingClass, 'g'), '').replace(decimal, '.')
    );
  } else {
    return null;
  }
};

/**
 * @summary Converts a number the user typed with the separators of the session locale into the dot-decimal form the
 *          backend expects, see {@link isValidDecimalNumberString} for what is valid. A text without any digit is read
 *          as empty; an invalid text is returned as typed.
 * @param {string} text the raw text from the input
 * @param {{decimal: string, thousands: string}} [delimiters] defaults to the session's separators
 * @returns {string} the normalized number; an empty or missing value is returned as is
 */
export function normalizeDecimalNumberString(
  text,
  delimiters = getSessionNumberDelimiters()
) {
  if (typeof text !== 'string' || !text) {
    return text;
  }
  return parseDecimalNumberString(text, delimiters) ?? text.trim();
}

/**
 * @summary Tells whether a text the user typed is a valid number with the separators of the session locale:
 *          the session's decimal separator, and grouping only in valid groups of three digits
 *          (de '3,57', '1.000', '1.234,56'; not '3.57' or '1.2'). An empty value, or a text without any digit, is valid.
 * @param {string} text
 * @param {{decimal: string, thousands: string}} [delimiters] defaults to the session's separators
 */
export function isValidDecimalNumberString(
  text,
  delimiters = getSessionNumberDelimiters()
) {
  return (
    typeof text !== 'string' ||
    !text ||
    parseDecimalNumberString(text, delimiters) !== null
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
 * @summary Tells whether a text may be typed into a decimal number input: digits, a leading minus, dots, commas,
 *          blanks and the session's own separators (e.g. the Swiss apostrophe). Whether the text is a valid number is
 *          decided by {@link normalizeDecimalNumberString}, because while typing e.g. '1,2' is on its way to '1,234'.
 * @param {string} text
 * @param {{decimal: string, thousands: string}} [delimiters] defaults to the session's separators
 */
export function isAllowedDecimalNumberInput(
  text,
  delimiters = getSessionNumberDelimiters()
) {
  const allowedSeparators = [
    '.',
    ',',
    delimiters.decimal,
    ...getGroupingCharacters(delimiters.thousands),
  ];
  const compact = (text ?? '').replace(/\s/g, '');
  return [...compact].every(
    (char, idx) =>
      (char >= '0' && char <= '9') ||
      allowedSeparators.includes(char) ||
      (char === '-' && idx === 0)
  );
}
