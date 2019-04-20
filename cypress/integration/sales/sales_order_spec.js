import { getBreadcrumbs } from '../../support/apiRequests';
import { salesOrders } from '../../page_objects/sales_orders';
import config from '../../config';
import { Product, ProductCategory } from '../../support/utils/product';
// import { BPartner } from '../../support/utils/bpartner';
import { SalesOrder } from '../../support/utils/sales_order';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('New sales order test', function() {
  const windowId = 143;
  let headerCaption = '';
  let menuOption = '';
  const timestamp = new Date().getTime();

  before(function() {
    cy.loginByForm();

    Cypress.Cookies.defaults({
      whitelist: ['SESSION', 'isLogged'],
    });

    getBreadcrumbs(windowId, '1000040-new').then(({ option, caption }) => {
      menuOption = option;
      headerCaption = caption;
    });

    const productName = `P002737_Convenience Salat 250g ${timestamp}`;
    const productValue = `Convencience Salat ${timestamp}`;
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
        cy.clickHeaderNav('New');
      }

      cy.get('.header-breadcrumb-sitename').should('contain', '<');
    });

    it('Fill Business Partner', function() {
      cy.writeIntoLookupListField('C_BPartner_ID', 'G', 'G0001_Test');

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

    // it('Create new Business Partner', function() {
    //   cy.writeIntoLookupListField('C_BPartner_ID', 'New', 'New Business Partner', true);
    //   cy.get('.panel-modal').should('exist');
    //   cy.writeIntoStringField('Companyname', vendorName);
    //   // cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    //   const aliasName = `addBP-${new Date().getTime()}`;
    //   const patchUrlPattern = '/rest/api/window/.*$';
    //   cy.server();
    //   cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);
    //   cy.get('.panel-modal-header')
    //     .find('.btn')
    //     .click();
    //   cy.wait(`@${aliasName}`);
    //   // form-field-C_Location_ID click
    //   // attributes-dropdown should.be.visible
    //   // C_Country_ID Deu Germany - Deutschland
    // });

    it('Fill order reference to differentiate cypress tests', function() {
      cy.writeIntoStringField('POReference', `Cypress Test ${new Date().getTime()}`);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new product', function() {
      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.panel-modal').should('exist');

      cy.get('.form-field-M_Product_ID')
        .find('input')
        .type('C');

      cy.get('.input-dropdown-list').should('exist');
      cy.contains('.input-dropdown-list-option', 'P002737_Convenience Salat 250g').click();
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

    it('Add new product via Batch Entry', function() {
      const addNewText = Cypress.messages.window.batchEntry.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn')
        .contains(addNewText)
        .should('exist')
        .click();

      cy.get('.quick-input-container').should('exist');
      cy.get('.quick-input-container').snapshot({ name: 'Empty Quick Inp' });

      cy.writeIntoLookupListField('M_Product_ID', 'C', 'Convenience Salat');

      cy.get('.form-field-Qty')
        .click()
        .find('.input-body-container.focused')
        .should('exist')
        .find('i')
        .eq(0)
        .click();

      const aliasName = `addProduct-${new Date().getTime()}`;
      const patchUrlPattern = '/rest/api/window/.*$';
      cy.server();
      cy.route('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.form-field-Qty')
        .find('input')
        .should('have.value', '0.1')
        .type('1{enter}');

      cy.wait(`@${aliasName}`);

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

    cy.fixture('product/simple_product.json').then(() => {
      new SalesOrder(salesReference)
        .setBPartner('G0001_Test Kunde 1')
        .setBPartnerLocation('Testadresse 3')
        .apply();
    });

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
    cy.loginByForm();

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
