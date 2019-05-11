export class PriceListVersion {
  constructor(name) {
    this.Name = name;
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
    const timestamp = new Date().getTime();

    cy.visitWindow('540321', ???);
    cy.selectTab('M_PriceList_Version');
      cy.pressAddNewButton();
      cy.writeIntoStringField('Name', `{selectall}{backspace}${pricelistversion.name} ${pricelistversion.ValidFrom}`);
    cy.writeIntoStringField('ValidFrom', `${pricelistversion.ValidFrom}{enter}`, false, null, false);
    cy.isChecked('IsActive').then(isActive => {
      if (pricelistversion.IsActive && !isActive) {
        cy.clickOnCheckBox('IsIsActive');
      }
    });
    cy.pressDoneButton();
  });
}