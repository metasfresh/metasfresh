export class PriceList {
  constructor(name) {
    this.Name = name;
  }

  setName(name) {
    cy.log(`Pricelist - set name = ${name}`);
    this.Name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`Pricelist - set description = ${description}`);
    this.Description = description;
    return this;
  }

  setPricingSystem(pricingsystem) {
    cy.log(`Pricelist - set pricingsystem = ${pricingsystem}`);
    this.PricingSystem = pricingsystem;
    return this;
  }

  setActive(isActive) {
    cy.log(`Pricelist - set isActive = ${isActive}`);
    this.IsActive = isActive;
    return this;
  }

  apply() {
    cy.log(`Pricelist - apply - START (name=${this.name})`);
    applyPricelist(this);
    cy.log(`Pricelist - apply - END (name=${this.name})`);
    return this;
  }
}

function applyPricelist(pricelist) {
  const timestamp = new Date().getTime();

  describe(`Create new Pricelist ${pricelist.Name}`, function() {
    cy.visitWindow('540321', 'NEW', 'priceListObj');
    cy.writeIntoStringField('Name', `${pricelist.Name} ${timestamp}`);
    cy.selectInListField('C_Country_ID', `none`);
    cy.selectInListField('C_Country_ID', `${pricelist.Country}`);
    cy.selectInListField('C_Currency_ID', `${pricelist.Currency}`);
    cy.get('.form-field-PricePrecision')
      .find('input')
      .clear()
      .type(`${pricelist.PricePrecision}`);
    cy.selectInListField('M_PricingSystem_ID', `${pricelist.PricingSystem}`);
    cy.writeIntoStringField('Description', `${pricelist.Description}`);
  });
}
