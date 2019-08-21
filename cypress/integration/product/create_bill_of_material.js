import { Product, ProductCategory } from '../../support/utils/product';
import { BillOfMaterial } from '../../support/utils/billOfMaterial';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create Product', function() {
  const date = humanReadableNow();
  const productName = `ProductName ${date}`;
  const bomName = `BOM ${date}`;

  const productCategoryName = `ProductCategoryName ${date}`;
  const productComponentName = `ProductComponentName ${date}`;

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  let mainProductId;

  it('Create main product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .setStocked(true)
        .setPurchased(true)
        .setSold(true)
        .apply();
    });
    cy.getCurrentWindowRecordId().then(id => {
      mainProductId = id;
    });
  });

  it('Create component product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productComponentName)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .setStocked(true)
        .setPurchased(true)
        .setSold(true)
        .apply();
    });
  });

  it('Create a new Bill of Material and add a component', function() {
    cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
      Object.assign(new BillOfMaterial(), billMaterialJson)
        .setName(bomName)
        .setProduct(productName)
        .setProductComponent(productComponentName)
        .apply();
    });
  });
  it('Verify the new BOM', function() {
    cy.executeHeaderActionWithDialog('PP_Product_BOM');
    cy.pressStartButton();

    cy.visitWindow('140', mainProductId);
    cy.getCheckboxValue('IsBOM').then(checkBoxValue => {
      expect(checkBoxValue).to.eq(true);
    });
    cy.getCheckboxValue('IsVerified').then(checkBoxValue => {
      expect(checkBoxValue).to.eq(true);
    });
  });
});
