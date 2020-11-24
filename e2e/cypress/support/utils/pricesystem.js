export class Pricesystem {
  constructor(name) {
    this.name = name;
  }

  setName(name) {
    cy.log(`Pricesystem - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Pricesystem - set description = ${description}`);
    this.description = description;
    return this;
  }

  setValue(value) {
    cy.log(`Pricesystem - set value = ${value}`);
    this.value = value;
    return this;
  }

  setActive(isActive) {
    cy.log(`Pricesystem - set isActive = ${isActive}`);
    this.IsActive = isActive;
    return this;
  }

  apply() {
    cy.log(`Pricesystem - apply - START (name=${this.name})`);
    applyPricesystem(this);
    cy.log(`Pricesystem - apply - END (name=${this.name})`);
    return this;
  }
}

function applyPricesystem(pricesystem) {
  cy.visitWindow('540320', 'NEW');
  cy.writeIntoStringField('Name', `${pricesystem.name}`);
  cy.writeIntoStringField('Description', `${pricesystem.description}`);
}
