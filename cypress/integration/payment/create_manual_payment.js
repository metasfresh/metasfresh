/// <reference types="Cypress" />

import {Product, ProductCategory} from "../../support/utils/product";
import {object} from "prop-types";
import {BPartner} from "../../support/utils/bpartner";
import {salesInvoices} from "../../page_objects/sales_invoices";
import {SalesInvoice, SalesInvoiceLine} from "../../support/utils/sales_invoice";

describe('Create a manual Payment for a Sales Invoice', function () {

  const timestamp = new Date().getTime();

  const productName = `Sales Order-to-Invoice Test ${timestamp}`;
  // const customerName = `Sales Order-to-Invoice Test ${timestamp}`;

  const customerName = 'Test Kunde 1_G0001';
  const salesInvoiceTargetDocumentType = 'Sales Invoice';

  let salesInvoiceNumber;
  let salesInvoiceTotalAmount = 0;


  const paymentDocumentType = 'Zahlungseingang';

  before(function () {
    const productValue = `sales_order_to_invoice_test ${timestamp}`;
    const productCategoryName = `ProductCategoryName ${timestamp}`;
    const productCategoryValue = `ProductNameValue ${timestamp}`;

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
    //     .setName(customerName)
    //     .apply();
    // });

    cy.readAllNotifications();
  });


  // it('Creates a Sales Order and Invoice', () => {
  //   new SalesInvoice(customerName, salesInvoiceTargetDocumentType)
  //     .addLine(new SalesInvoiceLine()
  //       .setProduct('Convenience Salat 250g_P002737')
  //       .setQuantity(6)
  //       .setPackingItem('IFCO 6410 x 10 Stk')
  //       .setTuQuantity(1)
  //     )
  //     .apply();
  // });






  it('Creates a manual Payment', () => {
    cy.visitWindow('195', 'NEW');

    cy.writeIntoLookupListField("C_BPartner_ID", customerName, customerName);

    cy.getFieldValue('DocumentNo').should('be.empty');
    cy.selectInListField('C_DocType_ID', paymentDocumentType);
    cy.getFieldValue('DocumentNo').should('not.be.empty');


    // @kuba this is not working
    cy.getFieldValue('PayAmt').should('equal', salesInvoiceTotalAmount); // todo how do i even equal?!!?!!

    salesInvoiceNumber = 145824;  // todo this has to be read somehow from the previously created sales invoice;
    cy.writeIntoLookupListField('C_Invoice_ID', salesInvoiceNumber, salesInvoiceNumber);

    // @kuba this is not working either
    salesInvoiceTotalAmount = 2.2; // todo this has to be read somehow from the previously created sales invoice;
    cy.getFieldValue('PayAmt').should('equal', salesInvoiceTotalAmount); // todo how do i even equal?!!?!!



  })

});
