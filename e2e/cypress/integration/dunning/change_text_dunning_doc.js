/// <reference types="Cypress" />

import { SalesInvoice, SalesInvoiceLine } from '../../support/utils/sales_invoice';
import { appendHumanReadableNow } from '../../support/utils/utils';
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

  // test
  let siDocumentNumber;
  let dunningTypeRecordId;
  let siRecordId;
  let dunningDocId;

  it('Read the fixture', function() {
    cy.fixture('dunning/change_text_dunning_doc.json').then(f => {
      dunningTypeName = appendHumanReadableNow(f['dunningTypeName']);

      businessPartnerName = appendHumanReadableNow(f['businessPartnerName']);
      paymentTerm = f['paymentTerm'];

      salesInvoiceTargetDocumentType = f['salesInvoiceTargetDocumentType'];
      productName = f['productName'];
      originalQuantity = f['originalQuantity'];
    });
  });

  it('Create dunning type', function() {
    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });
    cy.getCurrentWindowRecordId().then(recordId => {
      dunningTypeRecordId = recordId;
    });
  });

  it('Create bpartner', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: businessPartnerName })
        .setDunning(dunningTypeName)
        .setPaymentTerm(paymentTerm)
        .apply();
    });
  });

  it('Create sales invoice', function() {
    // eslint-disable-next-line
    new SalesInvoice(businessPartnerName, salesInvoiceTargetDocumentType)
      .addLine(new SalesInvoiceLine().setProduct(productName).setQuantity(originalQuantity))
      .apply();
    cy.completeDocument();
  });
  it('Sales Invoice is not paid', function() {
    cy.expectCheckboxValue('IsPaid', false);
  });

  it('Save document no from invoice', function() {
    cy.getStringFieldValue('DocumentNo').then(documentNumber => {
      siDocumentNumber = documentNumber;
    });
  });
  it('Save current record id of the invoice', function() {
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

  it('Ensure there are exactly 2 Dunning Candidates - one for each dunning type level', function() {
    DunningCandidates.visit();
    filterBySalesInvoiceDocNumber(siDocumentNumber);
    cy.expectNumberOfRows(2);
  });

  function filterBySalesInvoiceDocNumber(siDocNumber) {
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', siDocNumber, false, null, true);
    applyFilters();
  }

  it('Create Dunning Documents', function() {
    DunningCandidates.selectAllVisibleRows();
    cy.executeHeaderActionWithDialog('C_Dunning_Candidate_Process');
    cy.setCheckBoxValue('IsComplete', true, true);
    cy.pressStartButton();
  });
  it('Open and check dunning documents from sales invoice - open pdf report to check initial text', function() {
    cy.visitWindow(salesInvoices.windowId, siRecordId);
    cy.openReferencedDocuments('C_Invoice-C_DunningDoc');
    DunningDocuments.getRows().should('have.length', 2);
    cy.selectNthRow(0).dblclick();
    cy.getCurrentWindowRecordId().then(function(record) {
      dunningDocId = record;
    });
    /**open header action 'Print' to check pdf report initial text
     cy.clickHeaderNav('print');
     *The test is partialy implemented as for the moment(8/8/2019)
     * we cannot verify text inside pdf reports using cypress)
     */
  });

  it('Change text in dunning type - level 2', function() {
    cy.visitWindow('159', dunningTypeRecordId);
    cy.selectTab('C_DunningLevel');
    cy.selectNthRow(1);
    cy.openAdvancedEdit();
    cy.writeIntoStringField('PrintName', 'Verrechnung Mahnkosten CHF 15');
    cy.pressDoneButton();
  });
  it('Compare text in pdf report from dunning doc with the newly changed text in dunning type - level 2', function() {
    cy.visitWindow('540155', dunningDocId);
    /**open header action 'Print' to check pdf report initial text
     cy.clickHeaderNav('print');*/
  });
});
