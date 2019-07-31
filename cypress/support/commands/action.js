function executeHeaderAction(actionName) {
  /**
   * Only specific windows can have actions. They match one of the following urls:
   *
   * https://dev586.metasfresh.com/window/123?viewId=123-o&page=1 - list view
   *    - in this case also '.table-flex-wrapper' should exist
   * https://dev586.metasfresh.com/window/123/2156425 - single view
   *    - in this case also '.panel' should exist
   *
   * This match is needed because cypress is so fast that it may press the action button before any viewId is available, and the system will error out.
   */
  cy.url().should('matches', new RegExp(`window/[0-9]+(/[0-9]+|.*viewId=)`));

  cy.url().then(url => {
    const listViewRegexp = new RegExp(`window/[0-9]+.*viewId=`);
    // const singleViewRegexp = new RegExp(`window/[0-9]+/[0-9]+`);

    if (url.match(listViewRegexp)) {
      cy.get('.table-flex-wrapper').should('exist');
    } else {
      cy.get('.panel .row').should('exist');
    }
  });

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
    cy.server();
    cy.route('GET', 'rest/api/process/*/layout').as('dialogLayout');

    executeHeaderAction(actionName);

    cy.wait('@dialogLayout');

    return cy
      .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('exist');
  });
});

Cypress.Commands.add('executeQuickAction', (actionName, active, modal = false) => {
  describe('Fire a quick action with a certain name', function() {
    let path = `.quick-actions-wrapper`; // default action

    if (modal) {
      path = '.modal-content-wrapper ' + path;
    }

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
