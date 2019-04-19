// thx to https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/replace
function removeSubstringsWithCurlyBrackets(stringValue) {
  const regex = /{.*}/gi;
  const expectedPatchValue = stringValue.replace(regex, '');
  return expectedPatchValue;
}

/*
 * @param modal - use true if the field is in a modal overlay; required if the underlying window has a field with the same name
 */
Cypress.Commands.add('clearField', (fieldName, modal) => {
  describe('Clear field', function() {
    cy.log(`clearField - fieldName=${fieldName}; modal=${modal}`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    cy.get(path)
      .find('input')
      .clear();
  });
});

Cypress.Commands.add('getFieldValue', (fieldName, modal) => {
  describe('Get field value', function() {
    cy.log(`getFieldValue - fieldName=${fieldName}; modal=${modal}`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    return cy
      .get(path)
      .find('input')
      .invoke('val'); /* note: beats me why .its('value'); returned undefined */
  });
});

Cypress.Commands.add('isChecked', (fieldName, modal) => {
  describe('Get field value', function() {
    cy.log(`getFieldValue - fieldName=${fieldName}; modal=${modal}`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    return cy
      .get(path)
      .find('[type="checkbox"]')
      .should('be.checked');
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
Cypress.Commands.add('clickOnCheckBox', (fieldName, expectedPatchValue, modal) => {
  describe('Click on a checkbox field', function() {
    cy.log(`clickOnCheckBox - fieldName=${fieldName}`);

    cy.server();
    cy.route('PATCH', '/rest/api/window/**').as('patchCheckBox');
    cy.route('GET', '/rest/api/window/**').as('getData');

    cy.log(`clickOnCheckBox - fieldName=${fieldName}; modal=${modal};`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }

    cy.get(path)
      .find('.input-checkbox-tick')
      .click()
      .waitForFieldValue(`@patchCheckBox`, fieldName, expectedPatchValue);
  });
});

/**
 * Should also work for date columns, e.g. '01/01/2018{enter}'.
 *
 * @param modal - use true if the field is in a modal overlay; required if the underlying window has a field with the same name
 */
Cypress.Commands.add('writeIntoStringField', (fieldName, stringValue, modal, rewriteUrl) => {
  describe('Enter value into string field', function() {
    const aliasName = `writeIntoStringField-${new Date().getTime()}`;
    const expectedPatchValue = removeSubstringsWithCurlyBrackets(stringValue);
    // in the default pattern we want to match URLs that do *not* end with "/NEW"
    const patchUrlPattern = rewriteUrl || '/rest/api/window/.*[^/][^N][^E][^W]$';
    cy.log(
      `writeIntoStringField - fieldName=${fieldName}; stringValue=${stringValue}; modal=${modal}; patchUrlPattern=${patchUrlPattern}`
    );
    cy.server();
    cy.route('PATCH', new RegExp(patchUrlPattern)).as(aliasName);
    let path = `.form-field-${fieldName}`;
    if (modal) {
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('input')
      .type(`${stringValue}`)
      .type('{enter}')
      .waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue);
  });
});

/**
 * @param modal - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
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
  (fieldName, partialValue, listValue, typeList = false, modal = false, rewriteUrl = null) => {
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
          return cy
            .get('input')
            .clear()
            .type(partialValue);
        }

        return cy.get('.lookup-dropdown').click();
      });

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', listValue).click(/*{ force: true }*/);
      cy.waitForFieldValue(`@${aliasName}`, fieldName, expectedPatchValue, typeList);
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');
    });
  }
);

/**
 * Select the given list value in a static list.
 *
 * @param modal - use true, if the field is in a modal overlay; requered if the underlying window has a field with the same name
 */
Cypress.Commands.add('selectInListField', (fieldName, listValue, modal) => {
  describe('Select value in list field', function() {
    cy.log(`selectInListField - fieldName=${fieldName}; listValue=${listValue}; modal=${modal}`);

    // here we want to match URLs that don *not* end with "/NEW"
    cy.server();
    cy.route('PATCH', new RegExp('/rest/api/window/.*[^/][^N][^E][^W]$')).as(`patchListField`);

    let path = `.form-field-${fieldName}`;
    if (modal) {
      //path = `.panel-modal-content ${path}`;
      path = `.panel-modal ${path}`;
    }
    cy.get(path)
      .find('.input-dropdown')
      .click();

    cy.contains('.input-dropdown-list-option', listValue)
      .click()
      .waitForFieldValue(`@patchListField`, fieldName, listValue);
  });
});
