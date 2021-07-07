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

// import 'cypress-plugin-snapshots/commands';

import { List } from 'immutable';
import { goBack, push } from 'react-router-redux';

import { loginSuccess } from '../../../src/actions/AppActions';
import Auth from '../../../src/services/Auth';
import config from '../../config';
import nextTabbable from './nextTabbable';
import { humanReadableNow } from '../utils/utils';
import { RewriteURL } from '../utils/constants';

context('Reusable "login" custom command using API', function () {
  Cypress.Commands.add('loginViaAPI', (username, password, redirect) => {
    let user = username;
    let pass = password;

    if (!username || !password) {
      user = config.username;
      pass = config.password;
    }

    Cypress.log({
      name: 'loginViaAPI',
      message: user + ' | ' + '****' /*pass*/,
    });

    const handleSuccess = function () {
      if (redirect) {
        Cypress.reduxStore.dispatch(goBack());
      } else {
        Cypress.reduxStore.dispatch(push('/'));
      }
    };

    const checkIfAlreadyLogged = function () {
      const error = new Error('Error when checking if user logged in');

      return cy
        .request({
          method: 'GET',
          url: config.API_URL + '/login/isLoggedIn',
          failOnStatusCode: false,
          followRedirect: false,
        })
        .then((response) => {
          if (!response.body.error) {
            return Cypress.reduxStore.dispatch(push('/'));
          }

          cy.log(`Login failed because ${error}`);
          return Promise.reject(error);
        });
    };

    const auth = new Auth();

    cy.on('emit:reduxStore', (store) => {
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
      .then((response) => {
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

/**
 * Emulates Tab key navigation.
 */
Cypress.Commands.add('tab', { prevSubject: 'optional' }, ($subject, direction = 'forward', options = {}) => {
  const thenable = $subject ? cy.wrap($subject, { log: false }) : cy.focused({ log: options.log !== false });

  thenable
    .then(($el) => nextTabbable($el, direction))
    .then(($el) => {
      if (options.log !== false) {
        Cypress.log({
          $el,
          name: 'tab',
          message: direction,
        });
      }
    })
    .focus({ log: false });
});

/**
 * Queries for the active element, irrespective of document focus state, unlike cy.focused().
 *
 * This may not match Cypress' internal focus tracker, so you may have to .focus() the returned
 * element to interact with it.
 */
Cypress.Commands.add('active', (options = {}) => {
  cy.document({ log: false })
    .then((document) => cy.wrap(document.activeElement, { log: false }))
    .then(($el) => {
      if (options.log !== false) {
        Cypress.log({
          $el,
          name: 'active',
          message: '',
        });
      }
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
  describe('Wait for page name visible in the header', function () {
    if (pageName) {
      cy.get('.header-breadcrumb').find('.header-item-container').should('not.have.length', 1).get('.header-item').should('contain', pageName);
    } else {
      const breadcrumbNumber = breadcrumbNr || 0;

      cy.get('.header-breadcrumb')
        .find('.header-item-container')
        .should('not.have.length', 1)
        .window()
        .its('store')
        .invoke('getState')
        .its('menuHandler.breadcrumb')
        .should('not.have.length', 0)
        .window()
        .its('store')
        .invoke('getState')
        .its('menuHandler.breadcrumb')
        .then((breadcrumbs) => {
          cy.get('.header-item').should('contain', breadcrumbs[breadcrumbNumber].caption);
        });
    }
  });
});

function visitTableWindow(windowId) {
  const quickActionsAlias = 'quickActions_' + humanReadableNow();
  cy.server();
  cy.route('GET', new RegExp(RewriteURL.QuickActions)).as(quickActionsAlias);

  cy.visit(`/window/${windowId}`);

  cy.wait(`@${quickActionsAlias}`);
}

function visitDetailWindow(windowId, recordId) {
  describe('Open metasfresh single-record window and wait for layout and data', function () {
    performDocumentViewAction(windowId, function () {
      cy.visit(`/window/${windowId}/${recordId}`);
    });
  });
}

Cypress.Commands.add('performDocumentViewAction', (windowId, documentViewAction) => {
  performDocumentViewAction(windowId, documentViewAction);
});

function performDocumentViewAction(windowId, documentViewAction) {
  cy.server();
  const layoutAliasName = `visitWindow-layout-${new Date().getTime()}`;
  cy.route('GET', new RegExp(`/rest/api/window/${windowId}/layout`)).as(layoutAliasName);

  // - removed below lines because is redundant code.. that GET call is actually done by documentViewAction ..
  // const dataAliasName = `visitWindow-data-${new Date().getTime()}`;
  // cy.route('GET', new RegExp(`/rest/api/window/${windowId}/[0-9]+$`)).as(dataAliasName);

  documentViewAction();

  cy.wait(`@${layoutAliasName}`);

  // - Also remmoved below lines as timeout is too big
  // cy.wait(`@${dataAliasName}`);

  // cy.wait(`@${layoutAliasName}`, {
  //   requestTimeout: 20000,
  //   responseTimeout: 20000,
  // }).wait(`@${dataAliasName}`, {
  //   requestTimeout: 20000,
  //   responseTimeout: 20000,
  // });
}

Cypress.Commands.add('visitWindow', (windowId, recordId) => {
  if (recordId == null) {
    // null == undefined, thx to https://stackoverflow.com/a/2647888/1012103
    visitTableWindow(windowId);
  } else {
    visitDetailWindow(windowId, recordId);
  }
  cy.waitForSaveIndicator();
});

Cypress.Commands.add('readAllNotifications', () => {
  describe('Mark all current notifications as read in the API and reset counter', function () {
    return cy
      .request({
        method: 'PUT',
        url: config.API_URL + '/notifications/all/read',
        failOnStatusCode: false,
        followRedirect: false,
      })
      .then(() => {
        cy.window().its('store').invoke('dispatch', {
          type: 'READ_ALL_NOTIFICATIONS',
        });
      });
  });
});

Cypress.Commands.add('expectNumberOfDOMNotifications', (expectedNumber) => {
  const timeout = { timeout: 15000 };

  return cy
    .get('.header-item-badge', timeout)
    .find('.notification-number', timeout)
    .then((el) => {
      if (el[0]) {
        const val = el[0].textContent;

        return cy.wrap(parseInt(val, 10));
      }
    })
    .should('eq', expectedNumber);
});

/*
 * if `optionalText` is given it will look for it inside the notification element
 */
Cypress.Commands.add('getNotificationModal', (optionalText) => {
  const timeout = { timeout: 15000 };

  if (!optionalText) {
    return cy.get('.notification-handler', timeout).find('.notification-content', timeout);
  } else {
    return cy.get('.notification-handler', timeout).find('.notification-content', timeout).contains(optionalText);
  }
});
/**
 * Opens the inbox notification with the given text
 */
Cypress.Commands.add('openInboxNotificationWithText', (text) => {
  const timeout = { timeout: 20000 };
  cy.get('.header-item-badge.icon-lg .notification-number', timeout).click();
  cy.get('.inbox-item-unread .inbox-item-title', timeout)
    .filter(':contains("' + text + '")')
    .first()
    .click();
  cy.waitForSaveIndicator();

  /**
   * todo @kuba:
   *  Since Frontend already knows what a notification should do when clicked via the /all request and the "target" element in the response (see json below)
   *  it would be helpful for cypress if each notification would also contain a data- attribute with the target,
   *  so that i know what to wait after when the notification is clicked, or that nothing should happens.
   *
   *  This is helpful in case i need to move to a particular window, or whatever else i would need to do.
   *
   * maybe that notification could contain `data-cy="/window/169/1000043"` or `data-cy=null`.
   *
   *
   * JSON response
   {
   "notifications": [
   {
      "id": "1000058",
      "message": "Lieferung 545170 für Partner 1000107 CustomerTest 20T09_14_32_145 wurde erstellt.",
      "timestamp": "2019-08-20T08:17:06.000+02:00",
      "important": false,
      "read": true,
      "target": {         // i know that here i have to go to a specific single view: /window/169/1000043
        "targetType": "window",
        "windowId": "169",
        "documentId": "1000043",
        "documentType": "169"
      }
    },
    {
      "id": "1000055",
      "message": "AD_PInstance_ID=1001782\n Summary:\nIhr Test hat einen bisher unentdeckten Fehler offengelegt.\r\nBitte leiten Sie diese Meldung an metas weiter:\n\nError: Missing AD_Printer_Config record for hostKey=095c60c6-60a4-4477-8602-ede23627d1cc, userToPrintId=2188223, ctx={#AD_Org_ID=1000000, #AD_Client_ID=1000000}\nNotification",
      "timestamp": "2019-08-20T06:54:28.000+02:00",
      "important": true,
      "read": true,
      "target": null      // i know that here i have nothing to do
    }
   ]
   }
   *  Reference: https://docs.cypress.io/guides/references/best-practices.html#Selecting-Elements
   */
});

// may be useful to wait for the response to a particular patch where a particular field value was set
// thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
Cypress.Commands.add('waitForFieldValue', (alias, fieldName, expectedFieldValue, expectEmptyRequest = false) => {
  cy.wait(alias).then(function (xhr) {
    const responseBody = xhr.responseBody;

    if (!responseBody.documents) {
      responseBody.documents = responseBody;
    }

    if (!expectEmptyRequest) {
      if (responseBody.documents.length <= 0) {
        cy.log(`1 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response-body is empty; waiting once more`);
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }

      if (!responseBody.documents[0].fieldsByName) {
        cy.log(`2 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response-body has no fieldsByName property; waiting once more`);
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }

      const fieldsByName = responseBody.documents[0].fieldsByName;
      if (!fieldsByName.hasOwnProperty(fieldName)) {
        cy.log(`3 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response has no ${fieldName} property; waiting once more`);
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }

      const actualFieldValue = fieldsByName[fieldName].value;
      // @TODO: Why is this check written like that ? Sometimes we get an object value, and not string
      if (!isString(actualFieldValue)) {
        cy.log(
          `4 waitForFieldValue - waited for alias=${alias} and ${fieldName}='${expectedFieldValue}'; the current response body's field has ${fieldName}=${actualFieldValue}; I don't know how do check if non-string values are correct; stop waiting`
        );
        return;
      }

      /**
       * TODO: Please oh please let's fix the types at one point
       *  Here i'm using `!=` and not `!==` so that '222' == 222 (a string is equals to a number with the same value)
       *  We need this for cases such as `cy.writeIntoStringField('QtyEntered', 222, true);`
       */
      if (actualFieldValue != expectedFieldValue) {
        cy.log(
          `5 waitForFieldValue - waited for alias=${alias} and ${fieldName}='${expectedFieldValue}', but the current response body's field has ${fieldName}=${actualFieldValue}; waiting once more`
        );
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }
    }
  });

  // Thx to https://stackoverflow.com/a/9436948/1012103
  function isString(object) {
    return typeof object === 'string' || object instanceof String;
  }
});

Cypress.Commands.add('getCurrentWindowRecordId', () => {
  return cy.url().then((ulrr) => {
    // noinspection UnnecessaryLocalVariableJS
    const currentRecordId = ulrr.split('/').pop();
    return currentRecordId;
  });
});

Cypress.Commands.add('getSalesInvoiceTotalAmount', () => {
  cy.waitForSaveIndicator();
  return cy.get('.header-breadcrumb-sitename').then(function (si) {
    // noinspection UnnecessaryLocalVariableJS
    const newTotalAmount = parseFloat(si.html().split(' ')[2]); // the format is "DOC_NO MM/DD/YYYY total"
    return newTotalAmount;
  });
});

Cypress.Commands.add('waitUntilProcessIsFinished', () => {
  cy.wait(10000);
});

Cypress.Commands.add('waitForSaveIndicator', (expectIndicator = false) => {
  const timeout = { timeout: 10000 };

  if (expectIndicator) {
    cy.get('.indicator-pending', timeout).should('exist');
  }
  cy.get('.indicator-pending', timeout).should('not.exist');
  cy.get('.indicator-saved', timeout).should('exist');
});

Cypress.Commands.add('selectNotificationContaining', (expectedValue) => {
  cy.get('.header-item-badge.icon-lg i').click(); // notification icon
  return cy
    .get('.inbox-item-title') // search for text
    .contains(expectedValue)
    .first();
});

Cypress.Commands.add('openNotificationContaining', (expectedValue, destinationWindowID) => {
  cy.selectNotificationContaining(expectedValue).click();
  // wait until current window is "destinationWindowID"
  cy.url().should('contain', `/${destinationWindowID}`);
  // hope this is enough for the whole window to load
  cy.waitForSaveIndicator();
});

Cypress.Commands.add('selectLeftTable', function (isModal = false) {
  cy.log('Select left table');
  cy.waitForSaveIndicator();

  const parentWrapperPath = isModal ? '.modal-content-wrapper' : '.document-lists-wrapper';

  cy.get(`${parentWrapperPath} .document-list-has-included`).within((el) => {
    return cy.wrap(el);
  });
});

Cypress.Commands.add('selectRightTable', function (isModal = false) {
  cy.log('Select right table');
  cy.waitForSaveIndicator();

  const parentWrapperPath = isModal ? '.modal-content-wrapper' : '.document-lists-wrapper';

  cy.get(`${parentWrapperPath} .document-list-is-included`).within((el) => {
    return cy.wrap(el);
  });
});
