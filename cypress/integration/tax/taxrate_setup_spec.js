import { Taxrate } from '../../support/utils/taxrate';
import { getLanguageSpecific } from '../../support/utils/utils';

describe('Create Taxrate for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/74', function() {
  it('Create new Taxrate', function() {
    cy.wait(1000); // see comment/doc of getLanguageSpecific

    const timestamp = new Date().getTime();
    const taxrateName = `Text-Tax 10% ${timestamp}`;

    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      const taxCategoryName = getLanguageSpecific(miscDictionary, 'defaultTaxCategory');

      cy.fixture('tax/taxrate.json').then(taxrateJson => {
        Object.assign(new Taxrate(), taxrateJson)
          .setName(taxrateName)
          .setRate(10)
          .setValidFrom('2019/01/01')
          .setTaxCategory(taxCategoryName)
          .apply();
      });
    });
  });
});
