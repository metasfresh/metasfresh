import { Product, ProductCategory } from '../../support/utils/product';

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
    cy.writeIntoTextField('DocumentNote', '{selectall}{backspace}blah-blah-blah', true /*modal*/);
    cy.pressDoneButton();
  });

  it(`Set product's CU-TU allocation`, function() {
    cy.selectTab('M_HU_PI_Item_Product');

    cy.pressAddNewButton();

    cy.selectInListField('M_HU_PI_Item_ID', 'IFCO 6410', true /*modal*/);
    cy.writeIntoStringField('Qty', '1', true /*modal*/);
    cy.selectDateViaPicker('ValidFrom', true /*modal*/);
    cy.pressDoneButton();
  });
});
