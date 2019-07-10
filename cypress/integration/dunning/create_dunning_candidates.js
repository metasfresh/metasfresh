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

describe('Create Dunning Candidates', function () {
  // human readable date with millis!
  const date = new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString();

  const dunningTypeName = `Dunning ${date}`;
  // const dunningTypeName = `Dunning 2019-07-05T10:09:30.514Z`;


  const businessPartnerName = `Customer Dunning ${date}`;
  const paymentTerm = 'immediately';

  const salesInvoiceTargetDocumentType = 'Sales Invoice';
  const productName = 'Convenience Salat 250g';
  const originalQuantity = 200;


  // Test data
  let siDocumentNumber;
  let siCurrency;
  let siDueDate;
  let siTotalAmount;


  before(function () {
    // This wait is stupid.
    // It also appears to be a good workaround for the problems in
    // cypress/support/utils/utils.js:1
    cy.wait(5000);
  });

  it('Prepare dunning type', function () {

    cy.fixture('settings/dunning_type.json').then(dunningType => {
      Object.assign(new DunningType(), dunningType)
        .setName(dunningTypeName)
        .apply();
    });
  });

  it('Prepare customer bpartner (via api)', function () {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({...customerJson, name: businessPartnerName})
        .setCustomer(true)
        .setDunning(dunningTypeName)
        .setPaymentTerm(paymentTerm)
        .setBank(undefined);

      bpartner.apply();
    });
  });

  it('Prepare sales invoice', function () {
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


  it('Sales Invoice is Completed', function () {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
  });

  it('Sales Invoice is not paid', function () {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      cy.log(`IsPaid = ${checkBoxValue}`);
      assert.equal(checkBoxValue, false);
    });
  });

  it('Save values needed for the next step', function () {
    cy.getStringFieldValue('DocumentNo').then(documentNumber => {
      siDocumentNumber = documentNumber;
    });

    cy.getStringFieldValue('C_Currency_ID').then(currency => {
      siCurrency = currency;
    });

    cy.getStringFieldValue('DateInvoiced').then(dueDate => {
      siDueDate = dueDate;
    });

    cy.getSalesInvoiceTotalAmount().then(amount => {
      siTotalAmount = amount;
    });
  });


  it('Ensure there are no Dunning Candidates', function () {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);
    DunningCandidates.getRows().should('have.length', 0);
  });


  it('Create Dunning Candidates', function () {
    DunningCandidates.visit();
    cy.wait(1000); // without this wait, the action menu is not properly loaded

    cy.executeHeaderActionWithDialog('C_Dunning_Candidate_Create');
    cy.setCheckBoxValue('IsFullUpdate', true, true);
    cy.pressStartButton();
  });


  it('Ensure there are exactly 2 Dunning Candidates', function () {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);

    DunningCandidates.getRows().should('have.length', 2);
  });


  describe("Check the dunning candidates", function () {
    it('Check Level 1 candidate', function () {
      const dunningLevel = 'Level 1';
      checkDunningCandidate(dunningLevel);
    });

    it('Check Level 2 candidate', function () {
      const dunningLevel = 'Level 2';
      checkDunningCandidate(dunningLevel);
    });
  });


  function checkDunningCandidate(dunningLevel) {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);
    DunningCandidates.getRows().contains('td', dunningLevel, {log: true}).dblclick();

    cy.getStringFieldValue('DocumentNo').should('equals', siDocumentNumber);
    cy.getStringFieldValue('C_BPartner_ID').should('contains', businessPartnerName);
    cy.getStringFieldValue('OpenAmt').then(amount => {
      expect(parseFloat(amount)).to.equals(parseFloat(siTotalAmount));
    });
    cy.getStringFieldValue('DaysDue').should('equals', '0');
    cy.getStringFieldValue('DueDate').should('equals', siDueDate);
    cy.getStringFieldValue('C_Currency_ID').should('equals', siCurrency);
  }


  function filterBySalesInvoiceNumber(siDocNumber) {
    cy.wait(1000);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', siDocNumber, false, null, true);
    applyFilters();
    cy.wait(1000);
  }
});




