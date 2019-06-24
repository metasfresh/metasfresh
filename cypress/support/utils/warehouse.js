import { getLanguageSpecific } from './utils';

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
  describe(`Create new Warehouse ${Warehouse.name}`, function() {
    cy.visitWindow('139', 'NEW')
      .writeIntoStringField('Name', Warehouse.Name)
      .clearField('Value')
      .writeIntoStringField('Value', Warehouse.Value);
    cy.selectNthInListField('C_BPartner_Location_ID', 1, false);

    cy.writeIntoStringField('Description', Warehouse.description);

    cy.getFieldValue('IsSOWarehouse').then(IsSOWarehouseValue => {
      if (Warehouse.isSOWarehouse && !IsSOWarehouseValue) {
        cy.clickOnCheckBox('IsSOWarehouse');
      }
    });
    cy.getFieldValue('IsPOWarehouse').then(IsPOWarehouseValue => {
      if (Warehouse.isPOWarehouse && !IsPOWarehouseValue) {
        cy.clickOnCheckBox('IsPOWarehouse');
      }
    });
  });
} 

export class Locator {
    constructor() {
      cy.log(`Locator - set M_Locator= ${M_Locator}`);
      this.M_Locator = M_Locator;
    }
      
  setLocator(M_Locator) {
    cy.log(`Locator - set M_Locator= ${M_Locator}`);
    this.M_Locator = M_Locator;
    return this;
  }

  apply() {
    cy.log(`Locator - apply - START (name=${this.M_Locator})`);
    applyLocator(this);
    cy.log(`Locator - apply - END (name=${this.M_Locator})`);
    return this;
  }
}

function applyLocator(Locator) {
    describe(`Create new Locator ${Warehouse.M_Locator}`, function() {
      cy.get(`#tab_M_Locator`).click();
      cy.pressAddNewButton()
        .writeIntoStringField('X', '0')
        .writeIntoStringField('X1', '0')
        .writeIntoStringField('Z', '0')
        .writeIntoStringField('Y', '0')
        .pressDoneButton();
    }  
}

export class Routing {
    constructor(name) {
      cy.log(`Routing - set name = ${name}`);
      this.name = name;
    }
    
    setRouting(M_Warehouse_Routing) {
        cy.log(`Routing - set M_Warehouse_Routing= ${M_Warehouse_Routing}`);
        this.M_Warehouse_Routing = M_Warehouse_Routing;
        return this;
    }
    
  apply() {
    cy.log(`Routing - apply - START (name=${this.M_Warehouse_Routing})`);
    applyLocator(this);
    cy.log(`Routing - apply - END (name=${this.M_Warehouse_Routing})`);
    return this;
  }
}
    
function applyWarehouseRouting(Routing) {
    describe(`Create new Routing ${Warehouse.Warehouse_Routing}`, function() {
      const DocBaseType = getLanguageSpecific(applyWarehouseRouting, 'DocBaseType');
      cy.get(`#tab_M_Warehouse_Routing`).click();
      cy.pressAddNewButton()
        .selectInListField('DocBaseType', 'Distribution Order', true)
        .selectInListField('DocBaseType', 'Sales Order', true)
        .selectInListField('DocBaseType', 'Purchase Order', true)
        .selectInListField('DocBaseType', 'Material Receipt', true)
        .pressDoneButton();
    }
}