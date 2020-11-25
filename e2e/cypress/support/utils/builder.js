import { Pricesystem } from './pricesystem';
import { PriceList, PriceListVersion } from './pricelist';
import { Product, ProductCategory, ProductPrice } from './product';
import { PackingMaterial } from './packing_material';
import { PackingInstructions } from './packing_instructions';
import { PackingInstructionsVersion } from './packing_instructions_version';
import { getLanguageSpecific } from './utils';
import { Inventory, InventoryLine } from './inventory';

export class Builder {
  /**
   * Use this when you aren't interested in configuring anything (except for the name) for the PriceSystem, PriceList or PriceListVersion, but you only need them to exist.
   *
   * - Most tests should use this builder instead of copy pasting everything in the test file.
   *
   * - Only the tests which need customised Price* types should create their own (by copying the contents of this method and modifying as needed).
   */
  static createBasicPriceEntities(priceSystemName, ignored_priceListVersionName, priceListName, isSalesPriceList) {
    // TODO: remove PLVName
    cy.fixture('price/pricesystem.json').then(priceSystemJson => {
      Object.assign(new Pricesystem(), priceSystemJson)
        .setName(priceSystemName)
        .apply();
    });

    let priceListVersion;
    cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
      priceListVersion = Object.assign(new PriceListVersion(), priceListVersionJson);
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
  static createBasicProductEntities(productCategoryName, productCategoryValue, priceListName, productName, productValue, productType) {
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
      let product = Object.assign(new Product(), productJson);
      if (productType) {
        product.setProductType(productType);
      }
      product
        .setName(productName)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  }

  static createProductWithPriceAndCUTUAllocationUsingExistingCategory(productCategoryName, productCategoryValue, priceListName, productName, productValue, productType, packingInstructionsName) {
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      let product = Object.assign(new Product(), productJson);
      if (productType) {
        product.setProductType(productType);
      }
      product
        .setName(productName)
        .setProductCategory(productCategoryValue + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .addCUTUAllocation(packingInstructionsName)
        .apply();
    });
  }

  static createProductWithPriceUsingExistingCategory(priceListName, productName, productValue, productType, categoryName) {
    let productPrice;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice = Object.assign(new ProductPrice(), productPriceJson).setPriceList(priceListName);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      let product = Object.assign(new Product(), productJson);
      if (productType) {
        product.setProductType(productType);
      }
      product
        .setName(productName)
        .setProductCategory(categoryName)
        .addProductPrice(productPrice)
        .apply();
    });
  }

  /**
   * Create packing material and instructions from an existing Product
   *
   * @param productForPackingMaterialName
   * @param packingInstructionsName
   */
  static createPackingMaterial(productForPackingMaterialName, packingInstructionsName) {
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(productForPackingMaterialName)
        .setProduct(productForPackingMaterialName)
        .apply();
    });
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsName)
        .apply();
    });
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsName)
        .setPackingInstructions(packingInstructionsName)
        .setPackingMaterial(productForPackingMaterialName)
        .apply();
    });
  }

  /**
   * Create a HU with stock, from a Physical Inventory Document
   *
   * @returns the huValue of the newly create HU
   */
  static createHUWithStock(productName, productQty, locatorId) {
    // create HU
    // see https://github.com/metasfresh/metasfresh/pull/10090#issue-500479284
    // let uomName;
    // cy.fixture('product/simple_product.json').then(productJson => {
    //   uomName = getLanguageSpecific(productJson, 'c_uom');
    // });

    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      const docTypeName = getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName');

      const inventoryLine = new InventoryLine()
        .setProductName(productName)
        .setQuantity(productQty)
        // .setC_UOM_ID(uomName)
        .setM_Locator_ID(locatorId)
        .setIsCounted(true);

      new Inventory()
        .setWarehouse(inventoryJson.warehouseName)
        .setDocType(docTypeName)
        .addInventoryLine(inventoryLine)
        .apply();
    });

    // save and return the HU value
    cy.selectTab('M_InventoryLine');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();

    return cy.getStringFieldValue('M_HU_ID').then(val => {
      cy.pressDoneButton();
      return cy.wrap(val).then(val => {
        // noinspection JSUnresolvedFunction
        return val.split('_')[0];
      });
    });
  }
}
