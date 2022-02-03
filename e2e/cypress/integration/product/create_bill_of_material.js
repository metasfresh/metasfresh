import { Product, ProductCategory } from '../../support/utils/product';
// import { BillOfMaterialVersion, BillOfMaterialVersionLine } from '../../support/utils/billOfMaterialVersion';
import { BillOfMaterial } from '../../support/utils/billOfMaterial';
import { BillOfMaterialVersion, BillOfMaterialVersionLine } from '../../support/utils/billOfMaterialVersion';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Product and BOM', function() {
  let productName;
  let productCategoryName;
  let productComponentName;

  // test
  let mainProductId;
  let bomName1;
  const firstBomName = appendHumanReadableNow('BOM1', null);

  it('Read the fixture', function() {
    cy.fixture('product/create_bill_of_material.json').then(f => {
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      productComponentName = appendHumanReadableNow(f['productComponentName']);
    });
  });

  it('Create a new ProductCategory', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create main product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName)
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
        .setProductCategory(productCategoryName)
        .apply();
    });
  });

  it('Create BOMs and set their products', function () {
    bomName1 = `${productName}_BOM`;
    // bomName2 = `${componentProductName}_BOM`;

    Object.assign(new BillOfMaterial(), {}).setName(bomName1).setProduct(productName).apply();
    // Object.assign(new BillOfMaterial(), {}).setName(bomName2).setProduct(componentProductName).apply();
  });

  it('Create a new Bill of Material and add a component', function() {
    cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
      Object.assign(new BillOfMaterialVersion(), billMaterialJson)
        .setName(firstBomName)
        .setBOM(bomName1)
        .setProduct(productName)
        // .setProduct(productName)
        // eslint-disable-next-line
        .addLine(new BillOfMaterialVersionLine().setProduct(productComponentName).setQuantity(555).setScrap(3333))
        .setIsVerified(true)
        .apply();
    });
  });

  it('Verify the new BOM', function() {
    cy.visitWindow('140', mainProductId);
    cy.expectCheckboxValue('IsBOM', true);
    cy.expectCheckboxValue('IsVerified', true);
  });
});
