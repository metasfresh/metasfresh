import { CostCenter } from '../../support/utils/cost_center';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create test: create cost center, https://github.com/metasfresh/metasfresh-e2e/issues/124', function() {
  const date = humanReadableNow();
  const qualityNoteName = `Cost Center Test ${date}`;
  it('Create cost center', function() {
    cy.fixture('finance/cost_center.json').then(costCenterJson => {
      Object.assign(new CostCenter(), costCenterJson)
        .setName(qualityNoteName)
        .apply();
    });
  });
});
