/// <reference types="cypress" />

import {DocumentStatusKey, RewriteURL} from "./utils/constants";
import {ColumnAndValue} from "./commands/navigation";

declare namespace Cypress {

  // noinspection JSUnusedGlobalSymbols
  interface Chainable<Subject> {

    /**
     * Asserts that a particular filed is not shown (e.g. because of a display rule)
     *
     * @param fieldName - name of the field is question
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     */
    assertFieldNotShown(fieldName: string, modal?: boolean): Chainable<any>


    /**
     * Fire header action with a certain name and expect a modal dialog to pop up within 10 secs
     *
     * @param actionName internal name of the action to be executed
     */
    executeHeaderActionWithDialog(actionName: string): Chainable<any>

    /**
     * This command runs a quick actions.
     *
     * @param actionName - internal name of the action to be executed
     * @param modal - optional, default = false - use true if the field is in a modal overlay; required if the underlying window has a field with the same name.
     * @param isDialogExpected - optional, default true - use false if this action does not open any dialog
     */
    executeQuickAction(actionName: string, modal?: boolean, isDialogExpected ?: boolean): Chainable<any>

    /**
     * Run a quickAction inside a modal which opens a table on the right side.
     *
     * @param actionName - internal name of the action to be executed
     *
     * @example
     *
     * // Run the action "Open HU selection" window inside the window Picking Terminal (Prototype)
     * cy.executeQuickActionWithRightSideTable('WEBUI_PP_Order_HUEditor_Launcher');
     */
    executeQuickActionWithRightSideTable(actionName: string): Chainable<any>

    /**
     * Get the value of a field as a string.
     *
     * @param fieldName - name of the field
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     *
     * @example
     * cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
     */
    getStringFieldValue(fieldName: string, modal?: boolean): Chainable<any>


    /**
     * Used for reading text fields such as the `Description` field.
     *
     * @param fieldName - name of the field is question
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     *
     * @example
     * cy.getTextFieldValue('Description').should('contain', originalDocumentDescription);
     */
    getTextFieldValue(fieldName: string, modal?: boolean): Chainable<any>


    /**
     * @param fieldName - name of the field is question
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     *
     * @example
     * cy.getCheckboxValue('IsDefault').then(checkBoxValue => {
     *  cy.log(`IsDefault = ${checkBoxValue}`)
     * });
     */
    getCheckboxValue(fieldName: string, modal?: boolean): Chainable<any>

    /**
     * Select the current record ID from the URL.
     * The URL looks like `/window/123/123456789` and the value returned is `123456789`.
     *
     * @example
     * cy.getCurrentWindowRecordId().then(recordId => {
     *     // recordId has value 123456789
     *     theRecordId = recordId;
     * });
     */
    getCurrentWindowRecordId(): Chainable<any>


    /**
     * Select the total amount from Sales Invoice's header
     *
     * The header format is "DOCUMENT_NO MM/DD/YYYY totalAmount"
     * @example
     * let newTotalAmount = 0;
     * it('Save the new total amount', function () {
     *   cy.getSalesInvoiceTotalAmount().then(totalAmount => {
     *     newTotalAmount = totalAmount;
     *   });
     * });
     */
    getSalesInvoiceTotalAmount(): Chainable<any>

    /**
     * Better use {@link setCheckBoxValue} instead!
     *
     * @param fieldName - name of the field is question
     * @param expectedPatchValue - the expected value of the checkbox
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl optional, default = null; - specify to which URL the command expects the frontend to patch
     * @param skipRequest optional - set true if the command shall not verify that a patch with the "right" response takes place. This is currently required if you use this command to non-string fields.
     *
     * @example
     * // click on a checkbox in a modal started from action
     * cy.clickOnCheckBox('CompleteIt', true, true, '/rest/api/process/');
     *
     */
    clickOnCheckBox(fieldName: string, expectedPatchValue: string, modal?: boolean, rewriteUrl ?: string, skipRequest ?: boolean): Chainable<any>


    /**
     * Open the advanced edit overlay via ALT+E shortcut
     */
    openAdvancedEdit(): Chainable<any>

    /**
     * Presses a single document's add-new-button to create a new subrow / included document
     *
     * @example
     *
     * // press the button to open the new sub-record's modal dialog
     * cy.pressAddNewButton()
     * // set fields
     * // [...]
     * // close the modal dialog
     * cy.pressDoneButton()
     */
    pressAddNewButton(): Chainable<any>

    /**
     *
     * @param action
     * @param expectedStatus - optional; if given, the command verifies the status
     *
     * @example
     * cy.processDocument('Complete', 'Completed');
     */
    processDocument(action: string, expectedStatus?: string)

    /**
     * Select an item in a list field
     *
     * @param fieldName name of the field is question
     * @param stringValue (sub-)string of the list item to select
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl optional - specify to which URL the command expects the frontend to patch.
     * @param skipRequest optional, default false - if set to true, cypress won't expect a request to the server and won't wait for it
     *
     * @example
     * // select a certain flatrate condition is a process dialog
     * cy.selectInListField('C_Flatrate_Conditions_ID', conditionsName, true, '/rest/api/process/');
     */
    selectInListField(fieldName: string, stringValue: string, modal?: boolean, rewriteUrl?: string, skipRequest ?: boolean): Chainable<any>

    /**
     * Opens a new single document window
     *
     * @param windowId - the metasfresh AD_Window_ID of the window to visit
     * @param recordId - optional - the record ID of the record to open within the window, or NEW if a new record shall be created. If set the detail layout is shown and cypress waits for the layout into fo be send by the API; otherwise, the "table/grid" layout is shown.
     *
     * @example
     * // create a new business partner document
     * cy.visitWindow(123, 'NEW')
     *
     * cy.getCurrentWindowRecordId().then(id => {
     *   cy.log('Creating Bpartner with id: ' + id);
     * });
     */
    visitWindow(windowId: string | number, recordId?: string | number): Chainable<any>;

    /**
     * Wait for the response to a particular patch where a particular field value was set
     * thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
     *
     * @param alias name of the alias to wait for; needs to begin with '@'
     * @param fieldName ???? [help with docu]
     * @param fieldValue ???? [help with docu]
     */
    waitForFieldValue(alias: String, fieldName: String, fieldValue: String): Chainable<any>


    /**
     *
     * @param fieldName name of the field is question
     * @param partialValue string to enter into the lookup field
     * @param expectedListValue (sub-)string of the expected item to show up in the lookup list when the partial value was entered
     * @param typeList optional, default = false; use true when selecting value from a list not lookup field.
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl optional, default = null; specify to which URL the command expects the frontend to patch.
     * @param skipRequest optional, default false - if set to true, cypress won't expect a request to the server and won't wait for it
     */
    writeIntoLookupListField(fieldName: String, partialValue: String, expectedListValue: String, typeList?: boolean, modal?: boolean, rewriteUrl?: String, skipRequest ?: boolean): Chainable<any>


    /**
     * Write a string into an input field. Assert that the frontend performs a PATCH request with the given value.
     *
     * @param fieldName name of the field is question
     * @param value - the value to write. This command prepends "{enter}" to that string. Also works for number or date fields, e.g. '01/01/2018' when invoked with skipRequest=true.
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl optional - specify to which URL the command expects the frontend to patch.
     * @param skipRequest optional - set true if the command shall not verify that a patch with the "right" response takes place. This is currently required if you use this command to non-string fields.
     *
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoStringField('Description', 'myname')
     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoStringField('Description', 'myname', true)
     */
    writeIntoStringField(fieldName: string, value: string | number, modal?: boolean, rewriteUrl?: string, skipRequest?: boolean): Chainable<any>

    /**
     * Write a string into a text area
     *
     * @param fieldName name of the field is question
     * @param stringValue the string to write. This command prepends "{enter}" to that string
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl -optional, use custom url for the request
     * @param {boolean} skipRequest - if set to true, the PATCH request will be skipped
     *
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoTextField('Description', 'myname')
     *
     * @example
     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoTextField('Description', 'myname', true)
     */
    writeIntoTextField(fieldName: string, stringValue: string, modal?: boolean, rewriteUrl?: RewriteURL, skipRequest?: boolean): Chainable<any>


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
     * @param force optional, default = false;
     */
    selectTab(tabName: string, force?: boolean): Chainable<any>


    /**
     * Select the only row in the currently selected tab
     */
    selectSingleTabRow(): Chainable<any>

    /**
     * Expect a number of unread notifications to be displayed in the header alert element.
     *
     * Waits at most 15 seconds for a notification to appear.
     *
     */
    expectNumberOfDOMNotifications(expectedNumber: number): Chainable<any>


    /**
     * Opens the inbox notification with the given text
     * @param text - String to search for in the notification
     */
    openInboxNotificationWithText(text?: string): Chainable<any>

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
     * Waits at most 15 seconds for a notification to appear.
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
     * Change the current value of a checkBox (Yes/No box) to the desired state (checked (true) or not checked (false)).
     * If `isChecked` has any other value except `true` or `false`, nothing will be done.
     *
     * @param fieldName name of the field is question
     * @param isChecked if true the checkbox is set to checked state, if false the checkbox is set to unchecked state
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl - optional, default = null - specify to which URL the command expects the frontend to patch
     * @param skipRequest - optional, default = false - if set to true, cypress won't expect a request to the server and won't wait for it
     */
    setCheckBoxValue(fieldName: string, isChecked: boolean, modal ?: boolean, rewriteUrl ?: RewriteURL, skipRequest ?: boolean): Chainable<any>


    /**
     * Wait until the current value of a checkBox (Yes/No box) is in the desired state (checked (true) or not checked (false).
     *
     * @param fieldName name of the field is question
     * @param isChecked if true the checkbox should be in checked state, if false the checkbox should be unchecked
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     */
    expectCheckboxValue(fieldName: string, isChecked: boolean, modal?: boolean): Chainable<any>


    /**
     * Unset the value of a list.
     * Similar to pressing the (x) button of a list.
     *
     * @param fieldName - name of the field is question
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param rewriteUrl - optional, default = null - specify to which URL the command expects the frontend to patch
     */
    resetListValue(fieldName: string, modal?: boolean, rewriteUrl?: RewriteURL): Chainable<any>


    /**
     * Erase the contents of this field.
     *
     * @param fieldName - name of the field is question
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     */
    clearField(fieldName: string, modal?: boolean): Chainable<any>


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


    /**
     * Press the batch entry button
     * @param waitBeforePress - optional; if truthy, call cy.wait with the given parameter first
     */
    pressBatchEntryButton(waitBeforePress?: number): Chainable<any>


    /**
     * Close the batch entry quickInput
     * @param waitBeforePress - optional; if truthy, call cy.wait with the given parameter first
     */
    closeBatchEntry(waitBeforePress?: number): Chainable<any>


    /**
     * Basic command for clicking an element with certain selector.
     *
     * @param selector - string used to query for the element
     * @param force - use force clicking or normal clicking
     */
    clickElementWithClass(selector, force): Chainable<any>


    /**
     * This command allows waiting for the breadcrumb in the header to be visible, which
     * helps make the tests less flaky as even though the page fires load event, some
     * requests may still be pending/running.
     *
     * Command accepts two params:
     * - pageName : if we explicitly want to define what to wait for
     * - breadcrumbNr : if we want to select breadcrumb value from the redux store at
     *                  the given index
     * In case pageName is not defined, command will fall back to broadcrembNr and either
     * use the provided value or the value at index 0.
     *
     * [@TheBestPessimist]: i have no idea how to use this.
     */
    waitForHeader(pageName, breadcrumbNr): Chainable<any>


    /**
     * Open the referenced documents sidebar then click a reference.
     *
     * If the parameter `referenceId` is not present then only open the sidebar.
     *
     * @param referenceId - optional - the reference id

     * @example
     * // This is equivalent to pressing `[alt + 6]`, then selecting one of the referenced documents:
     * cy.get('body').type('{alt}6');
     * Select with mouse "Material Receipt Candidates"
     *
     * @example
     * // Only open the documents sidebar
     * cy.openReferencedDocuments();
     *
     * @example
     * // Open the sidebar and select a specific document
     * cy.openReferencedDocuments('AD_RelationType_ID-540150');
     */
    openReferencedDocuments(referenceId?: string): Chainable<any>


    /**
     * Select the option with a given index from a static list. This command does not wait for response from the server.
     *
     * @param fieldName - id of the field to select from
     * @param index - index of the item to select
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     */
    selectNthInListField(fieldName: string, index: number, modal?: boolean): Chainable<any>


    ////////////////////////////////
    ////////////////////////////////
    // ALL OF THESE FUNCTIONS NEED DOCUMENTATION!

    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/action.js
     */
    clickHeaderNav(navName): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/action.js
     */
    executeHeaderAction(actionName): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/form.js
     */
    clickOnIsActive(modal): Chainable<any>


    /**
     * It selects the current date from the picker
     * @fieldName - the date field for which the current date is selected
     * @modal - optional, default false, set to true is the date field is in a modal
     * from cypress/support/commands/form.js
     */
    selectDateViaPicker(fieldName: string, modal?: boolean): Chainable<any>


    /**Selects a date in the picker
     * should not be used for offsets larger than a couple of days
     * @param {string} fieldName - name of the field
     * @param {number} dayOffset - the number of days before/after today;
     * @param {boolean} modal - optional, default false, use true, if the field is in a modal overlay; required if the underlying window has a field with the same name
     */
    selectOffsetDateViaPicker(fieldName: string, dayOffset: number, modal?: boolean): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * cypress/support/commands/general.js
     */
    loginViaAPI(username, password, redirect): Chainable<any>

    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * cypress/support/commands/general.js
     */
    tab(prevSubject, subject, direction, options): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * cypress/support/commands/general.js
     */
    active(options): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * cypress/support/commands/general.js
     */
    readAllNotifications(): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/navigation.js
     */


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/navigation.js
     */
    clickButtonWithText(text): Chainable<any>


    /**
     * Please help with documentation!
     * The file where this function is declared appears below, however the parameters in this definition may be wrong. Please adjust as needed.
     *
     * from cypress/support/commands/test.js
     */
    editAddress(fieldName, addressFunction): Chainable<any>

    /**
     * Wait until a process is finished.
     *
     * Currently it just waits for 10 seconds, but maybe in the future backend will announce when a process is finished, and we will wait for that.
     */
    waitUntilProcessIsFinished(): Chainable<any>


    /**
     * Select the nth row in a list. Starts from 0.
     *
     * @param rowNumber - the row number
     * @param modal - optional, default = false - use true if the field is in a modal overlay
     * @param force - optional, default = false - use true when no checks should be done if the selection was successful;
     */
    selectNthRow(rowNumber: number, modal?: boolean, force ?: boolean): Chainable<any>


    /**
     * Expect the table to have a specific number of rows
     *
     * @param numberOfRows - the number of rows
     * @param modal - optional, default = false - use true if the table is in a modal overlay
     */
    expectNumberOfRows(numberOfRows: number, modal?: boolean): Chainable<any>

    /**
     * Complete the current document
     */
    completeDocument(): Chainable<any>

    /**
     * Reactivate the current document
     */
    reactivateDocument(): Chainable<any>

    /**
     * Reverse the current document
     */
    reverseDocument(): Chainable<any>

    /**
     * Wait until everything is saved and all requests are finished.
     *
     * @param expectIndicator - optional, default false - if true, expect the ".indicator-pending" save bar to exist then disappear
     */
    waitForSaveIndicator(expectIndicator ?: boolean): Chainable<any>

    /**
     * Open the notifications inbox/bell and select the first notification containing the expected value.
     *
     * @param expectedValue - the expected text of the notification. Can be string or RegExp
     */
    selectNotificationContaining(expectedValue: string | RegExp): Chainable<any>

    /**
     * Open the notifications inbox/bell and click the first notification containing the expected value.
     * This expects that clicking the notification will redirect us to a new window.
     *
     * @param expectedValue - the expected text of the notification. Can be string or RegExp
     * @param destinationWindowID - the expected window where the notification redirects
     */
    openNotificationContaining(expectedValue: string | RegExp, destinationWindowID: string | number): Chainable<any>

    /**
     * Expect the table rows to be greater than a given number
     *
     * @param numberOfRows - the number of rows
     */
    expectNumberOfRowsToBeGreaterThan(numberOfRows: number): Chainable<any>


    /**
     * Select the left table from a modal dialog with 2 tables side by side.
     */
    selectLeftTable(): Chainable<any>

    /**
     * Searches and selects a HU using the Barcode Filter.
     * Select a single table row by using column's `data-cy` attribute and the expected value of that column (the HU Barcode).
     *
     * @param columnAndValue - object or array of objects having 2 fields: `{ column: 'Value', value: 'P002753' }`
     * @param modal - optional, default = false - use true if the table is in a modal overlay
     * @param force - optional, default = false - use true when no checks should be done if the selection was successful
     *
     * @example
     * // usage in Handling Unit Editor window
     * cy.visitWindow(540189);
     * clearNotFrequentFilters();
     * const columnAndValue =  { column: 'Value', value: 1000014 }; // using a simple object
     * cy.selectItemUsingBarcodeFilter(columnAndValue);
     *
     * @example
     * // usage when inside a left/right table (eg. during Picking)
     * // note that we're using `.within()`
     * // also note that we're setting `modal=false`, and `force=true` because of within
     * const columnAndValue =  { column: 'Value', value: 1000014 }; // using a simple object
     *
     * cy.selectRightTable().within(() => {
     *   cy.selectItemUsingBarcodeFilter(columnAndValue, false, true).click();
     * });
     */
    selectItemUsingBarcodeFilter(columnAndValue: ColumnAndValue, modal ?: boolean, force ?: boolean): Chainable<any>

    /**
     * Select the right table from a modal dialog with 2 tables side by side.
     */
    selectRightTable(): Chainable<any>


    /**
     * Searches and selects one or multiple rows of a table by using column's `data-cy` attribute and the expected value of that column.
     *
     * @param columnAndValue - object or array of objects having 2 fields: `{ column: 'Value', value: 'P002753' }`
     * @param modal - optional, default = false - use true if the table is in a modal overlay
     * @param force - optional, default = false - use true when no checks should be done if the selection was successful
     * @param single - optional, default = true - use  false when expecting multiple rows
     *
     * @example
     * // normal usage
     * cy.visitWindow('140'); // Product window
     * const columnAndValue =  { column: 'Value', value: 'P002753' }; // using a simple object
     * cy.selectRowByColumnAndValue(columnAndValue).dblclick();
     *
     * @example
     * // multiple values for the same row
     * cy.visitWindow('140'); // Product window
     * const columnAndValue = [ // using an array of simple objects
     *   { column: 'Value', value: 'P002753' },
     *   { column: 'Name', value: 'Abzug für Beitrag Basic-Linie' },
     * ];
     * cy.selectRowByColumnAndValue(columnAndValue).dblclick();
     *
     * @example
     * // usage when inside a left/right table (eg. during Picking)
     * // note that we're using `.within()`
     * // also note that we're setting `modal=false`, and `force=true` because of within
     * const columnAndValue = [ // using an array of Class objects
     *   new ColumnAndValue('M_Product_ID', 'Convenience Salat 250g'),
     *   new ColumnAndValue('QtyOrdered', 20)
     * ];
     * cy.selectRightTable().within(() => {
     *   cy.selectRowByColumnAndValue(columnAndValue, false, true).click();
     * });
     */
    selectRowByColumnAndValue(columnAndValue: ColumnAndValue | ColumnAndValue[], modal ?: boolean, force ?: boolean, single ?: boolean): Chainable<any>

    /**
     * Select all rows on the current page
     */
    selectAllRowsOnCurrentPage(): Chainable<any>

    /**
     * Count all rows from all pages
     */
    countAllRows(): Chainable<any>
  }

}
