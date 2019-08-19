import { Product } from '../../support/utils/product';

import { Inventory, InventoryLine } from '../../support/utils/inventory';

import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';

describe('Aggregated-HUs inventory test', function() {
  const date = humanReadableNow();
  const productName = `AggregatedHUsInventory ${date}`;
  const productValue = `${date}`;

  before(function() {
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

        const inventoryLine = new InventoryLine()
          .setProductName(productName)
          .setQuantity(`20`)
          .setC_UOM_ID(uomName)
          .setM_Locator_ID('0_0_0')
          .setIsCounted(true);

        new Inventory()
          .setWarehouse(inventoryJson.warehouseName)
          .setDocType(docTypeName)
          .addInventoryLine(inventoryLine)
          .apply();
      });
    });
  });

  //check if snapshots match
  /*
  it('Check snapshots', function() {
    cy.get('@newInventoryRecord').then(newInventoryRecord => {
      inventory.toMatchSnapshots(newInventoryRecord, 'inventory_aggregatedHUs');
    });
  });
  */
});
