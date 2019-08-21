import { Product, ProductCategory } from '../../support/utils/product';
import { humanReadableNow } from '../../support/utils/utils';

// Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/40
describe('Create Product', function() {
  const date = humanReadableNow();
  const productName = `ProductName ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create Product & Price; edit document note', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName)
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
