describe('create new packing material', function() {
  before(function() {
    cy.visit('/window/140');
  });

  it('open new product', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
  });

  let text;

  it('config product', function() {
    cy.writeIntoStringField('Name', 'TestPackingMaterial');
    cy.selectInListField('M_Product_Category_ID', '24_Gebinde');
    cy.get('.form-field-Value')
      .find('input')
      .invoke('val')
      .then(val => {
        text = val;
      });
    cy.wait(500);
  });

  it('open packing material area', function() {
    cy.visit('/window/540192');
  });

  it('open new packing material', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
  });

  it('config packing material', function() {
    cy.writeIntoStringField('Name', 'TestPackingMaterial');
    cy.selectInListField('M_Product_ID', text);
  });

  it('done', function() {
    cy.wait(1000);
  });
});
