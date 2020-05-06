import { CurrencyRate } from '../../support/utils/currencyrate';

describe(`Create a new currency rate`, function() {
  let currency;
  let currencyTo;
  let multiplyRate;

  it('Read the fixture', function() {
    cy.fixture('currency/create_currency_rate_spec.json').then(f => {
      currency = f['currency'];
      currencyTo = f['currencyTo'];
      multiplyRate = f['multiplyRate'];
    });
  });

  it(`Create a new currency rate`, function() {
    cy.fixture('currency/currencyrate.json').then(currencyrateJson => {
      Object.assign(new CurrencyRate(), currencyrateJson)
        .setCurrency(currency)
        .setMultiplyRate(multiplyRate)
        .setCurrencyTo(currencyTo)
        .setIsActive(true)
        .apply();
    });
  });
});
