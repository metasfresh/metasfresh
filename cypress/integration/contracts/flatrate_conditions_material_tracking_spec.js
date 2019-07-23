import { Calendar, Year } from '../../support/utils/calendar';
import { ContractTransition } from '../../support/utils/contract_transition';
import { ContractConditions, ConditionsType, ProductAllocation } from '../../support/utils/contract_conditions';
import { LagerKonferenz, LagerKonferenzVersion } from '../../support/utils/contract_lagerKonferenz';

import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { Pricesystem } from '../../support/utils/pricesystem';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';
import { getLanguageSpecific } from '../../support/utils/utils';

describe('Create material tracking contract conditions', function() {
  const timestamp = new Date().getTime();
  //const timestamp = 1562596599851;

  // pricing
  const priceSystemName = `MaterialTracking ${timestamp}`;
  const priceListName = `MaterialTracking ${timestamp}`;
  const priceListVersionName = `MaterialTracking ${timestamp}`;

  // product
  const productCategoryName = `MaterialTracking ${timestamp}`;
  const productCategoryValue = productCategoryName;
  const scrapProductName = `Scrap ${timestamp}`;
  const processingFeeProductName = `ProcessingFee ${timestamp}`;
  const productionProductName = `Production ${timestamp}`;
  const witholdingProductName = `Witholding ${timestamp}`;

  const rawProductName = `Raw ${timestamp}`;

  let countryName;
  before(function() {
    cy.wait(1000);
    cy.fixture('misc/misc_dictionary.json').then(dictionary => {
      countryName = getLanguageSpecific(dictionary, 'c_country_CH');
    });
  });

  it('Create calendar with years', function() {
    const calendarBaseName = `MaterialTracking`;
    const currentYear = new Date().getFullYear();
    new Calendar(calendarBaseName)
      .setTimestamp(timestamp)
      .addYear(new Year(`${currentYear}`))
      .addYear(new Year(`${currentYear + 1}`))
      .apply();
  });

  it('Create transition', function() {
    new ContractTransition({ baseName: 'MaterialTracking' })
      .setTimestamp(timestamp)
      .setCalendar(timestamp)
      .apply();
    cy.screenshot();
  });

  it('Create LagerKonferenz PLV', function() {
    // priceList
    const priceListName = `MaterialTracking ${timestamp}`;
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(
        new Pricesystem(/* useless to set anything here since it's replaced by the fixture */),
        priceSystemJson
      )
        .setName(priceSystemName)
        .apply();
    });
    let priceListVersion;
    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      priceListVersion = Object.assign(
        new PriceListVersion(/* useless to set anything here since it's replaced by the fixture */),
        priceListVersionJson
      ).setName(priceListVersionName);
    });

    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(/* useless to set anything here since it's replaced by the fixture */), pricelistJson)
        .setName(priceListName)
        .setCountry(countryName)
        .setCurrency('CHF')
        .setPriceSystem(priceSystemName)
        .addPriceListVersion(priceListVersion)
        .apply();
    });
  });

  it('Create LagerKonferenz products', function() {
    const productType = 'Service';

    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryValue)
        .apply();
    });

    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(scrapProductName)
        .setValue(scrapProductName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(processingFeeProductName)
        .setValue(processingFeeProductName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productionProductName)
        .setValue(productionProductName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(witholdingProductName)
        .setValue(witholdingProductName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  });

  it('Create LagerKonferenz', function() {
    new LagerKonferenz({ baseName: 'LagerKonferenz' })
      .setTimestamp(timestamp)
      .addSettingsLine(
        new LagerKonferenzVersion()
          .setValidFrom('01/01/2019')
          .setValidTo('12/31/2019')
          .setScrapProduct(scrapProductName)
          .setScrapUOM('c_uom_KGM')
          .setProcessingFeeProduct(processingFeeProductName)
          .setRegularProductionProduct(productionProductName)
          .setWitholdingProduct(witholdingProductName)
      )
      .apply();
  });

  it('Create conditions', function() {
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(rawProductName)
        .setValue(rawProductName)
        .setProductType('Item')
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });

    new ContractConditions({ baseName: 'test-conditions' })
      .setTimestamp(timestamp)
      .setConditionsType(ConditionsType.QualityBased)
      .setTransition(timestamp)
      .setLagerKonferenz(timestamp)
      .addProductAllocation(new ProductAllocation().setProductCategory(timestamp).setProduct(rawProductName))
      .apply();
  });

  it('Create vendor', function() {
    const vendorName = `vendor ${timestamp}`;
    new BPartner({ name: vendorName })
      .setCustomer(false)
      .setBank(undefined)
      .setVendor(true)
      .setVendorPricingSystem(priceSystemName)
      .addLocation(new BPartnerLocation('Address1').setCity('Bern').setCountry(countryName))
      .apply();

    runProcessCreateContract(timestamp);
  });
});
