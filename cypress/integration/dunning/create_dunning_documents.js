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


import {SalesInvoice, SalesInvoiceLine} from "../../support/utils/sales_invoice";
import {getLanguageSpecific} from "../../support/utils/utils";
import {DocumentActionKey, DocumentStatusKey} from "../../support/utils/constants";
import {BPartner} from "../../support/utils/bpartner";
import {DunningCandidates} from "../../page_objects/dunning_candidates";
import {applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters} from "../../support/functions";
import {DunningType} from "../../support/utils/dunning_type";
import {Dunning} from "../../page_objects/dunning";

describe('Create Dunning Documents', function() {
  // human readable date with millis!
  const date = new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString();

  const dunningTypeName = `Dunning ${date}`;

  const businessPartnerName = `Customer Dunning ${date}`;
  const paymentTerm = 'immediately';

  const salesInvoiceTargetDocumentType = 'Sales Invoice';
  const productName = 'Convenience Salat 250g';
  const originalQuantity = 200;


  // Test data
  let siDocumentNumber;
  let siCurrency;


  it('Prepare dunning type', function() {

    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });
  });

  it('Prepare customer bpartner (via api)', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: businessPartnerName })
        .setCustomer(true)
        .setDunning(dunningTypeName)
        .setPaymentTerm(paymentTerm)
        .setBank(undefined);

      bpartner.apply();
    });
  });

  it('Prepare sales invoice', function() {
    cy.fixture('sales/sales_invoice.json').then((salesInvoiceJson) => {
      new SalesInvoice(businessPartnerName, salesInvoiceTargetDocumentType)
        .addLine(
          new SalesInvoiceLine().setProduct(productName).setQuantity(originalQuantity)
        )
        .setDocumentAction(getLanguageSpecific(salesInvoiceJson, DocumentActionKey.Complete))
        .setDocumentStatus(getLanguageSpecific(salesInvoiceJson, DocumentStatusKey.Completed))
        .apply();
    });
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
  });

  it('Create Dunning Candidates', function() {
    DunningCandidates.visit();
    cy.wait(1000); // without this wait, the action menu is not properly loaded

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


  describe('Create And Check Dunning Documents', function() {
    /**
     * Currently there's no way to move from Dunning -> Dunning Candidates (and so to Sales Invoice).
     * In order to check each dunning document, we have to go the other way: Dunning Candidates -> related docs -> the Dunning Doc.
     * There's also no way to check if there are more than 2 dunning documents created.
     */
    it('CANNOT Expect there are exactly 2 dunning documents created!', function() {
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
    describe('Open Dunning document', function() {
      // Open Dunning document
      DunningCandidates.visit();
      filterBySalesInvoiceNumber(siDocumentNumber);
      DunningCandidates.getRows().contains('td', dunningLevel, { log: true }).dblclick();
      cy.openReferencedDocuments('AD_RelationType_ID-540150');
      Dunning.getRows().get('tr').dblclick();
    });

    describe('Do the checks', function() {

      // @kuba how to check bpartner?
      // cy.get('#lookup_C_BPartner_ID')
      //   .get('input')
      //    //.invoke('value')
      //   .then(function (v) {
      //     cy.log(JSON.stringify(v));
      //   });


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
  }


  function filterBySalesInvoiceNumber(siDocNumber) {
    cy.wait(1000);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', siDocNumber, false, null, true);
    applyFilters();
  }
});




