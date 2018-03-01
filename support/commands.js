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

context('Reusable "login" custom command', function() {
  Cypress.Commands.add('loginByForm', (username, password) => {
    Cypress.log({
      name: 'loginByForm',
      message: username + ' | ' + password,
    });

    return cy.request({
      method: 'POST',
      url: 'http://w101.metasfresh.com:8081/rest/api/login/authenticate',
      // form: true,
      body: {
        username: username,
        password: password,
      },
    });
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
