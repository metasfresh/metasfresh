import { Product } from '../../support/utils/product';

describe('Create Product Masterdata for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/40', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const productName = `ProductName ${timestamp}`;
  const productValue = `ProductNameValue ${timestamp}`;

  it('Create a new Product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .apply();
    });
  });
});

/*
describe('Create a Product Category and Products with Product Price', function() {
  it('Create a new Product Category and Products', function() {
    //create ProductCategory
    cy.visitWindow('144', 'NEW');
    cy.writeIntoStringField('Name', 'TestProductCategory1');
    cy.clearField('Value');
    cy.writeIntoStringField('Value', 'TestProductCategory1');
    //cy.selectInListField('M_Product_Category_ID', 'Convenience Früchte');
  });
});
*/
