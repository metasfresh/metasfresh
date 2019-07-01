describe('Create Pricing Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/14', function() {
  it('Create a new Pricesystem and Pricelist with PLV', function() {
    const timestamp = new Date().getTime(); // used in the document names, for ordering

    //Pricesystem
    cy.visitWindow('540320', 'NEW');
    cy.writeIntoStringField('Name', `TestPriceSystemName ${timestamp}`);
    cy.clearField('Value');
    cy.writeIntoStringField('Value', `TestPriceSystemValue ${timestamp}`);

    //Pricelist
    cy.visitWindow('540321', 'NEW');
    cy.writeIntoStringField('Name', `TestPriceListName ${timestamp}`);
    cy.selectInListField('M_PricingSystem_ID', `TestPriceSystemName ${timestamp}`);
    cy.selectInListField('C_Currency_ID', 'EUR');

    //PLV
    cy.pressAddNewButton();
    cy.clearField('Name', true);
    cy.writeIntoStringField('Name', `TestPLV ${timestamp}`, true);
    cy.pressDoneButton();
  });
});
