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

import { humanReadableNow } from '../../support/utils/utils';
import { PriceListSchema, PriceListSchemaLine } from '../../support/utils/price_list_schema';
import { Builder } from '../../support/utils/builder';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';
import { ProductPrices } from '../../page_objects/product_prices';
import { PriceList, PriceListVersion } from '../../support/utils/pricelist';

let date = humanReadableNow();

// Price
const priceSystemName = `PriceSystem_${date}`;
const priceListVersionName = `PriceListVersion_${date}`;
const priceListName = `PriceList_${date}`;
const priceListSchemaVersionName = `PriceListSchemaVersion_${date}`;
const priceListVersion2ValidFrom = '01/02/2019';
const priceListVersionNameSearch1 = new RegExp(priceListName + '.*' + '2019-01-01$'); // magic from fixture (PLV doesn't have standard name :( )
const priceListVersionNameSearch2 = new RegExp(priceListName + '.*' + '2019-01-02$'); // magic from fixture (PLV doesn't have standard name :( )

// Product
const categoryName = `Category_${date}`;
const productName1 = `Product1 ${date}`;
const productName2 = `Product2 ${date}`;
const productType = 'Item';

// Price List Schema
const priceListSchemaName = `PriceListSchema_${date}`;
const surchargeAmount = 222;

// test
let priceListID;
let originalPriceStd;
let originalPriceLimit;
let originalPriceList;
let originalUOM;
let originalTaxCategory;

describe('Create Price and Products', function() {
  it('Create Price', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);

    cy.getCurrentWindowRecordId().then(id => (priceListID = id));
  });

  it('Create Product1 and Category', function() {
    // eslint-disable-next-line prettier/prettier
    Builder.createBasicProductEntities(categoryName, categoryName, priceListName, productName1, productName1, productType);
  });

  it('Create Product2', function() {
    // eslint-disable-next-line prettier/prettier
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productName2, productName2, productType, categoryName);
  });
});

describe('Create Price List Schema for Product', function() {
  it('Create Price List Schema', function() {
    new PriceListSchema()
      .setName(priceListSchemaName)
      // eslint-disable-next-line prettier/prettier
      .addLine(new PriceListSchemaLine().setProduct(productName1).setStandardPriceSurchargeAmount(surchargeAmount))
      .apply();
  });

  it('Expect Product1 has a single Product Price', function() {
    filterProductPricesByProduct(productName1);
    cy.expectNumberOfRows(1);
  });

  it('Expect PLV1 has 2 Product Prices', function() {
    filterProductPricesByPLV(priceListName, priceListVersionNameSearch1);
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
        .setBasisPricelistVersion(priceListVersionName)
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
    filterProductPricesByPLV(priceListName, priceListVersionNameSearch1);
    cy.expectNumberOfRows(2);
  });

  it('Expect PLV2 has 1 Product Price', function() {
    filterProductPricesByPLV(priceListName, priceListVersionNameSearch2);
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

function filterProductPricesByPLV(priceListName, priceListVersionMatch) {
  cy.visitWindow(ProductPrices.windowId);
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_PriceList_Version_ID', priceListName, priceListVersionMatch, false, false, null, true);
  applyFilters();
}
