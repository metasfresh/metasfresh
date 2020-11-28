import { confirmCalendarDay } from '../functions';
import { RewriteURL } from '../utils/constants';

// thx to https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replace
/**
 * TODO tbp: I don't know what's the point of this function.
 *    And since idk what it does, I want to remove it as it looks useless to me.
 *    Check what this does and remove it if it's useless
 */
function removeSubstringsWithCurlyBrackets(stringValue) {
  const regex = /{.*}/gi;

  if (stringValue && !stringValue.match) {
    return stringValue;
  }

  return stringValue.replace(regex, '');
}

Cypress.Commands.add('clearField', (fieldName, modal) => {
  cy.log(`clearField - fieldName=${fieldName}; modal=${modal}`);

  const path = createFieldPath(fieldName, modal);
  cy.get(path)
    .find('input')
    .clear();
});

Cypress.Commands.add('getStringFieldValue', (fieldName, modal) => {
  cy.log(`getStringFieldValue - fieldName=${fieldName}; modal=${modal}`);

  cy.waitForSaveIndicator();

  const path = createFieldPath(fieldName, modal);
  return cy
    .get(path)
    .find('input')
    .invoke('val'); /* note: beats me why .its('value'); returned undefined */
});

Cypress.Commands.add('getTextFieldValue', (fieldName, modal) => {
  cy.log(`getStringFieldValue - fieldName=${fieldName}; modal=${modal}`);

  const path = createFieldPath(fieldName, modal);
  return cy
    .get(path)
    .find('textarea')
    .invoke('val'); /* note: beats me why .its('value'); returned undefined */
});

Cypress.Commands.add('assertFieldNotShown', (fieldName, modal) => {
  cy.log(`assertFieldNotShown - fieldName=${fieldName}; modal=${modal}`);

  const path = createFieldPath(fieldName, modal);
  return cy.get(path).should('not.exist');
});

function createFieldPath(fieldName, modal) {
  let path = `.form-field-${fieldName}`;
  if (modal) {
    path = `.panel-modal ${path}`;
  }
  return path;
}

Cypress.Commands.add('getCheckboxValue', (fieldName, modal) => {
  cy.log(`getCheckboxValue - fieldName=${fieldName}; modal=${modal}`);

  const path = createFieldPath(fieldName, modal);

  return cy.get(path).then(el => {
    // noinspection RedundantIfStatementJS
    if (el.find('checked').length || el.find('.checked').length) {
      return true;
    }
    return false;
  });
});

Cypress.Commands.add('expectCheckboxValue', (fieldName, isChecked, modal) => {
  cy.log(`expectCheckboxValue - fieldName=${fieldName}; isChecked=${isChecked}; modal=${modal}`);

  cy.waitForSaveIndicator();

  const path = createFieldPath(fieldName, modal);

  const timeout = { timeout: 20000 };
  if (isChecked) {
    return cy
      .get(path)
      .find('.checked', timeout)
      .should('exist');
  } else {
    return cy
      .get(path)
      .find('.checked', timeout)
      .should('not.exist');
  }
});

Cypress.Commands.add('resetListValue', (fieldName, modal, rewriteUrl = null) => {
  cy.log(`resetListValue - fieldName=${fieldName}; modal=${modal}`);

  const patchUrlPattern = rewriteUrl || RewriteURL.Generic;
  const patchListValueAliasName = `patchListValue-${fieldName}-${new Date().getTime()}`;

  cy.server();
  cy.route('PATCH', new RegExp(patchUrlPattern)).as(patchListValueAliasName);

  const path = createFieldPath(fieldName, modal);

  cy.get(path)
    .find('.meta-icon-close-alt')
    .click();

  // todo rename resetListValue to something with Clear, so that all "clearing" methods have the same name (ie clear Field value)
  // todo make this work for lists as well, and rename properly
  // todo check if there are any other clearing/resetting methods
  cy.get(path).waitForFieldValue(`@${patchListValueAliasName}`, fieldName);
});

Cypress.Commands.add('clickOnIsActive', modal => {
  const path = createFieldPath('IsActive', modal);

  cy.get(path)
    .find('.input-slider')
    .click();
});

/*
 * @param modal - use true if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param {boolean} skipPatch - if true - the patch request will be skipped
 */
Cypress.Commands.add('clickOnCheckBox', (fieldName, expectedPatchValue, modal, rewriteUrl = null, skipPatch = false) => {
  cy.log(`clickOnCheckBox - fieldName=${fieldName}`);

  const patchUrlPattern = rewriteUrl || RewriteURL.Generic;
  const patchCheckBoxAliasName = `patchCheckBox-${fieldName}-${new Date().getTime()}`;
  if (!skipPatch) {
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(patchCheckBoxAliasName);
  }
  cy.log(`clickOnCheckBox - fieldName=${fieldName}; modal=${modal};`);

  const path = createFieldPath(fieldName, modal);

  cy.get(path)
    .find('.input-checkbox-tick')
    .click(); // we don't care if the checkbox scrolled out of view
  if (!skipPatch) {
    cy.waitForFieldValue(`@${patchCheckBoxAliasName}`, fieldName, expectedPatchValue);
  }
});

/**
 * Right now it can only select the current date
 * @param {string} fieldName - name of the field
 * @param {boolean} modal - use true, if the field is in a modal overlay
 */
Cypress.Commands.add('selectDateViaPicker', (fieldName, modal) => {
  const path = createFieldPath(fieldName, modal);

  cy.get(path)
    .find('.datepicker')
    .click();

  confirmCalendarDay();

  cy.get(`${path} input`).should($input => {
    const val = $input.val();

    assert.isOk(val, 'date set');
  });

  cy.get(path)
    .find('.form-control-label')
    .click();
});

/**Selects a date in the picker
 * should not be used for offsets larger than a couple of days
 * @param {string} fieldName - name of the field
 * @param {number} dayOffset - the number of days before/after today;
 * @param {boolean} modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 */
Cypress.Commands.add('selectOffsetDateViaPicker', (fieldName, dayOffset, modal) => {
  const path = createFieldPath(fieldName, modal);

  cy.get(path)
    .find('.datepicker')
    .click();
  cy.get('.rdtPicker td').then(e => {
    /**get the index of the day to select in the date picker */
    let dayIndex = e.index(e.filter('.rdtToday')) + dayOffset;
    e.filter(i => dayIndex == i).click();
  });
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
  const aliasName = `writeIntoStringField-${fieldName}-${new Date().getTime()}`;
  const expectedPatchValue = removeSubstringsWithCurlyBrackets(`${stringValue}`);
  const patchUrlPattern = rewriteUrl || RewriteURL.Generic; // todo @TheBestPessimist: get rid of rewriteUrl parameter everywhere and just use "generic". it's useless in the way we're using it now.
  cy.log(`writeIntoStringField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}; patchUrlPattern=${patchUrlPattern}`);

  const path = createFieldPath(fieldName, modal);
  cy.get(path)
    .find('input')
    .type('{selectall}')
    .wait(500)
    .type(stringValue, { delay: 20 });
  // if a typed string has missing characters, maybe the delay of type is not good and we should try another workaround.
  // for more details see: https://github.com/cypress-io/cypress/issues/3817
  // If you are wondering why we are using the wait(500) above ^^
  // Notes: 
  //  - we tried to used `delay` but that did not worked and made the tests to fail with chopped text
  //  - we picked the easiest solution, there are more complex solutions also but imply much allocated time to fix
  //    there is such solution documented in the link above (there are two links within to a blog) with event listeners on elements
  //  https://www.cypress.io/blog/2019/01/22/when-can-the-test-click/
  //  https://www.cypress.io/blog/2018/02/05/when-can-the-test-start/

  if (!noRequest) {
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);
  }

  cy.get(path)
    .find('input')
    .type('{enter}');

  if (!noRequest) {
    cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue);
  }
  cy.waitForSaveIndicator();
});

/**
 * Function to fill in textareas
 *
 * @param {string} fieldName - name of the field
 * @param {string} stringValue - value to put into field
 * @param {boolean} modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param {string} rewriteUrl - use custom url for the request
 * @param {boolean} skipRequest - if set to true, the PATCH request will be skipped
 */
Cypress.Commands.add('writeIntoTextField', (fieldName, stringValue, modal = false, rewriteUrl = null, skipRequest = false) => {
  cy.log(`writeIntoTextField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}`);

  const aliasName = `writeIntoTextField-${fieldName}-${new Date().getTime()}`;
  const expectedPatchValue = removeSubstringsWithCurlyBrackets(`${stringValue}`);
  // in the default pattern we want to match URLs that do *not* end with "/NEW"
  const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';

  // here we want to match URLs that don *not* end with "/NEW"
  cy.server();
  cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);

  if (stringValue) {
    const path = createFieldPath(fieldName, modal);
    cy.get(path)
      .find('textarea')
      .wait(500)
      .type(`${stringValue}{enter}`);
  }

  if (!skipRequest) {
    cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue);
  }
});

/**
 * @param modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
 * @param typeList - use when selecting value from a list not lookup field. Someone thought it's a great idea to return different
 *                   responses for different fields.
 * @param {boolean} skipRequest - if set to true, the PATCH request will be skipped
 */
Cypress.Commands.add('writeIntoLookupListField', (fieldName, partialValue, expectedListValue, typeList = false, modal = false, rewriteUrl = null, skipRequest = false) => {
  // TODO: i believe we can get rid of param typeList. It should be calculated depending on skipRequest.
  //    Or even better: we could clear the LookupList value before setting it, hence always expecting a value, and no longer skipping request checks.
  // TODO: rename this function to "selectInSearchField"
  let path = `#lookup_${fieldName}`;
  if (modal) {
    path = `.panel-modal ${path}`;
  }

  const aliasName = `writeIntoLookupListField-${fieldName}-${new Date().getTime()}`;
  //the value to wait for would not be e.g. "Letter", but {key: "540408", caption: "Letter"}
  const expectedPatchValue = removeSubstringsWithCurlyBrackets(`${partialValue}`);
  // in the default pattern we want to match URLs that do *not* end with "/NEW"
  const patchUrlPattern = rewriteUrl || '/rest/api/window';
  if (!skipRequest) {
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);
  }
  cy.get(path).within(el => {
    if (el.find('.lookup-widget-wrapper input').length) {
      return (
        cy
          .get('input')
          // we can't use `clear` here as sometimes it triggers request to the server
          // and then the whole flow becomes flaky
          .type('{selectall}')
          .type(partialValue)
      );
    }

    // this is extremely fiddly when selecting from a combo field such as bpartner address.
    // it will work locally, but most of the times will fail in jenkins.
    // Please create the tests such that adding an address in a combo field is not mandatory!
    return cy.get('.lookup-dropdown').click();
  });

  cy.get('.input-dropdown-list').should('exist');
  cy.contains('.input-dropdown-list-option', expectedListValue).click();
  if (!skipRequest) {
    cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue, typeList /*expectEmptyRequest*/);
  }
  cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
});

/**
 * Select the given list value in a static list.
 *
 * @param {boolean} modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 * @param {boolean} skipRequest - if set to true, cypress won't expect a request to the server and won't wait for it
 */
Cypress.Commands.add('selectInListField', (fieldName, listValue, modal, rewriteUrl = null, skipRequest) => {
  cy.log(`selectInListField - fieldName=${fieldName}; listValue=${listValue}; modal=${modal}`);

  const patchListFieldAliasName = `patchListField-${fieldName}-${new Date().getTime()}`;
  const patchUrlPattern = rewriteUrl || RewriteURL.Generic;

  // here we want to match URLs that don *not* end with "/NEW"
  if (!skipRequest) {
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(patchListFieldAliasName);
  }
  const path = createFieldPath(fieldName, modal);

  cy.get(path)
    .find('.input-dropdown')
    .click();  // -- removed click as dropdown shows up when you clear and type

  // no f*cki'n clue why it started going ape shit when there was the correct '.input-dropdown-list-option' here
  cy.get('.input-dropdown-list')
    .contains(listValue)
    .click();

  if (!skipRequest) {
    cy.waitForFieldValue(`@${patchListFieldAliasName}`, fieldName, listValue);
  }
});

/**
 * Select the option with a given index from a static list. This command does not wait for response from the server.
 *
 * @param {string} fieldName - id of the field to select from
 * @param {number} index - index of the item to select
 * @param {boolean} modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 */
Cypress.Commands.add('selectNthInListField', (fieldName, index, modal) => {
  cy.log(`selectNthInListField - fieldName=${fieldName}; index=${index}; modal=${modal}`);

  const path = createFieldPath(fieldName, modal);
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

Cypress.Commands.add('setCheckBoxValue', (fieldName, isChecked, modal = false, rewriteUrl = null, skipRequest = false) => {
  cy.log(`Set the Checkbox value ${fieldName} to ${isChecked}`);

  // the expected value is the same as the checked state
  // (used only for verification if the checkbox has the correct value)
  const expectedPatchValue = isChecked;

  // only allow true or false values
  if (!(isChecked === true || isChecked === false)) {
    return;
  }

  cy.getCheckboxValue(fieldName, modal).then(theCheckboxValue => {
    if (isChecked) {
      if (!theCheckboxValue) {
        cy.clickOnCheckBox(fieldName, expectedPatchValue, modal, rewriteUrl, skipRequest);
      }
    } else {
      if (theCheckboxValue) {
        cy.clickOnCheckBox(fieldName, expectedPatchValue, modal, rewriteUrl, skipRequest);
      }
    }
  });
});
