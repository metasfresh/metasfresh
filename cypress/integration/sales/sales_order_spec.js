import { getBreadcrumbs } from '../../support/apiRequests';
import { salesOrders } from '../../page_objects/sales_orders';
import config from '../../config';
import { Product, ProductCategory } from '../../support/utils/product';
import { SalesOrder } from '../../support/utils/sales_order';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
describe('New sales order test', function() {
  const windowId = salesOrders.windowId;
  let headerCaption = '';
  let menuOption = '';
  const timestamp = new Date().getTime();
  const productName = `Sales Order Test ${timestamp}`;
  const customerName = `Sales Order Test ${timestamp}`;

  before(function() {
    getBreadcrumbs(windowId, '1000040-new').then(({ option, caption }) => {
      menuOption = option;
      headerCaption = caption;
    });

    const productValue = `sales_order_test ${timestamp}`;
    const productCategoryName = `ProductCategoryName ${timestamp}`;
    const productCategoryValue = `ProductNameValue ${timestamp}`;
    const discountSchemaName = `sales_order_test ${timestamp}`;

    new DiscountSchema(discountSchemaName)
      .addDiscountBreak(new DiscountBreak().setBreakValue(0).setBreakDiscount(0))
      .apply();

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customerName)
        .setCustomerDiscountSchema(discountSchemaName)
        .apply();
    });
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
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .apply();
    });

    salesOrders.visit();
  });

  describe('Create a new sales order', function() {
    before(function() {
      cy.get('.header-breadcrumb').contains('.header-item', headerCaption, { timeout: 10000 });

      const option = ~~(Math.random() * (2 - 0)) + 0;

      if (option === 0) {
        cy.get('.header-breadcrumb')
          .contains('.header-item', headerCaption)
          .click();

        cy.get('.header-breadcrumb')
          .find('.menu-overlay')
          .should('exist')
          .find('.menu-overlay-link')
          .contains(menuOption)
          .click();
      } else {
        cy.clickHeaderNav(Cypress.messages.window.new);
      }

      cy.get('.header-breadcrumb-sitename').should('contain', '<');
    });

    it('Fill Business Partner', function() {
      cy.writeIntoLookupListField('C_BPartner_ID', `${timestamp}`, customerName);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

    it('Fill order reference to differentiate cypress tests', function() {
      cy.writeIntoStringField('POReference', `Cypress Test ${new Date().getTime()}`);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new order line via "add new" button', function() {
      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.panel-modal').should('exist');

      cy.get('.form-field-M_Product_ID')
        .find('input')
        .type(`${timestamp}`);

      cy.get('.input-dropdown-list').should('exist');

      // enter just the timestamp which is also part of the product name
      cy.contains('.input-dropdown-list-option', productName).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');

      cy.get('.form-field-QtyEntered', { timeout: 12000 })
        .find('input')
        .should('not.have.value', '0');

      const aliasName = `addProduct-${new Date().getTime()}`;
      const patchUrlPattern = '/rest/api/window/.*$';
      cy.server();
      cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.panel-modal-header')
        .find('.btn')
        .click();

      cy.wait(`@${aliasName}`);
    });

    it('Add new order line via Batch Entry', function() {
      const addNewText = Cypress.messages.window.batchEntry.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.quick-input-container .form-group').should('exist');

      // enter just the timestamp which is also part of the product name
      cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName);

      // increment the quantity via the widget's tiny "up" button
      cy.get('.form-field-Qty')
        .click()
        .find('.input-body-container.focused')
        .should('exist')
        .find('i')
        .eq(0)
        .click();

      cy.server();
      cy.route('POST', `/rest/api/window/${salesOrders.windowId}/*/${salesOrders.orderLineTabId}/quickInput`).as(
        'resetQuickInputFields'
      );
      cy.get('.form-field-Qty')
        .find('input')
        .should('have.value', '0.1')
        .type('1{enter}'); // hit enter to add the line
      cy.wait('@resetQuickInputFields'); // the input fields are reset after the new line was added

      cy.get('#lookup_M_Product_ID')
        .find('input')
        .should('have.value', '');
    });

    it('Change document status', function() {
      let completeActionCaption = '';
      const draftedCaption = Cypress.reduxStore.getState().windowHandler.master.data.DocStatus.value.caption;
      const docId = Cypress.reduxStore.getState().windowHandler.master.docId;

      cy.request('GET', `${config.API_URL}/window/${windowId}/${docId}/field/DocAction/dropdown`).then(response => {
        const resp = response.body;

        expect(resp).to.have.property('values');
        expect(resp.values.length).to.be.gt(0);

        for (let i = 0; i < resp.values.length; i += 1) {
          if (resp.values[i].key === 'CO') {
            completeActionCaption = resp.values[i].caption;

            break;
          }
        }

        cy.get('.form-field-DocAction')
          .find('.meta-dropdown-toggle')
          .click();

        cy.get('.form-field-DocAction')
          .find('.dropdown-status-toggler')
          .should('have.class', 'dropdown-status-open');

        cy.get('.form-field-DocAction .dropdown-status-list')
          .find('.dropdown-status-item')
          .contains(completeActionCaption)
          .click();

        cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist');
        cy.get('.meta-dropdown-toggle .tag-success').should('not.contain', draftedCaption);
      });
    });
  });
});

describe('List tests', function() {
  const list = salesOrders;
  const timestamp = new Date().getTime();

  before(function() {
    const salesReference = `Cypress Test ${timestamp}`;

    new SalesOrder(salesReference)
      .setBPartner('G0001_Test Kunde 1')
      .setBPartnerLocation('Testadresse 3')
      .apply();

    salesOrders.visit();
    salesOrders.verifyElements();
  });

  it('Test if rows get selected/deselected properly', function() {
    list
      .getRows()
      .eq(0)
      .find('td')
      .eq(0)
      .type('{shift}', { release: false })
      .click();

    list
      .getRows()
      .eq(1)
      .find('td')
      .eq(0)
      .type('{shift}', { release: false })
      .click();

    list.getSelectedRows().should('have.length', 2);
    list.clickListHeader();
    list.getSelectedRows().should('have.length', 0);
  });

  it('Test if filtering works', function() {
    list.getRows().should('not.have.length', 0);

    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('DocumentNo', `${timestamp}`, false, null, true);

    applyFilters();

    list.getRows().should('have.length', 0);
  });
});

/*
 * Not implemented yet
 *
describe('Existing sales order', function() {
  const timestamp = new Date().getTime();
  const vendorName = `Cypress Test Partner #1 ${timestamp}`;

  before(function() {
    cy.fixture('purchase/cypress_vendor.json').then(() => {
      new BPartner(vendorName).apply();
    });

    salesOrders.visit();
    salesOrders.verifyElements();
  });

  it('Fill Business Partner', function() {
    cy.writeIntoLookupListField('C_BPartner_ID', 'Cypress', 'Cypress Test Partner #1');
    //we need a fake click to close the opened dropdown
    cy.get('.header-breadcrumb-sitename').click();
    cy.writeIntoLookupListField('C_BPartner_Location_ID', 'Warsaw', 'Warsaw, Potocka 1', true);

    cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
  });

});
*/
