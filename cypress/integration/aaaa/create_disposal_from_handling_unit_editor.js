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

import { Product } from '../../support/utils/product';
import { Inventory, InventoryLine } from '../../support/utils/inventory';
import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';

const date = humanReadableNow();
const productName = `Product_${date}`;
const productQty = 20;
const locatorId = 'Hauptlager_StdWarehouse_Hauptlager_0_0_0';

describe('Create a single HU', function() {
  it('Create Product', function() {
    // eslint-disable-next-line cypress/no-unnecessary-waiting
    cy.wait(1000); // see comment/doc of getLanguageSpecific
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setValue(productName)
        .apply();
    });
  });

  it('Create a new single-HU inventory doc', function() {
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
});
