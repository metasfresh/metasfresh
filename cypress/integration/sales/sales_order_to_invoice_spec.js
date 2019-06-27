import {salesOrders} from '../../page_objects/sales_orders';
import {Product, ProductCategory, ProductPrice} from '../../support/utils/product';
import {BPartner} from '../../support/utils/bpartner';
import {invoiceCandidates} from '../../page_objects/invoice_candidates';
import {salesInvoices} from '../../page_objects/sales_invoices';

describe('New sales order test', function () {
  const timestamp = new Date().getTime();
  let notificationsNumber = null;

  const poReference = `Sales Order-to-Invoice Test ${timestamp}`;
  const productName = `Sales Order-to-Invoice Test ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const customerName = `Sales Order-to-Invoice Test ${timestamp}`;

  before(function () {
    const productValue = `sales_order_to_invoice_test ${timestamp}`;
    const productCategoryName = `ProductCategoryName ${timestamp}`;
    const productCategoryValue = `ProductNameValue ${timestamp}`;

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson)
        .setPriceList(priceListName)
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .setProductType('Service')
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customerName)
        .apply();
    });

    cy.readAllNotifications();
  });

  describe('Create a new sales order', function () {
    it('Create new sales order header', function () {
      cy.visitWindow(salesOrders.windowId, 'NEW');
      // cy.resetNotifications();

      cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');

      cy.writeIntoStringField('POReference', poReference, false /*modal*/, null /*rewriteUrl*/, true /*noRequest*/);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new product via Batch Entry', function () {
      const addNewText = Cypress.messages.window.batchEntry.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.quick-input-container').should('exist');

      cy.writeIntoLookupListField('M_Product_ID', productName, productName);

      const aliasName = `addProduct-${timestamp}`;
      const patchUrlPattern = '/rest/api/window/.*$';
      cy.server();
      cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.form-field-Qty')
        .find('input')
        .type('1{enter}');

      cy.wait(`@${aliasName}`);
    });

    it('Complete sales order', function () {
      cy.processDocument('Complete', 'Completed');
    });
  });

  describe('create an invoice', function () {
    it("Zoom to the sales order's invoice candidate", function () {
      // TODO: This is erally, really bad ! Unfortunately right now I see no way of fixing this, unless we'll
      // get a push notification from the server that would update this panel
      cy.wait(10000); // wait a bit for the invoice candidate(s) to be created, just like the user would
      cy.get('body').type('{alt}6'); // open referenced-records-sidelist

      cy.server();

      // zoom to the order's invoice candide(s), but also make sure to wait until the data is available
      const getDataAlias = `data-${timestamp}`;
      cy.route('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*?firstRow=0&pageLength=*`).as(
        getDataAlias
      );

      cy.selectReference('C_Order_C_Invoice_Candidate').click();
      cy.wait(`@${getDataAlias}`);
    });

    it('Select all invoice candidates and invoice them', function () {
      cy.server();

      // select all invoice candidates and wait for the list of available quick action to be updated
      const quickActionsAlias = `quickActions-${timestamp}`;
      cy.route('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*/quickActions?selectedIds=all`).as(
        quickActionsAlias
      );
      cy.clickElementWithClass('.pagination-link.pointer');
      cy.wait(`@${quickActionsAlias}`);

      // and *now* execute the invoicing action
      cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      cy.writeIntoStringField(
        'POReference',
        `Invoice -${timestamp}`,
        true /*modal*/,
        '/rest/api/process' /*rewriteUrl*/
      );
      cy.pressStartButton();
    });

    it('Zoom to the new invoice', function () {
      cy.getNotificationModal();
      cy.getDOMNotificationsNumber().then(number => {
        expect(number).to.equal(1);

        cy.server();

        const invoiceCandidateTab = `tab-${timestamp}`;
        cy.route('GET', `/rest/api/window/${invoiceCandidates.windowId}/*/AD_Tab*`).as(invoiceCandidateTab);
        invoiceCandidates
          .getRows()
          .eq(0)
          .find('td')
          .eq(0)
          .dblclick();
        cy.wait(`@${invoiceCandidateTab}`);

        cy.get('body').type('{alt}6'); // open referenced-records-sidelist

        // zoom to the invoice candidate's iinvoice, but also make sure to wait until the data is available
        const getDataAlias = `data-${timestamp}`;
        cy.route('GET', `/rest/api/documentView/${salesInvoices.windowId}/*?firstRow=0&pageLength=*`).as(getDataAlias);
        cy.selectReference('C_Invoice_Candidate_Sales_C_Invoice').click();
        cy.wait(`@${getDataAlias}`);
      });
    });
  });
});
