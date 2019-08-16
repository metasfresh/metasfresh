import { checkIfWindowCanExecuteActions } from './commands_utils';
import { RewriteURL } from '../utils/constants';
import { humanReadableNow } from '../utils/utils';

function executeHeaderAction(actionName) {
  checkIfWindowCanExecuteActions();
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
    cy.route('GET', new RegExp(RewriteURL.DocumentLayout)).as('dialogLayout');

    executeHeaderAction(actionName);

    cy.wait('@dialogLayout');

    return cy
      .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('exist');
  });
});

// eslint-disable-next-line prettier/prettier
Cypress.Commands.add('executeQuickAction', (actionName, defaultAction = false, modal = false, isDialogExpected = true) => {
    let path = `.quick-actions-wrapper`; // default action
    const requestAlias = `quickAction-${actionName}-${humanReadableNow()}`;

  cy.waitForSaveIndicator();

    if (modal) {
      path = '.modal-content-wrapper ' + path;
    }

    if (!defaultAction) {
      cy.get(`${path} .btn-inline`)
        .eq(0)
        .click();
      cy.get('.quick-actions-dropdown').should('exist');

      path = `#quickAction_${actionName}`;
    }
    if (!defaultAction && !isDialogExpected) {
      cy.server();
      cy.route('GET', new RegExp(RewriteURL.QUICKACTION)).as(requestAlias);
    }

    cy.get(path)
      .should('not.have.class', 'quick-actions-item-disabled')
      .get(path)
      .click({ timeout: 10000 })
      .then(el => {
        if (isDialogExpected) {
          cy.wrap(el)
            .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
            .should('exist');
        }
      });

    if (!defaultAction && !isDialogExpected) {
      cy.wait(`@${requestAlias}`);
    }
    cy.waitForSaveIndicator();
  }
);
