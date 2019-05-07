export class Currency {
  setIsoCode(isoCode) {
    cy.log(`Currency - set iso code = ${isoCode}`);
    this.isoCode = isoCode;
    return this;
  }

  setActive(isActive) {
    cy.log(`Currency - set isActive = ${isActive}`);
    this.isActive = isActive;
    return this;
  }

  setCurrencyID(ID) {
    cy.log(`Currency - set C_Currency_ID = ${ID}`);
    this.C_Currency_ID = ID;
    return this;
  }

  activate() {
    cy.log(`Currency - activate - START (isoCode=${this.isoCode})`);
    cy.visitWindow('115', this.ID);
    cy.isChecked('IsActive').then(isActive => {
      if (this.isActive && !isActive) {
        cy.clickOnCheckBox('IsActive');
      }
    });
    cy.log(`Currency - activate - END (isoCode=${this.isoCode})`);
    return this;
  }

  inactivate() {
    cy.log(`Currency - inactivate - START (isoCode=${this.isoCode})`);
    cy.visitWindow('115', this.ID);
    cy.isChecked('IsActive').then(isActive => {
      if (this.isActive && isActive) {
        cy.clickOnCheckBox('IsActive');
      }
    });
    cy.log(`Currency - inactivate - END (isoCode=${this.isoCode})`);
    return this;
  }
}
