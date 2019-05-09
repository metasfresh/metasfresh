import { Pricelist } from '../../support/utils/pricelist';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  it('Create new Pricelist', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new Pricelist('Test Preisliste DEU EUR'), pricelistJson).apply();
    });
  });
});
