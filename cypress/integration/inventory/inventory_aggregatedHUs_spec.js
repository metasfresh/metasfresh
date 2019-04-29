import { inventory } from '../../page_objects/inventory';
import { doctypes } from '../../page_objects/doctypes';
import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

import { Product } from '../../support/utils/product';

describe('Aggregated inventory test', function() {
  it('Makes sure that the inventory doc type for aggregated HUs exists and is default', function() {
    doctypes.visit();

    doctypes.getRows().should('not.have.length', 0); // this is more of a smoke test

    //toggleNotFrequentFilters();
    //selectNotFrequentFilterWidget('default'); //.click();
    //cy.selectInListField('DocBaseType', 'Inventory');
    //applyFilters();
  });

  const timestamp = new Date().getTime();
  const productName = `InventoryProduct ${timestamp}`;
  const productValue = `${timestamp}`;
  before(function() {
    setAggregatedHUsDocTypeAsDefault();

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        //.setProductCategory(productCategoryValue + '_' + productCategoryName)
        .apply();
    });
  });

  it('Create a new aggreagted-HUs inventory doc', function() {
    cy.visitWindow(inventory.windowId, 'NEW', 'newInventoryRecord');

    cy.getFieldValue('C_DocType_ID').then(docTypeName => {
      expect(docTypeName).to.eq('Inventur mit aggregierten Materialangaben');
    });

    cy.selectInListField('M_Warehouse_ID', 'StdWarehouse');

    cy.selectTab('M_InventoryLine');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false /*typeList*/, true /*modal*/);
    cy.writeIntoLookupListField('M_Locator_ID', '0_0_0', '0_0_0', true /*typeList*/, true /*modal*/);
    cy.writeIntoStringField('QtyCount', '20');
    cy.clickOnCheckBox('IsCounted');
    cy.getFieldValue('HUAggregationType').then(huAggregationType => {
      expect(huAggregationType).to.eq('Multiple HUs');
    });
    cy.pressDoneButton();

    cy.processDocument('Complete', 'Completed');

    cy.selectTab('M_InventoryLine');
    cy.get('table tbody tr')
      .should('have.length', 1)
      .eq(0)
      .click();
    cy.openAdvancedEdit();
    cy.assertFieldNotShown('M_HU_ID', true /*modal*/);
    cy.pressDoneButton();

    cy.get('@newInventoryRecord').then(newInventoryRecord => {
      inventory.toMatchSnapshots(newInventoryRecord, 'inventory_aggregatedHUs');
    });
  });
});

function setAggregatedHUsDocTypeAsDefault() {
  cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithSingleHU} is *not* default`);
  doctypes.visit(inventory.docTypeInventoryWithSingleHU);
  cy.isChecked('IsDefault').then(isDefaultValue => {
    cy.log(`isDefaultValue=${isDefaultValue}`);
    cy.pause();
    if (isDefaultValue) {
      cy.clickOnCheckBox('IsDefault');
    }
  });
  cy.log(`Make sure that C_DocType_ID=${inventory.docTypeInventoryWithMultipleHUs} is default`);
  doctypes.visit(inventory.docTypeInventoryWithMultipleHUs);
  cy.isChecked('IsDefault').then(isDefaultValue => {
    cy.log(`isDefaultValue=${isDefaultValue}`);
    if (!isDefaultValue) {
      cy.clickOnCheckBox('IsDefault');
    }
  });
}
