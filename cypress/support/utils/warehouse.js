import { getLanguageSpecific } from './utils';

export class Warehouse {
  constructor(name) {
    cy.log(`Warehouse - set name = ${name}`);
    this.name = name;
    this.warehouseLocator = [];
    this.warehouseRouting = [];
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

  setLocator(warehouseLocator) {
    cy.log(`Warehouse - set locator = ${JSON.stringify(warehouseLocator)}`);
    this.warehouseLocator.push(warehouseLocator);
    return this;
  }

  setRouting(warehouseRouting) {
    cy.log(`Warehouse - set routing = ${JSON.stringify(warehouseRouting)}`);
    this.warehouseRouting.push(warehouseRouting);
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
  describe(`Create new Warehouse ${Warehouse.name}`, function() {
    cy.visitWindow('139', 'NEW');
    cy.writeIntoStringField('Name', Warehouse.name);
    cy.clearField('Value').writeIntoStringField('Value', Warehouse.value);
    cy.selectNthInListField('C_BPartner_Location_ID', 1, false);

    if (Warehouse.description) {
      cy.writeIntoStringField('Description', Warehouse.description);
    }

    if (Warehouse.isSOWarehouse) {
      cy.clickOnCheckBox('IsSOWarehouse');
    }

    if (Warehouse.isPOWarehouse) {
      cy.clickOnCheckBox('IsPOWarehouse');
    }

    if (Warehouse.warehouseLocator.length > 0) {
      Warehouse.warehouseLocator.forEach(function(warehouseLocator) {
        applywarehouseLocator(warehouseLocator);
      });
    }
    cy.get(`#tab_M_Locator`).click();
    cy.pressAddNewButton()
      .writeIntoStringField('X', '0')
      .writeIntoStringField('X1', '0')
      .writeIntoStringField('Z', '0')
      .writeIntoStringField('Y', '0')
      .pressDoneButton();

    if (Warehouse.warehouseRouting.length > 0) {
      Warehouse.warehouseRouting.forEach(function(routing) {
        applywarehouseRouting(routing);
      });
    }

    const DocBaseType = getLanguageSpecific(warehouseRouting, 'DocBaseType');
    cy.get(`#tab_M_Warehouse_Routing`).click();

    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Distribution Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Sales Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Purchase Order', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Material Receipt', true))
      .pressDoneButton();
    cy.pressAddNewButton()
      .selectInListField('DocBaseType', getLanguageSpecific(DocBaseType, 'Manufacturing Order', true))
      .pressDoneButton();
  });
}
