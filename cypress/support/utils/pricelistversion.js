export class PriceListVersion {
  constructor(name, plDocId) {
    this.Name = name;
    this.PriceListDocID = plDocId;
  }

  setName(name) {
    cy.log(`PriceListVersion - set Name = ${name}`);
    this.Name = name;
    return this;
  }

  setDescription(description) {
    cy.log(`PriceListVersion - set Description = ${description}`);
    this.Description = description;
    return this;
  }

  setActive(isActive) {
    cy.log(`PriceListVersion - set isActive = ${isActive}`);
    this.IsActive = isActive;
    return this;
  }

  setValidFrom(validFrom) {
    cy.log(`PriceListVersion - set ValidFrom = ${validFrom}`);
    this.ValidFrom = validFrom;
    return this;
  }

  apply() {
    cy.log(`PriceListVersion - apply - START (name=${this.name})`);
    applyPriceListVersion(this);
    cy.log(`PriceListVersion - apply - END (name=${this.name})`);
    return this;
  }
}

function applyPriceListVersion(pricelistversion) {
  describe(`Create new PriceListVersion ${pricelistversion.Name}`, function() {
    //cy.visitWindow('540321', pricelistversion.plDocId);
    cy.visitWindow('540321', '2008405');
    cy.selectTab('M_PriceList_Version');
    cy.pressAddNewButton();
    cy.writeIntoStringField('Name', `{selectall}{backspace}`, true, null, true);
    cy.writeIntoStringField('Name', `${pricelistversion.name} ${pricelistversion.ValidFrom}`, true, null, true);
    cy.get('.form-field-ValidFrom')
      .find('input')
      .clear();
    cy.writeIntoStringField('ValidFrom', `${pricelistversion.ValidFrom}{enter}`, true, null, true);
    cy.pressDoneButton();
  });
}
