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

import { getLanguageSpecific, humanReadableNow } from '../../support/utils/utils';
import { salesOrders } from '../../page_objects/sales_orders';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { Inventory, InventoryLine } from '../../support/utils/inventory';
import { DocumentStatusKey } from '../../support/utils/constants';
import { Warehouse } from '../../support/utils/warehouse';

let productName = 'Convenience Salat 250g';
let productQty = 10;
let locatorId = 'Hauptlager_StdWarehouse_Hauptlager_0_0_0';

let businessPartnerName = 'Test Lieferant 1';
let soProductQuantity = 2 * productQty;

// shipment
let shipmentQuantityTypeOption = 'Picked quantity';
let shipmentNotificationModalText = 'Created: 1 WorkPackage Queue;';

// warehouse
let warehouseName = 'Troubled Quality Warehouse' + humanReadableNow();

// test columns
// todo @kuba: these should be somehow made translation independent!
//   eg. add the columnId as a data object in the table header (data object instead of class coz it's free form text so it may contains spaces and periods);
//      ref: https://docs.cypress.io/guides/references/best-practices.html#Selecting-Elements
//   or something else?
const pickingOrderColumn = 'Order';
const huCodeColumn = 'Code';
const productPartnerColumn = 'Product / Partner';

// test
let soDocNumber;
let soRecordId;
let huValue;
let shipmentRecordID;

describe('Create test data', function() {
// // // //    it('Read fixture and prepare the names', function() {
// // // //      cy.fixture('picking/pick_HUs_and_create_shipment.json').then(f => {
// // // //        productName = f['productName'];
// // // //        productQty = f['productQty'];
// // // //        locatorId = f['locatorId'];
// // // //
// // // //        businessPartnerName = f['businessPartnerName'];
// // // //        soProductQuantity = f['soProductQuantity'];
// // // //
// // // //        shipmentQuantityTypeOption = f['shipmentQuantityTypeOption'];
// // // //        shipmentNotificationModalText = f['shipmentNotificationModalText'];
// // // //        expectedPackingStatus = f['expectedPackingStatus'];

// // // //        warehouseName = appendDate(f['warehouseName'];)
// // // //      });
// // // //    });

  it('Create quality return warehouse', function() {
    cy.fixture('misc/warehouse.json').then(warehouseJson => {
      Object.assign(new Warehouse(), warehouseJson)
        .setName(warehouseName)
        .setValue(warehouseName)
        .setIsQualityReturnWarehouse(true)
        .apply();
    });
  });

  it('Create single-HU inventory doc', function() {
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
      huValue = val.split('_')[0];
    });
    cy.pressDoneButton();
  });

  it('Create Sales Order', function() {
    new SalesOrder()
      .setBPartner(businessPartnerName)
      .addLine(new SalesOrderLine().setProduct(productName).setQuantity(soProductQuantity))
      .apply();
    cy.completeDocument();

    cy.getCurrentWindowRecordId().then(id => (soRecordId = id));
    cy.getStringFieldValue('DocumentNo').then(docNO => (soDocNumber = docNO));
  });
});

describe('Pick the SO', function() {
  it('Visit "Picking Terminal (Prototype)"', function() {
    // unfortunately the picking assignment is not created instantly and there's no way to check when it is created except by refreshing the page,
    // so i have to wait for it to be created :(
    cy.waitUntilProcessIsFinished();
    cy.visitWindow('540345');
  });

  it('Select first row and run action Pick', function() {
    cy.selectRowByColumnAndValue(productPartnerColumn, productName);
    cy.executeQuickAction('WEBUI_Picking_Launcher');
  });

  it('Pick first HU', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue(pickingOrderColumn, soDocNumber, false, true);
    });
    cy.openPickingHUSelectionWindow();
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue(huCodeColumn, huValue, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_HUEditor_PickHU', true, false);
  });

  it('Confirm Picks', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue(pickingOrderColumn, soDocNumber, false, true);
    });
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue(huCodeColumn, huValue, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_M_Picking_Candidate_Process', true, false);
    cy.waitForSaveIndicator();
  });
});

describe('Generate the Shipment', function() {
  it('Open the Referenced Shipment Disposition', function() {
    salesOrders.visit(soRecordId);
    cy.openReferencedDocuments('M_ShipmentSchedule');

    cy.expectNumberOfRows(1);
    cy.selectNthRow(0).dblclick();
  });

  it('Run action "Generate shipments"', function() {
    cy.readAllNotifications();
    cy.executeHeaderAction('M_ShipmentSchedule_EnqueueSelection');
    cy.selectInListField('QuantityType', shipmentQuantityTypeOption, true, null, true);
    cy.expectCheckboxValue('IsCompleteShipments', true, true);
    cy.expectCheckboxValue('IsShipToday', false, true);

    cy.pressStartButton();
    cy.getNotificationModal(shipmentNotificationModalText);
    cy.expectCheckboxValue('Processed', true);
  });

  it('Open notifications and go to the shipment', function() {
    cy.openInboxNotificationWithText(businessPartnerName);
  });

  it('Shipment checks', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
    cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
    cy.selectTab('M_HU_Assignment');
    cy.expectNumberOfRows(1);
    cy.selectRowByColumnAndValue('Handling Units', huValue);
    cy.getCurrentWindowRecordId().then(id => (shipmentRecordID = id));
  });
});

describe('Create Customer return from Shipment', function() {
  it('Go to Shipment and create customer return', function() {
    cy.visitWindow(169, shipmentRecordID);
    cy.executeHeaderActionWithDialog('WEBUI_M_InOut_Shipment_SelectHUs');
    cy.selectRowByColumnAndValue(huCodeColumn, huValue);
    cy.executeQuickAction('WEBUI_M_HU_ReturnFromCustomer', true, false);
    cy.pressDoneButton();
  });

  it('Check the Customer Return', function() {
    salesOrders.visit(soRecordId);
    cy.openReferencedDocuments('AD_RelationType_ID-540180');

    cy.expectNumberOfRows(1);
    cy.selectNthRow(0).dblclick();
  });
  //
  // it('a', function() {
  //
  // });
  // it('a', function() {
  //
  // });
  //
  // it('a', function() {
  //
  // });
  //
  // it('a', function() {
  //
  // });
  //
  // it('a', function() {
  //
  // });

});
