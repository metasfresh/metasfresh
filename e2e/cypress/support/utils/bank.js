export class Bank {
  constructor(name) {
    cy.log(`Bank - set name = ${name}`);
    this.name = name;
    this.BLZ = undefined;
  }

  setName(name) {
    cy.log(`Bank - set name = ${name}`);
    this.name = name;
    return this;
  }

  setBLZ(BLZ) {
    cy.log(`Bank - set BLZ = ${BLZ}`);
    this.BLZ = BLZ;
    return this;
  }

  apply() {
    cy.log(`Bank - apply - START (name=${this.name})`);
    applyBank(this);
    cy.log(`Bank - apply - END (name=${this.name})`);
    return this;
  }
}
function applyBank(bank) {
  describe(`Create new Bank ${bank.name}`, function() {
    cy.visitWindow('540336', 'NEW');
    cy.writeIntoStringField('Name', bank.name);
    cy.writeIntoStringField('RoutingNo', bank.BLZ);
  });
}
