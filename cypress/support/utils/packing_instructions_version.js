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

  setHandlingUnitType(unit) {
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
    cy.selectInListField('M_HU_PI_ID', packingInstructionsVersion.packingInstructions, false, null, true);
    cy.writeIntoStringField('Name', packingInstructionsVersion.name);
    cy.clickOnCheckBox('IsCurrent');
    if (packingInstructionsVersion.unit) {
      cy.selectInListField('HU_UnitType', packingInstructionsVersion.unit, false, null, true);
    }

    cy.selectTab('M_HU_PI_Item');
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Packmittel', true);
    cy.selectInListField('M_HU_PackingMaterial_ID', packingInstructionsVersion.packingMaterial, true);
    cy.pressDoneButton();
    if (!packingInstructionsVersion.unit) {
      cy.pressAddNewButton();
      cy.selectInListField('ItemType', 'Packgut', true);
      cy.pressDoneButton();
    }
  });
}
