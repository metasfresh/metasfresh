import { Pricesystem } from './pricesystem';
import { PriceList, PriceListVersion } from './pricelist';
import { Product, ProductCategory, ProductPrice } from './product';

export class Builder {
  /**
   * Use this when you aren't interested in configuring anything (except for the name) for the PriceSystem, PriceList or PriceListVersion, but you only need them to exist.
   *
   * - Most tests should use this builder instead of copy pasting everything in the test file.
   *
   * - Only the tests which need customised Price* types should create their own (by copying the contents of this method and modifying as needed).
   */
  static createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, isSalesPriceList) {
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(new Pricesystem(), priceSystemJson)
        .setName(priceSystemName)
        .apply();
    });

    let priceListVersion;
    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      priceListVersion = Object.assign(new PriceListVersion(), priceListVersionJson).setName(priceListVersionName);
    });

    cy.fixture('price/pricelist.json').then(pricelistJson => {
      Object.assign(new PriceList(), pricelistJson)
        .setName(priceListName)
        .setPriceSystem(priceSystemName)
        .setIsSalesPriceList(isSalesPriceList)
        .addPriceListVersion(priceListVersion)
        .apply();
    });
  }

  /**
   * Use this when you aren't interested in configuring anything (except for the name) for the ProductCategory, ProductPrice or Product, but you only need them to exist.
   *
   * - Most tests should use this builder instead of copy pasting everything in the test file.
   *
   * - Only the tests which need customised Product* types should create their own (by copying the contents of this method and modifying as needed).
   */
  static createBasicProductEntities(
    productCategoryName,
    productCategoryValue,
    priceListName,
    productName,
    productValue,
    productType
  ) {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });

    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  }

  static createProductWithPriceAndCUTUAllocationUsingExistingCategory(
    productCategoryName,
    productCategoryValue,
    priceListName,
    productName,
    productValue,
    productType,
    packingInstructionsName
  ) {
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductType(productType)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .addCUTUAllocation(packingInstructionsName)
        .apply();
    });
  }

  static createProductWithPriceUsingExistingCategory(
    priceListName,
    productName,
    productValue,
    productType,
    categoryName
  ) {
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductType(productType)
        .setProductCategory(categoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  }
}
