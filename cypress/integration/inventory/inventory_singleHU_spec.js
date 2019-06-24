import { inventory } from '../../page_objects/inventory';

import { Product } from '../../support/utils/product';

import { getLanguageSpecific } from '../../support/utils/utils';
//import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('Aggregated inventory test', function() {
  const timestamp = new Date().getTime();
  const productName = `SingleHUInventory ${timestamp}`;
  const productValue = `${timestamp}`;

  before(function() {
    // setSingleHUsDocTypeAsDefault();
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .apply();
    });
  });

  it('Create a new single-HU inventory doc', function() {
    cy.visitWindow(inventory.windowId, 'NEW', 'newInventoryRecord');

    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      cy.getStringFieldValue('C_DocType_ID').then(docTypeName => {
        expect(docTypeName).to.eq(getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName')); /// <<====
      });

      // set warehouse by its language-specific name
      cy.selectInListField('M_Warehouse_ID', getLanguageSpecific(inventoryJson, 'stdWarehouseName'));
    });

    cy.selectTab('M_InventoryLine');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false /*typeList*/, true /*modal*/);
    cy.fixture('product/simple_product.json').then(productJson => {
      const uomName = getLanguageSpecific(productJson, 'c_uom');
      cy.writeIntoLookupListField('C_UOM_ID', uomName, uomName, false /*typeList*/, true /*modal*/);
    });
    cy.writeIntoLookupListField('M_Locator_ID', '0_0_0', '0_0_0', true /*typeList*/, true /*modal*/);
    cy.writeIntoStringField('QtyCount', '20');
    cy.clickOnCheckBox('IsCounted');

    cy.getStringFieldValue('HUAggregationType').then(huAggregationType => {
      expect(huAggregationType).to.eq('Single HU'); /// <<====
    });
    cy.pressDoneButton();

    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.processDocument(
        getLanguageSpecific(miscDictionary, 'docActionComplete'),
        getLanguageSpecific(miscDictionary, 'docStatusCompleted')
      );
    });

    cy.selectTab('M_InventoryLine');
    cy.get('table tbody tr')
      .should('have.length', 1)
      .eq(0)
      .click();
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_HU_ID', true /*modal*/) /// <<====
      .then(huValue => {
        expect(huValue).to.be.not.null;
      });
    cy.pressDoneButton();

    cy.get('@newInventoryRecord').then(newInventoryRecord => {
      inventory.toMatchSnapshots(newInventoryRecord, 'inventory_singleHU');
    });
  });
});
