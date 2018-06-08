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

context('Reusable "login" custom command', function() {
  Cypress.Commands.add('loginByForm', (username, password, redirect) => {
    Cypress.log({
      name: 'loginByForm',
      message: username + ' | ' + password,
    });

    const handleSuccess = function(){
      if (redirect) {
        Cypress.reduxStore.dispatch(goBack());
      } else {
        Cypress.reduxStore.dispatch(push('/'));
      }
    };

    const checkIfAlreadyLogged = function() {
      const error = new Error('Error when checking if user logged in')

      return cy.request({
        method: 'GET',
        url: 'http://w101.metasfresh.com:8081/rest/api/login/isLoggedIn',
        failOnStatusCode: false,
        followRedirect: false,
      }).then(response => {
        if (!response.body.error) {
          return Cypress.reduxStore.dispatch(push('/'));
        }

        return Promise.reject(error);
      });
    };

    const auth = new Auth();

    cy.on("emit:reduxStore", store => {
      Cypress.reduxStore = store;
    });

    cy.visit('/login');

    return cy.request({
      method: 'POST',
      url: 'http://w101.metasfresh.com:8081/rest/api/login/authenticate',
      failOnStatusCode: false,
      followRedirect: false,
      body: {
        username,
        password,
      },
    }).then(response => {
      if (!response.isOkStatusCode) {
        return checkIfAlreadyLogged();
      }

      if (response.body.loginComplete) {
        return handleSuccess();
      }
      const roles = List(response.body.roles);

      return cy.request({
        method: 'POST',
        url: 'http://w101.metasfresh.com:8081/rest/api/login/loginComplete',
        body: { ...roles.get(0) },
        failOnStatusCode: false,
      }).then(() => {
        Cypress.reduxStore.dispatch(loginSuccess(auth));
        
        handleSuccess();
      });
    })
  });
});
