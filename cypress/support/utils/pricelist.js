export class PriceList {
  constructor(name) {
    this.name = name;
  }

  setName(name) {
    cy.log(`PriceList - set name = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`PriceList - set description = ${description}`);
    this.description = description;
    return this;
  }

  setCountry(country) {
    cy.log(`PriceList - set country = ${country}`);
    this.country = country;
    return this;
  }

  setCurrency(currency) {
    cy.log(`PriceList - set currency = ${currency}`);
    this.currency = currency;
    return this;
  }

  setPricePrecision(pricePrecision) {
    cy.log(`PriceList - set pricePrecision = ${pricePrecision}`);
    this.pricePrecision = pricePrecision;
    return this;
  }


  setPriceSystem(priceSystem) {
    cy.log(`PriceList - set priceSystem = ${priceSystem}`);
    this.priceSystem = priceSystem;
    return this;
  }

  apply() {
    cy.log(`PriceList - apply - START (name=${this.name})`);
    applyPriceList(this);
    cy.log(`PriceList - apply - END (name=${this.name})`);
    return this;
  }
}

function applyPriceList(priceList) {
  describe(`Create new PriceList ${priceList.name}`, function () {
    cy.visitWindow('540321', 'NEW', 'priceListObj');
    cy.writeIntoStringField('Name', priceList.name);
    // cy.selectInListField('C_Country_ID', `none`);
    cy.selectInListField('C_Country_ID', priceList.country);
    cy.selectInListField('C_Currency_ID', priceList.currency);
    cy.get('.form-field-PricePrecision')
      .find('input')
      .clear()
      .type(priceList.pricePrecision);
    cy.selectInListField('M_PricingSystem_ID', priceList.priceSystem);
    cy.writeIntoStringField('Description', priceList.description);
  });
}
