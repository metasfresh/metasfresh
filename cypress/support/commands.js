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

// Should also work for date columns, e.g. '01/01/2018{enter}'
describe('Enter value into string field', function() {
Cypress.Commands.add('writeIntoStringField', (fieldName, stringValue) => {
    cy.get(`.form-field-${fieldName}`)
      .find('input')
      .type(stringValue);
  });
});

describe('Enter value into text field', function() {
  Cypress.Commands.add('writeIntoTextField', (fieldName, stringValue) => {
      cy.get(`.form-field-${fieldName}`)
        .find('textarea')
        .type(stringValue);
    });
  });

describe('Enter value into lookup list field', function() {
  Cypress.Commands.add(
    'writeIntoLookupListField',
    (fieldName, partialValue, listValue) => {
      cy.get(`.form-field-${fieldName}`)
        .find('input')
        .type(partialValue);
      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    }
  );
});

describe('Select value in list field', function() {
  Cypress.Commands.add(
    'selectInListField',
    (fieldName, listValue) => {
      cy.get(`.form-field-${fieldName}`)
        .find('.input-dropdown')
        .click();

      cy
        .contains('.input-dropdown-list-option', listValue)
        .click();
    }
  );
});

/** !!not working!! */
describe('Enter value into list field within a "fieldgroup" (field with additional fields, e.g sales order bPartner with lcoation and user)', function() {
  Cypress.Commands.add(
    'writeIntoMultiListField',
    (fieldName, index, partialValue, listValue) => {
      cy.get(`.form-field-${fieldName}`)
        .find('input')
        .find(`:nth.child(${index})`)
        .type(partialValue);
      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    }
  );
});

describe('Execute a doc action', function() {
  Cypress.Commands.add('processDocument', (action, expectedStatus) => {
    cy.get('.form-field-DocAction')
      .find('.meta-dropdown-toggle')
      .click();

    cy.get('.form-field-DocAction')
      .find('.dropdown-status-toggler')
      .should('have.class', 'dropdown-status-open');

    cy.get('.form-field-DocAction .dropdown-status-list')
      .find('.dropdown-status-item')
      .contains(action)
      .click();

    cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist');
    cy.get('.meta-dropdown-toggle .tag-success').contains(expectedStatus);
  })
});

describe('Open the advanced edit overlay via ALT+E shortcut', function() {
  Cypress.Commands.add('openAdvancedEdit', () => {
    cy.get('body').type('{alt}E')
  })
});

describe('Select and activate the tab with a certain name', function() {
  Cypress.Commands.add('selectTab', (tabName) => {
    return cy.get(`#tab_${tabName}`).click()
  });
});
