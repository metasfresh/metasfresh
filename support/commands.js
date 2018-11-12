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
Cypress.Commands.add('writeIntoStringField', (fieldName, stringValue) => {
  describe('Enter value into string field', function() {
    cy.get(`.form-field-${fieldName}`)
      .find('input')
      .type(stringValue);
  });
});

Cypress.Commands.add('writeIntoTextField', (fieldName, stringValue) => {
  describe('Enter value into text field', function() {
      cy.get(`.form-field-${fieldName}`)
        .find('textarea')
        .type(stringValue);
    });
  });

Cypress.Commands.add(
  'writeIntoLookupListField',
  (fieldName, partialValue, listValue) => {
    describe('Enter value into lookup list field', function() {
      cy.get(`.form-field-${fieldName}`)
        .find('input')
        .type(partialValue);
      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
});

Cypress.Commands.add(
  'writeIntoCompositeLookupField',
  (fieldName, partialValue, listValue) => {
    describe('Enter value into lookup list field', function() {
      cy.get(`#lookup_${fieldName}`)
        .within(($el) => {
          if ($el.find('.raw-lookup-wrapper input').length) {
            return cy.get('input').type(partialValue);
          }

          return cy.get('.lookup-dropdown').click();
        })

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
});

Cypress.Commands.add('selectInListField', (fieldName, listValue) => {
  describe('Select value in list field', function() {
      cy.get(`.form-field-${fieldName}`)
        .find('.input-dropdown')
        .click();

      cy
        .contains('.input-dropdown-list-option', listValue)
        .click();
    }
  );
});

Cypress.Commands.add('clickOnCheckBox', (fieldName) => {
  describe('Click on a checkbox field', function() {
    cy.get(`.form-field-${fieldName}`)
      .find('.input-checkbox-tick')
      .click();
  });
});

/** !!not working!! */
Cypress.Commands.add(
  'writeIntoMultiListField',
  (fieldName, index, partialValue, listValue) => {
    describe('Enter value into list field within a "fieldgroup" (field with additional fields, e.g sales order bPartner with lcoation and user)', function() {
      cy.get(`.form-field-${fieldName}`)
        .find('input')
        .find(`:nth.child(${index})`)
        .type(partialValue);
      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
});

Cypress.Commands.add('processDocument', (action, expectedStatus) => {
  describe('Execute a doc action', function() {
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

Cypress.Commands.add('openAdvancedEdit', () => {
  describe('Open the advanced edit overlay via ALT+E shortcut', function() {
    cy.get('body').type('{alt}E')
  })
});

Cypress.Commands.add('pressAddNewButton', () => {
  describe('Press a tab\'s add-new-record-button', function() {
    const addNewText = Cypress.messages.window.addNew.caption;
    cy.get('.btn')
        .contains(addNewText)
        .should('exist')
        .click();
        
    cy.get('.panel-modal').should('exist');
  })
});

/* ts: I don't see a nicer way to address this button; */
Cypress.Commands.add('pressDoneButton', () => {
  describe('Press an overlay\'s done-button', function() {

    //webui.modal.actions.done
    const doneText = Cypress.messages.modal.actions.done;
    cy.get('.btn')
      .contains(doneText)
      .should('exist')
      .click();
  })
});

Cypress.Commands.add('selectTab', (tabName) => {
  describe('Select and activate the tab with a certain name', function() {
    return cy.get(`#tab_${tabName}`).click()
  });
});

// This command runs a quick actions. If second parameter is truthy, the default action
// will be executed.
Cypress.Commands.add('executeQuickAction', (actionName, active) => {
  describe('Fire a quick action with a certain name', function() {
    if (!active) {
      cy.get('.quick-actions-wrapper .btn-inline').click();
      cy.get('.quick-actions-dropdown').should('exist');

      return cy.get(`#quickAction_${actionName}`).click();
    }

    return cy.get('.quick-actions-wrapper').click();
  });
});
