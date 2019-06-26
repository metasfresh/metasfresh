describe('Manufacturing order Test', function() {
  it('Test if Barcode widget shows up', function() {
    cy.visit('/window/53009');

    // Start Quickaction 'Issue/ Receipt (barcode)' and check if the barcode field is available
    cy.executeQuickAction('WEBUI_PP_Order_IssueReceipt_BarcodeLauncher');
    cy.get('.form-field-Barcode').should('exist');

    cy.get('.form-field-Barcode').should('exist');
  });

  it('Test if HU editor opens and closes', function() {
    cy.visit('/window/53009');

    cy.get('.table-flex-wrapper-row')
      .find('tbody tr')
      .eq(0)
      .should('exist');

    cy.get('.table-flex-wrapper-row')
      .find('tbody tr')
      .contains('630066')
      .click();

    cy.executeQuickAction('WEBUI_PP_Order_IssueReceipt_Launcher');
    cy.get('.panel-modal-header').should('exist');

    cy.get('.panel-modal-content .table-flex-wrapper-row')
      .find('tbody tr')
      .should('exist')
      .contains('1000033_Gitarrensaite')
      .click();

    cy.get('.panel-modal-content .document-list-included').should('not.exist');
    cy.executeQuickAction('WEBUI_PP_Order_HUEditor_Launcher');
    cy.get('.panel-modal-content .document-list-included').should('exist');

    cy.get('.panel-modal-content .document-list-included')
      .find('.document-list-header')
      .click();

    cy.get('.panel-modal-content .document-list-included').should('exist');

    cy.get('.panel-modal-header').click();

    cy.get('.panel-modal-content .document-list-included').should('not.exist');
  });
});
