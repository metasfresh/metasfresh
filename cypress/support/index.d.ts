/// <reference types="cypress" />

import {DocumentStatusKey, RewriteURL} from "./utils/constants";

declare namespace Cypress {
  interface Chainable<Subject> {

    /**
     * Asserts that a particular filed is not shown (e.g. because of a display rule)
     *
     * @param fieldName name of the field is question
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     */
    assertFieldNotShown(fieldName: string, modal?: boolean): Chainable<any>

    /**
     * @param fieldName name of the field is question
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     */
    clearField(fieldName: string, modal: boolean): Chainable<any>

    /**
     * Fire header action with a certain name and expect a modal dialog to pop up within 10 secs
     *
     * @param actionName internal name of the action to be executed
     */
    executeHeaderActionWithDialog(actionName: string): Chainable<any>

    /**
     * This command runs a quick actions. If the second parameter is truthy, the default action will be executed.
     *
     * @param actionName internal name of the action to be executed
     * @param active if truthy, the default action will be executed.
     */
    executeQuickAction(actionName: string, active: boolean): Chainable<any>

    /**
     * @param fieldName name of the field is question
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     *
     * @example
     * cy.getStringFieldValue('Description').then(fieldValue => {
     *  cy.log(`Description = ${fieldValue}`)
     * });
     */
    getStringFieldValue(fieldName: string, modal?: boolean): Chainable<any>

    /**
     * @param fieldName name of the field is question
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     *
     * @example
     * cy.getCheckboxValue('IsDefault').then(checkBoxValue => {
     *  cy.log(`IsDefault = ${checkBoxValue}`)
     * });
     */
    getCheckboxValue(fieldName: string, modal?: boolean): Chainable<any>


    /**
     * @param fieldName name of the field is question
     * @param expectedPatchValue - the expected value of the checkbox
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
     * @param rewriteUrl optional, default = null; specify to which URL the command expects the frontend to patch
     *
     * @example
     * // click on a checkbox in a modal started from action
     * cy.clickOnCheckBox('CompleteIt', true, true, '/rest/api/process/');
     *
     */
    clickOnCheckBox(fieldName: string, expectedPatchValue: string, modal?: boolean, rewriteUrl ?: string): Chainable<any>


    /**
     * Open the advanced edit overlay via ALT+E shortcut
     */
    openAdvancedEdit(): Chainable<any>

    /**
     * Presses a single document's add-new-button to create a new subrow / included document
     *
     * @param includedDocumentIdAliasName optional; default value: 'newIncludedDocumentId'; the name of the alias in which the command will store the new record's ID; example " { documentId: 1000001 }";
     *
     * @example
     *
     * // press the button to open the new sub-record's modal dialog using the default documentId
     * cy.pressAddNewButton()
     *
     * // press the button to open the new sub-record's modal dialog
     * cy.pressAddNewButton('myNewIncludedDcumentId')
     *
     * // set fields
     *
     * // close the modal dialog
     * cy.pressDoneButton()
     *
     * cy.get('@myNewIncludedDcumentId').then((newIncludedDocument) => {
     *   cy.log(`going to do things with the included document we added just before; newIncludedDocument=${JSON.stringify(newIncludedDocument)}`)
     * })
     */
    pressAddNewButton(includedDocumentIdAliasName?: string): Chainable<any>

    /**
     *
     * @param action
     * @param expectedStatus - optional; if given, the command verifies the status
     *
     * @example cy.processDocument('Complete', 'Completed');
     */
    processDocument(action: string, expectedStatus?: string)

    /**
     * Select an item in a list field
     *
     * @param fieldName name of the field is question
     * @param stringValue (sub-)string of the list item to select
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     * @param rewriteUrl optional - specify to which URL the command expects the frontend to patch.
     * @param skipRequest optional, default false - if set to true, cypress won't expect a request to the server and won't wait for it
     * @example
     * // select a certain flatrate condition is a process dialog
     * cy.selectInListField('C_Flatrate_Conditions_ID', conditionsName, true, '/rest/api/process/');
     */
    selectInListField(fieldName: string, stringValue: string, modal?: boolean, rewriteUrl?: string, skipRequest ?: boolean): Chainable<any>

    /**
     * Select a reference (zoom-to-target) from the reference-sidelist
     *
     * @param internalReferenceName
     * @example
     * // this should work from a sales order
     * cy.get('body').type('{alt}6'); // open referenced-records-sidelist
     * cy.selectReference('C_Order_C_Invoice_Candidate').click();
     */
    selectReference(internalReferenceName: string): Chainable<any>

    /**
     * Opens a new single document window
     * @param windowId the metasfresh AD_Window_ID of the window to visit
     * @param recordId optional; the record ID of the record to open within the window, or NEW if a new record shall be created. If set the detail layout is shown and cypress waits for the layout into fo be send by the API; otherwise, the "table/grid" layout is shown.
     *
     * @param documentIdAliasName optional; default: visitedDocumentId; the name of the alias in which the command will store the actual record; example " { documentId: 1000001 }"; usefull if recordId=NEW;
     *
     * @example
     * // create a new business partner document
     * cy.visitWindow(123, 'NEW', 'myNewDcumentId')
     *
     * cy.get('@myNewDcumentId').then((newDocument) => {
     *   cy.log(`going to do things with the document we added just before; newDocument=${JSON.stringify(newDocument)}`)
     * })
     *
     */
    visitWindow(windowId: string, recordId?: string, documentIdAliasName?: string): Chainable<any>

    /**
     * Wait for the response to a particular patch where a particular field value was set
     * thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
     *
     * @param alias name of the alias to wait for; needs to begin with '@'
     */
    waitForFieldValue(alias: String, fieldName: String, fieldValue: String): Chainable<any>


    /**
     *
     * @param fieldName name of the field is question
     * @param partialValue string to enter into the lookup field
     * @param expectedListValue (sub-)string of the expected item to show up in the lookup list when the partial value was entered
     * @param typeList optional, default = false; use true when selecting value from a list not lookup field.
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     * @param rewriteUrl optional, default = null; specify to which URL the command expects the frontend to patch.
     */
    writeIntoLookupListField(fieldName: String, partialValue: String, expectedListValue: String, typeList?: boolean, modal?: boolean, rewriteUrl?: String): Chainable<any>


    /**
     * Write a string into an input field. Assert that the frontend performs a PATCH request with the given value.
     *
     * @param fieldName name of the field is question
     * @param stringValue the string to write. This command prepends "{enter}" to that string. Also works for number or date fields, e.g. '01/01/2018' when invoked with noRequest=true.
     * @param modal optional - set true if the field in question is assumed to be in a modal/overlay dialog.
     * @param rewriteUrl optional - specify to which URL the command expects the frontend to patch.
     * @param noRequest optional - set true if the command shall not very that a patch with the "right" response takes place. This is currently required if you use this command to non-string fields.
     *
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoStringField('Description', 'myname')
     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoStringField('Description', 'myname', true)
     */
    writeIntoStringField(fieldName: string, stringValue: string, modal?: boolean, rewriteUrl?: string, noRequest?: boolean): Chainable<any>

    /**
     * Write a string into a text area
     *
     * @param fieldName name of the field is question
     * @param stringValue the string to write. This command prepends "{enter}" to that string
     * @param modal optional, default = false; use true, if the field is in a modal overlay; required if the underlying window has a field with the same name.
     *
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoTextField('Description', 'myname')
     *
     * @example
     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoTextField('Description', 'myname', true)
     */
    writeIntoTextField(fieldName: string, stringValue: string, modal?: boolean): Chainable<any>


    /**
     * Press an overlay's "Done" button. Fail if there is a confirm dialog since that means the record could not be saved.
     *
     * @param waitBeforePress - optional; if truthy, call cy.wait with the given parameter first
     */
    pressDoneButton(waitBeforePress?: number): Chainable<any>

    /**
     * Select a tab by its name.
     *
     * @param tabName the name of the tab
     * @param forced optional, default = false;
     */
    selectTab(tabName: string, forced?: boolean): Chainable<any>


    /**
     *   Select the only row in the currently selected tab
     */
    selectSingleTabRow(): Chainable<any>

    /**
     * Mark all current notifications as read in the API and reset counter.
     */
    readAllNotifications(): Chainable<any>


    /**
     * @param waitBeforePress optional, default 0 - wait this many milliseconds before pressing the start button
     */
    pressStartButton(waitBeforePress ?: number): Chainable<any>

    /**
     * Get the notification modal from the top-right of the screen
     * which appears when an error happens, or when a process produces a notification.
     *
     * @param containsText optional, default null - look for the given test in the notification element
     *
     * @example
     * // This will regex match the text "Created: Gutschrift, Document No 170256" (observe the "/" at start and end which denotes a regex)
     * // (regex docs: https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide/Regular_Expressions)
     * cy.getNotificationModal(/Created.*Document No/);
     *
     * @example
     * // This will work as String.contains('Tibi'):
     * cy.getNotificationModal('Tibi');
     */
    getNotificationModal(containsText?: string | RegExp)

    /**
     * Select the current record ID from the URL.
     * The URL looks like `/window/123/123456789` and the value returned is `123456789`.
     */
    getCurrentRecordId(): Chainable<any>


    /**
     * Change the current value of a checkBox (Yes/No box) to the desired state (checked (true) or not checked (false).
     *
     * @param fieldName name of the field is question
     * @param isChecked if true the checkbox is set to checked state, if false the checkbox is set to unchecked state
     * @param modal - optional, default = false - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
     * @param rewriteUrl - optional, default = null - specify to which URL the command expects the frontend to patch
     */
    setCheckBoxValue(fieldName: string, isChecked: boolean, modal ?: boolean, rewriteUrl ?: RewriteURL): Chainable<any>


    /**
     * Unset the value of a list.
     * Similar to pressing the (x) button of a list.
     *
     * @param fieldName name of the field is question
     * @param modal - optional, default = false - use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
     * @param rewriteUrl - optional, default = null - specify to which URL the command expects the frontend to patch
     */
    resetListValue(fieldName: string, modal?: boolean, rewriteUrl?: RewriteURL): Chainable<any>


    /**
     * Expect specific document status
     * @param expectedDocumentStatus - the status the document should have
     *
     * @example
     * cy.expectDocumentStatus(DocumentStatusKey.Completed);
     *
     * @example
     * cy.expectDocumentStatus(DocumentStatusKey.InProgress);
     */
    expectDocumentStatus(expectedDocumentStatus: DocumentStatusKey): Chainable<any>;

  }
}
