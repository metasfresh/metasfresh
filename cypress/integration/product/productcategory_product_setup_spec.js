import { Product, ProductCategory } from '../../support/utils/product';
import { confirmCalendarDay } from '../../support/functions';

// Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/40
describe('Create Product', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const productName = `ProductName ${timestamp}`;
  const productValue = `ProductNameValue ${timestamp}`;
  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductNameValue ${timestamp}`;

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
  });

  it('Create Product & Price; edit document note', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .apply();
    });

    cy.openAdvancedEdit();
    cy.writeIntoTextField('DocumentNote', '{selectall}{backspace}blah-blah-blah');
    cy.pressDoneButton();
  });

  it(`Set product's CU-TU allocation`, function() {
    const addNewText = Cypress.messages.window.addNew.caption;

    cy.selectTab('M_HU_PI_Item_Product');

    cy.get('.tabs-wrapper .form-flex-align .btn')
      .contains(addNewText)
      .should('exist')
      .click();

    cy.selectInListField('M_HU_PI_Item_ID', 'IFCO', 'IFCO 6410');
    cy.writeIntoStringField('Qty', '1');
    cy.get('.form-field-ValidFrom')
      .find('.datepicker')
      .click();

    confirmCalendarDay();
    cy.get('.panel-modal-header-title').click();
    cy.pressDoneButton();
  });
});
