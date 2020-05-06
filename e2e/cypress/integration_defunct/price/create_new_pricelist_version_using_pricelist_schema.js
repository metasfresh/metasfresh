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

describe('Create  a new pricelist version using pricelist schema', function() {
  let priceSystemName;
  let priceListVersionName;
  let priceListName;
  let priceListSchemaVersionName;
  let priceListVersion2ValidFrom;

  let categoryName;
  let productName;

  let priceListSchemaName;
  let surchargeAmount;

  let priceListID;
  let originalPriceStd;
  let originalPriceLimit;
  let originalPriceList;
  let originalUOM;
  let originalTaxCategory;

  it('Read the fixture', function() {
    cy.fixture('price/create_new_pricelist_version_using_pricelist_schema.json').then(f => {
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);

      priceListSchemaVersionName = appendHumanReadableNow(f['priceListSchemaVersionName']);
      priceListVersion2ValidFrom = f['priceListVersion2ValidFrom'];

      categoryName = appendHumanReadableNow(f['categoryName']);
      productName = appendHumanReadableNow(f['productName']);

      priceListSchemaName = appendHumanReadableNow(f['priceListSchemaName']);
      surchargeAmount = f['surchargeAmount'];
    });
  });

  describe('Create Price and Product', function() {
    it('Create Price', function() {
      Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);

      cy.getCurrentWindowRecordId().then(id => (priceListID = id));
    });

    it('Create Product and Category', function() {
      Builder.createBasicProductEntities(categoryName, categoryName, priceListName, productName, productName);
    });
  });

  describe('Create Price List Schema for Product Category', function() {
    it('Create Price List Schema', function() {
      new PriceListSchema()
        .setName(priceListSchemaName)
        .addLine(new PriceListSchemaLine().setProductCategory(categoryName).setStandardPriceSurchargeAmount(surchargeAmount))
        .apply();
    });

    it('Expect Product has a single Product Price', function() {
      filterProductPricesByProduct(productName);
      cy.expectNumberOfRows(1);
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

    it('Expect Product has 2 Product Prices', function() {
      filterProductPricesByProduct(productName);
      cy.expectNumberOfRows(2);
    });

    it('Check the new Product Price', function() {
      // not sure if the second row is always the correct one.
      cy.selectNthRow(1).dblclick();
      cy.getStringFieldValue('PriceStd').should(val => {
        expect(parseFloat(val)).to.be.closeTo(originalPriceStd + surchargeAmount, 0.01);
      });
      cy.getStringFieldValue('PriceLimit').should(val => {
        expect(parseFloat(val)).to.be.closeTo(originalPriceLimit, 0.01);
      });
      cy.getStringFieldValue('PriceList').should(val => {
        expect(parseFloat(val)).to.be.closeTo(originalPriceList, 0.01);
      });
      cy.getStringFieldValue('M_Product_ID').should('contains', productName);
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
});
