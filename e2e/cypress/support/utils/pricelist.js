export class PriceList {
  constructor() {
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

  setIsSalesPriceList(isSalesPriceList) {
    cy.log(`PriceList - set isSalesPriceList = ${isSalesPriceList}`);
    this.isSalesPriceList = isSalesPriceList;
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
    describe(`Create new PriceList ${priceList.name}`, function() {
      cy.visitWindow('540321', 'NEW', 'priceListObj');
      cy.writeIntoStringField('Name', priceList.name);
      cy.writeIntoLookupListField('C_Country_ID', priceList.country, priceList.country, false, false, null, true);
      cy.resetListValue('C_Currency_ID');
      cy.selectInListField('C_Currency_ID', priceList.currency);
      cy.writeIntoStringField('PricePrecision', priceList.pricePrecision);
      cy.writeIntoLookupListField('M_PricingSystem_ID', priceList.priceSystem, priceList.priceSystem);
      cy.writeIntoStringField('Description', priceList.description);
      cy.setCheckBoxValue('IsSOPriceList', priceList.isSalesPriceList);

      priceList.priceListVersions.forEach(version => {
        let debugName = `${priceList.name} - ${version.validFrom}`;
        PriceList.applyPriceListVersion(version, debugName);
      });
    });
  }

  static applyPriceListVersion(priceListVersion, debugName) {
    describe(`Create new PriceListVersion ${debugName}`, function() {
      cy.selectTab('M_PriceList_Version');
      cy.pressAddNewButton();
      cy.writeIntoStringField('ValidFrom', priceListVersion.validFrom, true, null, true);
      if (priceListVersion.discountSchema) {
        cy.selectInListField('M_DiscountSchema_ID', priceListVersion.discountSchema, true);
      }
      if (priceListVersion.basisPriceListVersion) {
        cy.selectInListField('M_Pricelist_Version_Base_ID', priceListVersion.basisPriceListVersion, true);
      }
      cy.pressDoneButton();
    });
  }
}

export class PriceListVersion {
  setDescription(description) {
    cy.log(`PriceListVersion - set Description = ${description}`);
    this.description = description;
    return this;
  }

  setBasisPriceListVersion(basisPriceListVersion) {
    cy.log(`PriceListVersion - set basisPriceListVersion = ${basisPriceListVersion}`);
    this.basisPriceListVersion = basisPriceListVersion;
    return this;
  }

  setDiscountSchema(discountSchema) {
    cy.log(`PriceListVersion - set discountSchema = ${discountSchema}`);
    this.discountSchema = discountSchema;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`PriceListVersion - set ValidFrom = ${validFrom}`);
    this.validFrom = validFrom;
    return this;
  }
}
