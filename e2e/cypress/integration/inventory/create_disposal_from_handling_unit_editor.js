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
import { getLanguageSpecific, appendHumanReadableNow } from '../../support/utils/utils';
import { applyFilters, clearNotFrequentFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

let productName;
let productQty;
let locatorId;
const notificationRegexpExpected = new RegExp(`Eigenverbrauch.*wurde erstellt.`);

// test
let huValue;

it('Read the fixture', function() {
  cy.fixture('inventory/create_disposal_from_handling_unit_editor.json').then(f => {
    productName = appendHumanReadableNow(f['productName']);
    productQty = f['productQty'];
    locatorId = f['locatorId'];
  });
});

describe('Create a single HU', function() {
  it('Create Product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .apply();
    });
  });

  it('Create a new single-HU inventory doc', function() {
    cy.fixture('inventory/inventory.json').then(inventoryJson => {
      const docTypeName = getLanguageSpecific(inventoryJson, 'singleHUInventoryDocTypeName');

      const inventoryLine = new InventoryLine()
        .setProductName(productName)
        .setQuantity(productQty)
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

describe('Create disposal from HU Editor', function() {
  it('Visit HU Editor, filter for the product, and expect a single row', function() {
    cy.visitWindow(540189);
    clearNotFrequentFilters();
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);
  });

  it('Save the HU Value', function() {
    cy.selectNthRow(0).dblclick();
    cy.waitForSaveIndicator();
    cy.getStringFieldValue('Value').then(value => {
      huValue = value;
    });
    cy.go('back');
  });

  it('Run quick-action "Dispose"', function() {
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_HU_MoveToGarbage', false, false);
  });

  it('Visit HU Editor, filter for the product, and expect no records', function() {
    cy.visitWindow(540189);
    clearNotFrequentFilters();
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(0);
  });
});

describe('Open Internal Use from notifications bell and do the checks', function() {
  it('Open Internal Use from notifications bell', function() {
    //window is "InternalUse"
    cy.openNotificationContaining(notificationRegexpExpected, 341).click();
  });

  it('Check Internal Use document', function() {
    cy.getStringFieldValue('M_Warehouse_ID').then(warehouseName => {
      expect(locatorId).contains(warehouseName);
    });
    cy.expectCheckboxValue('IsApproved', true);
    cy.getStringFieldValue('C_DocType_ID').should('equal', 'Disposal');
  });

  it('Check tab Handling Unit Assignment', function() {
    cy.selectTab('M_HU_Assignment');
    cy.expectNumberOfRows(1);
    // cy.selectNthRow(0); // removed - reason: first row is already selected by default, if we were to use the selection it will unselect 
    //                        and fail the test this due to https://github.com/metasfresh/metasfresh/issues/10167
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_HU_ID', true).then(val => {
      expect(val).contains(huValue);
    });
    cy.pressDoneButton();
  });

  it('Check tab Internal Use Line', function() {
    cy.selectTab('M_InventoryLine');
    cy.expectNumberOfRows(1);
    //cy.selectNthRow(0);  - reason why this was removed is described in the previous test
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_Product_ID', true).should('contain', productName);
    cy.getStringFieldValue('QtyInternalUse', true).should('equal', productQty.toString(10));
    cy.getStringFieldValue('M_Locator_ID', true).should('contain', locatorId);
    cy.pressDoneButton();
  });
});
