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
import { appendHumanReadableNow } from '../../support/utils/utils';
import { Builder } from '../../support/utils/builder';
import { Product, ProductCategory } from '../../support/utils/product';
import { ProductPrice } from '../../support/utils/product_price';
import { RewriteURL } from '../../support/utils/constants';
import { ProductPrices } from '../../page_objects/product_prices';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

// task: https://github.com/metasfresh/metasfresh-e2e/issues/233

let priceSystemName;
let priceListName;
let priceListVersionName;

// Attribute and AttributeSet
let attributeName1;
let attributeName2;
let attributeName3;
let attributeValue1;
let attributeValue2;
let attributeSetName1;
let attributeSetName2;

// ProductCategory
let productCategoryName1;
let productCategoryName2;

// Packing
let productForPacking1;
let productForPacking2;
let packingInstructionsName1;
let packingInstructionsName2;

// Product
let productName1;
let productName2;
let productName3;

// ProductPrice
let standardPrice;
let taxCategory;

// test variables
let productID1;
let productID2;

it('Read fixture and prepare the names', function() {
  cy.fixture('price/change_product_prices_and_add_a_new_one.json').then(f => {
    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

    attributeName1 = appendHumanReadableNow(f['attributeName1']);
    attributeName2 = appendHumanReadableNow(f['attributeName2']);
    attributeName3 = appendHumanReadableNow(f['attributeName3']);

    attributeValue1 = f['attributeValue1'];
    attributeValue2 = f['attributeValue2'];

    attributeSetName1 = appendHumanReadableNow(f['attributeSetName1']);
    attributeSetName2 = appendHumanReadableNow(f['attributeSetName2']);

    productCategoryName1 = appendHumanReadableNow(f['productCategoryName1']);
    productCategoryName2 = appendHumanReadableNow(f['productCategoryName2']);

    productForPacking1 = appendHumanReadableNow(f['productForPacking1']);
    productForPacking2 = appendHumanReadableNow(f['productForPacking2']);
    packingInstructionsName1 = appendHumanReadableNow(f['packingInstructionsName1']);
    packingInstructionsName2 = appendHumanReadableNow(f['packingInstructionsName2']);

    productName1 = appendHumanReadableNow(f['productName1']);
    productName2 = appendHumanReadableNow(f['productName2']);
    productName3 = appendHumanReadableNow(f['productName3']);

    standardPrice = f['standardPrice'];
    taxCategory = f['taxCategory'];
  });
});

// ===========================
// create test data
describe('Create Price data', function() {
  it('Create Price data', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
  });
});

describe('Create Attributes and AttributeSets', function() {
  it('Create attributes', function() {
    createAttribute(attributeName1, attributeValue1, attributeValue2);
    createAttribute(attributeName2, attributeValue1, attributeValue2);
    createAttribute(attributeName3, attributeValue1, attributeValue2);
  });
});
describe('Create Attributes and AttributeSets', function() {
  it('Create AttributeSet1', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName1)
      .addAttribute(attributeName1)
      .addAttribute(attributeName2)
      .apply();
  });
});
describe('Create Attributes and AttributeSets', function() {
  it('Create AttributeSet2', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName2)
      .addAttribute(attributeName2)
      .addAttribute(attributeName3)
      .apply();
  });
});

describe('Create Product Categories', function() {
  it('Create Product Categories with attribute sets', function() {
    createProductCategory(productCategoryName1, attributeSetName1);
    createProductCategory(productCategoryName2, attributeSetName2);
  });
});
describe('Create packing entities', function() {
  it('Create packing entities and instructions', function() {
    createPackingEntities(productForPacking1, packingInstructionsName1);
    createPackingEntities(productForPacking2, packingInstructionsName2);
  });
});
describe('Create the 3 products ', function() {
  it('Create product 1', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName1)
        .setProductCategory(productCategoryName1)
        .addCUTUAllocation(packingInstructionsName1)
        .apply();
    });
  });

  it('Create product 2', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName2)
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
        .setProductCategory(productCategoryName2)
        .addCUTUAllocation(packingInstructionsName1)
        .apply();
    });
  });
});

// ===========================
// begin test

describe('Create ProductPrices for Product 1', function() {
  it('Create ProductPrices with attributes for Product 1', function() {
    const productName = productName1;
    const expectedNumberOfRows = 1;
    createProductPriceWithAttributes(productName);

    cy.getCurrentWindowRecordId().then(id => {
      productID1 = id;
    });
    expectNumberOfProductPrices(expectedNumberOfRows, priceListName);
  });
});

describe('Create ProductPrices for Product 2', function() {
  it('Create ProductPrices with attributes for Product 2', function() {
    const productName = productName2;
    const expectedNumberOfRows = 2;

    createProductPriceWithAttributes(productName);
    cy.getCurrentWindowRecordId().then(id => {
      productID2 = id;
    });

    expectNumberOfProductPrices(expectedNumberOfRows, priceListName);
  });
});

describe('Change attributes for Product Prices 1 and 2', function() {
  it(`Edit products attributes`, function() {
    const expectedNumberOfRows = 2;
    editProductAttributes(productName1, productID1);
    editProductAttributes(productName2, productID2);

    expectNumberOfProductPrices(expectedNumberOfRows, priceListName);
  });
});

describe('Create ProductPrices for Product 3', function() {
  it(`Create ProductPrices with attributes for Product 3`, function() {
    const productName = productName3;
    const expectedNumberOfRows = 3;
    createProductPriceWithAttributes(productName);
    expectNumberOfProductPrices(expectedNumberOfRows, priceListName);
  });
});

function createProductCategory(productCategoryName, attributeSet) {
  cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
    Object.assign(new ProductCategory(), productCategoryJson)
      .setName(productCategoryName)
      .setAttributeSet(attributeSet)
      .apply();
  });
}

function createPackingEntities(productForPacking, packingInstructionsName) {
  Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPacking, productForPacking, null, '24_Gebinde');
  Builder.createPackingMaterial(productForPacking, packingInstructionsName);
}

function createProductPriceWithAttributes(productName) {
  createProductPrice(productName, priceListName, standardPrice, taxCategory);
  cy.get('.form-field-M_AttributeSetInstance_ID').click();
  cy.selectInListField(attributeName1, attributeValue1, false, RewriteURL.ATTRIBUTE);
  cy.selectInListField(attributeName2, attributeValue1, false, RewriteURL.ATTRIBUTE);
  cy.get('.form-field-M_AttributeSetInstance_ID').click({ force: true }); // save the attributes
  cy.waitForSaveIndicator(true);
}

function createProductPrice(productName, priceListName, standardPrice, taxCategory) {
  new ProductPrice()
    .setProduct(productName)
    .setIsAttributeDependant(true)
    .setPriceListVersion(priceListName)
    // .setPackingItem() // default none
    .setStandardPrice(standardPrice)
    // .setUOM() // default = each
    .setTaxCategory(taxCategory)
    .apply();
}

function editProductAttributes(productName, productID) {
  cy.visitWindow(ProductPrices.windowId, productID);
  cy.get('.form-field-M_AttributeSetInstance_ID').click();

  cy.getStringFieldValue(attributeName1).should('contain', attributeValue1);
  cy.getStringFieldValue(attributeName2).should('contain', attributeValue1);

  cy.selectInListField(attributeName1, attributeValue2, false, RewriteURL.ATTRIBUTE);
  cy.selectInListField(attributeName2, attributeValue2, false, RewriteURL.ATTRIBUTE);
  cy.get('.form-field-M_AttributeSetInstance_ID').click({ force: true }); // save the attributes
  cy.waitForSaveIndicator(true);
}

function expectNumberOfProductPrices(expectedNumberOfRows, priceListVersionName) {
  ProductPrices.visit();
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.selectInListField('M_PriceList_Version_ID', priceListVersionName, false, null, true);

  cy.setCheckBoxValue('IsAttributeDependant', true, false, null, true);
  applyFilters();
  cy.expectNumberOfRows(expectedNumberOfRows);
}

function createAttribute(attributeName, attributeValue1, attributeValue2) {
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
}
