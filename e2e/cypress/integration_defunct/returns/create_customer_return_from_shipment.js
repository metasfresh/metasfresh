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
import { salesOrders } from '../../page_objects/sales_orders';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { DocumentStatusKey } from '../../support/utils/constants';
import { Warehouse } from '../../support/utils/warehouse';
import { Builder } from '../../support/utils/builder';

let productName;
let productQty;
let locatorId;

let businessPartnerName;

// shipment
let shipmentQuantityTypeOption;
let shipmentNotificationModalText;

// warehouse
let warehouseName;

// test columns
const orderColumn = 'order';
const huSelectionHuCodeColumn = 'Value';
const pickingHuCodeColumn = 'huCode';
const productPartnerColumn = 'ProductOrBPartner';
const shipmentHandlingUnitsColumn = 'M_HU_ID';

// test
const expectedCustomerReturnDocType = 'Kundenwarenrückgabe';

let soDocNumber;
let soRecordId;
let huValue;
let shipmentRecordID;

describe('Create test data', function() {
  it('Read fixture and prepare the names', function() {
    cy.fixture('returns/create_customer_return_from_shipment.json').then(f => {
      productName = f['productName'];
      productQty = f['productQty'];
      locatorId = f['locatorId'];

      businessPartnerName = f['businessPartnerName'];

      shipmentQuantityTypeOption = f['shipmentQuantityTypeOption'];
      shipmentNotificationModalText = f['shipmentNotificationModalText'];

      warehouseName = appendHumanReadableNow(f['warehouseName']);
    });
  });

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
    Builder.createHUWithStock(productName, productQty, locatorId).then(huVal => (huValue = huVal));
  });

  it('Create Sales Order', function() {
    new SalesOrder()
      .setBPartner(businessPartnerName)
      .addLine(new SalesOrderLine().setProduct(productName).setQuantity(productQty))
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
    cy.selectRowByColumnAndValue({ column: productPartnerColumn, value: productName });
    cy.executeQuickAction('WEBUI_Picking_Launcher');
  });

  it('Pick first HU', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue({ column: orderColumn, value: soDocNumber }, false, true);
    });
    cy.executeQuickActionWithRightSideTable('WEBUI_Picking_HUEditor_Launcher');
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue({ column: huSelectionHuCodeColumn, value: huValue }, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_HUEditor_PickHU', true, false);
  });

  it('Confirm Picks', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue({ column: orderColumn, value: soDocNumber }, false, true);
    });
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue({ column: pickingHuCodeColumn, value: huValue }, false, true);
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
  });

  it('Open notifications and go to the shipment', function() {
    cy.openInboxNotificationWithText(businessPartnerName);
  });

  it('Shipment checks', function() {
    cy.expectDocumentStatus(DocumentStatusKey.Completed);
    cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
    cy.selectTab('M_HU_Assignment');
    cy.expectNumberOfRows(1);
    cy.selectRowByColumnAndValue({ column: shipmentHandlingUnitsColumn, value: huValue });
    cy.getCurrentWindowRecordId().then(id => (shipmentRecordID = id));
  });
});

describe('Create Customer return from Shipment', function() {
  it('Go to Shipment and create customer return', function() {
    cy.visitWindow(169, shipmentRecordID);
    cy.executeHeaderActionWithDialog('WEBUI_M_InOut_Shipment_SelectHUs');
    cy.selectRowByColumnAndValue({ column: huSelectionHuCodeColumn, value: huValue });
    cy.executeQuickAction('WEBUI_M_HU_ReturnFromCustomer', true, false);
    cy.pressDoneButton();
  });

  it('Check the Customer Return', function() {
    salesOrders.visit(soRecordId);
    cy.openReferencedDocuments('AD_RelationType_ID-540180');

    cy.expectNumberOfRows(1);
    cy.selectNthRow(0).dblclick();

    cy.expectDocumentStatus(DocumentStatusKey.Completed);
    cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
    cy.getStringFieldValue('C_DocType_ID').should('equals', expectedCustomerReturnDocType);

    cy.selectTab('M_HU_Assignment');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_HU_ID').should('contain', huValue);
    cy.getTextFieldValue('Products').should('contain', productName);
    cy.getStringFieldValue('Qty').should('equal', '0');
    cy.pressDoneButton();
  });
});
