import { Product, ProductCategory } from '../../support/utils/product';
import { ProductProcurementContracts } from '../../support/utils/productProcurementContracts';

describe('Create Product', function() {
  const timestamp = new Date().getTime();
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

  it('Create Product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .setStocked(true)
        .setPurchased(true)
        .apply();
    });
  });
  it('Create a product for procurement contracts', function() {
    cy.fixture('purchase/product_for_procurement_contracts.json').then(productJson => {
      Object.assign(new ProductProcurementContracts(), productJson)
        .setName(productName)
        .apply();
    });
  });
});
