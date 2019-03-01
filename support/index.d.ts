/// <reference types="cypress" />

declare namespace Cypress {
    interface Chainable<Subject> {
      /**
       * Opens a new single document window
       * 
       * @param recordId the record ID of the record to open within the window, or NEW if a new record shall be created
       * @param documentIdAliasName the name of the alias in which the command will store the actual record; example " { documentId: 1000001 }"; usefull if recordId=NEW; default: visitedDocumentId
       * 
       * @example
       * cy.visitWindow(123, 'NEW', 'aliasToStoreNewDocumentId')
       */
      visitWindow(windowId: BigInteger, recordId: BigInteger, documentIdAliasName: string): Chainable<any>
      /**
       * Presses a single document's add-new-button to create a new subrow / included document
       * 
       * @example
       * cy.pressAddNewButton('aliasToStoreNewDocumentId')
       */
      pressAddNewButton(includedDocumentIdAliasName: string): Chainable<any>
    }
  }