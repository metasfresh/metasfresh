import { getLanguageSpecific, humanReadableNow } from '../utils/utils';
import { DocumentActionKey, DocumentStatusKey, RewriteURL } from '../utils/constants';
import { checkIfWindowCanExecuteActions } from './commands_utils';

Cypress.Commands.add('editAddress', (fieldName, addressFunction) => {
  describe(`Select ${fieldName}'s address-button and invoke the given function`, function () {
    cy.intercept('POST', '/rest/api/address').as('postAddress');

    cy.get(`.form-field-${fieldName}`).find('button').click();

    cy.wait('@postAddress').then((xhr) => {
      const requestId = xhr.response.body.id;

      Cypress.emit('emit:addressPatchResolved', requestId);
    });
    /**for each POST address request above, the event handler code needs to happen only once */
    cy.once('emit:addressPatchResolved', (requestId) => {
      cy.intercept('POST', `/rest/api/address/${requestId}/complete`).as('completeAddress');

      const outerPatchUrl = `/rest/api/address/${requestId}`;
      addressFunction(outerPatchUrl);

      cy.get(`.form-field-C_Location_ID`).click();
      cy.wait('@completeAddress');
    });
  });
});

/**
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressStartButton', (waitBeforePress) => {
  describe("Press an overlay's start-button", function () {
    if (waitBeforePress) {
      cy.wait(waitBeforePress);
    }

    cy.waitForSaveIndicator();

    const startAlias = `startButton-${humanReadableNow()}`;
    cy.intercept('GET', new RegExp(RewriteURL.QUICKACTION)).as(startAlias);

    // fail if there is a confirm dialog because it's the "do you really want to leave" confirmation which means that the record can not be saved
    // https://docs.cypress.io/api/events/catalog-of-events.html#To-catch-a-single-uncaught-exception
    cy.on('window:confirm', (str) => {
      expect(str).to.eq('Everything is awesome and the process has started');
    });

    const startText = Cypress.messages.modal.actions.start;
    cy.clickButtonWithText(startText);
    cy.wait(`@${startAlias}`);
  });
});

Cypress.Commands.add('processDocument', (action, expectedStatus) => {
  cy.log(`Execute doc action ${action}`);

  const docActionAlias = `docAction-${new Date().getTime()}`;
  cy.intercept('GET', new RegExp(RewriteURL.DocActionDropdown)).as(docActionAlias);

  cy.get('.form-field-DocAction .meta-dropdown-toggle').click();
  cy.get('.form-field-DocAction .dropdown-status-open').should('exist');
  cy.get('.form-field-DocAction .dropdown-status-list').find('.dropdown-status-item').contains(action).click();

  cy.wait(`@${docActionAlias}`, {
    requestTimeout: 20000,
    responseTimeout: 20000,
  });

  cy.waitForSaveIndicator();
  if (expectedStatus) {
    cy.log(`Verify that the doc status is now ${expectedStatus}`);
    cy.contains('.meta-dropdown-toggle .tag', expectedStatus);
  }
});

Cypress.Commands.add('openAdvancedEdit', () => {
  checkIfWindowCanExecuteActions();
  cy.log('Open the advanced edit overlay via ALT+E shortcut');
  cy.get('body').type('{alt}E');
  cy.get('.panel-modal').should('exist');
});

/*
 * Press an overlay's "Done" button. Fail if there is a confirm dialog since that means the record could not be saved.
 *
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressDoneButton', (waitBeforePress) => {
  describe("Press an overlay's done-button", function () {
    if (waitBeforePress) {
      cy.wait(waitBeforePress);
    }

    // make sure that frontend & API did their things regarding possible preceeding field inputs
    cy.waitForSaveIndicator();

    // fail if there is a confirm dialog because it's the "do you really want to leave" confrimation which means that the record can not be saved
    // https://docs.cypress.io/api/events/catalog-of-events.html#To-catch-a-single-uncaught-exception
    cy.on('window:confirm', (str) => {
      expect(str).to.eq('Everything is awesome and the data record is saved');
    });

    //webui.modal.actions.done
    const doneText = Cypress.messages.modal.actions.done;
    cy.get('.btn').contains(doneText).should('exist').click();

    cy.get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to disappear
      .should('not.exist');
  });
});

Cypress.Commands.add('pressAddNewButton', (includedDocumentIdAliasName = 'newIncludedDocumentId') => {
  // route format: /window/<windowId>/<rootDocumentId>/<tabId>/NEW
  cy.intercept('PATCH', new RegExp('/rest/api/window/[^/]+/[^/]+/[^/]+/NEW$')).as('patchNewIncludedDocument');

  const addNewText = Cypress.messages.window.addNew.caption;
  cy.get('.btn')
    .contains(addNewText)
    .should('exist')
    .click()
    .wait('@patchNewIncludedDocument')
    .then((xhr) => {
      if (!xhr.response.body.documents) {
        xhr.response.body.documents = xhr.response.body;
      }
      return { documentId: xhr.response.body.documents[0].rowId };
    })
    .as(includedDocumentIdAliasName);

  cy.get('.panel-modal').should('exist');
});

/**
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressBatchEntryButton', (waitBeforePress) => {
  describe("Press table's batch-entry-record-button", function () {
    if (waitBeforePress) {
      cy.wait(waitBeforePress);
    }

    const batchEntryText = Cypress.messages.window.batchEntry.caption;
    cy.get('.btn').contains(batchEntryText).should('exist').click();

    cy.get('.quick-input-container').should('exist');
  });
});

/**
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('closeBatchEntry', (waitBeforePress) => {
  describe("Press table's batch-entry-record-button", function () {
    if (waitBeforePress) {
      cy.wait(waitBeforePress);
    }

    cy.get('.quick-input-container .meta-icon-preview').should('exist'); // only close batch entry if it's empty
    cy.get('.indicator-pending').should('not.exist');

    cy.get('body').type('{alt}q'); // cypress can't type to `.quick-input-container`
    cy.get('.quick-input-container').should('not.exist');
    cy.get('.indicator-pending').should('not.exist');
  });
});

Cypress.Commands.add('expectDocumentStatus', (expectedDocumentStatus) => {
  describe(`Expect specific document status`, function () {
    cy.fixture('misc/misc_dictionary.json').then((miscDictionaryJson) => {
      const expectedTrl = getLanguageSpecific(miscDictionaryJson, expectedDocumentStatus);
      cy.contains('.meta-dropdown-toggle .tag', expectedTrl);
    });
  });
});

Cypress.Commands.add('completeDocument', () => {
  describe('Complete the current document', function () {
    cy.fixture('misc/misc_dictionary.json').then((miscDictionary) => {
      cy.processDocument(getLanguageSpecific(miscDictionary, DocumentActionKey.Complete), getLanguageSpecific(miscDictionary, DocumentStatusKey.Completed));
    });
  });
});

Cypress.Commands.add('reactivateDocument', () => {
  describe('Reactivate the current document', function () {
    cy.fixture('misc/misc_dictionary.json').then((miscDictionary) => {
      cy.processDocument(getLanguageSpecific(miscDictionary, DocumentActionKey.Reactivate), getLanguageSpecific(miscDictionary, DocumentStatusKey.InProgress));
    });
  });
});

Cypress.Commands.add('reverseDocument', () => {
  describe('Reverse the current document', function () {
    cy.fixture('misc/misc_dictionary.json').then((miscDictionary) => {
      cy.processDocument(getLanguageSpecific(miscDictionary, DocumentActionKey.Reverse), getLanguageSpecific(miscDictionary, DocumentStatusKey.Reversed));
    });
  });
});
