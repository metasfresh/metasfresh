/*
 * Because often times we have to test for different languagues and some values in our fixtures
 * may be language-dependent, we have to provide versions for different languages prepended with
 * `language key`, like de_DE. Then instead of directly fetching data from the fixtures json
 * we pass it through this function, which selects keys accordingly.
 */
const getLanguageSpecific = (data, key) => {
  const lang = Cypress.reduxStore.getState().appHandler.me.language.key;

  if (lang !== 'en_US') {
    key = `${lang}__${key}`;
  }

  return data[key];
};

export { getLanguageSpecific };
