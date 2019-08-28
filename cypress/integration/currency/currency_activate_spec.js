import { Currency } from '../../support/utils/currency';

describe('Activate Currency USD for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/76', function() {
  let currencyId;
  let isoCode;
  it('Read fixture and prepare the names', function() {
    cy.fixture('currency/currency_activate_spec.json').then(f => {
      currencyId = f['currencyId'];
      isoCode = f['isoCode'];
    });
  });
  it('Deactivate the currency USD', function() {
    cy.fixture('currency/currency.json').then(currencyJson => {
      Object.assign(new Currency(), currencyJson)
        .setCurrencyID(currencyId)
        .setIsoCode(isoCode)
        .setActive(false)
        .apply();
    });
  });
  it('Activate the currency USD', function() {
    cy.fixture('currency/currency.json').then(currencyJson => {
      Object.assign(new Currency(), currencyJson)
        .setCurrencyID(currencyId)
        .setIsoCode(isoCode)
        .setActive(true)
        .apply();
    });
  });
});
