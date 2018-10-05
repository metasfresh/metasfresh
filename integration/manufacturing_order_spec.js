describe('Manufacturing order Test', function() {
  before(function() {
    cy.loginByForm();
  });

  it('Test if Barcode widget shows up', function() {
    cy.visit('/window/53009');

    // Start Quickaction 'Issue/ Receipt (barcode)' and check if the barcode field is available
    cy.executeQuickAction('WEBUI_PP_Order_IssueReceipt_BarcodeLauncher');
    cy.get('.form-field-Barcode').should('exist');

    cy.get('.form-field-Barcode').should('exist');
  });
});