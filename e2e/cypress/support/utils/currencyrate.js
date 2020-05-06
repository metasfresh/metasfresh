export class CurrencyRate {
    setCurrency(currency){
      cy.log(`CurrencyRate - set currency = ${currency}`);
      this.currency = currency;
      return this;
    }
  
    setMultiplyRate(multiplyRate){
      cy.log(`CurrencyRate - set multiplyRate = ${multiplyRate}`);
      this.multiplyRate = multiplyRate;
      return this;
    }
  
    setCurrencyTo(currencyTo){
      cy.log(`CurrencyRate - set currencyTo = ${currencyTo}`);
      this.currencyTo = currencyTo;
      return this;
    }
  
    setIsActive(isActive){
      cy.log(`CurrencyRate - set isActive = ${isActive}`);
      this.isActive = isActive;
      return this;
    }
  
    setValidFromDate(date){
      cy.log(`CurrencyRate - set validfromdate = ${date}`);
      this.date = date;
      return this;
    }
    apply(){
        cy.log(`CurrencyRate - apply: BEGIN`);
        CurrencyRate.applyCurrencyRate(this);
        cy.log(`CurrencyRate - apply: DONE`);
        return this;
    }

    static applyCurrencyRate(currencyRate){
        describe(`Create new CurrencyRate`, function() {
            cy.visitWindow(`116`,`NEW`);
            cy.selectInListField('C_Currency_ID', currencyRate.currency, false);
            cy.writeIntoStringField('MultiplyRate', currencyRate.multiplyRate, false, null, false);
            cy.selectInListField('C_Currency_ID_To', currencyRate.currencyTo, false);
            //cy.setCheckBoxValue('IsActive', currencyRate.isActive);
            if(!currencyRate.isActive){
                cy.clickOnIsActive(false);
            }
            cy.selectDateViaPicker(`ValidFrom`, false);
        })
    }
  }