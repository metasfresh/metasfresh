/*
 * #%L
 * metasfresh-e2e
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import { Attribute, AttributeSet, AttributeValue } from '../../support/utils/attribute';
import { humanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { Product, ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { ProductPrice } from '../../support/utils/product_price';
import { RewriteURL } from '../../support/utils/constants';
import { ProductPrices } from '../../page_objects/product_prices';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

// task: https://github.com/metasfresh/metasfresh-e2e/issues/233

const date = humanReadableNow();

// Price Entities
const priceSystemName = `PriceSystem_${date}`;
const priceListName = `PriceList_${date}`;
const priceListVersionName = `PriceListVersion_${date}`;

// Attribute and AttributeSet
const attributeName1 = `Attribute1_${date}`;
const attributeName2 = `Attribute2_${date}`;
const attributeName3 = `Attribute3_${date}`;
const attributeValue1 = `AttributeValue1`;
const attributeValue2 = `AttributeValue2`;
const attributeSetName1 = `AttributeSet1_${date}`;
const attributeSetName2 = `AttributeSet2_${date}`;

// ProductCategory
const productCategoryName1 = `ProductCategory1_${date}`;
const productCategoryName2 = `ProductCategory2_${date}`;

// Packing
const productForPacking1 = `ProductPackingMaterial1_${date}`;
const productForPacking2 = `ProductPackingMaterial2_${date}`;
const packingInstructionsName1 = `ProductPackingInstructions1_${date}`;
const packingInstructionsName2 = `ProductPackingInstructions2_${date}`;

// Product
const productType = 'Item';
const productName1 = `Product1_${date}`;
const productName2 = `Product2_${date}`;
const productName3 = `Product3_${date}`;

// ProductPrice
const standardPrice = 123.456;
const taxCategory = 'Regular Tax Rate 19% (Germany)';

// test variables
let productID1;
let productID2;

// ===========================
// create test data
describe('Create Price data', function() {
  it('Create Price data', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
  });
});

describe('Create Attributes and AttributeSets', function() {
  createAttribute(attributeName1, attributeValue1, attributeValue2);
  createAttribute(attributeName2, attributeValue1, attributeValue2);
  createAttribute(attributeName3, attributeValue1, attributeValue2);

  it('Create AttributeSet1', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName1)
      .addAttribute(attributeName1)
      .addAttribute(attributeName2)
      .apply();
  });

  it('Create AttributeSet2', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName2)
      .addAttribute(attributeName2)
      .addAttribute(attributeName3)
      .apply();
  });
});

describe('Create Product Categories', function() {
  createProductCategory(productCategoryName1, attributeSetName1);
  createProductCategory(productCategoryName2, attributeSetName2);
});

createPackingEntities(productForPacking1, packingInstructionsName1);
createPackingEntities(productForPacking2, packingInstructionsName2);

describe('Create the 3 products ', function() {
  it('Create product 1', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .setValue(productName1)
        .setProductType(productType)
        .setProductCategory(productCategoryName1)
        .addCUTUAllocation(packingInstructionsName1)
        .apply();
    });
  });

  it('Create product 2', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName2)
        .setValue(productName2)
        .setProductType(productType)
        .setProductCategory(productCategoryName1)
        .addCUTUAllocation(packingInstructionsName1)
        .addCUTUAllocation(packingInstructionsName2)
        .apply();
    });
  });

  it('Create product 3', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName3)
        .setValue(productName3)
        .setProductType(productType)
        .setProductCategory(productCategoryName2)
        .addCUTUAllocation(packingInstructionsName1)
        .apply();
    });
  });
});

// ===========================
// begin test

describe('Create ProductPrices for Product 1', function() {
  const productName = productName1;
  const expectedNumberOfRows = 1;

  createProductPriceWithAttributes(productName);
  it('Save productId', function() {
    cy.getCurrentWindowRecordId().then(id => {
      productID1 = id;
    });
  });
  expectNumberOfProductPrices(expectedNumberOfRows);
});

describe('Create ProductPrices for Product 2', function() {
  const productName = productName2;
  const expectedNumberOfRows = 2;

  createProductPriceWithAttributes(productName);
  it('Save productId', function() {
    cy.getCurrentWindowRecordId().then(id => {
      productID2 = id;
    });
  });
  expectNumberOfProductPrices(expectedNumberOfRows);
});

describe('Change attributes for Product Prices 1 and 2', function() {
  const expectedNumberOfRows = 2;

  it(`Edit ${productName1}`, function() {
    editProductAttributes(productName1, productID1);
  });

  it(`Edit ${productName2}`, function() {
    editProductAttributes(productName2, productID2);
  });

  expectNumberOfProductPrices(expectedNumberOfRows);
});

describe('Create ProductPrices for Product 3', function() {
  const productName = productName3;
  const expectedNumberOfRows = 3;

  createProductPriceWithAttributes(productName);
  expectNumberOfProductPrices(expectedNumberOfRows);
});

///////////////////////////////
///////////////////////////////
///////////////////////////////
///////////////////////////////
///////////////////////////////
///////////////////////////////

// all of the helper stuff belongs to <here>
function createAttribute(attributeName, attributeValue1, attributeValue2) {
  it(`Create Attribute ${attributeName}`, function() {
    new Attribute(attributeName)
      .setValue(attributeName)
      .setDescription(attributeName)
      .setAttributeValueType('List')
      .setInstanceAttribute(true)
      .setPricingRelevant(true)
      .setStorageRelevant(true)
      .setAttrDocumentRelevant(true)
      .addAttributeValue(new AttributeValue(attributeValue1).setValue(attributeValue1))
      .addAttributeValue(new AttributeValue(attributeValue2).setValue(attributeValue2))
      .apply();
  });
}

function createProductCategory(productCategoryName, attributeSet) {
  it(`Create ProductCategory ${productCategoryName}`, function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryName)
        .setAttributeSet(attributeSet)
        .apply();
    });
  });
}

function createPackingEntities(productForPacking, packingInstructionsName) {
  describe(`Create packing entities for material ${productForPacking}`, function() {
    it('Create packing product', function() {
      Builder.createBasicProductEntitiesWithPrice(priceListName, productForPacking, productForPacking, productType);
    });

    it('Create packing material', function() {
      cy.fixture('product/packing_material.json').then(packingMaterialJson => {
        Object.assign(new PackingMaterial(), packingMaterialJson)
          .setName(productForPacking)
          .setProduct(productForPacking)
          .apply();
      });
    });

    it('Create packing instructions', function() {
      cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
        Object.assign(new PackingInstructions(), packingInstructionsJson)
          .setName(packingInstructionsName)
          .apply();
      });

      cy.fixture('product/packing_instructions_version.json').then(pivJson => {
        Object.assign(new PackingInstructionsVersion(), pivJson)
          .setName(packingInstructionsName)
          .setPackingInstructions(packingInstructionsName)
          .setPackingMaterial(productForPacking)
          .apply();
      });
    });
  });
}

function createProductPrice(productName, priceListName, standardPrice, taxCategory) {
  it(`Create ProductPrice for ${productName}`, function() {
    new ProductPrice()
      .setProduct(productName)
      .setIsAttributeDependant(true)
      .setPriceListVersion(priceListName)
      // .setPackingItem() // default none
      .setStandardPrice(standardPrice)
      // .setUOM() // default = each
      .setTaxCategory(taxCategory)
      .apply();
  });
}

function filterByPriceListVersion(priceListVersionName) {
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField(
    'M_PriceList_Version_ID',
    priceListVersionName,
    priceListVersionName,
    false,
    false,
    null,
    true
  );
  cy.setCheckBoxValue('IsAttributeDependant', true, false, null, true);
  applyFilters();
}

function createProductPriceWithAttributes(productName) {
  createProductPrice(productName, priceListName, standardPrice, taxCategory);
  it(`Set attributes for ProductPrice of ${productName}`, function() {
    cy.get('.form-field-M_AttributeSetInstance_ID').click();
    cy.selectInListField(attributeName1, attributeValue1, false, RewriteURL.ATTRIBUTE);
    cy.selectInListField(attributeName2, attributeValue1, false, RewriteURL.ATTRIBUTE);
    cy.get('.form-field-M_AttributeSetInstance_ID').click({ force: true }); // save the attributes
    cy.waitUntilEverythingIsSaved(true);
  });
}

function editProductAttributes(productName, productID) {
  cy.visitWindow(ProductPrices.windowId, productID);
  cy.get('.form-field-M_AttributeSetInstance_ID').click();

  cy.getStringFieldValue(attributeName1).should('contain', attributeValue1);
  cy.getStringFieldValue(attributeName2).should('contain', attributeValue1);

  cy.selectInListField(attributeName1, attributeValue2, false, RewriteURL.ATTRIBUTE);
  cy.selectInListField(attributeName2, attributeValue2, false, RewriteURL.ATTRIBUTE);
  cy.get('.form-field-M_AttributeSetInstance_ID').click({ force: true }); // save the attributes
  cy.waitUntilEverythingIsSaved(true);
}

function expectNumberOfProductPrices(expectedNumberOfRows) {
  it(`Filter by PLV in ProductPrices window and expect ${expectedNumberOfRows} records`, function() {
    // todo: commented during development
    ProductPrices.visit();
    filterByPriceListVersion(priceListName);
    cy.expectNumberOfRows(expectedNumberOfRows);
  });
}
