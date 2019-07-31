/*
 * Basic command for clicking a button element having a certain text
 * @param text string to search for in the button
 */
Cypress.Commands.add('clickButtonWithText', text => {
  cy.get('button')
    .contains(text)
    .should('exist')
    .click();
});

/*
 * Basic command for clicking an element with certain selector
 * @param selector string used to query for the element
 */
Cypress.Commands.add('clickElementWithClass', (selector, forced) => {
  const opts = {};

  if (forced) {
    opts.force = true;
  }

  cy.get(selector)
    .should('exist')
    .click({ ...opts });
});

Cypress.Commands.add('selectTab', (tabName, forced) => {
  const opts = {};

  if (forced) {
    opts.force = true;
  }

  describe('Select and activate the tab with a certain name', function() {
    return cy.get(`#tab_${tabName}`).click(opts);
  });
});

Cypress.Commands.add('selectSingleTabRow', () => {
  describe('Select the only row in the currently selected tab', function() {
    cy.get('.table-flex-wrapper')
      .find('tbody tr')
      .should('exist')
      .click({ force: true });
  });
});

Cypress.Commands.add('selectReference', (refName, timeout) => {
  describe('Select reference with a certain name', function() {
    const options = {};
    if (timeout) {
      options.timeout = timeout;
    }
    return cy.get(`.reference_${refName}`, options);
  });
});

Cypress.Commands.add('openReferencedDocuments', (referenceId) => {
  cy.get('body').type('{alt}6');
  if (referenceId) {
    cy.selectReference(referenceId).click();
  }
});

/**
 * Select the nth row in a list. Starts from 0.
 *
 * @param rowNumber - the row number
 */
Cypress.Commands.add('selectNthRow', rowNumber => {
  return cy
    .get('.table-flex-wrapper')
    .find(`tbody tr:nth-child(${rowNumber + 1})`)
    .should('exist')
    .click({ force: true });
});

/**
 * Expect the table to have a specific number of rows
 *
 * @param numberOfRows - the number of rows
 */
Cypress.Commands.add('expectNumberOfRows', numberOfRows => {
  return cy.get('table tbody tr').should('have.length', numberOfRows);
});
