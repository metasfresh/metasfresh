import { Warehouse } from '../../support/utils/warehouse';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create test: warehouse', function() {
  let warehouseName;
  let warehouseValue;
  it('Read the fixture', function() {
    cy.fixture('masterdata/warehouse_spec.json').then(f => {
      warehouseName = appendHumanReadableNow(f['warehouseName']);
      warehouseValue = appendHumanReadableNow(f['warehouseValue']);
    });
  });
  it('Create a new warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
      Object.assign(new Warehouse(), warehouseJson)
        .setName(warehouseName)
        .setValue(warehouseValue)
        .apply();
    });
  });
});
