describe('Product DocumentNote test', function() {
  before(function() {
    cy.loginByForm();
  });

  it('open the product window', function() {
    cy.visit('window/140/2005577');
    cy.waitForHeader();
    cy.openAdvancedEdit();
    cy.writeIntoTextField('DocumentNote', '{selectall}{backspace}blah-blah-blah');
    cy.pressDoneButton();
  });
});
