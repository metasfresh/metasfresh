import { PriceList } from '../../support/utils/pricelist';
import { PriceListVersion } from '../../support/utils/pricelistversion';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/95', function() {
  const priceListName = 'Test Preisliste DEU EUR';

  it('Create new Pricelist', function() {
    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(priceListName), pricelistJson).apply();
    });
    cy.get('@priceListObj').then(obj => {
      // access the users argument
      cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
        cy.log(`PriceList - Name = ${priceListName}`);
        cy.log(`PriceList - DocID = ${obj.documentId}`);
        Object.assign(new PriceListVersion(`${priceListName}`, `${obj.documentId}`), priceListVersionJson).apply();
      });
    });
  });
});
