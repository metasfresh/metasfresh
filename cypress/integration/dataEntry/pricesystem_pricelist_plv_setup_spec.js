describe('Create Pricing Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/14', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  it('Create a new Pricesystem and Pricelist with PLV', function() {
    //Pricesystem
    cy.visitWindow('540320', 'NEW');
    cy.writeIntoStringField('Name', 'TestPriceSystem');
    cy.clearField('Value')
    cy.writeIntoStringField('Value', 'TestPriceSystem');

    //Pricelist
    cy.visitWindow('540321', 'NEW');
    cy.writeIntoStringField('Name', 'TestPriceList');
    cy.selectInListField('M_PricingSystem_ID', 'TestPriceSystem');
    cy.selectInListField('C_Currency_ID', 'EUR');

    //PLV
    cy.pressAddNewButton();
    cy.clearField('Name', 'T', true);
    cy.writeIntoStringField('Name', 'TestPLV', true);
    cy.pressDoneButton();
  });
});
