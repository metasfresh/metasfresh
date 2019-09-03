import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { Warehouse } from '../../support/utils/warehouse';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import {
  applyFilters,
  selectNotFrequentFilterWidget,
  toggleNotFrequentFilters,
  clearNotFrequentFilters,
} from '../../support/functions';

let productForPackingMaterial;
let packingInstructionsName;
let productName1;
let productCategoryName;
let discountSchemaName;
let priceSystemName;
let priceListName;
let priceListVersionName;
let productType;
let vendorName;
let warehouseName;
let quantity;
let quantityTu;
let materialentnahme;
let husAfterMoving;

it('Read the fixture', function() {
  cy.fixture('logistics/materialentnahme_complete_HUs.json').then(f => {
    productForPackingMaterial = appendHumanReadableNow(f['productForPackingMaterial']);
    packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
    productName1 = appendHumanReadableNow(f['productName1']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
    productType = f['productType'];
    vendorName = appendHumanReadableNow(f['vendorName']);
    materialentnahme = f['materialentnahme'];
    warehouseName = appendHumanReadableNow(f['warehouseName']);
    quantity = f['quantity'];
    quantityTu = f['quantityTu'];
    husAfterMoving = f['husAfterMoving'];
  });
});

it('Create  warehouse', function() {
  cy.fixture('misc/warehouse.json').then(warehouseJson => {
    Object.assign(new Warehouse(), warehouseJson)
      .setName(warehouseName)
      .setValue(warehouseName)
      .apply();
  });
});
it('Create price entities', function() {
  Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
  cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
    Object.assign(new DiscountSchema(), discountSchemaJson)
      .setName(discountSchemaName)
      .apply();
  });
});
it('Create product and packing related entities', function() {
  Builder.createProductWithPriceUsingExistingCategory(
    priceListName,
    productForPackingMaterial,
    productForPackingMaterial,
    productType,
    '24_Gebinde'
  );
  Builder.createPackingMaterial(productForPackingMaterial, packingInstructionsName);
});

it('Create category', function() {
  cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
    Object.assign(new ProductCategory(), productCategoryJson)
      .setName(productCategoryName)
      .apply();
  });
});

it('Create product1', function() {
  Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(
    productCategoryName,
    productCategoryName,
    priceListName,
    productName1,
    productName1,
    productType,
    packingInstructionsName
  );
});

it('Create vendor', function() {
  cy.fixture('sales/simple_vendor.json').then(vendorJson => {
    new BPartner({ ...vendorJson, name: vendorName })
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .apply();

    cy.readAllNotifications();
  });
});

it('Create a purchase order and visit Material Receipt Candidates', function() {
  new PurchaseOrder()
    .setBPartner(vendorName)
    .setPriceSystem(priceSystemName)
    .setPoReference('test')
    .setWarehouse(warehouseName)
    .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(quantity))
    .apply();
  cy.completeDocument();
});

it('Visit referenced Material Receipt Candidates', function() {
  cy.openReferencedDocuments('M_ReceiptSchedule');
  cy.expectNumberOfRows(2);
});

it('Receive HUs and create Material Receipt for all of the records created', function() {
  cy.selectNthRow(0).click();
  /**Receive HUs with a quantity of 20, that would mean that in HU - husAfterMoving=40 records will appear. */
  cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig', false, true);
  cy.writeIntoStringField('QtyTU', quantityTu, false, null, true);
  cy.pressStartButton();
  cy.waitUntilProcessIsFinished();
  cy.selectAllRowsOnCurrentPage();
  cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', true, false);
  cy.pressDoneButton();
});
it('Check if Materialentnahmelager warehouse exists', function() {
  cy.visitWindow('139');
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoStringField('Name', materialentnahme, false, null, true);
  applyFilters();

  cy.expectNumberOfRows(1);
});
it('Filter for product and warehouse=Materialentnahmelager in handling unit editor', function() {
  cy.visitWindow('540189');

  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
  cy.writeIntoLookupListField('M_Locator_ID', materialentnahme, materialentnahme, false, false, null, true);
  cy.selectInListField('HUStatus', 'Active', false, null, true);
  applyFilters();
  cy.expectNumberOfRows(0);
});
it('Filter for product and newly created warehouse in handling unit editor', function() {
  clearNotFrequentFilters();
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
  cy.writeIntoLookupListField('M_Locator_ID', warehouseName, warehouseName, false, false, null, true);
  cy.selectInListField('HUStatus', 'Active', false, null, true);
  applyFilters();
});
it('Select all rows and execute "Materialentnahme" which will move all the HUs from newly created warehouse to Materialentnahmelager', function() {
  cy.selectAllRowsOnCurrentPage();
  cy.executeQuickAction('WEBUI_M_HU_MoveToDirectWarehouse', false, false);

  clearNotFrequentFilters();
  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
  cy.writeIntoLookupListField('M_Locator_ID', materialentnahme, materialentnahme, false, false, null, true);
  cy.selectInListField('HUStatus', 'Active', false, null, true);
  applyFilters();

  cy.expectNumberOfRows(husAfterMoving);
});
