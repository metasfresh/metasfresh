import { RewriteURL } from './constants';
import { getLanguageSpecific } from './utils';

export class Warehouse {
  constructor() {
    this.locators = [];
    this.routes = [];
  }

  addLocator(warehouseLocator) {
    this.locators.push(warehouseLocator);
    return this;
  }

  addRoute(warehouseRoute) {
    this.routes.push(warehouseRoute);
    return this;
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

  setPlant(plant) {
    cy.log(`Warehouse - set plant = ${plant}`);
    this.plant = plant;
    return this;
  }

  setBPartnerLocation(C_BPartner_Location_ID) {
    cy.log(`BPartnerLocation - set C_BPartner_Location_ID=${C_BPartner_Location_ID}`);
    this.C_BPartner_Location_ID = C_BPartner_Location_ID;
    return this;
  }

  setIsQualityIssueWarehouse(isQualityIssueWarehouse) {
    cy.log(`Warehouse - set Quality Issue Warehouse= ${isQualityIssueWarehouse}`);
    this.isQualityIssueWarehouse = isQualityIssueWarehouse;
    return this;
  }

  setIsQualityReturnWarehouse(isQualityReturnWarehouse) {
    cy.log(`Warehouse - isQualityReturnWarehouse=${isQualityReturnWarehouse}`);
    this.isQualityReturnWarehouse = isQualityReturnWarehouse;
    return this;
  }

  apply() {
    cy.log(`Warehouse - apply - START (name=${this.name})`);
    applyWarehouse(this);
    cy.log(`Warehouse - apply - END (name=${this.name})`);
    return this;
  }
}

export class WarehouseLocator {
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

function applyWarehouse(warehouse) {
  cy.visitWindow('139', 'NEW');
  cy.writeIntoStringField('Name', warehouse.name);
  // .clearField('Value')
  cy.writeIntoStringField('Value', warehouse.value);

  if (warehouse.plant) {
    cy.selectInListField('PP_Plant_ID', warehouse.plant);
  }

  cy.selectNthInListField('C_BPartner_Location_ID', 1);
  if (warehouse.isQualityIssueWarehouse) {
    cy.setCheckBoxValue('IsIssueWarehouse', warehouse.isQualityIssueWarehouse);
  }
  if (warehouse.isQualityReturnWarehouse) {
    cy.setCheckBoxValue('IsQualityReturnWarehouse', warehouse.isQualityReturnWarehouse);
  }

  warehouse.locators.forEach(locator => {
    applyLocator(locator);
  });
  warehouse.routes.forEach(route => {
    applyRoute(route);
  });
}

function applyLocator(locator) {
  cy.selectTab('M_Locator');
  cy.pressAddNewButton();
  cy.writeIntoStringField('X', locator.x, true, null, true);
  cy.writeIntoStringField('X1', locator.x1, true, null, true);
  cy.writeIntoStringField('Z', locator.z, true, null, true);
  cy.writeIntoStringField('Y', locator.y, true, null, true);

  if (locator.value) {
    cy.clearField('Value', true);
    cy.writeIntoStringField('Value', locator.value, true, null, true);
  }
  if (locator.isAfterPickingLocator) {
    cy.setCheckBoxValue('IsAfterPickingLocator', locator.isAfterPickingLocator, true, RewriteURL.PROCESS);
  }
  cy.pressDoneButton();
}

function applyRoute(route) {
  cy.selectTab(`M_Warehouse_Routing`);
  cy.pressAddNewButton();
  cy.selectInListField('DocBaseType', getLanguageSpecific(route, 'docBaseType'), true);
  cy.pressDoneButton();
}
