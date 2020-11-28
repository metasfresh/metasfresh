/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
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

import { SalesInvoice, SalesInvoiceLine } from '../../support/utils/sales_invoice';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { DocumentStatusKey } from '../../support/utils/constants';
import { BPartner } from '../../support/utils/bpartner';
import { DunningCandidates } from '../../page_objects/dunning_candidates';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';
import { DunningType } from '../../support/utils/dunning_type';
import { DunningDocuments } from '../../page_objects/dunning_documents';
import { salesInvoices } from '../../page_objects/sales_invoices';

describe('Create Dunning Documents', function() {
  let dunningTypeName;

  let businessPartnerName;
  let paymentTerm;

  let salesInvoiceTargetDocumentType;
  let productName;
  let originalQuantity;

  // Test data
  let siDocumentNumber;
  let siCurrency;
  let siRecordId;

  it('Read the fixture', function() {
    cy.fixture('dunning/create_dunning_documents.json').then(f => {
      businessPartnerName = appendHumanReadableNow(f['businessPartnerName']);
      productName = f['productName'];
      originalQuantity = f['originalQuantity'];
      dunningTypeName = appendHumanReadableNow(f['dunningTypeName']);
      paymentTerm = f['paymentTerm'];
      salesInvoiceTargetDocumentType = f['salesInvoiceTargetDocumentType'];
    });
  });

  it('Prepare dunning type', function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });
  });

  it('Prepare customer bpartner (via api)', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: businessPartnerName })
        .setDunning(dunningTypeName)
        .setPaymentTerm(paymentTerm)
        .apply();
    });
  });

  it('Prepare sales invoice', function() {
    // eslint-disable-next-line
    new SalesInvoice(businessPartnerName, salesInvoiceTargetDocumentType)
      .addLine(new SalesInvoiceLine().setProduct(productName).setQuantity(originalQuantity))
      .apply();
    cy.completeDocument();
  });

  it('Sales Invoice is Completed', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
  });

  it('Sales Invoice is not paid', function() {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`);
      assert.equal(checkBoxValue, false);
    });
  });

  it('Save values needed for the next step', function() {
    cy.getStringFieldValue('DocumentNo').then(documentNumber => {
      siDocumentNumber = documentNumber;
    });

    cy.getStringFieldValue('C_Currency_ID').then(currency => {
      siCurrency = currency;
    });

    cy.getCurrentWindowRecordId().then(function(record) {
      siRecordId = record;
    });
  });

  it('Create Dunning Candidates', function() {
    DunningCandidates.visit();

    cy.executeHeaderActionWithDialog('C_Dunning_Candidate_Create');
    cy.setCheckBoxValue('IsFullUpdate', true, true);
    cy.pressStartButton();
  });

  it('Ensure there are exactly 2 Dunning Candidates', function() {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);

    DunningCandidates.getRows().should('have.length', 2);
  });

  it('Create Dunning Documents', function() {
    DunningCandidates.selectAllVisibleRows();
    cy.executeHeaderActionWithDialog('C_Dunning_Candidate_Process');
    cy.setCheckBoxValue('IsComplete', true, true);
    cy.pressStartButton();
  });

  describe('Check Dunning Documents', function() {
    it('Expect there are exactly 2 dunning documents created!', function() {
      cy.visitWindow(salesInvoices.windowId, siRecordId);
      cy.openReferencedDocuments('C_Invoice-C_DunningDoc');
      DunningDocuments.getRows().should('have.length', 2);
    });

    it('Check Level 1 document', function() {
      const dunningLevel = 'Level 1';
      checkDunningDocument(dunningLevel);
    });

    it('Check Level 2 document', function() {
      const dunningLevel = 'Level 2';
      checkDunningDocument(dunningLevel);
    });
  });

  function checkDunningDocument(dunningLevel) {
    describe(`Open Dunning document ${dunningLevel} via reference from Sales Invoice`, function() {
      cy.visitWindow(salesInvoices.windowId, siRecordId);
      cy.openReferencedDocuments('C_Invoice-C_DunningDoc');
      DunningDocuments.getRows()
        .contains('td', dunningLevel, { log: true })
        .dblclick();
    });

    describe('Do the checks', function() {
      cy.get('#lookup_C_BPartner_ID')
        .find('input')
        .invoke('val')
        .should('contain', businessPartnerName);

      cy.getCheckboxValue('Processed').then(checkBoxValue => {
        assert.equal(checkBoxValue, true);
      });
      cy.getStringFieldValue('C_DunningLevel_ID').should('equals', dunningLevel);

      cy.selectSingleTabRow();
      cy.openAdvancedEdit();
      cy.getStringFieldValue('C_DunningLevel_ID', true).should('equals', dunningLevel);
      cy.getStringFieldValue('C_Currency_ID', true).should('equals', siCurrency);
      cy.getStringFieldValue('C_BPartner_ID', true).should('contain', businessPartnerName);
      cy.pressDoneButton();
    });

    describe(`Check reference from Dunning document to its Sales Invoices`, function() {
      cy.openReferencedDocuments('C_DunningDoc-C_Invoice');
      DunningDocuments.getRows().contains('td', siDocumentNumber, { log: true });
    });
  }

  function filterBySalesInvoiceNumber(siDocNumber) {
    // cy.wait(1000); // if it's past 01.08.2019 and the test won't fail because this sleep is missing, then we can delete this line
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', siDocNumber, false, null, true);
    applyFilters();
  }
});
