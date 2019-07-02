import { Product, ProductCategory } from '../../support/utils/product';

describe('Create Test: set packing item in product window https://github.com/metasfresh/metasfresh-e2e/issues/26', function() {
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
  });

  it('Set packing item', function() {
    cy.selectTab('M_HU_PI_Item_Product')
      .pressAddNewButton()
      .selectInListField('M_HU_PI_Item_ID', 'IFCO 6410', true /*modal*/)
      .clearField('Qty', true /*modal*/)
      .writeIntoStringField('Qty', '10', true /*modal*/)
      .selectDateViaPicker('ValidFrom', 'today', true /*modal*/)
      .pressDoneButton();
  });
});
