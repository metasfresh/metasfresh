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

// Import commands.js using ES2015 syntax:
import './commands';
import nextTabbable from './nextTabbable';

// Alternatively you can use CommonJS syntax:
// require('./commands')

Cypress.on('uncaught:exception', () =>{ //(err, runnable) => {
  // returning false here prevents Cypress from
  // failing the test
  return false
});

Cypress.on('emit:counterpartTranslations', messages => {
  Cypress.messages = messages;
});

/**
 * Emulates Tab key navigation.
 */
Cypress.Commands.add('tab', { prevSubject: 'optional' }, ($subject, direction = 'forward', options = {}) => {
  const thenable = $subject
    ? cy.wrap($subject, { log: false })
    : cy.focused({ log: options.log !== false });
  thenable
    .then($el => nextTabbable($el, direction))
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
    .then(document => cy.wrap(document.activeElement, { log: false }))
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