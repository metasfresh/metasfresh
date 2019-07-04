/* eslint-disable no-undef */
export class WarehouseLocator {
  constructor(value) {
    this.value = value;
  }

  setValue(value) {
    cy.log(`WarehouseLocator - set value = ${value}`);
    this.value = value;
    return this;
  }

  setX(X) {
    cy.log(`WarehouseLocator - set X = ${X}`);
    this.X = X;
    return this;
  }

  setX1(X1) {
    cy.log(`WarehouseLocator - set X1 = ${X1}`);
    this.X1 = X1;
    return this;
  }

  setZ(Z) {
    cy.log(`WarehouseLocator - set Z = ${Z}`);
    this.Z = Z;
    return this;
  }

  setY(Y) {
    cy.log(`WarehouseLocator - set Y = ${Y}`);
    this.Y = Y;
    return this;
  }

  apply() {
    cy.log(`Locator - apply - START (value=${this.value})`);
    applyLocator(this);
    cy.log(`Locator - apply - END (value=${this.value})`);
    return this;
  }
}
function applyLocator(Locator) {
  describe(`Create new Locator ${Locator.Locator}`, function() {
    cy.get(`#tab_M_Locator`).click();
    cy.pressAddNewButton()
      .clearField('Value', true)
      .writeIntoStringField('Value', warehouseValue, true)
      .writeIntoStringField('X', '0')
      .writeIntoStringField('X1', '0')
      .writeIntoStringField('Z', '0')
      .writeIntoStringField('Y', '0')
      .pressDoneButton();
  });
}
