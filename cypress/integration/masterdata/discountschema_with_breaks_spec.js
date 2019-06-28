import { Product, ProductCategory } from '../../support/utils/product';

describe('New discount schema Test', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const discountschemaName = `discountschema_with_breaks-${timestamp}`;
  const productCategoryName = `discountschema_with_breaks-${timestamp}`;
  const productName = `discountschema_with_breaks-${timestamp}`;
  it('Create a product to test with', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryName)
        .apply();
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productName)
        .setProductType('Service')
        .apply();
    });
  });

  it('Create a discount schema record', function() {
    describe('Create a new discount schema record', function() {
      cy.visitWindow('233', 'NEW');
      cy.writeIntoStringField('Name', discountschemaName, false);
      cy.selectInListField('DiscountType', 'Breaks');
      cy.writeIntoStringField('ValidFrom', '01/01/2019', false, '', true);
      cy.writeIntoStringField('Description', `Description for ${discountschemaName}`, false);
    });

    describe(`Create new break records for ${discountschemaName}`, function() {
      addBreakRecord(productName, '1', '10');
      addBreakRecord(productName, '50', '20');
    });
  });
});

function addBreakRecord(productValue, breakValue, breakDiscount) {
  cy.pressAddNewButton();

  cy.writeIntoLookupListField('M_Product_ID', productValue, productValue, false, true /*modal*/);
  cy.clearField('BreakValue', true /*modal*/).writeIntoStringField('BreakValue', breakValue, true /*modal*/);
  cy.clearField('BreakDiscount', true /*modal*/).writeIntoStringField('BreakDiscount', breakDiscount, true /*modal*/);

  cy.resetListValue('PriceBase', true /*modal*/);

  cy.pressDoneButton();
}
