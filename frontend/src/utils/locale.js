import axios from 'axios';
import Moment from 'moment';

import LOCAL_LANG from '../constants/Constants';

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

export function setCurrentActiveLocale(lang) {
  localStorage.setItem(LOCAL_LANG, lang);
  Moment.locale(lang);
  axios.defaults.headers.common['Accept-Language'] = lang;
}
