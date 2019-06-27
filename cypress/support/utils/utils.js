/*
 * IMPORTANT: be sure not to do this right at the start of a spec; if in doubt, do an extra "cy.wait(1000)".
 * Otherwise you will likely get this error
 *   "TypeError: Cannot read property 'key' of undefined"

 * Because often times we have to test for different languagues and some values in our fixtures
 * may be language-dependent, we have to provide versions for different languages prepended with
 * `language key`, like de_DE. Then instead of directly fetching data from the fixtures json
 * we pass it through this function, which selects keys accordingly.
 * 
 * @example
 * cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
 *     const taxCategoryName = getLanguageSpecific(miscDictionary, 'defaultTaxCategory');
 * });
 * 
 */
const getLanguageSpecific = (data, key) => {
  // this is more of a guard; it succeeds, even if this whole function fails with; comment it back in, if you are interested, or see a benefit
  // cy.window()
  //   .its('store')
  //   .invoke('getState')
  //   .its('appHandler.me.language.key')
  //   .should('exist');

  const lang = Cypress.reduxStore.getState().appHandler.me.language.key;
  if (lang !== 'en_US') {
    key = `${lang}__${key}`;
  }
  return data[key];

  // 1. this gives me
  //    "CypressError: cy.then() failed because you are mixing up async and sync code.""
  // 2. if it worked, I still would not know how to wait for all of this to be done asynchronously, and then return my result
  // cy.wrap(data)
  //   .should('exist')
  //   .then(dataObj => {
  //     cy.log(`dataObj=${JSON.stringify(dataObj)}`);
  //     if (lang !== 'en_US') {
  //       key = `${lang}__${key}`;
  //     }
  //     return { trl: dataObj[key] };
  //   })
  //   .as(`${key}_trl`);
};

export { getLanguageSpecific };
