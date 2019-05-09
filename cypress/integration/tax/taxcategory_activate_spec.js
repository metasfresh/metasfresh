import { Taxcategory } from '../../support/utils/taxcategory';

describe('Create Taxcategory for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/75', function() {
  it('Create and deactivate new Taxcategory', function() {
    // 1. New Taxcategory and deactivate the Taxcategory
    cy.log(`Taxcategory new`);
    cy.fixture('tax/taxcategory.json')
      .as('taxCatJSON')
      .then(taxcategoryJson => {
        Object.assign(new Taxcategory('Test Steuer'), taxcategoryJson)
          .apply()
          .setActive(false) // 2. Deactivate Taxcategory
          .activate();
      });
    // 3. Open Window Product Prices and new Record. Deactivated Taxrate cannot be selected.

    cy.get('@taxCatObj').then(obj => {
      // access the users argument
      cy.log(`Taxcategory - ID = ${obj.documentId}`);
      cy.get('@taxCatJSON').then(json => {
        // access the users argument
        Object.assign(new Taxcategory(), json)
          .setID(obj.documentId)
          .setActive(true) // 4. Activate Taxcategory
          .activate();
      });
    });
  });
});
// 5. Open Window Product Prices and new Record. Activated Taxrate can be selected.
