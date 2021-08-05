import { salesOrders } from '../../page_objects/sales_orders';

import { BPartner } from '../../support/utils/bpartner';
import { invoiceCandidates } from '../../page_objects/invoice_candidates';
import { salesInvoices } from '../../page_objects/sales_invoices';
import { Builder } from '../../support/utils/builder';
import { getLanguageSpecific } from '../../support/utils/utils';

describe('New sales order test', function () {
  const timestamp = new Date().getTime();
  //let notificationsNumber = null;

  const productValue = `sales_order_test ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductCategoryValue ${timestamp}`;

  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;

  const poReference = `Sales Order-to-Invoice ${timestamp}`;
  const productName = `Sales Order-to-Invoice ${timestamp}`;

  const customerName = `Sales Order-to-Invoice ${timestamp}`;

  describe('Do test preparations', function () {
    it('Create product and price', function () {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);
      Builder.createBasicProductEntities(
        productCategoryName,
        productCategoryValue,
        priceListName,
        productName,
        productValue,
        'Service' /* we want hassle-free invoicing without having done a shipment */
      );
    });
    it('Create customer', function () {
      cy.fixture('sales/simple_customer.json').then((customerJson) => {
        Object.assign(new BPartner(), customerJson)
          .setName(customerName)
          .setCustomerPricingSystem(priceSystemName)
          .setBank(undefined) // we don't need a bank for this test
          .apply();
      });
    });
  });

  describe('Create a new sales order', function () {
    it('Create new sales order header', function () {
      cy.visitWindow(salesOrders.windowId, 'NEW');

      cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');

      cy.writeIntoStringField('POReference', poReference, false /*modal*/, null /*rewriteUrl*/, true /*noRequest*/);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Has an add-new button', function () {
      const addNewText = Cypress.messages.window.batchEntry.caption;
      cy.get('.tabs-wrapper .form-flex-align .btn').contains(addNewText).should('exist').click();
    });

    it('Add new product via Batch Entry', function () {
      cy.get('.quick-input-container').should('exist');
      cy.writeIntoLookupListField('M_Product_ID', productName, productName);

      const aliasName = `addProduct-${timestamp}`;
      const patchUrlPattern = '/rest/api/window/.*$';

      cy.intercept('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.form-field-Qty').find('input').type('1{enter}');

      cy.wait(`@${aliasName}`);
    });

    it('Complete sales order', function () {
      // complete it and verify the status
      cy.fixture('misc/misc_dictionary.json').then((miscDictionary) => {
        cy.processDocument(getLanguageSpecific(miscDictionary, 'docActionComplete'), getLanguageSpecific(miscDictionary, 'docStatusCompleted'));
      });
    });
  });

  describe('create an invoice', function () {
    it("Zoom to the sales order's invoice candidate", function () {
      // TODO: This is really, really bad ! Unfortunately right now I see no way of fixing this, unless we'll
      // get a push notification from the server that would update this panel
      cy.wait(10000); // wait a bit for the invoice candidate(s) to be created, just like the user would
      cy.get('body').type('{alt}6'); // open referenced-records-sidelist

      // zoom to the order's invoice candide(s), but also make sure to wait until the data is available
      const getDataAlias = `data-${timestamp}`;
      cy.intercept('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*?firstRow=0&pageLength=*`).as(getDataAlias);

      cy.selectReference('C_Order_C_Invoice_Candidate').click();
      cy.wait(`@${getDataAlias}`);
    });

    it('Select all invoice candidates and invoice them', function () {
      // select all invoice candidates and wait for the list of available quick action to be updated
      const quickActionsAlias = `quickActions-${timestamp}`;
      cy.intercept('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*/quickActions?selectedIds=all`).as(quickActionsAlias);
      cy.clickElementWithClass('.pagination-link.pointer');
      cy.wait(`@${quickActionsAlias}`);

      cy.readAllNotifications();

      // and *now* execute the invoicing action
      cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      cy.writeIntoStringField('POReference', `Invoice -${timestamp}`, true /*modal*/, '/rest/api/process' /*rewriteUrl*/);

      cy.pressStartButton();
    });

    it('Zoom to the new invoice', function () {
      cy.getNotificationModal(customerName /*optionalText*/); // wait for the notifcation; the text contains the customer's name :-D

      // cy.getDOMNotificationsNumber().then(number => {
      //   expect(number).to.equal(1);
      //cy.wait(10000); // TODO use notifactions instead
      // doubleclick on the first invoice candidate

      const invoiceCandidateDetail = `tab-${timestamp}`;
      cy.intercept('GET', `/rest/api/window/${invoiceCandidates.windowId}/*/AD_Tab*`).as(invoiceCandidateDetail);
      invoiceCandidates.getRows().eq(0).find('td').eq(0).dblclick();
      cy.wait(`@${invoiceCandidateDetail}`);

      // zoom to the invoice by using the references side-listc
      cy.get('body').type('{alt}6'); // open referenced-records-sidelist

      // zoom to the invoice candidate's invoice, but also make sure to wait until the data is available
      const getDataAlias = `data-${timestamp}`;
      cy.intercept('GET', `/rest/api/documentView/${salesInvoices.windowId}/*?firstRow=0&pageLength=*`).as(getDataAlias);
      cy.selectReference('C_Invoice_Candidate_Sales_C_Invoice').click();
      cy.wait(`@${getDataAlias}`);
    });
  });
});
