describe('Creating GL journal entry and change posting type', function() {
  before(function() {
    cy.visit('/window/540356');
  });

  it('create tour', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);

    cy.writeIntoStringField('Description', 'test');
    cy.selectInListField('GL_Category_ID', 'Standard');
    cy.selectInListField('PostingType', 'Statistical');
    cy.selectInListField('C_Currency_ID', 'CHF');
    cy.selectInListField('C_DocType_ID', 'Journal');
    cy.selectDateViaPicker('DateDoc');
    cy.selectInListField('PostingType', 'Budget');
    cy.selectInListField('PostingType', 'Ist (Jahr Ende)');
    cy.selectInListField('PostingType', 'Statistical');
  });
});
