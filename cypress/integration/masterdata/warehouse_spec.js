import { Warehouse } from '../../support/utils/warehouse';

describe('Create test: warehouse, https://github.com/metasfresh/metasfresh-e2e/issues/46', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const warehouseName = `TestWarehouseName ${timestamp}`;
  const warehouseValue = `TestWarehouseValue ${timestamp}`;
  const warehouseLocator = ;
  const warehouseRouting = 

  it('Create a new warehouse', function() {
    new Warehouse()
      .setName(warehouseName)
      .setValue(warehouseValue)
      .setLocator(warehouseLocator)
      .setWarehouseRouting(warehouseRouting)
      .apply();
  });
});
