export class PackingInstructions {
  constructor(name) {
    cy.log(`PackingInstructionsBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`PackingInstructionsBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  apply() {
    cy.log(`PackingInstructions - apply - START (name=${this.name})`);
    applyPackingInstructions(this);
    cy.log(`PackingInstructions - apply - END (name=${this.name})`);
    return this;
  }
}
function applyPackingInstructions(packingInstructions) {
  describe('create new packing instructions', function() {
    cy.visitWindow('540343', 'NEW');

    cy.writeIntoStringField('Name', packingInstructions.name);
  });
}
