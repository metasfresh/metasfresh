import { getLanguageSpecific } from './utils';

export class Inventory {
  constructor() {
    this.warehouseName = ``;
    this.docTypeName = ``;
    this.inventoryLines = [];
    this.windowId = '168';
  }

  setWarehouse(warehouseName) {
    cy.log(`Set warehouse name = ${warehouseName}`);
    this.warehouseName = warehouseName;
    return this;
  }

  setDocType(docTypeName) {
    cy.log(`Set document type name = ${docTypeName}`);
    this.docTypeName = docTypeName;
    return this;
  }

  addInventoryLine(inventoryLine) {
    cy.log(`added inventory line: ${inventoryLine}`);
    this.inventoryLines.push(inventoryLine);
    return this;
  }

  apply() {
    cy.log(`Inventory: apply START`);
    Inventory.applyInventory(this);
    cy.log(`Inventory: apply STOP`);
  }

  static applyInventory(newInventory) {
    cy.visitWindow(newInventory.windowId, 'NEW', 'newInventoryRecord');
    cy.log(`Change C_DocType_ID to ${newInventory.docTypeName}`);

    //setting the document type
    cy.getStringFieldValue('C_DocType_ID').then(currentDocTypeName => {
      if (currentDocTypeName !== newInventory.docTypeName) {
        cy.log(`Change C_DocType_ID from ${currentDocTypeName} to ${newInventory.docTypeName}`);
        cy.selectInListField('C_DocType_ID', newInventory.docTypeName, false, null, true);
      }
    });

    //setting warehouse
    cy.selectInListField('M_Warehouse_ID', newInventory.warehouseName);

    //inventory line apply section begins
    newInventory.inventoryLines.forEach(line => {
      Inventory.applyInventoryLine(line);
    });

    // completing the document
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, 'docActionComplete'),
        getLanguageSpecific(miscDictionary, 'docStatusCompleted')
      );
    });

    // HU field verification (shown for SingleHU, not shown for MultipleHU)
    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      if (this.docTypeName == getLanguageSpecific(inventoryJson, 'aggregatedHUsInventoryDocTypeName')) {
        cy.selectTab('M_InventoryLine');
        cy.selectSingleTabRow();
        cy.openAdvancedEdit();
        cy.assertFieldNotShown('M_HU_ID', true /*modal*/);
        cy.pressDoneButton();
      } else if (this.docTypeName == getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName')) {
        cy.selectTab('M_InventoryLine');
        cy.selectSingleTabRow();
        cy.openAdvancedEdit();
        cy.getStringFieldValue('M_HU_ID', true /*modal*/) /// <<====
          .then(huValue => {
            expect(huValue).to.be.not.null;
          });
        cy.pressDoneButton();
      }
    });
  }

  static applyInventoryLine(inventoryLine) {
    cy.selectTab('M_InventoryLine');
    cy.pressAddNewButton();

    //HUAggregationType verification
    cy.getStringFieldValue('HUAggregationType', true /*modal*/).then(huAggregationType => {
      cy.fixture('inventory/inventory.json').then(inventoryJson => {
        if (this.docTypeName == getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName'))
          expect(huAggregationType).to.eq('Single HU');
        /// <<====
        else if (this.docTypeName == getLanguageSpecific(inventoryJson, 'aggregatedHUsInventoryDocTypeName'))
          expect(huAggregationType).to.eq('Multiple HUs');
      });
    });

    //setting the line parameters
    cy.writeIntoLookupListField(
      'M_Product_ID',
      inventoryLine.productName,
      inventoryLine.productName,
      false /*typeList*/,
      true /*modal*/
    );
    if (inventoryLine.uomName) {
      cy.writeIntoLookupListField(
        'C_UOM_ID',
        inventoryLine.uomName,
        inventoryLine.uomName,
        false /*typeList*/,
        true /*modal*/
      );
    }
    cy.writeIntoLookupListField(
      'M_Locator_ID',
      inventoryLine.M_Locator_ID,
      inventoryLine.M_Locator_ID,
      true /*typeList*/,
      true /*modal*/
    );
  
    cy.writeIntoStringField('QtyCount', inventoryLine.quantity, true /*modal*/);
    cy.clickOnCheckBox('IsCounted', inventoryLine.isCounted, true /*modal*/);
    cy.pressDoneButton();
  }
}

export class InventoryLine {
  constructor() {
    this.productName = ``;
    this.quantity = ``;
    this.uomName = ``;
    this.M_Locator_ID = ``;
    this.isCounted = false;
  }

  setProductName(productName) {
    this.productName = productName;
    return this;
  }

  setQuantity(quantity) {
    this.quantity = quantity;
    return this;
  }

  setC_UOM_ID(uomName) {
    this.uomName = uomName;
    return this;
  }

  setM_Locator_ID(M_Locator_ID) {
    this.M_Locator_ID = M_Locator_ID;
    return this;
  }

  setIsCounted(isCounted) {
    this.isCounted = isCounted;
    return this;
  }
}
