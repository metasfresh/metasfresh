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
