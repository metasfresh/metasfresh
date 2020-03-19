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

describe('Create Dunning Candidates', function() {
  let dunningTypeName;
  let businessPartnerName;
  let paymentTerm;
  let salesInvoiceTargetDocumentType;
  let productName;
  let originalQuantity;

  // Test data
  let siDocumentNumber;
  let siCurrency;
  let siDueDate;
  let siTotalAmount;

  it('Read the fixture', function() {
    cy.fixture('dunning/create_dunning_candidates.json').then(f => {
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

    cy.getStringFieldValue('DateInvoiced').then(dueDate => {
      siDueDate = dueDate;
    });

    cy.getSalesInvoiceTotalAmount().then(amount => {
      siTotalAmount = amount;
    });
  });

  it('Ensure there are no Dunning Candidates', function() {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);
    DunningCandidates.getRows().should('have.length', 0);
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

  describe('Check the dunning candidates', function() {
    it('Check Level 1 candidate', function() {
      const dunningLevel = 'Level 1';
      checkDunningCandidate(dunningLevel);
    });

    it('Check Level 2 candidate', function() {
      const dunningLevel = 'Level 2';
      checkDunningCandidate(dunningLevel);
    });
  });

  function checkDunningCandidate(dunningLevel) {
    DunningCandidates.visit();
    filterBySalesInvoiceNumber(siDocumentNumber);
    DunningCandidates.getRows()
      .contains('td', dunningLevel, { log: true })
      .dblclick();

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
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', siDocNumber, false, null, true);
    applyFilters();
  }
});
