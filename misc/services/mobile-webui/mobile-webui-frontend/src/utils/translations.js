import counterpart from 'counterpart';
import translations_en from './translations_en';
import translations_de from './translations_de';

import { getApplicationMessages } from '../apps';
import { getMessages } from '../api/configuration';

/** Just a shortcut & abstraction of counterpart's translate function */
export const trl = (key, args = {}) => {
  return counterpart.translate(key, args);
};

export const setupCounterpart = () => {
  console.log('Setting up counterpart defaults...');

  counterpart.setMissingEntryGenerator(generateMissingTranslation);
  counterpart.registerTranslations('en', translations_en);
  counterpart.registerTranslations('de', translations_de);

  const applicationsMessages = getApplicationMessages();
  Object.keys(applicationsMessages).forEach((locale) => {
    counterpart.registerTranslations(locale, applicationsMessages[locale]);
    console.log(`Registered apps messages ${locale}`, applicationsMessages[locale]);
  });

  registerBackendMessages({ lang: 'en' });
  registerBackendMessages({ lang: 'de' });

  //setLanguage('de_DE'); // used only for debugging
};

const registerBackendMessages = ({ lang }) => {
  getMessages({ lang })
    .then(({ messages }) => {
      counterpart.registerTranslations(lang, messages);
      console.log(`Registered backend messages for ${lang}`, { messages });
    })
    .catch((error) => {
      console.warn(`Got error while trying to register backend messages for '${lang}' language`, { error });
    });
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
