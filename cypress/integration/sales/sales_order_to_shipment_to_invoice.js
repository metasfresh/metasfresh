import { salesOrders } from '../../page_objects/sales_orders';
import { BPartner } from '../../support/utils/bpartner';
import { Product, ProductCategory } from '../../support/utils/product';

describe('Create Sales order', function() {
  const timestamp = new Date().getTime();
  const customer = `CustomerTest ${timestamp}`;
  const productName = `Product Test ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductNameValue ${timestamp}`;
  const productValue = `sales_order_test ${timestamp}`;

  it('Create product category, product and customer for a sales order', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      Object.assign(new BPartner(), customerJson)
        .setName(customer)
        .setCustomer(true)
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
    cy.visitWindow('540651', 'NEW');
    cy.get('#lookup_C_BPartner_ID input')
      .type(customer)
      .type('\n');
    cy.contains('.input-dropdown-list-option', customer).click();
    // cy.wait(3000);
    cy.get('.form-field-C_DocTypeTarget_ID input')
      .click()
      .get('.input-dropdown-list')
      .wait(2000)
      .click();

    const addNewText = Cypress.messages.window.batchEntry.caption;

    cy.get('.tabs-wrapper .form-flex-align .btn')
      .contains(addNewText)
      .should('exist')
      .click();

    cy.get('.quick-input-container .form-group').should('exist');

    cy.writeIntoLookupListField('M_Product_ID', `${timestamp}`, productName);

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
      .type('1{enter}');
    // cy.wait('@resetQuickInputFields');
    cy.wait(3000);
    cy.get('.form-field-DocAction ul')
      .click({ force: true })
      .get('li')
      .eq('1')
      .click({ force: true });
  });
});
