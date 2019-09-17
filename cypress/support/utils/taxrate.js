export class TaxRate {
  setName(name) {
    cy.log(`TaxRate - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`TaxRate - set validFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }

  setTaxCategory(taxCategory) {
    cy.log(`TaxRate - set taxCategory = ${taxCategory}`);
    this.taxCategory = taxCategory;
    return this;
  }

  setRate(rate) {
    cy.log(`TaxRate - set rate = ${rate}`);
    this.rate = rate;
    return this;
  }

  setCountry(country) {
    cy.log(`TaxRate - set Country = ${country}`);
    this.country = country;
    return this;
  }

  setCountryTo(country) {
    cy.log(`TaxRate - set CountryTo = ${country}`);
    this.countryTo = country;
    return this;
  }

  apply() {
    cy.log(`TaxRate - apply - START (name=${this.name})`);
    applyTaxRate(this);
    cy.log(`TaxRate - apply - END (name=${this.name})`);
    return this;
  }
}

function applyTaxRate(taxRate) {
  cy.visitWindow('137', 'NEW');
  cy.writeIntoStringField('Name', taxRate.name);
  cy.writeIntoStringField('ValidFrom', taxRate.validFrom, false, null, true);
  cy.selectInListField('C_TaxCategory_ID', taxRate.taxCategory);
  cy.writeIntoStringField('Rate', taxRate.rate);
  cy.selectInListField('C_Country_ID', 'none', false);
  cy.selectInListField('C_Country_ID', taxRate.country, false);
  cy.selectInListField('To_Country_ID', taxRate.countryTo, false);
}
