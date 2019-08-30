import { CostCenter } from '../../support/utils/cost_center';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create test: create cost center', function() {
  let qualityNoteName;

  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_from_sales_order.json').then(f => {
      qualityNoteName = appendHumanReadableNow(f['qualityNoteName']);
    });
  });

  it('Create cost center', function() {
    cy.fixture('finance/cost_center.json').then(costCenterJson => {
      Object.assign(new CostCenter(), costCenterJson)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
