/// <reference types="Cypress" />

// import {Product, ProductCategory} from '../../support/utils/product';
// import {object} from 'prop-types';
// import {BPartner} from '../../support/utils/bpartner';
// import {salesInvoices} from '../../page_objects/sales_invoices';
import { SalesInvoice, SalesInvoiceLine } from '../../support/utils/sales_invoice';

describe('Create a manual Payment for a Sales Invoice', function() {
  // const timestamp = new Date().getTime();
  // const productName = `Sales Order-to-Invoice Test ${timestamp}`;
  // const bPartnerName = `Sales Order-to-Invoice Test ${timestamp}`;

  const bPartnerName = 'Test Kunde 1_G0001';
  const salesInvoiceTargetDocumentType = 'Sales Invoice';

  let salesInvoiceNumber;
  let salesInvoiceTotalAmount = 0;

  const paymentDocumentType = 'Zahlungseingang';

  /*
  before(function() {
    // const productValue = `sales_order_to_invoice_test ${timestamp}`;
    // const productCategoryName = `ProductCategoryName ${timestamp}`;
    // const productCategoryValue = `ProductNameValue ${timestamp}`;

    // cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
    //   Object.assign(new ProductCategory(), productCategoryJson)
    //     .setName(productCategoryName)
    //     .setValue(productCategoryValue)
    //     .apply();
    // });
    // cy.fixture('product/simple_product.json').then(productJson => {
    //   Object.assign(new Product(), productJson)
    //     .setName(productName)
    //     .setValue(productValue)
    //     .setProductType('Service')
    //     .setProductCategory(productCategoryValue + '_' + productCategoryName)
    //     .apply();
    // });
    // cy.fixture('sales/simple_customer.json').then(customerJson => {
    //   Object.assign(new BPartner(), customerJson)
    //     .setName(bPartnerName)
    //     .apply();
    // });

    cy.readAllNotifications();
  });
  */

  it('Creates a Sales Order and Invoice', function() {
    new SalesInvoice(bPartnerName, salesInvoiceTargetDocumentType)
      .addLine(
        new SalesInvoiceLine()
          .setProduct('Convenience Salat 250g_P002737')
          .setQuantity(20)
          .setPackingItem('IFCO 6410 x 10 Stk')
          .setTuQuantity(2)
      )
      .addLine(new SalesInvoiceLine().setProduct('IFCO 6410_P001512').setQuantity(2))
      .setDocumentAction('Complete')
      .setDocumentStatus('Completed')
      .apply();

    // save values needed for the next step
    cy.getFieldValue('DocumentNo').then(documentNumber => {
      salesInvoiceNumber = documentNumber;
    });

    cy.get('.header-breadcrumb-sitename').then(si => {
      salesInvoiceTotalAmount = parseFloat(si.html().split(' ')[2], 10); // the format is "DOC_NO MM/DD/YYYY total"
    });
  });

  it('Creates a manual Payment', function() {
    cy.visitWindow('195', 'NEW');

    cy.writeIntoLookupListField('C_BPartner_ID', bPartnerName, bPartnerName);

    cy.getFieldValue('DocumentNo').should('be.empty');
    cy.selectInListField('C_DocType_ID', paymentDocumentType);
    cy.getFieldValue('DocumentNo').should('not.be.empty');

    cy.getFieldValue('PayAmt').then(val => {
      const cast = parseFloat(val);
      assert.equal(cast, 0);
    });

    cy.writeIntoLookupListField('C_Invoice_ID', salesInvoiceNumber, salesInvoiceNumber);

    cy.processDocument('Complete', 'Completed');
  });

  it('Checks the paid and discount amount', function() {
    // check the paid amount (payAmount+discount amount!)
    // discount amount is taken from the advanced edit dialog of the line
    cy.selectTab('C_AllocationLine');
    cy.selectSingleTabRow();
    cy.openAdvancedEdit();

    let discountAmount;
    cy.getFieldValue('DiscountAmt', true).then(val => {
      discountAmount = parseFloat(val);
    });

    cy.getFieldValue('Amount').then(val => {
      const value = parseFloat(val).toFixed(2);
      const amount = parseFloat(salesInvoiceTotalAmount - discountAmount).toFixed(2);
      assert.equal(value, amount);
    });

    cy.pressDoneButton();
  });

  it('Checks the new Sales Invoice', function() {
    cy.selectTab('C_AllocationLine');
    cy.get('.table-flex-wrapper')
      .find('tbody td')
      .contains(salesInvoiceNumber)
      .click()
      .trigger('contextmenu');

    cy.get('.context-menu-item')
      .contains('Zoom Into')
      .click();
  });
});
