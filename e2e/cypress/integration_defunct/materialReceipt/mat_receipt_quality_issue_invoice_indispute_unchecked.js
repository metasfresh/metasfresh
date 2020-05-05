/**this test is incomplete as the deduction needs to be checked in the jasper report too
 * but currently, we don't have a solution for using cypress in pdf reports */
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { BPartner } from '../../support/utils/bpartner';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { Warehouse, WarehouseRoute } from '../../support/utils/warehouse';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { purchaseOrders } from '../../page_objects/purchase_orders';

let qualityNoteName;
let qualityDiscountPercent;
let qtyToInvoice1;
let qtyToInvoice2;

let warehouseName;

let productName1;
let productName2;
let productCategoryName;
let discountSchemaName;
let priceSystemName;
let priceListName;
let priceListVersionName;
let productType;
let vendorName;
let product1Quantity;
let product2Quantity;
let docBaseType;
let deduction;

let purchaseOrderRecordId;

const productColumnName = 'M_Product_ID';
const qtyEnteredColumnName = 'QtyEntered';

it('Read the fixture', function() {
  cy.fixture('materialReceipt/mat_receipt_quality_issue_invoice_indispute_unchecked.json').then(f => {
    qualityNoteName = f['qualityNoteName'];
    qualityDiscountPercent = f['qualityDiscountPercent'];
    qtyToInvoice1 = f['qtyToInvoice1'];
    qtyToInvoice2 = f['qtyToInvoice2'];

    warehouseName = appendHumanReadableNow(f['warehouseName']);
    productName1 = appendHumanReadableNow(f['productName1']);
    productName2 = appendHumanReadableNow(f['productName2']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);

    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
    productType = f['productType'];
    vendorName = appendHumanReadableNow(f['vendorName']);
    product1Quantity = f['product1Quantity'];
    product2Quantity = f['product2Quantity'];
    docBaseType = f['docBaseType'];
    deduction = f['deduction'];
  });
});
it('Disable all other quality issue warehouses', function() {
  cy.visitWindow('139');
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.setCheckBoxValue('IsIssueWarehouse', true, false, null, true);
  applyFilters();

  cy.get('tr').then(el => {
    if (el.length > 1) {
      cy.selectNthRow(0).dblclick();
      cy.setCheckBoxValue('IsIssueWarehouse', false);
    }
  });
});

it('Ensure no quality issue warehouse exists', function() {
  cy.visitWindow('139');
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.setCheckBoxValue('IsIssueWarehouse', true, false, null, true);
  applyFilters();

  cy.expectNumberOfRows(0);
});

it('Create quality issue warehouse', function() {
  cy.fixture('misc/warehouse.json').then(warehouseJson => {
    Object.assign(new Warehouse(), warehouseJson)
      .setName(warehouseName)
      .setValue(warehouseName)
      .setIsQualityIssueWarehouse(true)
      .addRoute(new WarehouseRoute().setDocBaseType(docBaseType))
      .apply();
  });
});

it('Create Price', function() {
  Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
});

it('Create Product Category', function() {
  cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
    Object.assign(new ProductCategory(), productCategoryJson)
      .setName(productCategoryName)
      .apply();
  });
});

it('Create product 1', function() {
  Builder.createProductWithPriceUsingExistingCategory(priceListName, productName1, productName1, productType, productCategoryName);
});
it('Create product 2 and vendor', function() {
  Builder.createProductWithPriceUsingExistingCategory(priceListName, productName2, productName2, productType, productCategoryName);
  cy.fixture('sales/simple_vendor.json').then(vendorJson => {
    new BPartner({ ...vendorJson, name: vendorName })
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .apply();
  });
  cy.readAllNotifications();
});

it('Create the purchase order and complete it', function() {
  new PurchaseOrder()
    .setBPartner(vendorName)
    .setPriceSystem(priceSystemName)
    .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(product1Quantity))
    .addLine(new PurchaseOrderLine().setProduct(productName2).setQuantity(product2Quantity))
    .apply();

  cy.completeDocument();
});

it('Save values needed for the next steps', function() {
  cy.getCurrentWindowRecordId().then(recordId => {
    purchaseOrderRecordId = recordId;
  });
});

it('Visit referenced Material Receipt Candidates, expect 2 rows', function() {
  cy.openReferencedDocuments('M_ReceiptSchedule');
  cy.expectNumberOfRows(2);
});

it('Select the first row and Receive the CUs', function() {
  cy.selectNthRow(1);
  cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveCUs');
});
it('Select the row and create material receipt for it', function() {
  cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
  cy.waitForSaveIndicator();
  cy.pressDoneButton();
});

it('Select the second row and Receive the CUs with 5% alteration', function() {
  cy.selectNthRow(0);
  cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveCUs');
  cy.writeIntoStringField('QualityDiscountPercent', qualityDiscountPercent, true, null, true);
  cy.selectInListField('QualityNotice', qualityNoteName, true, null, true);
});
it('Select the row and create material receipt for it', function() {
  cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
  cy.waitForSaveIndicator();
  cy.pressDoneButton();
});

it('Go to the referenced Material Receipt and check the one with 5% alteration', function() {
  cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
  cy.openReferencedDocuments('184');
  cy.selectNthRow(0).dblclick();
});
it('Go to Invoice Disposition and uncheck In Dispute', function() {
  cy.visitWindow(purchaseOrders.windowId, purchaseOrderRecordId);
  cy.openReferencedDocuments('C_Invoice_Candidate');
  cy.expectNumberOfRows(2);
  cy.selectRowByColumnAndValue({ column: productColumnName, value: productName1 }).dblclick();
  cy.expectCheckboxValue('IsInDispute', true);
  cy.getStringFieldValue('QtyToInvoice').should('equal', qtyToInvoice1.toString());
  cy.setCheckBoxValue('IsInDispute', false);
  filterInBillingCandidatesWindow();
  cy.selectRowByColumnAndValue({ column: productColumnName, value: productName2 }).dblclick();
  cy.expectCheckboxValue('IsInDispute', false);
  cy.getStringFieldValue('QtyToInvoice').should('equal', qtyToInvoice2.toString());
  filterInBillingCandidatesWindow();
});
it('Select both rows and generate invoices', function() {
  cy.selectAllRowsOnCurrentPage();
  cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing', false, true);
  cy.pressStartButton();
  cy.waitUntilProcessIsFinished();
});
it('Open Purchase Invoice and check the deduction in Invoice Line tab', function() {
  cy.openInboxNotificationWithText('Rechnung');
  cy.selectTab('C_InvoiceLine');
  cy.expectNumberOfRows(3);
  cy.selectRowByColumnAndValue({ column: qtyEnteredColumnName, value: deduction });
});
function filterInBillingCandidatesWindow() {
  cy.visitWindow('540092');
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('Bill_BPartner_ID', vendorName, vendorName, false, false, null, true);
  applyFilters();
}
