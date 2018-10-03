describe('Manufacturing order Test', function() {
  before(function() {
    cy.loginByForm();
  });

  it('Test if Barcode widget shows up', function() {
    cy.visit('/window/53009');

    cy.executeQuickAction('ADP_540788');

    cy.get('.form-field-Barcode').should('exist');
  });
});