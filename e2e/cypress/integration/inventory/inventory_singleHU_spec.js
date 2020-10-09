import { Product } from '../../support/utils/product';

import { Inventory, InventoryLine } from '../../support/utils/inventory';

import { appendHumanReadableNow, getLanguageSpecific } from '../../support/utils/utils';

let productName;
let productQty;
let locatorId;
let isCounted;
let inventoryDoctypeKey;

describe('Create a single HU', function() {
  it('Read the fixture', function() {
    cy.fixture('inventory/inventory_singleHU_spec.json').then(f => {
      productName = appendHumanReadableNow(f['productName']);
      productQty = f['productQty'];
      locatorId = f['locatorId'];
      isCounted = f['isCounted'];
      inventoryDoctypeKey = f['inventoryDoctypeKey'];
    });
  });

  it('Create Product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .apply();
    });
  });

  it('Create a new single-HU inventory doc', function() {
    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      const docTypeName = getLanguageSpecific(inventoryJson, inventoryDoctypeKey);

      const inventoryLine = new InventoryLine()
        .setProductName(productName)
        .setQuantity(productQty)
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
