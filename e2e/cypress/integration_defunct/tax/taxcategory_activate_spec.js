import { TaxCategory } from '../../support/utils/taxcategory';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { TaxRate } from '../../support/utils/taxrate';
import { Builder } from '../../support/utils/builder';
import { ProductPrice } from '../../support/utils/product_price';

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
let initialTaxCategory;

// test
let taxCategoryId;
let productPriceId;

describe('Test TaxCategory activation and deactivation', function() {
  it('Read the fixture', function() {
    cy.fixture('tax/taxcategory_activate_spec.json').then(f => {
      taxRateName = appendHumanReadableNow(f['taxRateName']);
      taxCategoryName = appendHumanReadableNow(f['taxCategoryName']);
      validFrom = f['validFrom'];
      rate = f['rate'];

      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      productName = appendHumanReadableNow(f['productName']);
      standardPrice = f['standardPrice'];
      initialTaxCategory = f['initialTaxCategory'];
    });
  });

  it('Create TaxCategory', function() {
    cy.fixture('tax/taxcategory.json').then(taxCategoryJson => {
      Object.assign(new TaxCategory(), taxCategoryJson)
        .setName(taxCategoryName)
        .apply();
    });
    cy.getCurrentWindowRecordId().then(id => (taxCategoryId = id));
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

    new ProductPrice()
      .setProduct(productName)
      .setIsAttributeDependant(true)
      .setPriceListVersion(priceListName)
      .setStandardPrice(standardPrice)
      .setTaxCategory(initialTaxCategory)
      .apply();

    cy.getCurrentWindowRecordId().then(id => (productPriceId = id));
  });

  it('Deactivate the TaxCategory', function() {
    cy.visitWindow('138', taxCategoryId);
    cy.clickOnIsActive();
  });

  it('Expect TaxCategory can not be selected in a Product Price when it is inactive', function() {
    cy.visitWindow(540325, productPriceId);

    cy.get(`.form-field-C_TaxCategory_ID`)
      .find('.input-dropdown')
      .click()
      .get('.input-dropdown-list')
      .should($list => {
        expect($list.text()).to.not.contain(taxCategoryName);
      });
  });

  it('Activate the TaxCategory', function() {
    cy.visitWindow('138', taxCategoryId);
    cy.clickOnIsActive();
  });

  it('Expect TaxCategory can be selected in a Product Price when it is active', function() {
    cy.visitWindow(540325, productPriceId);

    cy.get(`.form-field-C_TaxCategory_ID`)
      .find('.input-dropdown')
      .click()
      .get('.input-dropdown-list')
      .should($list => {
        expect($list.text()).to.contain(taxCategoryName);
      });
  });
});
