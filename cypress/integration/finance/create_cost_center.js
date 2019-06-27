import { CostCenter } from '../../support/utils/cost_center';

describe('Create test: create cost center, https://github.com/metasfresh/metasfresh-e2e/issues/124', function() {
  const timestamp = new Date().getTime();
  const qualityNoteName = `Cost Center Test ${timestamp}`;
  it('Create cost center', function() {
    cy.fixture('finance/cost_center.json').then(costCenterJson => {
      Object.assign(new CostCenter(), costCenterJson)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
