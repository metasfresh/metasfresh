describe('New subscription flatrate conditions Test', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const discountschemaName = `${timestamp} (Cypress Test)`;

  it('Create a discount schema record', function() {
    describe('Create a new discount schema record', function() {
      cy.visit('/window/233/NEW');
      cy.writeIntoStringField('Name', `${discountschemaName}`);
      cy.selectInListField('DiscountType', 'Breaks');
      cy.writeIntoStringField('ValidFrom', '01/01/2018{enter}');
    });

    describe('Create break records', function() {
      addBreakRecord('P002737', '0', '0');
      addBreakRecord('P002737', '50', '20');
    });
  });
});

function addBreakRecord(productValue, breakValue, breakDiscount) {
  cy.pressAddNewButton();
  cy.writeIntoLookupListField('M_Product_ID', productValue, productValue);
  cy.writeIntoStringField('BreakValue', breakValue);
  cy.writeIntoStringField('BreakDiscount', breakDiscount);
  cy.get('.items-row-2 > .btn').click();
}
