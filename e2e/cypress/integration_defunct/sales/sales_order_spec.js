import { salesOrders } from '../../page_objects/sales_orders';

import { SalesOrder } from '../../support/utils/sales_order';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';
import { BPartner } from '../../support/utils/bpartner';
import { getLanguageSpecific } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';

const timestamp = new Date().getTime();
const customerName = `Sales Order Test ${timestamp}`;

describe('New sales order test', function () {
  const productName = `Sales Order Test ${timestamp}`;

  const productValue = `sales_order_test ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductNameValue ${timestamp}`;

  const priceSystemName = `PriceSystem ${timestamp}`;
  const priceListName = `PriceList ${timestamp}`;
  const priceListVersionName = `PriceListVersion ${timestamp}`;

  before(function () {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName);
    Builder.createBasicProductEntities(productCategoryName, productCategoryValue, priceListName, productName, productValue);

    cy.fixture('sales/simple_customer.json').then((customerJson) => {
      new BPartner({ ...customerJson, name: customerName })
        .setName(customerName)
        .setCustomerPricingSystem(priceSystemName)
        .setBank(undefined)
        .apply()
        .then(() => {
          cy.visitWindow(salesOrders.windowId, 'NEW');
        });
    });
  });

  describe('Sales order tests', function () {
    it('Fill Business Partner', function () {
      cy.writeIntoLookupListField('C_BPartner_ID', `${timestamp}`, customerName);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

    it('Fill order reference to differentiate cypress tests', function () {
      cy.writeIntoStringField('POReference', `Cypress Test ${new Date().getTime()}`);
      cy.get('.indicator-pending').should('not.exist');
    });

    it('Add new order line via "add new" button', function () {
      const addNewText = Cypress.messages.window.addNew.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn').contains(addNewText).should('exist').click();

      cy.get('.panel-modal').should('exist');

      cy.get('.form-field-M_Product_ID').find('input').type(`${timestamp}`);

      cy.get('.input-dropdown-list').should('exist');

      // enter just the timestamp which is also part of the product name
      cy.contains('.input-dropdown-list-option', productName).click();
      cy.get('.input-dropdown-list .input-dropdown-list-header').should('not.exist');

      cy.get('.form-field-QtyEntered', { timeout: 12000 }).find('input').should('not.have.value', '0');

      const aliasName = `addProduct-${new Date().getTime()}`;
      const patchUrlPattern = '/rest/api/window/.*$';
      cy.intercept('GET', new RegExp(patchUrlPattern)).as(aliasName);

      cy.get('.panel-modal-header').find('.btn').click();

      cy.wait(`@${aliasName}`);
    });

    it('Add new order line via Batch Entry', function () {
      const addNewText = Cypress.messages.window.batchEntry.caption;

      cy.get('.tabs-wrapper .form-flex-align .btn').contains(addNewText).should('exist').click();

      cy.get('.quick-input-container .form-group').should('exist');

      // enter just the timestamp which is also part of the product name
      cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName);

      // increment the quantity via the widget's tiny "up" button
      cy.get('.form-field-Qty').click().find('.input-body-container.focused').should('exist').find('i').eq(0).click();

      cy.intercept('POST', `/rest/api/window/${salesOrders.windowId}/*/${salesOrders.orderLineTabId}/quickInput`).as('resetQuickInputFields');
      cy.get('.form-field-Qty').find('input').should('have.value', '0.1').type('1{enter}'); // hit enter to add the line
      cy.wait('@resetQuickInputFields'); // the input fields are reset after the new line was added

      cy.get('#lookup_M_Product_ID').find('input').should('have.value', '');
    });

    it('Complete sales order', function () {
      // complete it and verify the status
      cy.fixture('misc/misc_dictionary.json').then((miscDictionary) => {
        cy.processDocument(getLanguageSpecific(miscDictionary, 'docActionComplete'), getLanguageSpecific(miscDictionary, 'docStatusCompleted'));
      });

      // cy.request('GET', `${config.API_URL}/window/${windowId}/${docId}/field/DocAction/dropdown`).then(response => {
      //   const resp = response.body;

      //   expect(resp).to.have.property('values');
      //   expect(resp.values.length).to.be.gt(0);

      //   for (let i = 0; i < resp.values.length; i += 1) {
      //     if (resp.values[i].key === 'CO') {
      //       completeActionCaption = resp.values[i].caption;

      //       break;
      //     }
      //   }

      //   cy.get('.form-field-DocAction')
      //     .find('.meta-dropdown-toggle')
      //     .click();

      //   cy.get('.form-field-DocAction')
      //     .find('.dropdown-status-toggler')
      //     .should('have.class', 'dropdown-status-open');

      //   cy.get('.form-field-DocAction .dropdown-status-list')
      //     .find('.dropdown-status-item')
      //     .contains(completeActionCaption)
      //     .click();

      //   cy.get('.indicator-pending', { timeout: 10000 }).should('not.exist');
      //   cy.get('.meta-dropdown-toggle .tag-success').should('not.contain', draftedCaption);
      // });
    });
  });
});

describe('List tests', function () {
  const list = salesOrders;
  const timestamp = new Date().getTime();

  before(function () {
    const salesReference = `Cypress Test ${timestamp}`;

    // make sure to have at least one more sales order
    new SalesOrder(salesReference).setBPartner(customerName).apply();

    salesOrders.visit();
    salesOrders.verifyElements();
  });

  it('Test if rows get selected/deselected properly', function () {
    list.getRows().eq(0).find('td').eq(0).type('{shift}', { release: false }).click();

    list.getRows().eq(1).find('td').eq(0).type('{shift}', { release: false }).click();

    list.getSelectedRows().should('have.length', 2);
    list.clickListHeader();
    list.getSelectedRows().should('have.length', 0);
  });

  it('Test if filtering works', function () {
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
