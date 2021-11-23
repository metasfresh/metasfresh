import counterpart from 'counterpart';
import translations_en from './translations_en';
import translations_de from './translations_de';
<<<<<<< HEAD

import { getApplicationMessages } from '../apps';

/** Just a shortcut & abstraction of counterpart's translate function */
export const trl = (key, ...args) => {
  return counterpart.translate(key, ...args);
};
=======
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))

export const setupCounterpart = () => {
  console.log('Setting up counterpart defaults...');

  counterpart.setMissingEntryGenerator(generateMissingTranslation);
  counterpart.registerTranslations('en', translations_en);
  counterpart.registerTranslations('de', translations_de);

<<<<<<< HEAD
  const applicationsMessages = getApplicationMessages();
  Object.keys(applicationsMessages).forEach((locale) => {
    counterpart.registerTranslations(locale, applicationsMessages[locale]);
    console.log(`Registered apps messages ${locale}`, applicationsMessages[locale]);
  });

  //setLanguage('de_DE'); // used only for debugging
=======
  setLanguage('de_DE');
>>>>>>> 3c402d269b2 (mobile UI: fix trls (#12043))
};

const generateMissingTranslation = (key) => {
  // eslint-disable-next-line no-console
  console.error(`Missing translation: ${key}`);

  if (!key) {
    return '';
  }

  const idx = key.lastIndexOf('.');
  if (idx > 0) {
    return key.substring(idx + 1);
  } else {
    return `${key}`;
  }
};

export const setLanguage = (language) => {
  const idx = language.indexOf('_');
  if (idx > 0) {
    language = language.substr(0, idx);
  }

  console.log('Setting language to ', language);
  counterpart.setLocale(language);
};

export const getLanguage = () => {
  return counterpart.getLocale();
};
