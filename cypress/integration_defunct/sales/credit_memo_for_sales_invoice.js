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

import { appendHumanReadableNow } from '../../support/utils/utils';
import { salesInvoices } from '../../page_objects/sales_invoices';
import { Builder } from '../../support/utils/builder';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Bank } from '../../support/utils/bank';
import { BPartner } from '../../support/utils/bpartner';
import { SalesInvoice, SalesInvoiceLine } from '../../support/utils/sales_invoice';
import { DocumentStatusKey, RewriteURL } from '../../support/utils/constants';

describe('Create a Credit memo for Sales Invoice', function() {
  const creditMemo = 'Credit Memo';
  let originalSalesInvoiceNumber;
  let originalPriceList;
  let originalCurrency;
  let originalProduct;
  let originalSalesInvoiceID;

  let priceSystemName;
  let priceListName;
  let priceListVersionName;

  // product
  let productCategoryName;
  let productCategoryValue;
  let productName;
  let productValue;

  // BPartner
  let discountSchemaName;
  let bPartnerName;

  // Sales Invoice
  let salesInvoiceTargetDocumentType;
  let originalQuantity;
  it('Read the fixture', function() {
    cy.fixture('sales/credit_memo_for_sales_invoice.json').then(f => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      productCategoryValue = productCategoryName;
      productName = appendHumanReadableNow(f['productName']);
      productValue = productName;
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      bPartnerName = appendHumanReadableNow(f['bPartnerName']);
      salesInvoiceTargetDocumentType = f['salesInvoiceTargetDocumentType'];
      originalQuantity = f['originalQuantity'];
    });
  });

  it('Prepare product and bartner', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

    Builder.createBasicProductEntities(productCategoryName, productCategoryValue, priceListName, productName, productValue);

    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson).apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: bPartnerName }).setCustomerDiscountSchema(discountSchemaName).apply();
    });
  });

  it('Prepare sales invoice', function() {
    new SalesInvoice(bPartnerName, salesInvoiceTargetDocumentType)
      .addLine(new SalesInvoiceLine().setProduct(productName).setQuantity(originalQuantity))
      .setPriceList(priceListName)
      .apply();
    cy.completeDocument();

    cy.readAllNotifications();
  });

  it('Sales Invoice is Completed', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
  });

  it('Sales Invoice is not paid', function() {
    cy.expectCheckboxValue('IsPaid', false);
  });

  it('Save values needed for the next step', function() {
    cy.getCurrentWindowRecordId().then(id => (originalSalesInvoiceID = id));

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
    cy.pressDoneButton();
  });

  it('Create the Credit Memo', function() {
    cy.executeHeaderActionWithDialog('C_Invoice_Create_CreditMemo');

    cy.selectInListField('C_DocType_ID', creditMemo, true, null, true);

    // ensure all the checkboxes are ok
    cy.setCheckBoxValue('CompleteIt', false, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsReferenceOriginalOrder', true, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsReferenceInvoice', true, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsCreditedInvoiceReinvoicable', false, true, RewriteURL.PROCESS);

    cy.pressStartButton(100);
    cy.getNotificationModal(/Created.*Document No/);
  });

  it('Open the Referenced Sales Invoice documents', function() {
    cy.openReferencedDocuments('AD_RelationType_ID-540184');
  });

  it('Ensure there is only 1 Sales Invoice row and open it', function() {
    salesInvoices.getRows().should('have.length', 1);

    // select the first Table Row and click it (open it)
    salesInvoices
      .getRows()
      .eq(0)
      .dblclick();
  });

  it('The Sales Invoice is a Credit Memo', function() {
    cy.expectDocumentStatus(DocumentStatusKey.InProgress);
    cy.getStringFieldValue('C_DocTypeTarget_ID').should('be.equal', creditMemo);
  });

  it('Has the same properties and reference to the original', function() {
    cy.getStringFieldValue('DocumentNo').should('not.be.equal', originalSalesInvoiceNumber);
    cy.getStringFieldValue('M_PriceList_ID').should('be.equal', originalPriceList);
    cy.getStringFieldValue('C_Currency_ID').should('be.equal', originalCurrency);
  });

  it('Has the same properties and reference to the original -- Advanced edit', function() {
    cy.openAdvancedEdit();
    cy.getStringFieldValue('Ref_Invoice_ID', true).should('be.equal', originalSalesInvoiceNumber);
    cy.pressDoneButton();
  });

  it('Has the same properties and reference to the original -- Line advanced edit', function() {
    cy.selectTab('C_InvoiceLine');
    cy.selectSingleTabRow();
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_Product_ID', true).should('be.equal', originalProduct);
    cy.getStringFieldValue('QtyEntered', true).should('be.equal', originalQuantity.toString(10));
    cy.pressDoneButton();
  });

  it('Complete the Credit Memo SI', function() {
    cy.completeDocument();
  });

  it('Credit Memo SI is paid', function() {
    cy.expectCheckboxValue('IsPaid', true);
  });

  it('Original Sales Invoice is paid', function() {
    cy.visitWindow('167', originalSalesInvoiceID);
    cy.expectCheckboxValue('IsPaid', true);
  });
});
