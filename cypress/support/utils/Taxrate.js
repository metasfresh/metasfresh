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
  const timestamp = new Date().getTime();

  describe(`Create new Taxrate ${taxrate.name}`, function() {
    cy.visitWindow('137', 'NEW');
    cy.log(`Taxrate - Name (name=${taxrate.name} ${timestamp})`);
    cy.writeIntoStringField('Name', `${taxrate.name} ${timestamp}`);
    cy.log(`Taxrate - validFrom (validFrom=${taxrate.validFrom}{enter})`);
    //cy.writeIntoStringField('ValidFrom', `${taxrate.validFrom}{enter}`);
    cy.writeIntoStringField('ValidFrom', `${taxrate.validFrom}{enter}`, false, null, true);
    cy.log(`Taxrate - C_TaxCategory_ID (TaxCategory=${taxrate.taxCategory})`);
    cy.selectInListField('C_TaxCategory_ID', taxrate.taxCategory);
    cy.log(`Taxrate - Rate (Rate=${taxrate.rate})`);
    cy.get('.form-field-Rate')
      .find('input')
      .clear()
      .type('10{enter}');
    cy.log(`Taxrate - C_Country_ID (C_Country_ID=${taxrate.country})`);
    cy.selectInListField('C_Country_ID', 'none', false);
    cy.selectInListField('C_Country_ID', taxrate.country, false);
    cy.log(`Taxrate - To_Country_ID (To_Country_ID=${taxrate.countryTo})`);
    cy.selectInListField('To_Country_ID', taxrate.countryTo, false);
  });
}
