import { products } from '../../page_objects/products';

describe('create new packing material', function() {
  let text;

  before(function() {
    products.visit();
    products.verifyElements();
  });

  it('open new product', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
  });

  it('config product', function() {
    cy.writeIntoStringField('Name', 'TestPackingMaterial');
    cy.selectInListField('M_Product_Category_ID', '24_Gebinde');
    cy.getStringFieldValue('Value').then(val => (text = val)); // store the value that metasfresh assigned in 'text'
  });

  it('configure new packing material', function() {
    cy.visitWindow('540192', 'NEW');

    cy.writeIntoStringField('Name', 'TestPackingMaterial');
    cy.selectInListField('M_Product_ID', text);
  });
});
