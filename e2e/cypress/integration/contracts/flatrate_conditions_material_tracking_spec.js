import { Calendar, Year } from '../../support/utils/calendar';
import { ContractTransition } from '../../support/utils/contract_transition';
import { ConditionsType, ContractConditions, ProductAllocation } from '../../support/utils/contract_conditions';
import { LagerKonferenz, LagerKonferenzVersion } from '../../support/utils/contract_lagerKonferenz';

import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { Pricesystem } from '../../support/utils/pricesystem';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';
import { BPartner } from '../../support/utils/bpartner';
import { runProcessCreateContract } from '../../support/functions/contractFunctions';
import { appendHumanReadableNow, getLanguageSpecific } from '../../support/utils/utils';
import { StorageConferenceVersion, CostLine, LagerKonfMonth, AdditionalContribution } from '../../support/utils/storage_conferenceversion';

let priceSystemName;
let priceListName;
let priceListVersionName;

let productCategoryName;
let scrapProductName;
let processingFeeProductName;
let productionProductName;
let witholdingProductName;

let rawProductName;

let calendarName;
let contactTransitionName;
let serviceProductType;
let itemProductType;
let contractConditionsName;
let vendorName;
let lagerKonferenzName;
let validFrom;
let validTo;
let scrapFeeAmt;
let percentageScrapTreshhold;
let uomScrap;

let percentFrom1;
let percentFrom2;
let percentFrom3;
let percentFrom4;
let percentFrom5;
let percentFrom6;
let percentFrom7;
let processingFee1;
let processingFee2;
let processingFee3;
let processingFee4;
let processingFee5;
let processingFee6;
let processingFee7;
let cUomId;
let juni;
let juli;
let august;
let september;
let oktober;
let november;
let dezember;
let januar;
let februar;
let marz;
let april;
let mai;

let compensationAmount1;
let compensationAmount2;
let compensationAmount3;
let compensationAmount4;
let compensationAmount5;
let compensationAmount6;
let contributionProduct1;
let contributionProduct2;
let seqNo1;
let seqNo2;
let contributionAmount1;
let contributionAmount2;

// static
const currentYear = new Date().getFullYear();

// test
let countryName;
let priceListVersion;
let productPrice;

it('Read the fixture', function() {
  cy.fixture('contracts/flatrate_conditions_material_tracking_spec.json').then(f => {
    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    scrapProductName = appendHumanReadableNow(f['scrapProductName']);
    processingFeeProductName = appendHumanReadableNow(f['processingFeeProductName']);
    productionProductName = appendHumanReadableNow(f['productionProductName']);
    witholdingProductName = appendHumanReadableNow(f['witholdingProductName']);

    rawProductName = appendHumanReadableNow(f['rawProductName']);

    calendarName = appendHumanReadableNow(f['calendarName']);
    contactTransitionName = appendHumanReadableNow(f['contactTransitionName']);
    serviceProductType = f['serviceProductType'];
    itemProductType = f['itemProductType'];
    contractConditionsName = appendHumanReadableNow(f['contractConditionsName']);
    vendorName = appendHumanReadableNow(f['vendorName']);
    lagerKonferenzName = appendHumanReadableNow(f['lagerKonferenzName']);
    validFrom = f['validFrom'];
    validTo = f['validTo'];

    scrapFeeAmt = f['scrapFeeAmt'];
    percentageScrapTreshhold = f['percentageScrapTreshhold'];
    uomScrap = f['uomScrap'];

    percentFrom1 = f['percentFrom1'];
    percentFrom2 = f['percentFrom2'];
    percentFrom3 = f['percentFrom3'];
    percentFrom4 = f['percentFrom4'];
    percentFrom5 = f['percentFrom5'];
    percentFrom6 = f['percentFrom6'];
    percentFrom7 = f['percentFrom7'];
    processingFee1 = f['processingFee1'];
    processingFee2 = f['processingFee2'];
    processingFee3 = f['processingFee3'];
    processingFee4 = f['processingFee4'];
    processingFee5 = f['processingFee5'];
    processingFee6 = f['processingFee6'];
    processingFee7 = f['processingFee7'];
    cUomId = f['cUomId'];

    juni = f['juni'];
    juli = f['juli'];
    august = f['august'];
    september = f['september'];
    oktober = f['oktober'];
    november = f['november'];
    dezember = f['dezember'];
    januar = f['januar'];
    februar = f['februar'];
    marz = f['marz'];
    april = f['april'];
    mai = f['mai'];

    compensationAmount1 = f['compensationAmount1'];
    compensationAmount2 = f['compensationAmount2'];
    compensationAmount3 = f['compensationAmount3'];
    compensationAmount4 = f['compensationAmount4'];
    compensationAmount5 = f['compensationAmount5'];
    compensationAmount6 = f['compensationAmount6'];

    contributionProduct1 = appendHumanReadableNow(f['contributionProduct1']);
    contributionProduct2 = appendHumanReadableNow(f['contributionProduct2']);

    seqNo1 = f['seqNo1'];
    seqNo2 = f['seqNo2'];
    contributionAmount1 = f['contributionAmount1'];
    contributionAmount2 = f['contributionAmount2'];
  });
});

it('Read country name', function() {
  cy.fixture('misc/misc_dictionary.json').then(dictionary => {
    countryName = getLanguageSpecific(dictionary, 'c_country_CH');
  });
});

it('Create calendar with years', function() {
  new Calendar()
    .setName(calendarName)
    .addYear(new Year(currentYear))
    .addYear(new Year(currentYear + 1))
    .apply();
});

it('Create transition', function() {
  new ContractTransition({ name: contactTransitionName }).setCalendar(calendarName).apply();
  cy.screenshot();
});

it('Create PriceList and Product Price', function() {
  cy.fixture('price/pricesystem.json').then(priceSystemJson => {
    Object.assign(new Pricesystem(), priceSystemJson)
      .setName(priceSystemName)
      .apply();
  });

  cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
    priceListVersion = Object.assign(new PriceListVersion(), priceListVersionJson);
  });

  cy.fixture('product/product_price.json').then(productPriceJson => {
    productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
  });
});

it('Create LagerKonferenz PLV', function() {
  cy.fixture('price/pricelist.json').then(pricelistJson => {
    Object.assign(new PriceList(), pricelistJson)
      .setName(priceListName)
      .setCountry(countryName)
      .setCurrency('CHF')
      .setPriceSystem(priceSystemName)
      .addPriceListVersion(priceListVersion)
      .apply();
  });
});

it('Create LagerKonferenz products', function() {
  cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
    Object.assign(new ProductCategory(), productCategoryJson)
      .setName(productCategoryName)
      .apply();
  });

  createProduct(scrapProductName, serviceProductType, productCategoryName, productPrice);
  createProduct(processingFeeProductName, serviceProductType, productCategoryName, productPrice);
  createProduct(productionProductName, serviceProductType, productCategoryName, productPrice);
  createProduct(witholdingProductName, serviceProductType, productCategoryName, productPrice);
});
it('Create products for additional contribution tab', function() {
  createProduct(contributionProduct1, itemProductType, productCategoryName, productPrice);
  createProduct(contributionProduct2, itemProductType, productCategoryName, productPrice);
});

it('Create LagerKonferenz', function() {
  new LagerKonferenz({ name: lagerKonferenzName })
    .addSettingsLine(
      new LagerKonferenzVersion()
        .setValidFrom(validFrom)
        .setValidTo(validTo)
        .setScrapProduct(scrapProductName)
        .setScrapUOM('c_uom_KGM')
        .setScrapFeeAmt(scrapFeeAmt)
        .setPercentageScrapTreshhold(percentageScrapTreshhold)
        .setProcessingFeeProduct(processingFeeProductName)
        .setRegularProductionProduct(productionProductName)
        .setWitholdingProduct(witholdingProductName)
    )
    .apply();
});

it('Create conditions', function() {
  createProduct(rawProductName, itemProductType, productCategoryName, productPrice);
  new ContractConditions({ name: contractConditionsName })
    .setConditionsType(ConditionsType.QualityBased)
    .setTransition(contactTransitionName)
    .setLagerKonferenz(lagerKonferenzName)
    .addProductAllocation(new ProductAllocation().setProductCategory(productCategoryName).setProduct(rawProductName))
    .apply();
});

it('Create vendor', function() {
  cy.fixture('sales/simple_vendor.json').then(vendorJson => {
    new BPartner({ ...vendorJson, name: vendorName }).setVendorPricingSystem(priceSystemName).apply();
  });
  runProcessCreateContract(contractConditionsName);
});

it('Create new Storage conference version', function() {
  cy.fixture('contract/storage_conferenceversion.json').then(conferenceVersionJson => {
    Object.assign(new StorageConferenceVersion(), conferenceVersionJson)
      .setLagerKonferenz(lagerKonferenzName)
      .setProductProcessingFee(processingFeeProductName)
      .setProductWitholding(witholdingProductName)
      .setProductRegularPPOrder(productionProductName)
      .setProductScrap(scrapProductName)
      .setUOMScrap(uomScrap)
      .setValidFrom(validFrom)
      .setValidTo(validTo)
      .setPercentageScrapTreshhold(percentageScrapTreshhold)
      .setScrapFeeAmt(scrapFeeAmt)
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom1)
          .setProcessingFeeAmtPerUOM(processingFee1)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom2)
          .setProcessingFeeAmtPerUOM(processingFee2)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom3)
          .setProcessingFeeAmtPerUOM(processingFee3)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom4)
          .setProcessingFeeAmtPerUOM(processingFee4)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom5)
          .setProcessingFeeAmtPerUOM(processingFee5)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom6)
          .setProcessingFeeAmtPerUOM(processingFee6)
          .setCUOMID(cUomId)
      )
      .addLine(
        new CostLine()
          .setPercentFrom(percentFrom7)
          .setProcessingFeeAmtPerUOM(processingFee7)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(juni)
          .setQualityAdjAmt(compensationAmount1)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(juli)
          .setQualityAdjAmt(compensationAmount1)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(august)
          .setQualityAdjAmt(compensationAmount2)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(september)
          .setQualityAdjAmt(compensationAmount2)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(oktober)
          .setQualityAdjAmt(compensationAmount2)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(november)
          .setQualityAdjAmt(compensationAmount3)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(dezember)
          .setQualityAdjAmt(compensationAmount1)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(januar)
          .setQualityAdjAmt(compensationAmount4)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(februar)
          .setQualityAdjAmt(compensationAmount4)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(marz)
          .setQualityAdjAmt(compensationAmount5)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(april)
          .setQualityAdjAmt(compensationAmount6)
          .setCUOMID(cUomId)
      )
      .addMonth(
        new LagerKonfMonth()
          .setMonth(mai)
          .setQualityAdjAmt(compensationAmount6)
          .setCUOMID(cUomId)
      )
      .addContribution(
        new AdditionalContribution()
          .setSeqNo(seqNo1)
          .setProduct(contributionProduct1)
          .setAdditionalFeeAmount(contributionAmount1)
      )
      .addContribution(
        new AdditionalContribution()
          .setSeqNo(seqNo2)
          .setProduct(contributionProduct2)
          .setAdditionalFeeAmount(contributionAmount2)
      )
      .apply();
  });
});

function createProduct(productName, productType, productCategoryName, productPrice) {
  cy.fixture('product/simple_product.json').then(productJson => {
    Object.assign(new Product(), productJson)
      .setName(productName)
      .setProductType(productType)
      .setProductCategory(productCategoryName)
      .addProductPrice(productPrice)
      .apply();
  });
}
