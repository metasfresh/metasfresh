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

import { Builder } from "../../support/utils/builder";
import { Pricesystem } from "../../support/utils/pricesystem";
import { PriceList, PriceListVersion } from "../../support/utils/pricelist";
import { products } from "../../page_objects/products";
import { Attribute, AttributeSet, AttributeValue } from "../../support/utils/attribute";
import { humanReadableNow } from "../../support/utils/utils";

// task: https://github.com/metasfresh/metasfresh-e2e/issues/233

describe('Create Attributes and AttributeSets', function() {
  const date = humanReadableNow();
  const attributeName1 = `Attribute1 ${date}`;
  const attributeName2 = `Attribute2 ${date}`;
  const attributeName3 = `Attribute3 ${date}`;
  const attributeValue1 = `AttributeValue1`;
  const attributeValue2 = `AttributeValue2`;

  const attributeSetName1 = `AttributeSet1 ${date}`;
  const attributeSetName2 = `AttributeSet2 ${date}`;

  let attribute1;
  let attribute2;
  let attribute3;
  let attributeSet1;
  let attributeSet2;

  it('Create Attribute1', function() {
    attribute1 = new Attribute(attributeName1)
      .setValue(attributeName1)
      .setAttributeValueType('List')
      .setInstanceAttribute(true)
      .setPricingRelevant(true)
      .setStorageRelevant(true)
      .setAttrDocumentRelevant(true)
      .addAttributeValue(new AttributeValue(attributeValue1).setValue(attributeValue1))
      .addAttributeValue(new AttributeValue(attributeValue2).setValue(attributeValue2))
      .apply();
  });

  it('Create Attribute2', function() {
    attribute2 = new Attribute(attributeName2)
      .setValue(attributeName2)
      .setAttributeValueType('List')
      .setInstanceAttribute(true)
      .setPricingRelevant(true)
      .setStorageRelevant(true)
      .setAttrDocumentRelevant(true)
      .addAttributeValue(new AttributeValue(attributeValue1).setValue(attributeValue1))
      .addAttributeValue(new AttributeValue(attributeValue2).setValue(attributeValue2))
      .apply();
  });

  it('Create Attribute3', function() {
    attribute3 = new Attribute(attributeName3)
      .setValue(attributeName3)
      .setAttributeValueType('List')
      .setInstanceAttribute(true)
      .setPricingRelevant(true)
      .setStorageRelevant(true)
      .setAttrDocumentRelevant(true)
      .addAttributeValue(new AttributeValue(attributeValue1).setValue(attributeValue1))
      .addAttributeValue(new AttributeValue(attributeValue2).setValue(attributeValue2))
      .apply();
  });

  it('Create AttributeSet1', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName1)
      .addAttribute(attribute1)
      .addAttribute(attribute2)
      .apply();
  });

  it('Create AttributeSet2', function() {
    // eslint-disable-next-line
    new AttributeSet(attributeSetName2)
      .addAttribute(attribute2)
      .addAttribute(attribute3)
      .apply();
  });
});


})

// describe('Change product prices and add a new one', function() {
//   it('aaaaaaaaaaaaaaa', function() {
//     cy.fixture('price/pricesystem.json').then(priceSystemJson => {
//       Object.assign(
//         new Pricesystem(/* useless to set anything here since it's replaced by the fixture */),
//         priceSystemJson
//       )
//         .setName(priceSystemName)
//         .apply();
//     });
//
//     let priceListVersion;
//     cy.fixture('price/pricelistversion.json').then(priceListVersionJson => {
//       priceListVersion = Object.assign(
//         new PriceListVersion(/* useless to set anything here since it's replaced by the fixture */),
//         priceListVersionJson
//       ).setName(priceListVersionName);
//     });
//
//     cy.fixture('price/pricelist.json').then(pricelistJson => {
//       Object.assign(new PriceList(/* useless to set anything here since it's replaced by the fixture */), pricelistJson)
//         .setName(priceListName)
//         .setPriceSystem(priceSystemName)
//         .setIsSalesPriceList(isSalesPriceList)
//         .addPriceListVersion(priceListVersion)
//         .apply();
//     });
//   });
//
// });
