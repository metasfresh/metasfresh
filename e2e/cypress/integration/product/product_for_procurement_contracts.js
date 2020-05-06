import { Product, ProductCategory } from '../../support/utils/product';
import { ProductProcurementContracts } from '../../support/utils/productProcurementContracts';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Product', function() {
  let productName;
  let productCategoryName;

  it('Read the fixture', function() {
    cy.fixture('product/product_for_procurement_contracts.json').then(f => {
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    });
  });

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create Product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName)
        .apply();
    });
  });

  it('Create a product for procurement contracts', function() {
    new ProductProcurementContracts().setName(productName).apply();
  });
});
