import { Product, ProductCategory } from '../../support/utils/product';
import { BillOfMaterial, BillOfMaterialLine } from '../../support/utils/billOfMaterial';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create Product and BOM', function() {
  const date = humanReadableNow();
  const productName = `ProductName ${date}`;

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
        .setProduct(productName)
        // eslint-disable-next-line
        .addLine(new BillOfMaterialLine().setProduct(productComponentName).setQuantity(555).setScrap(3333))
        .apply();
    });
  });

  it('Verify the new BOM', function() {
    cy.executeHeaderActionWithDialog('PP_Product_BOM');
    cy.pressStartButton();

    cy.visitWindow('140', mainProductId);
    cy.expectCheckboxValue('IsBOM', true);
    cy.expectCheckboxValue('IsVerified', true);
  });
});
