import { Product, ProductCategory } from '../../support/utils/product';
import { BillOfMaterial } from '../../support/utils/billOfMaterial';
import { applyFilters, toggleNotFrequentFilters, selectNotFrequentFilterWidget } from '../../support/functions';

describe('Create Product', function() {
  const timestamp = new Date().getTime();
  const productName = `ProductName ${timestamp}`;
  const productValue = `ProductNameValue ${timestamp}`;
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

  it('Create Product', function() {
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
  });

  it('Create Product', function() {
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

  it('Create a new Bill of Material, add a component and verify BOM', function() {
    cy.fixture('product/bill_of_material.json').then(billMaterialJson => {
      Object.assign(new BillOfMaterial(), billMaterialJson)
        .setProduct(productName)
        .setProductComponent(productComponentName)
        .apply();
    });
    cy.executeHeaderActionWithDialog('PP_Product_BOM');
    cy.wait(2000);
    cy.pressStartButton();
    cy.visitWindow('140');
    cy.log('Now going to verify that the BOM was set correctly');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('Name', productName, false, null, true);
    applyFilters();
    cy.wait(3000);
    cy.get('table tr')
      .eq(1)
      .dblclick();
    cy.getCheckboxValue('IsBOM');
    cy.getCheckboxValue('IsVerified');
    /**Below is the implementation of how to check IsBOM field directly from the table, without entering the record. */
    // cy.get('table tr')
    //   .eq(0)
    //   .get('td')
    //   .eq(9)
    //   .get('.cell-text-wrapper.yesno-cell')
    //   .eq(4)
    //   .get('i')
    //   .should('have.class', 'meta-icon-checkbox-1');
  });
});
