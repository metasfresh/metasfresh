function executeHeaderAction(actionName) {
  cy.get('.header-container .btn-square .meta-icon-more').click();
  cy.get('.subheader-container').should('exist');
  cy.get(`#headerAction_${actionName}`).click();
}

Cypress.Commands.add('clickHeaderNav', navName => {
  const name = navName.toLowerCase().replace(/\s/g, '');

  describe('Fire header action with a certain name', function() {
    cy.get('.header-container .btn-header').click();
    cy.get('.subheader-container').should('exist');

    return cy.get(`#subheaderNav_${name}`).click();
  });
});

Cypress.Commands.add('executeHeaderAction', actionName => {
  describe('Fire header action with a certain name', function() {
    executeHeaderAction(actionName);
  });
});

Cypress.Commands.add('executeHeaderActionWithDialog', actionName => {
  describe('Fire header action with a certain name and expect a modal dialog to pop up within 10 secs', function() {
    executeHeaderAction(actionName);

    return cy
      .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('exist');
  });
});

/*
 * This command runs a quick actions. If the second parameter is truthy, the default action will be executed.
 */
Cypress.Commands.add('executeQuickAction', (actionName, active) => {
  describe('Fire a quick action with a certain name', function() {
    let path = `.quick-actions-wrapper`; // default action

    if (!active) {
      cy.get('.quick-actions-wrapper .btn-inline')
        .eq(0)
        .click();
      cy.get('.quick-actions-dropdown').should('exist');

      path = `#quickAction_${actionName}`;
    }

    return cy
      .get(path)
      .click()
      .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('exist');
  });
});
