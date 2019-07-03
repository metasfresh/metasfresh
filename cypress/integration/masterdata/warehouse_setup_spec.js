describe('Create test: warehouse, https://github.com/metasfresh/metasfresh-e2e/issues/46', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const warehouseName = `TestWarehouseName ${timestamp}`;
  const warehouseValue = `TestWarehouseValue ${timestamp}`;

  it('Create a new warehouse', function() {
    cy.visitWindow('139', 'NEW')
      .writeIntoStringField('Name', warehouseName)
      .clearField('Value')
      .writeIntoStringField('Value', warehouseValue);
    cy.selectNthInListField('C_BPartner_Location_ID', 1, false);
    //create locator
   cy.get(`#tab_M_Locator`).click();
    cy.pressAddNewButton()
    .writeIntoStringField('X', '0')
    .writeIntoStringField('X1', '0')
    .writeIntoStringField('Z', '0')
    .writeIntoStringField('Y', '0')
    .pressDoneButton();
     //create warehouse routing
    cy.get(`#tab_M_Warehouse_Routing`).click();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', 'Distribution Order', true)
      .selectInListField('DocBaseType', 'Sales Order', true)
      .pressDoneButton();
  });
});
