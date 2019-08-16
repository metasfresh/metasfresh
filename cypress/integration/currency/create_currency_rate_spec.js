import {CurrencyRate} from '../../support/utils/currencyrate';

describe(`Create a new currency rate`, function() {
    const currency = `EUR`;
    const currencyTo = `CHF`;
    const multiplyRate = `0.919191919191`;
    
    it(`Create a new currency rate`, function(){
        new CurrencyRate()
            .setCurrency(currency)
            .setMultiplyRate(multiplyRate)
            .setCurrencyTo(currencyTo)
            .setIsActive(true)
            .apply();
    });
});

