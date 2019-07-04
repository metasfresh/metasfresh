import { Warehouse } from '../../support/utils/warehouse';
import { WarehouseLocator } from '../../support/utils/warehouselocator';
import { WarehouseRouting } from '../../support/utils/warehouserouting';


describe('Create test: warehouse, https://github.com/metasfresh/metasfresh-e2e/issues/46', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const warehouseName = `TestWarehouseName ${timestamp}`;
  const warehouseValue = `TestWarehouseValue ${timestamp}`;

  it('Create a new warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
    Object.assign(new Warehouse(warehouseName), warehouseJson).apply()
      .setName(warehouseName)
      .setValue(warehouseValue)
      .apply();
    });
    
    Object.assign(new WarehouseLocator)
    Object.assign(new WarehouseRouting())
      
  });
});
