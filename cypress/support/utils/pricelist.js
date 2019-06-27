export class PriceList {
  constructor(name) {
    this.name = name;
    this.priceListVersions = [];
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

  addPriceListVersion(priceListVersion) {
    cy.log(`PriceList - add priceListVersion = ${priceListVersion}`);
    this.priceListVersions.push(priceListVersion);
    return this;
  }

  apply() {
    cy.log(`PriceList - apply - START (name=${this.name})`);
    PriceList.applyPriceList(this);
    cy.log(`PriceList - apply - END (name=${this.name})`);
    return this;
  }


  static applyPriceList(priceList) {
    describe(`Create new PriceList ${priceList.name}`, function () {
      cy.visitWindow('540321', 'NEW', 'priceListObj');
      cy.writeIntoStringField('Name', priceList.name);
      cy.selectInListField('C_Country_ID', priceList.country);
      cy.selectInListField('C_Currency_ID', priceList.currency);
      cy.get('.form-field-PricePrecision')
        .find('input')
        .clear()
        .type(priceList.pricePrecision);
      cy.selectInListField('M_PricingSystem_ID', priceList.priceSystem);
      cy.writeIntoStringField('Description', priceList.description);


      priceList.priceListVersions.forEach(version => {
        PriceList.applyPriceListVersion(version);
      });
    });
  }


  static applyPriceListVersion(priceListVersion) {
    describe(`Create new PriceListVersion ${priceListVersion.name}`, function () {
      cy.selectTab('M_PriceList_Version');
      cy.pressAddNewButton();
      cy.writeIntoStringField('Name', `${priceListVersion.name} ${priceListVersion.validFrom}`, true, null, true);
      cy.writeIntoStringField('ValidFrom', `${priceListVersion.validFrom}{enter}`, true, null, true);
      cy.pressDoneButton();
    });
  }
}

export class PriceListVersion {
  constructor(name, plDocId) {
    this.name = name;
  }

  setName(name) {
    cy.log(`PriceListVersion - set n = ${name}`);
    this.name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`PriceListVersion - set Description = ${description}`);
    this.description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`PriceListVersion - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`PriceListVersion - set ValidFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }
}
