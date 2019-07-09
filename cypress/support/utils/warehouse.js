import {RewriteURL} from "./constants";

export class Warehouse {
  constructor(name) {
    cy.log(`Create Warehouse - name = ${name}`);
    this.name = name;
    this.locators = [];
    this.routes = [];
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

export  class WarehouseLocator {
  setX(x) {
    cy.log(`WarehouseLocator - set x= ${x}`);
    this.x = x;
    return this;
  }

  setX1(x1) {
    cy.log(`WarehouseLocator - set x1= ${x1}`);
    this.x1 = x1;
    return this;
  }

  setY(y) {
    cy.log(`WarehouseLocator - set y= ${y}`);
    this.y = y;
    return this;
  }

  setZ(z) {
    cy.log(`WarehouseLocator - set z= ${z}`);
    this.z = z;
    return this;
  }

  setValue(value) {
    cy.log(`WarehouseLocator - set value= ${value}`);
    this.value = value;
    return this;
  }
  setIsAfterPickingLocator(isAfterPickingLocator) {
    cy.log(`WarehouseLocator - set isAfterPickingLocator= ${isAfterPickingLocator}`);
    this.isAfterPickingLocator = isAfterPickingLocator;
    return this;
  }
}

export class WarehouseRoute {
  setDocBaseType(docBaseType) {
    cy.log(`WarehouseRoute - set docBaseType= ${docBaseType}`);
    this.docBaseType = docBaseType;
    return this;
  }
}


function applyWarehouse(Warehouse) {
  cy.visitWindow('139', 'NEW')
    .writeIntoStringField('Name', Warehouse.name)
    .clearField('Value')
    .writeIntoStringField('Value', Warehouse.value);
  cy.selectNthInListField('C_BPartner_Location_ID', 1, false);
  Warehouse.locators.forEach(locator => {
    applyLocator(locator);
    Warehouse.routes.forEach(routes => {
      applyRoutes(routes);
    });
  });
}

function applyLocator(locator) {
  cy.get(`#tab_M_Locator`).click();
  cy.pressAddNewButton()
    .clearField('Value', true)
    .writeIntoStringField('Value', locator.value, true, null, true)
    .writeIntoStringField('X', locator.x1, true, null, true)
    .writeIntoStringField('X1', locator.x1, true, null, true)
    .writeIntoStringField('Z', locator.z, true, null, true)
    .writeIntoStringField('Y', locator.y, true, null, true)
    .setCheckBoxValue('IsAfterPickingLocator', locator.isAfterPickingLocator, true, RewriteURL.PROCESS)
    .pressDoneButton();
}

function applyRoutes(routes) {
  cy.get(`#tab_M_Warehouse_Routing`).click();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docBaseType, true) // note: the way it's implemented now, there's no de_DE support!
    .pressDoneButton();
}
