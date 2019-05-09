import { Taxcategory } from '../../support/utils/taxcategory';

describe('Create Taxcategory for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/75', function() {
  it('Create and deactivate new Taxcategory', function() {
    cy.fixture('tax/taxcategory.json').then(taxcategoryJson => {
      Object.assign(new Taxcategory('Test Steuer'), taxcategoryJson)
        .apply()
        .setActive(false)
        .activate();
    });
    cy.get('@taxCatObj').then(obj => {
      // access the users argument
      cy.log(`Taxcategory - Name = ${obj.documentId}`);
    });

    // 2. Deactivate Taxrate

    // 3. Open Window Product Prices and new Record. Deactivated Taxrate cannot be selected.

    // 4. Activate Taxrate

    // 5. Open Window Product Prices and new Record. Activated Taxrate can be selected.
  });
});
