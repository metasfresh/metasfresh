import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { Pricesystem } from '../../support/utils/pricesystem';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  let priceSystemName;
  let priceListName;

  it('Read fixture and prepare the names', function() {
    cy.fixture('price/pricelist_setup_spec.json').then(f => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
    });
  });
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
