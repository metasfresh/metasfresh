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

export function initNumeralLocales(lang, locale) {
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
 * @returns {{decimal: string, thousands: string}} the number separators of the logged-in user's session locale,
 *          as registered into numeral by {@link initNumeralLocales}
 */
const getSessionNumberDelimiters = () => numeral.localeData().delimiters;

/**
 * @summary Converts a number the user typed with the separators of the session locale into the
 *          dot-decimal form the backend expects. The rules:
 *          - when the text contains the session's decimal separator, every grouping separator is dropped
 *            and the decimal separator becomes a dot (de: '1.234,56' -> '1234.56', en: '1,234.56' -> '1234.56');
 *          - when it does not, a single dot is still read as the decimal point, so '3.57' keeps working for a
 *            user whose decimal separator is a comma; any other separator is a grouping one
 *            (de: '1.234.567' -> '1234567', en: '1,234' -> '1234');
 *          - a text without any digit is read as empty; a text with more than one decimal separator, or with
 *            a separator after the decimal one, is ambiguous and is returned as typed for the backend to reject it.
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
  const trimmed = text.trim();
  const decimalIdx = trimmed.lastIndexOf(decimal);

  if (!/[0-9]/.test(trimmed)) {
    return ''; // e.g. a lone '-' or ',': nothing typed yet
  } else if (decimal !== '.' && decimalIdx >= 0) {
    const integerPart = trimmed.substring(0, decimalIdx);
    const fractionPart = trimmed.substring(decimalIdx + 1);
    if (
      integerPart.includes(decimal) ||
      /[^0-9]/.test(fractionPart) // e.g. a pasted English '1,234.56' in a German session
    ) {
      return trimmed; // ambiguous: sent as typed, so that the backend rejects it instead of storing a wrong number
    }
    return `${removeSeparators(integerPart, [
      thousands,
      '.',
      ',',
    ])}.${fractionPart}`;
  } else if (
    decimal === '.' &&
    trimmed.indexOf('.') !== trimmed.lastIndexOf('.')
  ) {
    return trimmed; // ambiguous: more than one decimal point
  } else if (trimmed.indexOf('.') === trimmed.lastIndexOf('.')) {
    return removeSeparators(
      trimmed,
      [thousands, ','].filter((s) => s !== '.')
    );
  } else {
    return removeSeparators(trimmed, [thousands, '.', ',']);
  }
}

const removeSeparators = (text, separators) =>
  separators.reduce((acc, separator) => acc.split(separator).join(''), text);

/**
 * @summary Tells whether a text may be typed into a decimal number input: digits, a leading minus,
 *          dots, commas and the session's grouping separator (e.g. the Swiss apostrophe).
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
  return [...(text ?? '')].every(
    (char, idx) =>
      (char >= '0' && char <= '9') ||
      allowedSeparators.includes(char) ||
      (char === '-' && idx === 0)
  );
}
