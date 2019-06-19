// ***********************************************************
// This example support/index.js is processed and
// loaded automatically before your test files.
//
// This is a great place to put global configuration and
// behavior that modifies Cypress.
//
// You can change the location of this file or turn off
// automatically serving support files with the
// 'supportFile' configuration option.
//
// You can read more here:
// https://on.cypress.io/configuration
// ***********************************************************

import 'cypress-skip-and-only-ui/support';
import './commands/general';
import './commands/navigation';
import './commands/form';
import './commands/action';
import './commands/test';

Cypress.on('uncaught:exception', () => {
  //(err, runnable) => {
  // returning false here prevents Cypress from
  // failing the test
  return false;
});

Cypress.on('emit:counterpartTranslations', messages => {
  Cypress.messages = messages;
});

Cypress.on('window:alert', text => {
  cy.log(`Alert modal confirmed: ${text}`);
});

before(function() {
  cy.loginViaAPI();

  Cypress.Cookies.defaults({
    whitelist: ['SESSION', 'isLogged'],
  });
});
