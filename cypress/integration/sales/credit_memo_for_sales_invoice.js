/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/// <reference types="Cypress" />


import {getLanguageSpecific} from "../../support/utils/utils";
import {salesInvoices} from "../../page_objects/sales_invoices";


describe('Create a Credit memo for Sales Invoice', function () {

  const creditMemo = 'Credit Memo';
  let originalSalesInvoiceNumber;
  let originalPriceList;
  let originalCurrency;
  let originalProduct;
  let originalQuantity;

  it('Open Sales Invoice Window', () => {
    cy.visitWindow('167', '1000010');
  });


  it('Sales Invoice is not paid', function () {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`);
      assert.equal(checkBoxValue, false);
    });
  });

  it('Sales Invoice is Completed', function () {
    expectDocumentStatus(DocumentStatus.Completed);
  });

  it('Save values needed for the next step', function () {
    cy.getStringFieldValue('DocumentNo').then(documentNumber => {
      originalSalesInvoiceNumber = documentNumber;
    });

    cy.getStringFieldValue('M_PriceList_ID').then(priceList => {
      originalPriceList = priceList;
    });

    cy.getStringFieldValue('C_Currency_ID').then(currency => {
      originalCurrency = currency;
    });


    cy.selectTab('C_InvoiceLine');
    cy.selectSingleTabRow();
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_Product_ID', true).then(product => {
      originalProduct = product;
    });

    cy.getStringFieldValue('QtyEntered', true).then(qty => {
      originalQuantity = qty;
    });
    cy.pressDoneButton();
  });

  it('Create the Credit Memo', function () {
    cy.executeHeaderActionWithDialog('C_Invoice_Create_CreditMemo');

    cy.selectInListField('C_DocType_ID', creditMemo, true, null, true);

    // ensure all the checkboxes are ok
    setCheckBoxValue('CompleteIt', false, true, RewriteURL.PROCESS);
    setCheckBoxValue('IsReferenceOriginalOrder', true, true, RewriteURL.PROCESS);
    setCheckBoxValue('IsReferenceInvoice', true, true, RewriteURL.PROCESS);
    setCheckBoxValue('IsCreditedInvoiceReinvoicable', false, true, RewriteURL.PROCESS);

    cy.pressStartButton(100);
    cy.getNotificationModal(/Created.*Document No/);
  });

  it('Open the Referenced Sales Invoice documents', function () {
    cy.wait(5000);  // give the system some breathing space
    cy.get('body').type('{alt}6'); // open referenced-records-sidelist
    cy.selectReference('AD_RelationType_ID-540184').click();
  });


  it('Ensure there is only 1 Sales Invoice row and open it', function () {
    // salesInvoices.getRows().should('have.length', 1);

    // select the first Table Row and click it (open it)
    salesInvoices.getRows().eq(0).dblclick();
  });


  it('The Sales Invoice is a Credit Memo', function () {
    expectDocumentStatus(DocumentStatus.InProgress);
    cy.getStringFieldValue('C_DocTypeTarget_ID').should('be.equal', creditMemo);
  });


  it('Has the same properties and reference to the original', function () {
    cy.getStringFieldValue('DocumentNo').should('not.be.equal', originalSalesInvoiceNumber);
    cy.getStringFieldValue('M_PriceList_ID').should('be.equal', originalPriceList);
    cy.getStringFieldValue('C_Currency_ID').should('be.equal', originalCurrency);
  });


  it('Has the same properties and reference to the original -- Advanced edit', function () {
    cy.openAdvancedEdit();
    cy.getStringFieldValue('Ref_Invoice_ID', true).should('be.equal', originalSalesInvoiceNumber);
    cy.pressDoneButton();
  });


  it('Has the same properties and reference to the original -- Line advanced edit', function () {
    cy.selectTab('C_InvoiceLine');
    cy.selectSingleTabRow();
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_Product_ID', true).should('be.equal', originalProduct);
    cy.getStringFieldValue('QtyEntered', true).should('be.equal', originalQuantity);
    cy.pressDoneButton();
  });
});


//////////////////////////////////////////
//////////////////////////////////////////
//        all the functions/classes below should be public and/or added as `cy.helper`
//
//


//////////////////////////////////////////
//////////////////////////////////////////
function setCheckBoxValue(fieldName, checked, modal = false, rewriteUrl = null) {

  // the expected value is the same as the checked state
  // (used only for verification if the checkbox has the correct value)
  const expectedPatchValue = checked;

  cy.getCheckboxValue(fieldName, modal).then(theCheckboxValue => {
    if (checked) {
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
}

//////////////////////////////////////////
//////////////////////////////////////////


//////////////////////////////////////////
//////////////////////////////////////////
export class RewriteURL {
  /**
   * This is the default
   *
   * @type {string}
   */
  static WINDOW = '/rest/api/window/.*[^/][^N][^E][^W]$';

  static PROCESS = '/rest/api/process/';
}

//////////////////////////////////////////
//////////////////////////////////////////


///////////////////////////
///////////////////////////
export class DocumentStatus {
  static Completed = 'docStatusCompleted';
  static _tag_docStatusCompleted = '.tag-success';

  static InProgress = 'docStatusInProgress';
  static _tag_docStatusInProgress = '.tag-default';
}

function expectDocumentStatus(expectedDocumentStatus) {
  cy.fixture('misc/misc_dictionary.json').then(miscDictionaryJson => {
    const expectedTrl = getLanguageSpecific(miscDictionaryJson, expectedDocumentStatus);
    const documentTag = DocumentStatus[`_tag_${expectedDocumentStatus}`];
    cy.get(`.meta-dropdown-toggle ${documentTag}`).contains(expectedTrl);
  });
}

//////////////////////////////////////////
//////////////////////////////////////////
