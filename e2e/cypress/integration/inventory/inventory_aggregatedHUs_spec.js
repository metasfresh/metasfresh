import { Product } from '../../support/utils/product';

import { Inventory, InventoryLine } from '../../support/utils/inventory';

import { appendHumanReadableNow, getLanguageSpecific } from '../../support/utils/utils';

describe('Aggregated-HUs inventory test', function() {
  let productName;
  let locatorId;
  let isCounted;
  let quantity;

  // static
  let docTypeKey = 'aggregatedHUsInventoryDocTypeName';

  it('Read the fixture', function() {
    cy.fixture('inventory/inventory_aggregatedHUs_spec.json').then(f => {
      productName = appendHumanReadableNow(f['productName']);
      locatorId = f['locatorId'];
      isCounted = f['isCounted'];
      quantity = f['quantity'];
    });
  });

  it('Create test data', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .apply();
    });
  });

  it('Create a new single-HU inventory doc', function() {
    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      const docTypeName = getLanguageSpecific(inventoryJson, docTypeKey);

      const inventoryLine = new InventoryLine()
        .setProductName(productName)
        .setQuantity(quantity)
        .setM_Locator_ID(locatorId)
        .setIsCounted(isCounted);

      new Inventory()
        .setWarehouse(inventoryJson.warehouseName)
        .setDocType(docTypeName)
        .addInventoryLine(inventoryLine)
        .apply();
    });
  });
});
