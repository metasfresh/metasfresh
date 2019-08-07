import { humanReadableNow } from '../../support/utils/utils';

describe('Create Pricing Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/14', function() {
  it('Create a new Pricesystem and Pricelist with PLV', function() {
    const date = humanReadableNow();

    //Pricesystem
    cy.visitWindow('540320', 'NEW');
    cy.writeIntoStringField('Name', `TestPriceSystemName ${date}`);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', `TestPriceSystemValue ${date}`);

    //Pricelist
    cy.visitWindow('540321', 'NEW');
    cy.writeIntoStringField('Name', `TestPriceListName ${date}`);
    cy.selectInListField('M_PricingSystem_ID', `TestPriceSystemName ${date}`);
    cy.selectInListField('C_Currency_ID', 'EUR');

    //PLV
    cy.pressAddNewButton();
    cy.clearField('Name', true);
    cy.writeIntoStringField('Name', `TestPLV ${date}`, true);
    cy.pressDoneButton();
  });
});
