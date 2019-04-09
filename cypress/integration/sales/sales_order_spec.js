import { getBreadcrumbs } from '../../support/apiRequests';
import { salesOrders } from '../../page_objects/sales_orders';
import config from '../../config';

describe('New sales order test', function() {
  const windowId = 143;
  const list = salesOrders;
  let headerCaption = '';
  let menuOption = '';

  before(function() {
    cy.loginByForm();

    getBreadcrumbs(windowId).then(({ option, caption }) => {
      menuOption = option;
      headerCaption = caption;
    });

    salesOrders.visit();
    salesOrders.verifyElements();
  });

  describe('List tests', function() {
    it('Test if rows get selected/deselected properly', function() {
      list
        .getRows()
        .eq(1)
        .find('td')
        .eq(0)
        .type('{shift}', { release: false })
        .click();

      list
        .getRows()
        .eq(2)
        .find('td')
        .eq(0)
        .type('{shift}', { release: false })
        .click();

      list.getSelectedRows().should('have.length', 2);
      list.clickListHeader();
      list.getSelectedRows().should('have.length', 0);
    });
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
      cy.writeIntoLookupListField('C_BPartner_ID', 'Cypress', 'Cypress Test Partner #1');
      //we need a fake click to close the opened dropdown
      cy.get('.header-breadcrumb-sitename').click();
      cy.writeIntoLookupListField('C_BPartner_Location_ID', 'Warsaw', 'Warsaw, Potocka 1', true);

      cy.get('.header-breadcrumb-sitename').should('not.contain', '<');
    });

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

      cy.writeIntoLookupListField('M_Product_ID', 'C', 'Convenience Salat', true);

      // cy.focused().type('{tab}');
      // cy.tab();

      // cy.focused()
      //   .should('have.value', 'IFCO 6410 x 10 Stk')
        // .type('{tab}');

      cy.get('.form-field-Qty', { timeout: 12000 })
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
