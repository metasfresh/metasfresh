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
    this.c_currency_ID = ID;
    return this;
  }

  apply() {
    cy.log(`Currency - apply - START (isoCode=${this.isoCode})`);
    cy.visitWindow('115', `${this.c_currency_ID}`);
    cy.getCheckboxValue('IsActive').then(isActive => {
      if (!isActive) {
        cy.clickOnIsActive();
      }
    });
    cy.log(`Currency - apply - END (isoCode=${this.isoCode})`);
    return this;
  }
}
