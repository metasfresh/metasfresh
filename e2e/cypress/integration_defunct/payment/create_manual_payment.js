/// <reference types="Cypress" />

import { BPartner } from '../../support/utils/bpartner';
import { SalesInvoice, SalesInvoiceLine } from '../../support/utils/sales_invoice';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Bank } from '../../support/utils/bank';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create a manual Payment for a Sales Invoice', function() {
  let salesInvoiceTargetDocumentType;
  let salesInvoiceNumber;
  let salesInvoiceTotalAmount;
  let productQty;

  let paymentDocumentType;
  let paymentTotalAmount; // may be different from salesInvoiceTotalAmount because there could be some discounts

  // data for "before" section
  // priceList
  let priceSystemName;
  let priceListName;
  let priceListVersionName;

  // product
  let productCategoryName;
  let productCategoryValue;
  let productName;
  let productValue;
  let productType;

  // BPartner
  let discountSchemaName;
  let bPartnerName;

  // test
  let salesInvoiceID;

  it('Read the fixture', function() {
    cy.fixture('payment/create_manual_payment.json').then(f => {
      salesInvoiceTargetDocumentType = f['salesInvoiceTargetDocumentType'];
      salesInvoiceTotalAmount = f['salesInvoiceTotalAmount'];
      productQty = f['productQty'];

      paymentDocumentType = f['paymentDocumentType'];
      paymentTotalAmount = f['paymentTotalAmount'];
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      productCategoryValue = productCategoryName;
      productName = appendHumanReadableNow(f['productName']);
      productValue = productName;
      productType = f['productType'];
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      bPartnerName = appendHumanReadableNow(f['bPartnerName']);
    });
  });

  it('Prepare product and pricing', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);

    Builder.createBasicProductEntities(productCategoryName, productCategoryValue, priceListName, productName, productValue, productType);

    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });

  it('Prepare customer', function() {
    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson).apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: bPartnerName }).setCustomerDiscountSchema(discountSchemaName).apply();
    });
  });

  it('Create a Sales Invoice', function() {
    new SalesInvoice(bPartnerName, salesInvoiceTargetDocumentType)
      .addLine(new SalesInvoiceLine().setProduct(productName).setQuantity(productQty))
      .setPriceList(priceListName)
      .apply();
    cy.completeDocument();
    cy.getCurrentWindowRecordId().then(id => {
      salesInvoiceID = id;
    });
  });

  it('The Sales Invoice should not be paid', function() {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      assert.equal(checkBoxValue, false);
    });
  });

  it('Save values needed for the next step', function() {
    cy.getStringFieldValue('DocumentNo').then(documentNumber => {
      salesInvoiceNumber = documentNumber;
    });

    cy.getSalesInvoiceTotalAmount().then(totalAmount => {
      salesInvoiceTotalAmount = totalAmount;
    });
  });

  it('Creates a manual Payment', function() {
    cy.visitWindow('195', 'NEW');

    cy.writeIntoLookupListField('C_BPartner_ID', bPartnerName, bPartnerName);

    cy.getStringFieldValue('DocumentNo').should('be.empty');
    cy.selectInListField('C_DocType_ID', paymentDocumentType);
    cy.getStringFieldValue('DocumentNo').should('not.be.empty');

    cy.getStringFieldValue('PayAmt').then(val => {
      const cast = parseFloat(val);
      assert.equal(cast, 0);
    });

    cy.writeIntoLookupListField('C_Invoice_ID', salesInvoiceNumber, salesInvoiceNumber);

    cy.completeDocument();
  });

  it('Checks the paid + discount amount', function() {
    // discount amount is taken from the advanced edit dialog of the line
    cy.selectTab('C_AllocationLine');
    cy.selectSingleTabRow();
    cy.openAdvancedEdit();

    let discountAmount;
    cy.getStringFieldValue('DiscountAmt', true).then(val => {
      discountAmount = parseFloat(val);
    });

    cy.getStringFieldValue('Amount').then(val => {
      const value = parseFloat(val).toFixed(2);
      paymentTotalAmount = salesInvoiceTotalAmount - discountAmount;
      assert.equal(value, paymentTotalAmount);
    });

    cy.pressDoneButton();
  });

  it('Expect 2 Accounting transactions are created', function() {
    cy.openReferencedDocuments('AD_RelationType_ID-540201');
    cy.expectNumberOfRows(2);
    cy.go('back');
  });

  it('The paid Sales Invoice is linked to this Payment', function() {
    cy.selectTab('C_AllocationLine');

    cy.get('.table-flex-wrapper')
      .find('tbody td')
      .contains(salesInvoiceNumber)
      .click()
      .trigger('contextmenu');

    // - invoice of the bpartner can be selected/zoomed into
    cy.get('.context-menu-item').contains('Zoom Into');

    // Note: we must not click the zoom into button, since that will open a new browser tab, and cypress
    // cannot handle that.
    // Instead we saved the ID of the sales invoice we want to return to (when we created it),
    // and now use it to visit the window.
    cy.visitWindow('167', salesInvoiceID);
  });

  it('Sales Invoice is now marked as paid', function() {
    cy.getCheckboxValue('IsPaid').then(checkBoxValue => {
      // this will keep on failing until https://github.com/metasfresh/metasfresh/issues/5435 is fixed
      assert.equal(checkBoxValue, true);
    });
  });
});
