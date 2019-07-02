import { getLanguageSpecific } from '../../support/utils/utils';

export class NewInventory {
    constructor(){
        this.warehouseName = ``;
        this.docTypeName = ``;
        this.inventoryLines = [];
        this.windowId = `168`;
    }

    setWarehouse(warehouseName){
        cy.log(`Set warehouse name = ${warehouseName}`);
        this.warehouseName = warehouseName;
        return this;
    }

    setDocType(docTypeName){
        cy.log(`Set document type name = ${docTypeName}`);
        this.docTypeName = docTypeName;
        return this;
    }

    addInventoryLine(inventoryLine){
        cy.log(`added inventory line: ${inventoryLine}`);
        this.inventoryLines.push(inventoryLine);
        return this;
    }

    apply(){
        cy.log(`Inventory: apply START`);
        NewInventory.applyInventory(this);
        cy.log(`Inventory: apply STOP`);
    }

    static applyInventory(newInventory){
            cy.visitWindow(newInventory.windowId, 'NEW', 'newInventoryRecord');
            cy.log(`Change C_DocType_ID to ${newInventory.docTypeName}`);
            cy.resetListValue('C_DocType_ID');
            cy.wait(1000);
            cy.selectInListField('C_DocType_ID', newInventory.docTypeName, false, null, true);
            cy.selectInListField('M_Warehouse_ID', newInventory.warehouseName);
            newInventory.inventoryLines.forEach(line => {
                NewInventory.applyInventoryLine(line);
              });
            cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
                cy.processDocument(
                    getLanguageSpecific(miscDictionary, 'docActionComplete'),
                    getLanguageSpecific(miscDictionary, 'docStatusCompleted')
                );
            });
    }

    static applyInventoryLine(inventoryLine){
        cy.selectTab('M_InventoryLine');
            cy.pressAddNewButton();
            cy.getStringFieldValue('HUAggregationType', true /*modal*/).then(huAggregationType => {
                expect(huAggregationType).to.eq('Single HU'); /// <<====
            });

            cy.writeIntoLookupListField('M_Product_ID', inventoryLine.productName, inventoryLine.productName, false /*typeList*/, true /*modal*/);
            cy.writeIntoLookupListField('C_UOM_ID', inventoryLine.uomName, inventoryLine.uomName, false /*typeList*/, true /*modal*/);
            cy.writeIntoLookupListField('M_Locator_ID', inventoryLine.M_Locator_ID, inventoryLine.M_Locator_ID, true /*typeList*/, true /*modal*/);
            cy.writeIntoStringField('QtyCount', inventoryLine.quantity, true /*modal*/);
            cy.clickOnCheckBox('IsCounted', inventoryLine.isCounted, true /*modal*/);
            cy.pressDoneButton();
    }
}

export class NewInventoryLine{
    constructor(){
        this.productName = ``;
        this.quantity = ``;
        this.uomName = ``;
        this.M_Locator_ID = ``;
        this.isCounted = false;
    }

    setProductName(productName){
        this.productName = productName;
        return this;
    }

    setQuantity(quantity){
        this.quantity = quantity;
        return this;
    }

    setC_UOM_ID(uomName){
        this.uomName = uomName;
        return this;
    }

    setM_Locator_ID(M_Locator_ID){
        this.M_Locator_ID = M_Locator_ID;
        return this;
    }

    setIsCounted(isCounted){
        this.isCounted = isCounted;
        return this;
    }
}