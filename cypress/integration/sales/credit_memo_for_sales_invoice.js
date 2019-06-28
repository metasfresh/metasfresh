/// <reference types="Cypress" />

import {getLanguageSpecific} from "../../support/utils/utils";
import {salesInvoices} from "../../page_objects/sales_invoices";
import {Builder} from "../../support/utils/builder";
import {DiscountSchema} from "../../support/utils/discountschema";
import {Bank} from "../../support/utils/bank";
import {BPartner} from "../../support/utils/bpartner";
import {SalesInvoice, SalesInvoiceLine} from "../../support/utils/sales_invoice";
import {DocumentStatusKey, RewriteURL} from "../../support/utils/constants";


describe('Create a Credit memo for Sales Invoice', function () {
  const timestamp = new Date().getTime();

  const creditMemo = 'Credit Memo';
  let originalSalesInvoiceNumber;
  let originalPriceList;
  let originalCurrency;
  let originalProduct;
  let originalQuantity;


  // data for "before" section
  // priceList
  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;

  // product
  const productCategoryName = `ProductCategory ${timestamp}`;
  const productCategoryValue = productCategoryName;
  const productName = `Product ${timestamp}`;
  const productValue = productName;
  const productType = 'Item';

  // BPartner
  const discountSchemaName = `DiscountSchema ${timestamp}`;
  const bPartnerName = `bPartner ${timestamp}`;

  // Sales Invoice
  const salesInvoiceTargetDocumentType = 'Sales Invoice';


  before(function () {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);

    Builder.createBasicProductEntities(productCategoryName, productCategoryValue, priceListName, productName, productValue, productType);

    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(bPartnerName)
        .setCustomerDiscountSchema(discountSchemaName)
        .apply();
    });


    cy.fixture('sales/sales_invoice.json').then(salesInvoiceJson => {
      new SalesInvoice(bPartnerName, salesInvoiceTargetDocumentType)
        .addLine(
          new SalesInvoiceLine()
            .setProduct(productName)
            .setQuantity(20)
          // todo @dh: how to add packing item
          // .setPackingItem('IFCO 6410 x 10 Stk')
          // .setTuQuantity(2)
        )
        // .addLine(
        // todo @dh: how to add this line which depends on the packing item?
        //   new SalesInvoiceLine()
        //     .setProduct('IFCO 6410_P001512')
        //     .setQuantity(2)
        // )
        .setPriceList(priceListName)
        .setDocumentAction(getLanguageSpecific(salesInvoiceJson, 'docActionComplete'))
        .setDocumentStatus(getLanguageSpecific(salesInvoiceJson, 'Completed'))
        .apply();
    });

    cy.readAllNotifications();
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
    cy.setCheckBoxValue('CompleteIt', false, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsReferenceOriginalOrder', true, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsReferenceInvoice', true, true, RewriteURL.PROCESS);
    cy.setCheckBoxValue('IsCreditedInvoiceReinvoicable', false, true, RewriteURL.PROCESS);

    cy.pressStartButton(100);
    cy.getNotificationModal(/Created.*Document No/);
  });

  it('Open the Referenced Sales Invoice documents', function () {
    cy.wait(5000);  // give the system some breathing space
    cy.get('body').type('{alt}6'); // open referenced-records-sidelist
    cy.selectReference('AD_RelationType_ID-540184').click();
  });


  it('Ensure there is only 1 Sales Invoice row and open it', function () {
    salesInvoices.getRows().should('have.length', 1);

    // select the first Table Row and click it (open it)
    salesInvoices.getRows().eq(0).dblclick();
  });


  it('The Sales Invoice is a Credit Memo', function () {
    cy.expectDocumentStatus(DocumentStatusKey.InProgress);
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
