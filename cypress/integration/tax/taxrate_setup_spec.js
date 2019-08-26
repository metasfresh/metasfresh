import { Taxrate } from '../../support/utils/taxrate';
import { getLanguageSpecific, appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Taxrate for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/74', function() {
  let taxRateName;
  let validFrom;
  let rate;
  let defaultTaxCategory;

  it('Read fixture and prepare the names', function() {
    cy.fixture('tax/taxrate_setup_spec.json').then(f => {
      taxRateName = appendHumanReadableNow(f['taxRateName']);
      validFrom = f['validFrom'];
      rate = f['rate'];
      defaultTaxCategory = f['defaultTaxCategory'];
    });
  });
  it('Create new Taxrate', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      const taxCategoryName = getLanguageSpecific(miscDictionary, defaultTaxCategory);

      cy.fixture('tax/taxrate.json').then(taxrateJson => {
        Object.assign(new Taxrate(), taxrateJson)
          .setName(taxRateName)
          .setRate(rate)
          .setValidFrom(validFrom)
          .setTaxCategory(taxCategoryName)
          .apply();
      });
    });
  });
  it(`TEST TEST ${this.documentId} TEST TEST`, function() {
    // Test
  });
});
