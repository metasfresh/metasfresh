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

import { getLanguageSpecific } from '../../support/utils/utils';
import { Inventory, InventoryLine } from '../../support/utils/inventory';
import { Builder } from '../../support/utils/builder';
import { DiscountSchema } from '../../support/utils/discountschema';

const productName = 'Product';
const productQty = 10;
const locatorId = 'Hauptlager_StdWarehouse_Hauptlager_0_0_0';

// test columns
// todo @kuba: these should be somehow made translation independent!
//   eg. add the columnId as a data object in the table header (data object instead of class coz it's free form text so it may contains spaces and periods);
//      ref: https://docs.cypress.io/guides/references/best-practices.html#Selecting-Elements
//   or something else?
const pickingOrderColumn = 'Order';
const huCodeColumn = 'Code';

// test
let soDocNumber;
let soRecordId;
let huValue1;
let huValue2;

describe('Create test data', function() {
  it('Create price entities', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });

  it('Create first single-HU inventory doc', function() {
    let uomName;
    cy.fixture('product/simple_product.json').then(productJson => {
      uomName = getLanguageSpecific(productJson, 'c_uom');
    });

    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      const docTypeName = getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName');

      const inventoryLine = new InventoryLine()
        .setProductName(productName)
        .setQuantity(productQty)
        .setC_UOM_ID(uomName)
        .setM_Locator_ID(locatorId)
        .setIsCounted(true);

      new Inventory()
        .setWarehouse(inventoryJson.warehouseName)
        .setDocType(docTypeName)
        .addInventoryLine(inventoryLine)
        .apply();
    });
  });

  it('Save HU Value 1', function() {
    cy.selectTab('M_InventoryLine');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_HU_ID').then(val => {
      huValue1 = val.split('_')[0];
    });
    cy.pressDoneButton();
  });
});
