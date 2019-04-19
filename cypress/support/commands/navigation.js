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
Cypress.Commands.add('clickElementWithClass', selector => {
  cy.get(selector)
    .should('exist')
    .click();
});

Cypress.Commands.add('selectSingleTabRow', () => {
  describe('Select the only row in the currently selected tab', function() {
    cy.get('.table-flex-wrapper')
      .find('tbody tr')
      .should('exist')
      .click();
  });
});

Cypress.Commands.add('selectTab', tabName => {
  describe('Select and activate the tab with a certain name', function() {
    return cy.get(`#tab_${tabName}`).click();
  });
});

Cypress.Commands.add('selectReference', refName => {
  describe('Select reference with a certain name', function() {
    return cy.get(`.reference_${refName}`);
  });
});
