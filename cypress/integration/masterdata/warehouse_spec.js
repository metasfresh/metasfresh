import { Warehouse } from '../../support/utils/warehouse';

describe('Create test: warehouse, https://github.com/metasfresh/metasfresh-e2e/issues/46', function() {
  const date = new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString();
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
