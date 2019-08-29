import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { applyFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

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
let materialentnahme;
let warehouseName;

let totalHUs;
let countOnPage;
let countAfterMoving;

it('Read the fixture', function() {
  cy.fixture('logistics/materialentnahme_complete_HU.json').then(f => {
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
    warehouseName = f['warehouseName'];
  });
});

describe('Change warehouse to Materialentnahmelager', function() {
  it('Create price entities', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
  });
  it('Create packing related entities', function() {
    // eslint-disable-next-line
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterial, productForPackingMaterial, productType, "24_Gebinde");
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(productForPackingMaterial)
        .setProduct(productForPackingMaterial)
        .apply();
    });
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsName)
        .apply();
    });
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsName)
        .setPackingInstructions(packingInstructionsName)
        .setPackingMaterial(productForPackingMaterial)
        .apply();
    });
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
});

describe('Create a purchase order and Material Receipts', function() {
  it('Create a purchase order and visit Material Receipt Candidates', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .setPoReference('test')
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(1))
      .apply();
    cy.completeDocument();
  });

  it('Visit referenced Material Receipt Candidates', function() {
    cy.openReferencedDocuments('M_ReceiptSchedule');
    cy.expectNumberOfRows(2);
  });

  it('Create Material Receipt 1', function() {
    cy.selectNthRow(0).click();
    cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults');
    cy.selectNthRow(0, true);
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams');
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
  it('Change warehouse in handling unit editor', function() {
    cy.visitWindow('540189');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Locator_ID', warehouseName, warehouseName, false, false, null, true);
    cy.selectInListField('HUStatus', 'Active', false, null, true);
    applyFilters();
  });
  it('Count all rows with the warehouseName=Hauptlager and status=Active', function() {
    cy.countAllRows('540189').then(rows => {
      cy.log(rows);
      totalHUs = parseInt(rows);
    });
  });
  it('Select all rows on current page and move them to direct warehouse', function() {
    cy.selectAllRowsOnCurrentPage();
    cy.get('.table tbody tr').then(el => {
      countOnPage = parseInt(el.length);
      countAfterMoving = parseInt(totalHUs) - parseInt(countOnPage);
    });
    cy.executeQuickAction('WEBUI_M_HU_MoveToDirectWarehouse', false, false);
    /**Filter to check if HUs have been moved - the number of HUs to be moved is the total number
     * minus those moved earlier
     */
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoLookupListField('M_Locator_ID', warehouseName, warehouseName, false, false, null, true);
    cy.selectInListField('HUStatus', 'Active', false, null, true);
    applyFilters();
    /**currently this fails because "Handling Unit 1000178 is currently selected in the picking as source HU
     * and must therefore not be moved. (SourceHuMayNotBeRemovedException)"
     * see: https://chat.metasfresh.org/metasfresh/pl/61sw4n5z5ib8te8ibne6ckrtxy*/
    cy.countAllRows('540189').then(el => {
      expect(el).to.equals(countAfterMoving);
    });
  });
});
