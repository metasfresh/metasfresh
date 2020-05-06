import { Pricesystem } from '../../support/utils/pricesystem';

describe('Create Pricesystem for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  it('Create new Pricesystem', function() {
    cy.fixture('price/pricesystem.json').then(pricesystemJson => {
      Object.assign(new Pricesystem('Test Preissystem'), pricesystemJson).apply();
    });
  });
});
