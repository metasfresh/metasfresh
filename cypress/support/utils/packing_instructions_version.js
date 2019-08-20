export class PackingInstructionsVersion {
  constructor(name) {
    cy.log(`PackingInstructionsVersionBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`PackingInstructionsVersionBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  setPackingInstructions(packingInstructions) {
    cy.log(`PackingInstructionsVersionBuilder - set packingInstructions = ${packingInstructions}`);
    this.packingInstructions = packingInstructions;
    return this;
  }
  setPackingMaterial(packingMaterial) {
    cy.log(`PackingInstructionsVersionBuilder - set packingMaterial = ${packingMaterial}`);
    this.packingMaterial = packingMaterial;
    return this;
  }

  setUnit(unit) {
    cy.log(`PackingInstructionsVersionBuilder - set handling unit type = ${unit}`);
    this.unit = unit;
    return this;
  }

  apply() {
    cy.log(`PackingInstructionsVersion - apply - START (name=${this.name})`);
    applyPackingInstructionsVersion(this);
    cy.log(`PackingInstructionsVersion - apply - END (name=${this.name})`);
    return this;
  }
}
function applyPackingInstructionsVersion(packingInstructionsVersion) {
  describe('create new packing instructions version', function() {
    cy.visitWindow('540344', 'NEW');
    cy.selectInListField('M_HU_PI_ID', packingInstructionsVersion.packingInstructions);
    cy.writeIntoStringField('Name', packingInstructionsVersion.name);
    if (packingInstructionsVersion.unit) {
      cy.selectInListField('HU_UnitType', packingInstructionsVersion.unit, false, null, true);
    }
    cy.clickOnCheckBox('IsCurrent');

    cy.selectTab('M_HU_PI_Item');
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packmittel', true);
    cy.selectInListField('M_HU_PackingMaterial_ID', packingInstructionsVersion.packingMaterial, true);
    cy.pressDoneButton();
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packgut', true);
    cy.pressDoneButton();
  });
}
