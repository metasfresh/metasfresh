import { PriceList } from '../../support/utils/pricelist';
import { Pricesystem } from '../../support/utils/pricesystem';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create a Pricelist with currency CHF and precision 3', function() {
  let priceSystemName;
  let priceListName;
  let pricePrecision;
  let currency;
  let country;
  it('Read fixture and prepare the names', function() {
    cy.fixture('price/pricelist_with_currency_CHF_and_precision_3_spec.json').then(f => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      pricePrecision = f['pricePrecision'];

      currency = f['currency'];
      country = f['country'];
    });
  });
  it('Create new Pricesystem', function() {
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(new Pricesystem(), priceSystemJson)
        .setName(priceSystemName)
        .apply();
    });
  });

  it('Create new Pricelist with currency CHF and precision 3', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(), pricelistJson)
        .setName(priceListName)
        .setPriceSystem(priceSystemName)
        .setCountry(country)
        .setCurrency(currency)
        .setPricePrecision(pricePrecision)
        .setIsSalesPriceList(true)
        .apply();
    });
  });
});
