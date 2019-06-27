import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { Pricesystem } from '../../support/utils/pricesystem';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  const timestamp = new Date().getTime();
  const priceSystemName = `Test Preissystem ${timestamp}`;
  const priceListName = `Test Preisliste DEU EUR ${timestamp}`;
  it('Create new Pricesystem', function() {
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(
        new Pricesystem(/* useless to set anything here since it's replaced by the fixture */),
        priceSystemJson
      )
        .setName(priceSystemName)
        .apply();
    });
  });
  it('Create new Pricelist and PLV', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
        Object.assign(new PriceList(), pricelistJson)
          .setName(priceListName)
          .setPriceSystem(priceSystemName)
          .addPriceListVersion(Object.assign(new PriceListVersion(), priceListVersionJson))
          .apply();
      });
    });
  });
});
