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
import { localLoginRequest } from '../../src/api';
import Auth from '../../src/services/Auth';

context('Reusable "login" custom command', function() {
  Cypress.Commands.add('loginByForm', (username, password) => {
    Cypress.log({
      name: 'loginByForm',
      message: username + ' | ' + password,
    });

    const handleSuccess = function(){
      console.log('CYPRESS handleSuccess')

      // getUserLang().then(response => {
        //GET language shall always return a result
        // Moment.locale(response.data['key']);

        Cypress.reduxStore.dispatch(push('/'));
      // });
    };

    const checkIfAlreadyLogged = function() {
      console.log('chickifUserLogggedIn');
      const error = new Error('bla')
      // // return Promise.reject(error)
      // return;
      return localLoginRequest().then(response => {
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
      // form: true,
      failOnStatusCode: false,
      body: {
        username,
        password,
      },
    }).then(response => {
      console.log('response: ', response)
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
        body: { ...roles[0] },
      }).then(() => {
        Cypress.reduxStore.dispatch(loginSuccess(auth));
        
        handleSuccess();
      })
    })
  });


  // beforeEach(function(){
  //   // login before each test
  //   cy.loginByForm('kuba', 'kuba1234');
  // });

  // it('can visit dashboard', function(){
  //   cy.url().should('not.include', '/login');
  //   cy.get('.header-item').should('contain', 'Dashboard');
  // });
});
