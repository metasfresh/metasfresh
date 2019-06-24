describe('Creating GL journal entry and change posting type', function() {
  before(function() {
    cy.visit('/window/540356');
  });

  it('create journal', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', 'Spende');
    cy.selectInListField('GL_Category_ID', 'Standard');
    cy.selectInListField('PostingType', 'Actual');
    cy.selectInListField('C_Currency_ID', 'CHF');
    cy.selectInListField('C_DocType_ID', 'Journal');
    cy.selectDateViaPicker('DateDoc');
  });

  it('add new entry line', function() {
    cy.pressAddNewButton();
    cy.writeIntoStringField('Description', 'Spende', true);
    cy.writeIntoLookupListField('Account_DR_ID', '10', '1020_UBS Kontokorrent');
    cy.writeIntoStringField('AmtSourceDr', '100.00');
    cy.writeIntoLookupListField('Account_CR_ID', '10', '1020_UBS Kontokorrent');
    cy.selectInListField('C_Activity_ID', '250_Verwaltung');
    cy.pressDoneButton();
  });

  it('complete', function() {
    cy.processDocument('Complete', 'Completed');
  });
});
