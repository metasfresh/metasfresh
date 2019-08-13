import { PriceList } from '../../support/utils/pricelist';
import { Pricesystem } from '../../support/utils/pricesystem';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create Pricelist for Automatic End2End Tests with cypress https://github.com/metasfresh/metasfresh-e2e/issues/195', function() {
    const date = humanReadableNow();
    const priceSystemName = `TestPriceSystem ${date}`;
    const priceListName = `TestPriceList ${date}`;
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
            .setCountry(`Switzerland`)
            .setCurrency(`CHF`)
            .setPricePrecision(3)
            .setIsSalesPriceList(true)
            .apply();
        });
    });
});
