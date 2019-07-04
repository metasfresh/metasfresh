/* eslint-disable no-unused-vars */
/* eslint-disable no-undef */
export class Warehouse {
  constructor(name) {
    cy.log(`Warehouse - set name = ${name}`);
    this.name = name;
  }

  setName(name) {
    cy.log(`Warehouse - set name = ${name}`);
    this.name = name;
    return this;
  }

  setValue(value) {
    cy.log(`Warehouse - set value = ${value}`);
    this.value = value;
    return this;
  }

  setDescription(description) {
    cy.log(`Warehouse - set description = ${description}`);
    this.description = description;
    return this;
  }

  setSOWarehouse(isSOWarehouse) {
    cy.log(`Warehouse - set isSOWarehouse = ${isSOWarehouse}`);
    this.isSOWarehouse = isSOWarehouse;
    return this;
  }

  setPOWarehouse(isPOWarehouse) {
    cy.log(`Warehouse - set isPOWarehouse = ${isPOWarehouse}`);
    this.isPOWarehouse = isPOWarehouse;
    return this;
  }

  setBPartnerLocation(C_BPartner_Location_ID) {
    cy.log(`BPartnerLocation - set C_BPartner_Location_ID= ${C_BPartner_Location_ID}`);
    this.C_BPartner_Location_ID = C_BPartner_Location_ID;
    return this;
  }

  apply() {
    cy.log(`Warehouse - apply - START (name=${this.name})`);
    applyWarehouse(this);
    cy.log(`Warehouse - apply - END (name=${this.name})`);
    return this;
  }
}

function applyWarehouse(Warehouse) {
  const timestamp = new Date().getTime();

  describe(`Create new Warehouse ${Warehouse.name}`, function() {
    cy.visitWindow('139', 'NEW');
    cy.writeIntoStringField('Name', `${Warehouse.name}${timestamp}`);
    cy.clearField('Value').writeIntoStringField('Value', Warehouse.value);
    cy.selectNthInListField('C_BPartner_Location_ID', 1, false);

    if (Warehouse.isSOWarehouse && !isSOWarehouseValue) {
      cy.getFieldValue('isSOWarehouse').then(isSOWarehouseValue => {
        cy.clickOnCheckBox('isSOWarehouse');
      });
    }

    if (Warehouse.isPOWarehouse && !isPOWarehouseValue) {
      cy.getFieldValue('isPOWarehouse').then(isPOWarehouseValue => {
        cy.clickOnCheckBox('isPOWarehouse');
      });
    }
  });
}
