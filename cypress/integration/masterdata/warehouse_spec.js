import { Warehouse } from '../../support/utils/warehouse';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create test: warehouse', function() {
  const date = humanReadableNow();
  const warehouseName = `TestWarehouseName ${date}`;
  const warehouseValue = `TestWarehouseValue ${date}`;

  it('Create a new warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
      Object.assign(new Warehouse(), warehouseJson)
        .setName(warehouseName)
        .setValue(warehouseValue)
        .apply();
    });
  });
});
