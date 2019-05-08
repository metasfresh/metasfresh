export class Taxrate {
  constructor(name, validFrom, taxCategory) {
    this.name = name;
    this.validFrom = validFrom;
    this.taxCategory = taxCategory;
  }

  setName(name) {
    cy.log(`Taxrate - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`Taxrate - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  setTaxCategory(taxCategory) {
    cy.log(`Taxrate - set taxCategory = ${taxCategory}`);
    this.taxCategory = taxCategory;
    return this;
  }

  setActive(isActive) {
    cy.log(`Taxrate - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  setRate(rate) {
    cy.log(`Taxrate - set rate = ${rate}`);
    this.rate = rate;
    return this;
  }

  setCountry(country) {
    cy.log(`Taxrate - set Country = ${country}`);
    this.country = country;
    return this;
  }

  setCountryTo(country) {
    cy.log(`Taxrate - set CountryTo = ${country}`);
    this.countryTo = country;
    return this;
  }

  apply() {
    cy.log(`Taxrate - apply - START (name=${this.name})`);
    applyTaxrate(this);
    cy.log(`Taxrate - apply - END (name=${this.name})`);
    return this;
  }
}

function applyTaxrate(taxrate) {
  describe(`Create new Taxrate ${taxrate.name}`, function() {
    debugger;
    cy.visitWindow('137', 'NEW');
    cy.writeIntoStringField('Name', taxrate.name);
    cy.writeIntoStringField('ValidFrom', taxrate.validFrom);
    cy.selectInListField('C_TaxCategory_ID', taxrate.taxCategory, taxrate.taxCategory);
    cy.writeIntoStringField('Rate', taxrate.rate);
    cy.writeIntoLookupListField('C_Country_ID', taxrate.country);
    cy.writeIntoLookupListField('To_Country_ID', taxrate.countryTo);
  });
}
