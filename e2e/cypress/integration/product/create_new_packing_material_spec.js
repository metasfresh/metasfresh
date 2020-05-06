import { products } from '../../page_objects/products';

let packingMaterialName;
let category;
it('Read fixture and prepare the names', function() {
  cy.fixture('product/create_new_packing_material_spec.json').then(f => {
    packingMaterialName = f['packingMaterialName'];
    category = f['category'];
  });
});

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
    cy.writeIntoStringField('Name', packingMaterialName);
    cy.selectInListField('M_Product_Category_ID', category);
    cy.getStringFieldValue('Value').then(val => (text = val)); // store the value that metasfresh assigned in 'text'
  });

  it('configure new packing material', function() {
    cy.visitWindow('540192', 'NEW');

    cy.writeIntoStringField('Name', packingMaterialName);
    cy.selectInListField('M_Product_ID', text);
  });
});
