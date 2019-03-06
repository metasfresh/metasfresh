// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add("login", (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add("drag", { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add("dismiss", { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This is will overwrite an existing command --
// Cypress.Commands.overwrite("visit", (originalFn, url, options) => { ... })

// https://www.cypress.io/blog/2018/01/16/end-to-end-snapshot-testing/#End-to-end-snapshot-testing
require('@cypress/snapshot').register()

import { List } from 'immutable';
import { goBack, push } from 'react-router-redux';

import { loginSuccess } from '../../src/actions/AppActions';
import Auth from '../../src/services/Auth';
import config from '../config';

context('Reusable "login" custom command', function() {
  Cypress.Commands.add('loginByForm', (username, password, redirect) => {
    let user = username;
    let pass = password;

    if (!username || !password) {
      user = config.username;
      pass = config.password;
    }

    Cypress.log({
      name: 'loginByForm',
      message: user + ' | ' + pass,
    });

    const handleSuccess = function() {
      if (redirect) {
        Cypress.reduxStore.dispatch(goBack());
      } else {
        Cypress.reduxStore.dispatch(push('/'));
      }
    };

    const checkIfAlreadyLogged = function() {
      const error = new Error('Error when checking if user logged in');

      return cy
        .request({
          method: 'GET',
          url: config.API_URL + '/login/isLoggedIn',
          failOnStatusCode: false,
          followRedirect: false,
        })
        .then(response => {
          if (!response.body.error) {
            return Cypress.reduxStore.dispatch(push('/'));
          }

          return Promise.reject(error);
        });
    };

    const auth = new Auth();

    cy.on('emit:reduxStore', store => {
      Cypress.reduxStore = store;
    });

    cy.visit('/login');

    return cy
      .request({
        method: 'POST',
        url: config.API_URL + '/login/authenticate',
        failOnStatusCode: false,
        followRedirect: false,
        body: {
          username: user,
          password: pass,
        },
      })
      .then(response => {
        if (!response.isOkStatusCode) {
          return checkIfAlreadyLogged();
        }

        if (response.body.loginComplete) {
          return handleSuccess();
        }
        const roles = List(response.body.roles);

        return cy
          .request({
            method: 'POST',
            url: config.API_URL + '/login/loginComplete',
            body: roles.get(0),
            failOnStatusCode: false,
          })
          .then(() => {
            Cypress.reduxStore.dispatch(loginSuccess(auth));

            handleSuccess();
          });
      });
  });
});

/*
 * @param modal - use true if the field is in a modal overlay; requiered if the underlying window has a field with the same name
 */
Cypress.Commands.add('clickOnCheckBox', (fieldName, modal) => {
  describe('Click on a checkbox field', function() {

    cy.log(`clickOnCheckBox - fieldName=${fieldName}`);

    cy.server()
    cy.route('PATCH', '/rest/api/window/**').as('patchCheckBox')
    cy.route('GET', '/rest/api/window/**').as('getData')

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
  
    cy.get(path)
      .find('.input-checkbox-tick')
      .click()
      .wait(['@patchCheckBox', '@getData'])
  });
});

Cypress.Commands.add('clickOnIsActive', (modal) => {
  describe('Click on the IsActive slider', function() {

    let path = `.form-field-IsActive`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    cy.get(path)
      .find('.input-slider')
      .click();
  });
});

/** 
 * Should also work for date columns, e.g. '01/01/2018{enter}'.
 * 
 * @param modal - use true if the field is in a modal overlay; requiered if the underlying window has a field with the same name
 */
Cypress.Commands.add('writeIntoStringField', (fieldName, stringValue, modal) => {
  describe('Enter value into string field', function() {

    cy.log(`writeIntoStringField - fieldName=${fieldName}; stringValue=${stringValue}`);
   
    // here we want to match URLs that don *not* end with "/NEW"
    cy.server()
    cy.route('PATCH', new RegExp('/rest/api/window/.*[^/][^N][^E][^W]$')).as(`patchInputField`)

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('input')
      .type(`${stringValue}{enter}`)
      .wait('@patchInputField')
  });
});

/**
 * @param modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 */
Cypress.Commands.add('writeIntoTextField', (fieldName, stringValue, modal) => {
  describe('Enter value into text field', function() {

      cy.log(`writeIntoTextField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}`);
      
      // here we want to match URLs that don *not* end with "/NEW"
      cy.server()
      cy.route('PATCH', new RegExp('/rest/api/window/.*[^/][^N][^E][^W]$')).as('patchTextArea')

      let path = `.form-field-${fieldName}`;
      if (modal) {
        path = `.panel-modal ${path}`;
      }
      cy.get(path)
        .find('textarea')
        .type(`${stringValue}{enter}`)
        .wait('@patchTextArea')
    });
  });

/**
 * @param modal - use true, if the field is in a modal overlay; requiered if the underlying window has a field with the same name
 */
Cypress.Commands.add(
  'writeIntoLookupListField',
  (fieldName, partialValue, listValue, modal) => {
    describe('Enter value into lookup list field', function() {

      // here we want to match URLs that don *not* end with "/NEW"
      cy.server()
      cy.route('PATCH', new RegExp('/rest/api/window/.*[^/][^N][^E][^W]$')).as(`patchLookupField`)

      let path = `#lookup_${fieldName}`;
      if (modal) {
        //path = `.panel-modal-content ${path}`;
        path = `.panel-modal ${path}`;
      }

      cy.get(path)
        .within(($el) => {
          if ($el.find('.lookup-widget-wrapper input').length) {
            return cy.get('input').clear()
              .type(partialValue);
          }

          return cy.get('.lookup-dropdown').click();
        })

      cy.get('.input-dropdown-list').should('exist')
      cy.contains('.input-dropdown-list-option', listValue).click({ force: true })
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist')
      cy.wait('@patchLookupField')
    });
});

/**
 * Select the given list value in a static list.
 * 
 * @param modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 */
Cypress.Commands.add('selectInListField', (fieldName, listValue, modal) => {
  describe('Select value in list field', function() {

      cy.log(`selectInListField - fieldName=${fieldName}; listValue=${listValue}; modal=${modal}`);

      // here we want to match URLs that don *not* end with "/NEW"
      cy.server()
      cy.route('PATCH', new RegExp('/rest/api/window/.*[^/][^N][^E][^W]$')).as(`patchListField`)

      let path = `.form-field-${fieldName}`;
      if (modal) {
        //path = `.panel-modal-content ${path}`;
        path = `.panel-modal ${path}`;
      }
      cy.get(path)
        .find('.input-dropdown')
        .click();

      cy
        .contains('.input-dropdown-list-option', listValue)
        .click()
        .wait('@patchListField')
    });
});

/**
 * @param expectedStatus - optional; if given, the command verifies the status
 */
Cypress.Commands.add('processDocument', (action, expectedStatus) => {
  describe('Execute a doc action', function() {

    cy.log(`Execute doc action ${action}`)

    cy.get('.form-field-DocAction .meta-dropdown-toggle')
      .click()

    cy.get('.form-field-DocAction .dropdown-status-open')
      .should('exist')

    cy.get('.form-field-DocAction .dropdown-status-list')
      .find('.dropdown-status-item')
      .contains(action)
      .click()
      // .click({ force: true }) // force is needed in some cases with chrome71 (IDK why, to the naked eye the action seems to be visible)

    cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist')
    if(expectedStatus) {
      cy.log(`Verify that the doc status is now ${expectedStatus}`)

      cy.get('.meta-dropdown-toggle .tag-success').contains(expectedStatus)
    }
  })
});

Cypress.Commands.add('openAdvancedEdit', () => {
  describe('Open the advanced edit overlay via ALT+E shortcut', function() {
    cy.get('body').type('{alt}E');
    cy.get('.panel-modal').should('exist');
  })
});

Cypress.Commands.add('pressAddNewButton', (includedDocumentIdAliasName='newIncludedDocumentId') => {
  describe('Press table\'s add-new-record-button', function() {

    cy.server()
    // window/<windowId>/<rootDocumentId>/<tabId>/NEW
    cy.route('PATCH', new RegExp('/rest/api/window/[^/]+/[^/]+/[^/]+/NEW$')).as('patchNewIncludedDocument')

    const addNewText = Cypress.messages.window.addNew.caption;
    cy.get('.btn')
        .contains(addNewText)
        .should('exist')
        .click()
        .wait('@patchNewIncludedDocument')
        .then((xhr) => {

          return { documentId: xhr.response.body[0].rowId }
        })
        .as(includedDocumentIdAliasName)

    cy.get('.panel-modal')
        .should('exist')
  })
});

/** 
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressBatchEntryButton', (waitBeforePress) => {
  describe('Press table\'s batch-entry-record-button', function() {

    if(waitBeforePress) {
      cy.wait(waitBeforePress)
    }
    const batchEntryText = Cypress.messages.window.batchEntry.caption;
    cy.get('.btn')
        .contains(batchEntryText)
        .should('exist')
        .click();
        
    cy.get('.quick-input-container').should('exist');
  })
});

/*
 * Press an overlay's "Done" button. Fail if there is a confirm dialog since that means the record could not be saved. 
 * 
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressDoneButton', (waitBeforePress) => {
  describe('Press an overlay\'s done-button', function() {

    if(waitBeforePress) {
      cy.wait(waitBeforePress)
    }

    // fail if there is a confirm dialog because it's the "do you really want to leave" confrimation which means that the record can not be saved
    // https://docs.cypress.io/api/events/catalog-of-events.html#To-catch-a-single-uncaught-exception
    cy.on('window:confirm', (str) => {
      expect(str).to.eq('Everything is awesome and the data record is saved')
    });

    //webui.modal.actions.done
    const doneText = Cypress.messages.modal.actions.done;
    cy.get('.btn')
      .contains(doneText)
      .should('exist')
      .click();

    cy.get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('not.exist');
  })
});

/** 
 * @param waitBeforePress if truthy, call cy.wait with the given parameter first
 */
Cypress.Commands.add('pressStartButton', (waitBeforePress) => {
  describe('Press an overlay\'s start-button', function() {

    if(waitBeforePress) {
      cy.wait(waitBeforePress)
    }

    // fail if there is a confirm dialog because it's the "do you really want to leave" confrimation which means that the record can not be saved
    // https://docs.cypress.io/api/events/catalog-of-events.html#To-catch-a-single-uncaught-exception
    cy.on('window:confirm', (str) => {
      expect(str).to.eq('Everything is awesome and the process has started')
    });

    //webui.modal.actions.done
    const startText = Cypress.messages.modal.actions.start;
    cy.get('.btn')
      .contains(startText)
      .should('exist')
      .click();
  })
});

Cypress.Commands.add('selectTab', (tabName) => {
  describe('Select and activate the tab with a certain name', function() {
    return cy.get(`#tab_${tabName}`).click()
  });
});

Cypress.Commands.add('selectReference', (refName) => {
  describe('Select reference with a certain name', function() {
    return cy.get(`.reference_${refName}`)
  });
});

/*
 * This command allows waiting for the breadcrumb in the header to be visible, which
 * helps make the tests less flaky as even though the page fires load event, some
 * requests may still be pending/running.
 *
 * Command accepts two params:
 * - pageName : if we explicitly want to define what to wait for
 * - breadcrumbNr : if we want to select breadcrumb value from the redux store at
 *                  the given index
 * In case pageName is not defined, command will fall back to broadcrembNr and either
 * use the provided value or the value at index 0.
 *
 */
Cypress.Commands.add('waitForHeader', (pageName, breadcrumbNr) => {
  describe('Wait for page name visible in the header', function() {
    if (pageName) {
      cy.get('.header-breadcrumb')
        .find('.header-item-container')
        .should('not.have.length', 1)
        .get('.header-item').should('contain', pageName);
    } else {
      const breadcrumbNumber = breadcrumbNr || 0;

      cy.get('.header-breadcrumb')
        .find('.header-item-container')
        .should('not.have.length', 1)
        .window().its('store').invoke('getState').its('menuHandler.breadcrumb')
        .should('not.have.length', 0)
        .window().its('store').invoke('getState')
        .its('menuHandler.breadcrumb')
        .then((breadcrumbs) => {
          cy.get('.header-item').should('contain', breadcrumbs[breadcrumbNumber].caption);
        });
    }
  });
});

Cypress.Commands.add('selectSingleTabRow', () => {
  describe('Select the only row in the currently selected tab', function() {
    cy.get('.table-flex-wrapper')
    .find('tbody tr')
    .should('exist')
    .click();
  });
});

Cypress.Commands.add('editAddress', (fieldName, addressFunction) => {
  describe(`Select ${fieldName}'s address-button and invoke the given function`, function() {
   
    cy.server()
    cy.route('POST', '/rest/api/address').as('postAddress')

    cy.get(`.form-field-${fieldName}`).find('button').click();

    cy.wait('@postAddress').then(xhr => {
      const requestId = xhr.response.body.id;

      Cypress.emit('emit:addressPatchResolved', requestId);
    });

    cy.on('emit:addressPatchResolved', (requestId) => {

      cy.route('POST', `/rest/api/address/${requestId}/complete`).as('completeAddress');

      addressFunction();
      cy.get(`.form-field-C_Location_ID`).click();
      cy.wait('@completeAddress');
    });
  });
});

/*
 * This command runs a quick actions. If the second parameter is truthy, the default action will be executed.
 */
Cypress.Commands.add('executeQuickAction', (actionName, active) => {
  describe('Fire a quick action with a certain name', function() {

    let path = `.quick-actions-wrapper` // default action

    if (!active) {

      cy.get('.quick-actions-wrapper .btn-inline').eq(0).click()
      cy.get('.quick-actions-dropdown').should('exist')

      path = `#quickAction_${actionName}`
    }

    return cy
      .get(path)
      .click()
      .get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
      .should('exist')
  });
});

Cypress.Commands.add('executeHeaderAction', (actionName) => {
  describe('Fire header action with a certain name', function() {

   executeHeaderAction(actionName);
  });
});

Cypress.Commands.add('executeHeaderActionWithDialog', (actionName) => {
  describe('Fire header action with a certain name and expect a modal dialog to pop up within 10 secs', function() {

   executeHeaderAction(actionName);

   return cy.get('.panel-modal', { timeout: 10000 }) // wait up to 10 secs for the modal to appear
     .should('exist')
 });
});

function executeHeaderAction(actionName) {

  cy.get('.header-container .btn-square .meta-icon-more').click();
  cy.get('.subheader-container').should('exist');
  cy.get(`#headerAction_${actionName}`).click();
}

Cypress.Commands.add('clickHeaderNav', (navName) => {
  const name = navName.toLowerCase().replace(/\s/g, '');

  describe('Fire header action with a certain name', function() {
    cy.get('.header-container .btn-header').click();
    cy.get('.subheader-container').should('exist');

    return cy.get(`#subheaderNav_${name}`).click();
  });
});

Cypress.Commands.add('visitWindow', (windowId, recordId, documentIdAliasName='visitedDocumentId') => {
  describe('Open metasfresh window and wait for layout and data', function() {

    cy.server()
    cy.route('GET', `/rest/api/window/${windowId}/layout`).as('getLayout')
    cy.route('GET', new RegExp(`/rest/api/window/${windowId}/[0-9]+$`)).as('getRecordData')

    cy.visit(`/window/${windowId}/${recordId}`)
      .wait('@getLayout', {requestTimeout: 20000, responseTimeout: 20000})
      .wait('@getRecordData', {requestTimeout: 20000, responseTimeout: 20000})
      .then((xhr) => {

          return { documentId: xhr.response.body[0].id }
        })
      .as(documentIdAliasName)
  })
})

// may be useful to wait for the response to a particular patch where a particular field value was set
// not yet tested
// thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
Cypress.Commands.add('waitForFieldValue', (alias, fieldName, fieldValue) => {
  cy.wait(alias)
    .then(function(xhr){
      const responseBody = xhr.responseBody
      if (!responseBody.length <= 0) {
        cy.log(`waitForFieldValue - waited for alias=${alias} and ${fieldName}=${fieldValue}, but the response-body is empty; continuing to wait`)
        return cy.waitForFieldValue(alias, fieldName, fieldValue); //<---- this is the hacky bit        
      }

      if (!responseBody.fieldsByName) {
        cy.log(`waitForFieldValue - waited for alias=${alias} and ${fieldName}=${fieldValue}, but the response-body has no fieldsByName property; continuing to wait`)
        return cy.waitForFieldValue(alias, fieldName, fieldValue); //<---- this is the hacky bit        
      }

      const fieldsByName = responseBody[0].fieldsByName
      if (fieldsByName[fieldName] !== fieldValue) {
        cy.log(`waitForFieldValue - waited for alias=${alias} and ${fieldName}=${fieldValue}, but the field has value=${fieldsByName[fieldName]}; continuing to wait`)
        return cy.waitForFieldValue(alias, fieldName, fieldValue); //<---- this is the hacky bit
      }
    })
})