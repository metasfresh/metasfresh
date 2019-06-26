import { Taxcategory } from '../../support/utils/taxcategory';

describe('Create Taxcategory for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/75', function() {
  it('Create and deactivate new Taxcategory', function() {
    const timestamp = new Date().getTime();
    const name = `Test Steuer ${timestamp}`;

    // 1. New Taxcategory and deactivate the Taxcategory
    cy.log(`Taxcategory new`);
    cy.fixture('tax/taxcategory.json')
      .as('taxCatJSON')
      .then(taxcategoryJson => {
        Object.assign(new Taxcategory(), taxcategoryJson)
          .setName(name)
          .apply()
          .setActive(false) // 2. Deactivate Taxcategory
          .activate();
      });
    // 2. Open Window Product Prices and new Record. Deactivated Taxrate cannot be selected.
    //Pricesystem
    cy.log(`Select inactive Taxcategory - name = ${name}`);
    cy.visitWindow('540325', '2767012');
    cy.clearField('C_TaxCategory_ID');
    cy.selectInListField('C_TaxCategory_ID', `${name}`, false).should('not.exist');

    // 3. Open existing Taxcategory and activate the Taxcategory
    cy.get('@taxCatObj').then(obj => {
      // access the users argument
      cy.log(`Taxcategory - ID = ${obj.documentId}`);
      cy.get('@taxCatJSON').then(json => {
        // access the users argument
        Object.assign(new Taxcategory(), json)
          .setName(name)
          .setID(obj.documentId)
          .setActive(true) // 4. Activate Taxcategory
          .activate();
      });
    });

    // 5. Open Window Product Prices and new Record. Activated Taxrate can be selected.
    cy.log(`Select inactive Taxcategory - name = ${name}`);
    cy.visitWindow('540325', '2767012');
    //cy.clearField('C_TaxCategory_ID');
    cy.selectInListField('C_TaxCategory_ID', `${name}`, false);
  });
});
