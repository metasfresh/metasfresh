export class CostCenter {
  constructor(name) {
    cy.log(`CostCenterBuilder - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`CostCenterBuilder - set name = ${name}`);
    this.name = name;
    return this;
  }

  apply() {
    cy.log(`CostCenter - apply - START (name=${this.name})`);
    applyCostCenter(this);
    cy.log(`CostCenter - apply - END (name=${this.name})`);
    return this;
  }
}
function applyCostCenter(costCenter) {
  describe(`Create new CostCenter ${costCenter.name}`, function() {
    cy.visitWindow(134, 'NEW');
    cy.writeIntoStringField('Name', costCenter.name);
  });
}
