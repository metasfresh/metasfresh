import { getLanguageSpecific } from '../../support/utils/utils';
import { salesOrders } from '../../page_objects/sales_orders';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { Inventory, InventoryLine } from '../../support/utils/inventory';
import { DocumentStatusKey } from '../../support/utils/constants';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

const productName = 'Convenience Salat 250g';
const productQty = 10;
const locatorId = 'Hauptlager_StdWarehouse_Hauptlager_0_0_0';

const businessPartnerName = 'Test Lieferant 1';
const soProductQuantity = 2 * productQty;

// shipment
const shipmentQuantityTypeOption = 'Picked quantity';
const shipmentNotificationModalText = 'Created: 1 WorkPackage Queue;';
const expectedPackingStatus = 'Shipped';

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
let huValue1;
let huValue2;

describe('Create test data', function() {
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

  it('Create second single-HU inventory doc', function() {
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

  it('Save HU Value 2', function() {
    cy.selectTab('M_InventoryLine');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.getStringFieldValue('M_HU_ID').then(val => {
      huValue2 = val.split('_')[0];
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
      cy.selectRowByColumnAndValue(huCodeColumn, huValue1, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_HUEditor_PickHU', false, true, false);
  });

  it('Pick second HU', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue(pickingOrderColumn, soDocNumber, false, true);
    });
    cy.openPickingHUSelectionWindow();
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue(huCodeColumn, huValue2, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_HUEditor_PickHU', false, true, false);
  });

  it('Confirm Picks', function() {
    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue(pickingOrderColumn, soDocNumber, false, true);
    });
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue(huCodeColumn, huValue2, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_M_Picking_Candidate_Process', false, true, false);
    cy.waitForSaveIndicator();

    cy.selectLeftTable().within(() => {
      cy.selectRowByColumnAndValue(pickingOrderColumn, soDocNumber, false, true);
    });
    cy.selectRightTable().within(() => {
      cy.selectRowByColumnAndValue(huCodeColumn, huValue1, false, true);
    });
    cy.executeQuickAction('WEBUI_Picking_M_Picking_Candidate_Process', false, true, false);
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

  it('Shipment Disposition checks', function() {
    cy.expectCheckboxValue('IsToRecompute', false);
    cy.getStringFieldValue('C_BPartner_ID').should('contain', businessPartnerName);
    cy.getStringFieldValue('M_Product_ID').should('contain', productName);
    cy.getStringFieldValue('C_Order_ID').should('equal', soDocNumber);
    cy.getStringFieldValue('QtyOrdered_Calculated').should('equal', soProductQuantity.toString(10));
    cy.getStringFieldValue('QtyToDeliver ').should('equal', '0');
    cy.getStringFieldValue('QtyPickList ').should('equal', soProductQuantity.toString(10));
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
    cy.expectNumberOfRows(2);
    cy.selectRowByColumnAndValue('Handling Units', huValue1);
    cy.selectRowByColumnAndValue('Handling Units', huValue2);
  });

  it('Visit HU Editor and expect the 2 HUs have Packing Status Shipped', function() {
    cy.visitWindow(540189);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.selectInListField('HUStatus', expectedPackingStatus, false, null, true);
    cy.writeIntoStringField('Value', huValue1, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);

    cy.visitWindow(540189);
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.selectInListField('HUStatus', expectedPackingStatus, false, null, true);
    cy.writeIntoStringField('Value', huValue2, false, null, true);
    applyFilters();

    cy.expectNumberOfRows(1);
  });
});
