/// <reference types="cypress" />

declare namespace Cypress {
  interface Chainable<Subject> {

    /**
     * Write a string into an input field. Assert that the frontend performs a PATCH request with the given value.
     * 
     * @param fieldName name of the field is question
     * @param stringValue the string to write. This command prepends "{enter}" to that string. Also works for date fields, e.g. '01/01/2018'.
     * @param modal optional - set true if the field in question is in a modal dialog.
     * @param rewriteUrl optional - specify to which URL the command expects the frontend to patch.
     * 
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoStringField('Description', 'myname')

     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoStringField('Description', 'myname', true)
     */
    writeIntoStringField(fieldName: string, stringValue: string, modal: boolean): Chainable<any>

    /**
     * Write a string into a text area
     * 
     * @param fieldName name of the field is question
     * @param stringValue the string to write. This command prepends "{enter}" to that string
     * @param modal optional - set true if the field in question is in a modal/overlay dialog.
     * 
     * @example
     * // This will work also with modal dialogs, *unless* there is also a description field in the underlying document
     * cy.writeIntoTextField('Description', 'myname')
     * 
     * // This will fail if the field in question is *not* in a modal dialog
     * cy.writeIntoTextField('Description', 'myname', true)
     */
    writeIntoTextField(fieldName: string, stringValue: string, modal: boolean): Chainable<any>

    /**
     * Presses a single document's add-new-button to create a new subrow / included document
     * 
     * @param aliasToStoreNewDocumentId the name of the alias in which the command will store the new record's ID; example " { documentId: 1000001 }";
     * 
     * @example
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
    pressAddNewButton(includedDocumentIdAliasName: string): Chainable<any>

    /**
     * Opens a new single document window
     * 
     * @param recordId the record ID of the record to open within the window, or NEW if a new record shall be created
     * @param documentIdAliasName the name of the alias in which the command will store the actual record; example " { documentId: 1000001 }"; usefull if recordId=NEW; default: visitedDocumentId
     * 
     * @example
     * // create a new business partner document
     * cy.visitWindow(123, 'NEW', 'myNewDcumentId')
     * 
     * // set fields
     * 
     * cy.get('@myNewDcumentId').then((newDocument) => {
     *   cy.log(`going to do things with the document we added just before; newDocument=${JSON.stringify(newDocument)}`)
     * })
     */
    visitWindow(windowId: BigInteger, recordId: BigInteger, documentIdAliasName: string): Chainable<any>

    /**
     * May be useful to wait for the response to a particular patch where a particular field value was set
     * not yet tested
     * thx to https://github.com/cypress-io/cypress/issues/387#issuecomment-458944112
     * 
     * @param alias name of the alias to wait for; needs to begin with '@'
     */
    waitForFieldValue(alias: String, fieldName: String, fieldValue: String): Chainable<any>
  }
}