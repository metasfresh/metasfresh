import { salesOrders } from '../../page_objects/sales_orders';
import { Product, ProductCategory } from '../../support/utils/product';
import { BPartner } from '../../support/utils/bpartner';
import { invoiceCandidates } from '../../page_objects/invoice_candidates';

describe('New sales order test', function() {
  const timestamp = new Date().getTime();

  const poReference = `Sales Order-to-Invoice Test ${timestamp}`;
  const productName = `Sales Order-to-Invoice Test ${timestamp}`;
  const customerName = `Sales Order-to-Invoice Test ${timestamp}`;

  before(function() {
    const productValue = `sales_order_to_invoice_test ${timestamp}`;
    const productCategoryName = `ProductCategoryName ${timestamp}`;
    const productCategoryValue = `ProductNameValue ${timestamp}`;

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .setProductType('Service')
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customerName)
        .apply();
    });
  });

  describe('Create a new sales order', function() {
    it('Create new sales order header', function() {
      cy.visitWindow(salesOrders.windowId, 'NEW');

      cy.writeIntoLookupListField('C_BPartner_ID', customerName, customerName);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');

      cy.writeIntoStringField('POReference', poReference, false /*modal*/, null /*rewriteUrl*/, true /*noRequest*/);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new product via Batch Entry', function() {
      const addNewText = Cypress.messages.window.batchEntry.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.quick-input-container').should('exist');

      cy.writeIntoLookupListField('M_Product_ID', productName, productName);

      cy.get('.form-field-Qty')
        .click()
        .find('.input-body-container.focused')
        .should('exist')
        .find('i')
        .eq(0)
        .click();

      const aliasName = `addProduct-${timestamp}`;
      const patchUrlPattern = '/rest/api/window/.*$';
      cy.server();
      cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.form-field-Qty')
        .find('input')
        .should('have.value', '0.1')
        .type('1{enter}');

      cy.wait(`@${aliasName}`);

      // cy.get('#lookup_M_Product_ID')
      //   .find('input')
      //   .should('have.value', '');
    });

    it('Complete sales order', function() {
      cy.processDocument('Complete', 'Completed');
    });
  });

  describe('create an invoice', function() {
    it("Zoom to the sales order's invoice candidate", function() {
      cy.wait(5000); // wait a bit for the invoice candidate(s) to be created, just like the user would
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

    it('Select all invoice candidates and invoice them', function() {
      // select all invoice candidates and wait for the list of available quick action to be updated
      cy.route('GET', `/rest/api/documentView/${invoiceCandidates.windowId}/*/quickActions?selectedIds=all`).as(
        'selectAll'
      );
      cy.clickElementWithClass('.pagination-link.pointer');
      cy.wait('@selectAll');

      // and *now* execute the invoiceing action
      cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing');
      cy.writeIntoStringField(
        'POReference',
        `Invoice -${timestamp}`,
        true /*modal*/,
        '/rest/api/process' /*rewriteUrl*/
      );
      cy.pressStartButton();
    });
  });
});
