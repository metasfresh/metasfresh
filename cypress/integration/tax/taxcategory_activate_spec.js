import { Taxcategory } from '../../support/utils/taxcategory';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create Taxcategory for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/75', function() {
  it('Create and deactivate new Taxcategory', function() {
    const date = humanReadableNow();
    const name = `Test Steuer ${date}`;

    // 1. New Taxcategory and deactivate the Taxcategory
    cy.log(`Taxcategory new`);
    cy.fixture('tax/taxcategory.json')
      .as('taxCatJSON')
      .then(taxcategoryJson => {
        Object.assign(new Taxcategory(), taxcategoryJson)
          .setName(name)
          .apply();
        //.setActive(true) // 2. Deactivate Taxcategory
        //.activate();
      });
    // 2. Open Window Product Prices and new Record. Activated Taxrate can be selected.
    //    Open Product Prices
    cy.log(`Select inactive Taxcategory - name = ${name}`);
    cy.visitWindow('540325', '2767012');
    cy.selectInListField('C_TaxCategory_ID', `${name}`, false);

    // 3. Open existing Taxcategory and activate the Taxcategory
    cy.get('@taxCatObj').then(obj => {
      // access the users argument
      cy.log(`Taxcategory - ID = ${obj.documentId}`);
      cy.visitWindow('138', `${obj.documentId}`);
      cy.clickOnIsActive();
    });

    // 4. Open Window Product Prices and new Record. Activated Taxrate can be selected.
    //    Open Product Prices
    cy.log(`Select inactive Taxcategory - name = ${name}`);
    cy.visitWindow('540325', '2767012');
    cy.get(`.form-field-C_TaxCategory_ID`)
      .find('.input-dropdown')
      .click();

    cy.get('.input-dropdown-list-option')
      .contains(`${name}`)
      .should('not.exist');
  });
});
