describe('create new packing instructions ', function() {
  before(function() {
    cy.visit('/window/540343');
  });

  const name = 'TestPackingMaterial';

  it('creating packing instructions', function() {
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.writeIntoStringField('Name', name);
    cy.wait(1000);
  });

  it('creating packing instructions version', function() {
    cy.visit('/window/540344');
    cy.clickHeaderNav(Cypress.messages.window.new.caption);
    cy.selectInListField('M_HU_PI_ID', name);
    cy.writeIntoStringField('Name', name);
    cy.selectInListField('HU_UnitType', 'Load/Logistique Unit');
  });

  it('add new in modal', function() {
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packmittel');
    cy.selectInListField('M_HU_PackingMaterial_ID', name);
    cy.pressDoneButton();

    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Unter-Packvorschrift');
    cy.selectInListField('Included_HU_PI_ID', 'IFCO 6416');
    cy.writeIntoStringField('Qty', '10');
    cy.writeIntoLookupListField('C_BPartner_ID', 'c', '1000001_Customer1 1560426344773');
    cy.pressDoneButton();
  });
});
