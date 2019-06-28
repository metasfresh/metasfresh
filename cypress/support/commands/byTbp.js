/*
@kuba idk where all these should be placed so i just threw them here.

Pls tell me the _organisation_ standards for cypress/js :^).
 */


import {getLanguageSpecific} from "../utils/utils";

Cypress.Commands.add('getCurrentRecordId', () => {
  describe('Select the current record ID from the url', function () {
    let currentRecordId = 0;
    cy.url().then(ulrr => {
      currentRecordId = ulrr.split('/').pop();
    });
    return currentRecordId;
  });
});


Cypress.Commands.add('setCheckBoxValue', (fieldName, isChecked, modal = false, rewriteUrl = null) => {
  describe(`Set the Checkbox value ${fieldName} to ${isChecked}`, function () {

    // the expected value is the same as the checked state
    // (used only for verification if the checkbox has the correct value)
    const expectedPatchValue = isChecked;

    cy.getCheckboxValue(fieldName, modal).then(theCheckboxValue => {
      if (isChecked) {
        if (theCheckboxValue) {
          // Nothing to do, already checked
        } else {
          cy.clickOnCheckBox(fieldName, expectedPatchValue, modal, rewriteUrl);
        }
      } else {
        if (theCheckboxValue) {
          cy.clickOnCheckBox(fieldName, expectedPatchValue, modal, rewriteUrl);
        } else {
          // Nothing to do, already unchecked
        }
      }
    });
  });
});

/**
 * These constants should be used instead of writing strings every time
 */
export class RewriteURL {
  // noinspection JSUnusedGlobalSymbols
  /**
   * WINDOW is the default
   */
  static WINDOW = '/rest/api/window/.*[^/][^N][^E][^W]$';

  static PROCESS = '/rest/api/process/';
}


Cypress.Commands.add('expectDocumentStatus', (expectedDocumentStatus) => {
  describe(`Expect specific document status`, function () {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionaryJson => {
      const expectedTrl = getLanguageSpecific(miscDictionaryJson, expectedDocumentStatus);
      const documentTag = DocumentStatusKey[`_tag_${expectedDocumentStatus}`];
      cy.get(`.meta-dropdown-toggle ${documentTag}`).contains(expectedTrl);
    });
  });
});

export class DocumentStatusKey {
  static Completed = 'docStatusCompleted';
  // noinspection JSUnusedGlobalSymbols
  static _tag_docStatusCompleted = '.tag-success';


  static InProgress = 'docStatusInProgress';
  // noinspection JSUnusedGlobalSymbols
  static _tag_docStatusInProgress = '.tag-default';
}
