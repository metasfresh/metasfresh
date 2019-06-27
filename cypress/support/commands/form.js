import { confirmCalendarDay } from '../functions';

// thx to https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replace
function removeSubstringsWithCurlyBrackets(stringValue) {
  const regex = /{.*}/gi;

  if (!stringValue.match) {
    return stringValue;
  }
  return stringValue.replace(regex, '');
}

Cypress.Commands.add('clearField', (fieldName, modal) => {
  describe('Clear field', function() {
    cy.log(`clearField - fieldName=${fieldName}; modal=${modal}`);

    const path = createFieldPath(fieldName, modal);
    cy.get(path)
      .find('input')
      .clear();
  });
});

Cypress.Commands.add('getStringFieldValue', (fieldName, modal) => {
  describe('Get field value', function() {
    cy.log(`getStringFieldValue - fieldName=${fieldName}; modal=${modal}`);

    const path = createFieldPath(fieldName, modal);
    return cy
      .get(path)
      .find('input')
      .invoke('val'); /* note: beats me why .its('value'); returned undefined */
  });
});

Cypress.Commands.add('assertFieldNotShown', (fieldName, modal) => {
  describe('Assert that a vield is not shown', function() {
    cy.log(`assertFieldNotShown - fieldName=${fieldName}; modal=${modal}`);

    const path = createFieldPath(fieldName, modal);
    return cy.get(path).should('not.exist');
  });
});

function createFieldPath(fieldName, modal) {
  let path = `.form-field-${fieldName}`;
  if (modal) {
    path = `.panel-modal ${path}`;
  }
  return path;
}

Cypress.Commands.add('getCheckboxValue', (fieldName, modal) => {
  describe('Get field value', function() {
    cy.log(`getCheckboxValue - fieldName=${fieldName}; modal=${modal}`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    return cy.get(path).then(el => {
      if (el.find('checked').length || el.find('.checked').length) {
        return true;
      }
      return false;
    });
  });
});

Cypress.Commands.add('resetListValue', (fieldName, modal) => {
  describe('Get field value', function() {
    cy.log(`resetListValue - fieldName=${fieldName}; modal=${modal}`);
    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('.meta-icon-close-alt')
      .click();
  });
});

Cypress.Commands.add('clickOnIsActive', modal => {
  describe('Click on the IsActive slider', function() {
    let path = `.form-field-IsActive`;

    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('.input-slider')
      .click();
  });
});

/*
 * @param modal - use true if the field is in a modal overlay; required if the underlying window has a field with the same name
 */
Cypress.Commands.add('clickOnCheckBox', (fieldName, expectedPatchValue, modal, rewriteUrl = null) => {
  describe('Click on a checkbox field', function() {
    cy.log(`clickOnCheckBox - fieldName=${fieldName}`);

    const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';
    const patchCheckBoxAliasName = `patchCheckBox-${new Date().getTime()}`;

    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(patchCheckBoxAliasName);

    cy.log(`clickOnCheckBox - fieldName=${fieldName}; modal=${modal};`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    cy.get(path)
      .find('.input-checkbox-tick')
      .click({ force: true }) // we don't care if the checkbox scrolled out of view
      .waitForFieldValue(`@${patchCheckBoxAliasName}`, fieldName, expectedPatchValue);
  });
});

/*
 * Right now it can only select the current date
 */
Cypress.Commands.add('selectDateViaPicker', (fieldName, modal) => {
  let path = `.form-field-${fieldName}`;
  if (modal) {
    path = `.panel-modal ${path}`;
  }
  cy.get(path)
    .find('.datepicker')
    .click();

  confirmCalendarDay();
  cy.get(path)
    .find('.form-control-label')
    .click();
});

/**
 * Function to fill in text inputs
 *
 * @param {string} fieldName - name of the field
 * @param {string} stringValue - value to put into field
 * @param {boolean} modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param {string} rewriteUrl - use custom url for the request
 * @param {boolean} noRequest - if set to true, don't wait for the response from the server
 */
Cypress.Commands.add('writeIntoStringField', (fieldName, stringValue, modal, rewriteUrl, noRequest) => {
  describe('Enter value into string field', function() {
    const aliasName = `writeIntoStringField-${new Date().getTime()}`;
    const expectedPatchValue = removeSubstringsWithCurlyBrackets(stringValue);
    // in the default pattern we want to match URLs that do *not* end with "/NEW"
    const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';
    cy.log(
      `writeIntoStringField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}; patchUrlPattern=${patchUrlPattern}`
    );

    if (!noRequest) {
      cy.server();
      cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);
    }

    const path = createFieldPath(fieldName, modal);
    cy.get(path)
      .find('input')
      .type('{selectall}')
      .type(`${stringValue}`)
      .type('{enter}');

    if (!noRequest) {
      cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue);
    }
  });
});

/**
 * Function to fill in textareas
 *
 * @param {string} fieldName - name of the field
 * @param {string} stringValue - value to put into field
 * @param {boolean} modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param {string} rewriteUrl - use custom url for the request
 */
Cypress.Commands.add('writeIntoTextField', (fieldName, stringValue, modal, rewriteUrl) => {
  describe('Enter value into text field', function() {
    cy.log(`writeIntoTextField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}`);

    const aliasName = `writeIntoTextField-${new Date().getTime()}`;
    const expectedPatchValue = removeSubstringsWithCurlyBrackets(stringValue);
    // in the default pattern we want to match URLs that do *not* end with "/NEW"
    const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';

    // here we want to match URLs that don *not* end with "/NEW"
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('textarea')
      .type(`${stringValue}{enter}`);
    cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue);
  });
});

/**
 * @param modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param typeList - use when selecting value from a list not lookup field. Someone thought it's a great idea to return different
 *                   responses for different fields.
 */
Cypress.Commands.add(
  'writeIntoLookupListField',
  (fieldName, partialValue, expectedListValue, typeList = false, modal = false, rewriteUrl = null) => {
    describe('Enter value into lookup list field', function() {
      let path = `#lookup_${fieldName}`;
      if (modal) {
        path = `.panel-modal ${path}`;
      }

      const aliasName = `writeIntoLookupListField-${new Date().getTime()}`;
      //the value to wait for would not be e.g. "Letter", but {key: "540408", caption: "Letter"}
      const expectedPatchValue = removeSubstringsWithCurlyBrackets(partialValue);
      // in the default pattern we want to match URLs that do *not* end with "/NEW"
      const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';
      cy.server();
      cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get(path).within(el => {
        if (el.find('.lookup-widget-wrapper input').length) {
          return (
            cy
              .get('input')
              // we can't use `clear` here as sometimes it triggers request to the server
              // and then the whole flov becomes flaky
              .type('{selectall}')
              .type(partialValue)
          );
        }

        cy.get('.lookup-dropdown').click();
        return cy.get('.lookup-dropdown').click();
      });

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', expectedListValue).click(/*{ force: true }*/);
      cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue, typeList);
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
  }
);

/**
 * Select the given list value in a static list.
 *
 * @param {boolean} modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 * @param {boolean} skipRequest - if set to true, cypress won't expect a request to the server and won't wait for it
 */
Cypress.Commands.add('selectInListField', (fieldName, listValue, modal, rewriteUrl = null, skipRequest) => {
  describe('Select value in list field', function() {
    cy.log(`selectInListField - fieldName=${fieldName}; listValue=${listValue}; modal=${modal}`);

    const patchListFieldAliasName = `patchListField-${new Date().getTime()}`;
    const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';

    // here we want to match URLs that don *not* end with "/NEW"
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(patchListFieldAliasName);

    const path = createFieldPath(fieldName, modal);

    cy.get(path)
      .find('.input-dropdown')
      .click();

    // no f*cki'n clue why it started going ape shit when there was the correct '.input-dropdown-list-option' here
    cy.get('.input-dropdown-list')
      .contains(listValue)
      .click();

    if (!skipRequest) {
      cy.waitForFieldValue(`@${patchListFieldAliasName}`, fieldName, listValue);
    }
  });
});

/**
 * Select the option with a given index from a static list. This command does not wait for response from the server.
 *
 * @param {string} fieldName - id of the field to select from
 * @param {number} index - index of the item to select
 * @param {boolean} modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 */
Cypress.Commands.add('selectNthInListField', (fieldName, index, modal) => {
  describe('Select n-th option in list field', function() {
    cy.log(`selectNthInListField - fieldName=${fieldName}; index=${index}; modal=${modal}`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('.input-dropdown')
      .click();

    cy.get('.input-dropdown-list-option').then(options => {
      for (let i = 0; i < options.length; i += 1) {
        if (i === index) {
          cy.get(options[i]).click();
        }
      }
    });
  });
});
