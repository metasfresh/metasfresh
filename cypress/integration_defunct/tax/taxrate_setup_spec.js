import { TaxRate } from '../../support/utils/taxrate';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { TaxCategory } from '../../support/utils/taxcategory';
import { ProductPrice } from '../../support/utils/product_price';
import { Builder } from '../../support/utils/builder';

describe('Create new TaxRate', function() {
  // tax
  let taxRateName;
  let taxCategoryName;
  let validFrom;
  let rate;

  // price and product
  let priceSystemName;
  let priceListVersionName;
  let priceListName;
  let productName;
  let standardPrice;

  it('Read the fixture', function() {
    cy.fixture('tax/taxrate_setup_spec.json').then(f => {
      taxRateName = appendHumanReadableNow(f['taxRateName']);
      taxCategoryName = appendHumanReadableNow(f['taxCategoryName']);
      validFrom = f['validFrom'];
      rate = f['rate'];

      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productName = appendHumanReadableNow(f['productName']);
      standardPrice = f['standardPrice'];
    });
  });

  it('Create TaxCategory', function() {
    cy.fixture('tax/taxcategory.json').then(taxCategoryJson => {
      Object.assign(new TaxCategory(), taxCategoryJson)
        .setName(taxCategoryName)
        .apply();
    });
  });

  it('Create new TaxRate', function() {
    cy.fixture('tax/taxrate.json').then(taxrateJson => {
      Object.assign(new TaxRate(), taxrateJson)
        .setName(taxRateName)
        .setRate(rate)
        .setValidFrom(validFrom)
        .setTaxCategory(taxCategoryName)
        .apply();
    });
  });

  it('Create Price and Product', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productName, productName, null, '24_Gebinde');
  });

  it('Expect TaxCategory can be selected in a Product Price', function() {
    new ProductPrice()
      .setProduct(productName)
      .setIsAttributeDependant(true)
      .setPriceListVersion(priceListName)
      .setStandardPrice(standardPrice)
      .setTaxCategory(taxCategoryName)
      .apply();

    cy.getStringFieldValue('C_TaxCategory_ID').should('equals', taxCategoryName);
  });
});
