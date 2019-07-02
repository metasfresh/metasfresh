import { inventory } from '../../page_objects/inventory';

import { Product } from '../../support/utils/product';

import { NewInventory, NewInventoryLine } from '../../support/utils/create_inventory';

import { getLanguageSpecific } from '../../support/utils/utils';
//import { toggleNotFrequentFilters, selectNotFrequentFilterWidget, applyFilters } from '../../support/functions';

describe('Aggregated inventory test', function() {
  const timestamp = new Date().getTime();
  const productName = `AggregatedHUsInventory ${timestamp}`;
  const productValue = `${timestamp}`;

  before(function() {
    cy.wait(1000); // see comment/doc of getLanguageSpecific
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productValue)
        .apply();
    });
  });
  
  it('Create a new single-HU inventory doc', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      const uomName = getLanguageSpecific(productJson, 'c_uom');
      cy.fixture('inventory/inventory.json').then(inventoryJson => {
        const docTypeName = getLanguageSpecific(inventoryJson, `aggregatedHUsInventoryDocTypeName`);
        var inventoryLine = new NewInventoryLine()
            .setProductName(productName)
            .setQuantity(`20`)
            .setC_UOM_ID(uomName)
            .setM_Locator_ID('0_0_0')
            .setIsCounted(true);

        var newInventory = new NewInventory()
          .setWarehouse(inventoryJson.warehouseName)
          .setDocType(docTypeName)
          .addInventoryLine(inventoryLine)
          .apply();
      });
    });
  });
  
    
    //check if snapshots match

  it('Check snapshots', function() {
    cy.get('@newInventoryRecord').then(newInventoryRecord => {
      inventory.toMatchSnapshots(newInventoryRecord, 'inventory_aggregatedHUs');
    });
  });
});



