import { PriceList, PriceListVersion } from '../../support/utils/pricelist';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  it('Create new Pricelist', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList('Test Preisliste DEU EUR'), pricelistJson).apply();
    });

    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      Object.assign(new PriceListVersion('Test Preisliste Version'), priceListVersionJson).apply();
    });
  });
});
