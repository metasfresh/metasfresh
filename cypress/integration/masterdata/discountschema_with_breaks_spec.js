describe('New sdiscount schema Test', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const discountschemaName = `${timestamp} (Cypress Test)`;

  it('Create a discount schema record', function() {
    describe('Create a new discount schema record', function() {
      cy.visit('/window/233/NEW');
      cy.writeIntoStringField('Name', discountschemaName, false);
      cy.selectInListField('DiscountType', 'Breaks');
      cy.writeIntoStringField('ValidFrom', '01/01/2019', false, '', true);
      cy.writeIntoStringField('Description', `Description for ${discountschemaName}`, false);
    });

    describe(`Create new break records for ${discountschemaName}`, function() {
      addBreakRecord('P002737', '1', '10');
      addBreakRecord('P002737', '50', '20');
    });
  });
});

function addBreakRecord(productValue, breakValue, breakDiscount) {
  cy.pressAddNewButton();

  cy.writeIntoLookupListField('M_Product_ID', productValue, productValue, true /*modal*/);
  cy.clearField('BreakValue').writeIntoStringField('BreakValue', breakValue, true /*modal*/);
  cy.clearField('BreakDiscount').writeIntoStringField('BreakDiscount', breakDiscount, true /*modal*/);

  cy.resetListValue('PriceBase', true /*modal*/);

  cy.pressDoneButton();
}
