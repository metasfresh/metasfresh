export class Warehouse {
  constructor(name) {
    cy.log(`Warehouse - set name = ${name}`);
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

function applyWarehouse(Warehouse) {
  cy.visitWindow('139', 'NEW')
    .writeIntoStringField('Name', Warehouse.Name)
    .clearField('Value')
    .writeIntoStringField('Value', Warehouse.Value);
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
    .writeIntoStringField('Value', locator.value, true)
    .writeIntoStringField('X', locator.x1)
    .writeIntoStringField('X1', locator.x1)
    .writeIntoStringField('Z', locator.z)
    .writeIntoStringField('Y', locator.y)
    .setCheckBoxValue('IsAfterPickingLocator', locator.IsAfterPickingLocator, true)
    .pressDoneButton();
}
function applyRoutes(routes) {
  cy.get(`#tab_M_Warehouse_Routing`).click();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docbasetype, true)
    .pressDoneButton();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docbasetype, true)
    .pressDoneButton();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docbasetype, true)
    .pressDoneButton();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docbasetype, true)
    .pressDoneButton();
  cy.pressAddNewButton()
    .selectInListField('DocBaseType', routes.docbasetype, true)
    .pressDoneButton();
}
