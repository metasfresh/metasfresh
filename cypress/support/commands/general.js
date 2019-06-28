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

import 'cypress-plugin-snapshots/commands';

import { List } from 'immutable';
import { goBack, push } from 'react-router-redux';

import { loginSuccess } from '../../../src/actions/AppActions';
import Auth from '../../../src/services/Auth';
import config from '../../config';
import nextTabbable from './nextTabbable';
import notificationFixtures from '../../fixtures/misc/notifications.json';

const NOTIFICATION_FIXTURE = notificationFixtures['540375'];

context('Reusable "login" custom command using API', function() {
  Cypress.Commands.add('loginViaAPI', (username, password, redirect) => {
    let user = username;
    let pass = password;

    if (!username || !password) {
      user = config.username;
      pass = config.password;
    }

    Cypress.log({
      name: 'loginViaAPI',
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

/**
 * Emulates Tab key navigation.
 */
Cypress.Commands.add('tab', { prevSubject: 'optional' }, ($subject, direction = 'forward', options = {}) => {
  const thenable = $subject ? cy.wrap($subject, { log: false }) : cy.focused({ log: options.log !== false });

  thenable
    .then($el => nextTabbable($el, direction))
    .then($el => {
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
    .then(document => cy.wrap(document.activeElement, { log: false }))
    .then($el => {
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
  describe('Wait for page name visible in the header', function() {
    if (pageName) {
      cy.get('.header-breadcrumb')
        .find('.header-item-container')
        .should('not.have.length', 1)
        .get('.header-item')
        .should('contain', pageName);
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
        .then(breadcrumbs => {
          cy.get('.header-item').should('contain', breadcrumbs[breadcrumbNumber].caption);
        });
    }
  });
});

function visitTableWindow(windowId) {
  cy.visit(`/window/${windowId}`);
}

function visitDetailWindow(windowId, recordId, documentIdAliasName) {
  cy.server();
  const layoutAliasName = `visitWindow-layout-${new Date().getTime()}`;
  cy.route('GET', new RegExp(`/rest/api/window/${windowId}/layout`)).as(layoutAliasName);
  const dataAliasName = `visitWindow-data-${new Date().getTime()}`;
  cy.route('GET', new RegExp(`/rest/api/window/${windowId}/[0-9]+$`)).as(dataAliasName);
  cy.visit(`/window/${windowId}/${recordId}`)
    .wait(`@${layoutAliasName}`, {
      requestTimeout: 20000,
      responseTimeout: 20000,
    })
    .wait(`@${dataAliasName}`, {
      requestTimeout: 20000,
      responseTimeout: 20000,
    })
    .then(xhr => {
      return { documentId: xhr.response.body[0].id };
    })
    .as(documentIdAliasName);
}

Cypress.Commands.add('visitWindow', (windowId, recordId, documentIdAliasName = 'visitedDocumentId') => {
  describe('Open metasfresh single-record window and wait for layout and data', function() {
    if (recordId == null) {
      // null == undefined, thx to https://stackoverflow.com/a/2647888/1012103
      visitTableWindow(windowId);
    } else {
      visitDetailWindow(windowId, recordId, documentIdAliasName);
    }
  });
});

Cypress.Commands.add('resetNotifications', () => {
  describe('Clear current notifications', function() {
    return cy
      .window()
      .its('store')
      .invoke('dispatch', {
        type: 'REMOVE_ALL_NOTIFICATIONS',
      });
  });
});

Cypress.Commands.add('readAllNotifications', () => {
  describe('Mark all current notifications as read in the API and reset counter', function() {
    return cy
      .request({
        method: 'PUT',
        url: config.API_URL + '/notifications/all/read',
        failOnStatusCode: false,
        followRedirect: false,
      })
      .then(() => {
        cy.window()
          .its('store')
          .invoke('dispatch', {
            type: 'READ_ALL_NOTIFICATIONS',
          });
      });
  });
});

const getNotificationFixture = () => {
  const timestamp = new Date().getTime();
  const message = `Test notification ${timestamp}`;

  return {
    ...NOTIFICATION_FIXTURE,
    message,
  };
};

Cypress.Commands.add('addNotification', notificationObject => {
  describe('Push a new notification to the existing list', function() {
    notificationObject = notificationObject || getNotificationFixture();

    return cy
      .window()
      .its('store')
      .invoke('dispatch', {
        type: 'ADD_NOTIFICATION',
        notification: {
          ...notificationObject,
        },
      })
      .then(() => {
        return notificationObject;
      });
  });
});

Cypress.Commands.add('newNotification', (notificationObject, unreadCount = 0) => {
  describe('Clear current notifications and add a new one', function() {
    notificationObject = notificationObject || getNotificationFixture();

    return cy
      .window()
      .its('store')
      .invoke('dispatch', {
        type: 'NEW_NOTIFICATION',
        notification: {
          ...notificationObject,
        },
        unreadCount,
      })
      .then(() => notificationObject);
  });
});

Cypress.Commands.add('getDOMNotificationsNumber', () => {
  describe('Get the number of notifications displayed in the header alert element', function() {
    return cy
      .get('.header-item-badge')
      .find('.notification-number')
      .then(el => {
        const val = el[0].textContent;

        return parseInt(val, 10);
      });
  });
});

Cypress.Commands.add('getNotificationsInbox', () => {
  describe('Get the notifications inbox in the app state', function() {
    return cy
      .window()
      .its('store')
      .invoke('getState')
      .then(state => {
        return state.appHandler.inbox;
      });
  });
});

/*
 * if `optionalText` is given it will look for it inside the notification element
 */
Cypress.Commands.add('getNotificationModal', optionalText => {
  describe('Get the number of notifications displayed in the header alert element', function() {
    if (!optionalText) {
      return cy.get('.notification-handler').find('.notification-content');
    } else {
      return cy
        .get('.notification-handler')
        .find('.notification-content')
        .contains(optionalText);
    }
  });
});

// may be useful to wait for the response to a particular patch where a particular field value was set
// thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
Cypress.Commands.add('waitForFieldValue', (alias, fieldName, expectedFieldValue, expectEmptyRequest = false) => {
  cy.wait(alias).then(function(xhr) {
    const responseBody = xhr.responseBody;

    if (!expectEmptyRequest) {
      if (responseBody.length <= 0) {
        cy.log(
          `1 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response-body is empty; waiting once more`
        );
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }

      if (!responseBody[0].fieldsByName) {
        cy.log(
          `2 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response-body has no fieldsByName property; waiting once more`
        );
        return cy.waitForFieldValue(alias, fieldName, expectedFieldValue); //<---- this is the hacky bit
      }

      const fieldsByName = responseBody[0].fieldsByName;
      if (!fieldsByName.hasOwnProperty(fieldName)) {
        cy.log(
          `3 waitForFieldValue - waited for alias=${alias} and ${fieldName}=${expectedFieldValue}, but the current response has no ${fieldName} property; waiting once more`
        );
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

      if (actualFieldValue !== expectedFieldValue) {
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


Cypress.Commands.add('getCurrentRecordId', () => {
  describe('Select the current record ID from the url', function () {
    let currentRecordId = 0;
    cy.url().then(ulrr => {
      currentRecordId = ulrr.split('/').pop();
    });
    return currentRecordId;
  });
});
