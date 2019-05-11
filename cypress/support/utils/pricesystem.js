export class Pricesystem {
  constructor(name) {
    this.Name = name;
  }

  setName(name) {
    cy.log(`Pricesystem - set name = ${name}`);
    this.Name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Pricesystem - set description = ${description}`);
    this.Description = description;
    return this;
  }

  setValue(value) {
    cy.log(`Pricesystem - set value = ${value}`);
    this.Value = value;
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
  const timestamp = new Date().getTime();

  describe(`Create new Pricesystem ${pricesystem.Name}`, function() {
    cy.visitWindow('540320', 'NEW');
    cy.writeIntoStringField('Name', `${pricesystem.Name} ${timestamp}`);
    cy.writeIntoStringField('Description', `${pricesystem.Description}`);
  });
}
