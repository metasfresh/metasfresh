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
import 'cypress-localstorage-commands';
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

// Cypress.on('fail', (err, runnable) => {
//   console.log('Err =>', err);
//   return false
// });

Cypress.on('emit:counterpartTranslations', (messages) => {
  Cypress.messages = messages;
});

Cypress.on('window:alert', (text) => {
  cy.log(`Alert modal confirmed: ${text}`);
});

before(function () {
  // use the login routine only for the e2e tests, this will not be used for the component testing(independent)
  if (Cypress.testingType !== 'component') {
    cy.clearLocalStorageSnapshot();

    // cy.loginViaAPI().then(() => {
    //   cy.log('logged in successfully');
    // }, { timeout: 20000 });

    const autoLogin = function () {
      return cy.loginViaForm();
    };
    autoLogin().then((msg) => {
      cy.log(msg);
    });

    Cypress.Cookies.defaults({
      preserve: ['SESSION', 'isLogged'],
    });
  }
});

beforeEach(() => {
  cy.restoreLocalStorage();
});

Cypress.on('scrolled', ($el) => {
  $el.get(0).scrollIntoView({
    block: 'center',
    inline: 'center',
  });
});

// This adds a delay between cypress commands for easier debugging
// Ref: https://github.com/cypress-io/cypress/issues/249
//
// const COMMAND_DELAY = 1000;
//
// for (const command of ['visit', 'click', 'trigger', 'type', 'clear', 'reload', 'contains']) {
//   Cypress.Commands.overwrite(command, (originalFn, ...args) => {
//     const origVal = originalFn(...args);
//
//     return new Promise((resolve) => {
//       setTimeout(() => {
//         resolve(origVal);
//       }, COMMAND_DELAY);
//     });
//   });
// }
