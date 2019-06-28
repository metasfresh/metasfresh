import { Product, ProductCategory } from '../../support/utils/product';
import { BillOfMaterial } from '../../support/utils/billOfMaterial';
import { applyFilters, toggleNotFrequentFilters, selectNotFrequentFilterWidget } from '../../support/functions';

describe('Create Product', function() {
  const timestamp = new Date().getTime();
  const productName = `ProductName ${timestamp}`;
  const productValue = `ProductNameValue ${timestamp}`;
  const bomName = `BOM ${timestamp}`;

  const productCategoryName = `ProductCategoryName ${timestamp}`;
  const productCategoryValue = `ProductNameValue ${timestamp}`;
  const productComponentName = `ProductComponentName ${timestamp}`;
  const productComponentValue = `ProductComponentValue ${timestamp}`;

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });
  });

  let mainProductId;

  it('Create main product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .setStocked(true)
        .setPurchased(true)
        .setSold(true)
        .apply();
    });
    cy.get(`@${productName}`).then(mainProduct => {
      mainProductId = mainProduct.documentId;
    });
  });

  it('Create component product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productComponentName)
        .setValue(productComponentValue)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
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
