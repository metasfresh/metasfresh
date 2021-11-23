import counterpart from 'counterpart';
import translations_en from './translations_en';
import translations_de from './translations_de';

export const setupCounterpart = () => {
  console.log('Setting up counterpart defaults...');

  counterpart.setMissingEntryGenerator(function (key) {
    // eslint-disable-next-line no-console
    console.error(`Missing translation: ${key}`);
    return `${key}`;
  });

  counterpart.registerTranslations('en', translations_en);
  counterpart.registerTranslations('de', translations_de);

  // setLanguage('de_DE');
};

export const setLanguage = (language) => {
  const idx = language.indexOf('_');
  if (idx > 0) {
    language = language.substr(0, idx);
  }

  console.log('Setting language to ', language);
  counterpart.setLocale(language);
};
