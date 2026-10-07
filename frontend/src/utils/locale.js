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
const getSessionNumberDelimiters = () => sessionNumberDelimiters;

const escapeRegExp = (text) => text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');

/**
 * @summary Converts a number the user typed with the separators of the session locale into the
 *          dot-decimal form the backend expects. Blanks (also no-break and narrow no-break spaces) are dropped first.
 *          The accepted forms:
 *          - a number with the session's decimal separator: de '3,57' -> '3.57', en '3.57' -> '3.57';
 *          - for a comma-decimal session, a number with a decimal point, because edit mode shows the stored value
 *            that way: de '3.57' -> '3.57', also de '1.234' -> '1.234';
 *          - a number grouped in valid groups of three digits: de '1.234,56' / '1.234.567', en '1,234.56' / '12,345'.
 *          Anything else (e.g. en '1,5', de '1.2.3', de '1,234.56') is ambiguous and is returned as typed, so that the
 *          backend rejects it instead of storing a wrong number. A text without any digit is read as empty.
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

  const { decimal, thousands } = delimiters;
  const compact = text.replace(/\s/g, '');
  const sign = compact.startsWith('-') ? '-' : '';
  const unsigned = compact.substring(sign.length);
  const decimalPattern = escapeRegExp(decimal);
  const isBlankGrouping = /^\s$/.test(thousands); // already dropped with the blanks

  if (!/[0-9]/.test(compact)) {
    return ''; // e.g. a lone '-' or ',': nothing typed yet
  } else if (new RegExp(`^\\d*(${decimalPattern}\\d*)?$`).test(unsigned)) {
    return sign + unsigned.replace(decimal, '.');
  } else if (decimal !== '.' && /^\d*\.\d*$/.test(unsigned)) {
    return sign + unsigned;
  } else if (
    !isBlankGrouping &&
    new RegExp(
      `^\\d{1,3}(${escapeRegExp(thousands)}\\d{3})+(${decimalPattern}\\d*)?$`
    ).test(unsigned)
  ) {
    return sign + unsigned.split(thousands).join('').replace(decimal, '.');
  } else {
    return text.trim(); // ambiguous: sent as typed, so that the backend rejects it
  }
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
    delimiters.thousands,
  ];
  const compact = (text ?? '').replace(/\s/g, '');
  return [...compact].every(
    (char, idx) =>
      (char >= '0' && char <= '9') ||
      allowedSeparators.includes(char) ||
      (char === '-' && idx === 0)
  );
}
