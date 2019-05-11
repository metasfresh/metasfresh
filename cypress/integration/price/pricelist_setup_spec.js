import { PriceList } from '../../support/utils/pricelist';
import { PriceListVersion } from '../../support/utils/pricelistversion';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  it('Create new Pricelist', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList('Test Preisliste DEU EUR'), pricelistJson).apply();
    });
    cy.get('@priceListObj').then(obj => {
      cy.get('@priceListAlias').then(priceList => {
        // access the users argument
        cy.log(`PriceList - Name = ${priceList.Name}`);
        cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
          Object.assign(new PriceListVersion(`${priceList.Name}`, obj.documentId), priceListVersionJson).apply();
        });
      });
    });
  });
});
