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

import { appendHumanReadableNow } from '../../support/utils/utils';
import { PriceListSchema, PriceListSchemaLine } from '../../support/utils/price_list_schema';
import { Builder } from '../../support/utils/builder';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';
import { ProductPrices } from '../../page_objects/product_prices';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';

// Price
let priceSystemName;
let priceListName;
let priceListVersionName;

let priceListSchemaVersionName;
let priceListVersion2ValidFrom;

let priceListVersionNameSearch1; // magic from fixture (PLV doesnt have standard name :( )"
let priceListVersionNameSearch2; // magic from fixture (PLV doesnt have standard name :( )"

// Product
let categoryName;
let productName1;
let productName2;

// Price List Schema
let priceListSchemaName;
let surchargeAmount;

// test
let priceListID;
let originalPriceStd;
let originalPriceLimit;
let originalPriceList;
let originalUOM;
let originalTaxCategory;

it('Read fixture and prepare the names', function() {
  cy.fixture('price/add_a_product_to_a_pricelist_schema_and_create_a_new_PLV.json').then(f => {
    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

    priceListSchemaVersionName = appendHumanReadableNow(f['priceListSchemaVersionName']);
    priceListVersion2ValidFrom = f['priceListVersion2ValidFrom'];

    // the magics
    const appendedDate = appendHumanReadableNow('').substr(1);
    priceListVersionNameSearch1 = new RegExp(f['priceListVersionNameSearch1_part1'] + '.*' + appendedDate + '.*' + f['priceListVersionNameSearch1_part2']);
    priceListVersionNameSearch2 = new RegExp(f['priceListVersionNameSearch2_part1'] + '.*' + appendedDate + '.*' + f['priceListVersionNameSearch2_part2']);

    categoryName = appendHumanReadableNow(f['categoryName']);
    productName1 = appendHumanReadableNow(f['productName1']);
    productName2 = appendHumanReadableNow(f['productName2']);

    priceListSchemaName = appendHumanReadableNow(f['priceListSchemaName']);
    surchargeAmount = f['surchargeAmount'];
  });
});

describe('Create Price and Products', function() {
  it('Create Price', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);

    cy.getCurrentWindowRecordId().then(id => (priceListID = id));
  });

  it('Create Product1 and Category', function() {
    Builder.createBasicProductEntities(categoryName, categoryName, priceListName, productName1, productName1);
  });

  it('Create Product2', function() {
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productName2, productName2, null, categoryName);
  });
});

describe('Create Price List Schema for Product', function() {
  it('Create Price List Schema', function() {
    new PriceListSchema()
      .setName(priceListSchemaName)
      .addLine(new PriceListSchemaLine().setProduct(productName1).setStandardPriceSurchargeAmount(surchargeAmount))
      .apply();
  });

  it('Expect Product1 has a single Product Price', function() {
    filterProductPricesByProduct(productName1);
    cy.expectNumberOfRows(1);
  });

  it('Expect PLV1 has 2 Product Prices', function() {
    filterProductPricesByPLV(priceListVersionNameSearch1);
    cy.expectNumberOfRows(2);
  });

  it('Save initial Product Price data', function() {
    cy.selectNthRow(0).dblclick();
    cy.getStringFieldValue('PriceStd').then(val => {
      originalPriceStd = parseFloat(val);
    });
    cy.getStringFieldValue('PriceLimit').then(val => {
      originalPriceLimit = parseFloat(val);
    });
    cy.getStringFieldValue('PriceList').then(val => {
      originalPriceList = parseFloat(val);
    });
    cy.getStringFieldValue('C_UOM_ID').then(val => {
      originalUOM = val;
    });
    cy.getStringFieldValue('C_TaxCategory_ID').then(val => {
      originalTaxCategory = val;
    });
  });
});

describe('Create new Price List Version using the Price List Schema', function() {
  it('Add the Schema PriceList Version', function() {
    cy.visitWindow('540321', priceListID);
    PriceList.applyPriceListVersion(
      new PriceListVersion()
        .setName(priceListSchemaVersionName)
        .setValidFrom(priceListVersion2ValidFrom)
        .setDiscountSchema(priceListSchemaName)
        .setBasisPriceListVersion(priceListVersionName)
    );
    cy.expectNumberOfRows(2);
  });

  it('Run action "Create Price List"', function() {
    // the PriceListSchema PLV should always be the first, as its date is the biggest and the default sorting is by ValidFrom, desc
    cy.selectNthRow(0);
    cy.executeHeaderAction('M_PriceList_Create');
    cy.waitForSaveIndicator();
  });

  it('Expect Product1 has 2 Product Prices', function() {
    filterProductPricesByProduct(productName1);
    cy.expectNumberOfRows(2);
  });

  it('Expect PLV1 has 2 Product Prices', function() {
    filterProductPricesByPLV(priceListVersionNameSearch1);
    cy.expectNumberOfRows(2);
  });

  it('Expect PLV2 has 1 Product Price', function() {
    filterProductPricesByPLV(priceListVersionNameSearch2);
    cy.expectNumberOfRows(1);
  });

  it('Check the new Product Price', function() {
    cy.selectNthRow(0).dblclick();
    cy.getStringFieldValue('PriceStd').should(val => {
      expect(parseFloat(val)).to.be.closeTo(originalPriceStd + surchargeAmount, 0.01);
    });
    cy.getStringFieldValue('PriceLimit').should(val => {
      expect(parseFloat(val)).to.be.closeTo(originalPriceLimit, 0.01);
    });
    cy.getStringFieldValue('PriceList').should(val => {
      expect(parseFloat(val)).to.be.closeTo(originalPriceList, 0.01);
    });
    cy.getStringFieldValue('M_Product_ID').should('contains', productName1);
    cy.getStringFieldValue('M_Product_Category_ID').should('contain', categoryName);
    cy.getStringFieldValue('M_PriceList_Version_ID').should('contain', priceListName);
    cy.getStringFieldValue('C_UOM_ID').should('contain', originalUOM);
    cy.getStringFieldValue('C_TaxCategory_ID').should('contain', originalTaxCategory);
  });
});

function filterProductPricesByProduct(product) {
  cy.visitWindow(ProductPrices.windowId);
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_Product_ID', product, product, false, false, null, true);
  applyFilters();
}

function filterProductPricesByPLV(priceListVersionMatch) {
  cy.visitWindow(ProductPrices.windowId);
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.selectInListField('M_PriceList_Version_ID', priceListVersionMatch, false, null, true);
  applyFilters();
}
