import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import { applyFilters, clearNotFrequentFilters, selectNotFrequentFilterWidget, toggleNotFrequentFilters } from '../../support/functions';

describe('Partial material withdrawal in handling unit editor with Materialentnahmelager', function() {
  let productForPackingMaterialTU;
  let productForPackingMaterialLU;
  let packingMaterialForTU;
  let packingMaterialForLU;
  let packingInstructionsNameForTU;
  let packingInstructionsNameForLU;
  let productName1;
  let productCategoryName;
  let discountSchemaName;
  let priceSystemName;
  let priceListName;
  let priceListVersionName;
  let vendorName;
  let materialentnahme;
  let warehouseName;
  let packingInstructionsVersionForTU;
  let packingInstructionsVersionForLU;

  it('Read the fixture', function() {
    cy.fixture('logistics/materialentnahme_partial_HU.json').then(f => {
      productForPackingMaterialTU = appendHumanReadableNow(f['productForPackingMaterialTU']);
      productForPackingMaterialLU = appendHumanReadableNow(f['productForPackingMaterialLU']);
      packingMaterialForTU = appendHumanReadableNow(f['packingMaterialForTU']);
      packingMaterialForLU = appendHumanReadableNow(f['packingMaterialForLU']);
      packingInstructionsNameForTU = appendHumanReadableNow(f['packingInstructionsNameForTU']);
      packingInstructionsNameForLU = appendHumanReadableNow(f['packingInstructionsNameForLU']);
      productName1 = appendHumanReadableNow(f['productName1']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      vendorName = appendHumanReadableNow(f['vendorName']);
      materialentnahme = f['materialentnahme'];
      warehouseName = f['warehouseName'];
      packingInstructionsVersionForTU = appendHumanReadableNow(f['packingInstructionsVersionForTU']);
      packingInstructionsVersionForLU = appendHumanReadableNow(f['packingInstructionsVersionForLU']);
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
  it('create product from 24_Gebinde category in order to use it for packing material for transport unit', function() {
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterialTU, productForPackingMaterialTU, null, '24_Gebinde');
  });
  it('create product from 24_Gebinde category in order to use it for packing material for loading unit', function() {
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterialLU, productForPackingMaterialLU, null, '24_Gebinde');
  });
  it('create packing material for handling unit - Transport unit', function() {
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialForTU)
        .setProduct(productForPackingMaterialTU)
        .apply();
    });
  });
  it('create packing material for handling unit - Load unit', function() {
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialForLU)
        .setProduct(productForPackingMaterialLU)
        .apply();
    });
  });
  it('create packing instruction for handling unit - Transport unit', function() {
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsNameForTU)
        .apply();
    });
  });
  it('create packing instruction for handling unit - Load unit', function() {
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsNameForLU)
        .apply();
    });
  });
  it('create packing instruction version for handling unit - Transport unit; with packgut - quantity=0.00 and packmittel', function() {
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsVersionForTU)
        .setPackingInstructions(packingInstructionsNameForTU)
        .setPackingMaterial(packingMaterialForTU)
        .apply();
    });
  });

  it('create packing instruction version for handling unit - Loading unit; with packmittel and packingInstructionsNameForTU as unter-packvorschrift', function() {
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsVersionForLU)
        .setPackingInstructions(packingInstructionsNameForLU)
        .setPackingMaterial(packingMaterialForLU)
        .setHandlingUnitType('Load/Logistique Unit')
        .apply();
    });
    cy.selectTab('M_HU_PI_Item');
    cy.pressAddNewButton();
    cy.selectInListField('ItemType', 'Unter-Packvorschrift', true);
    /**VERY IMPORTANT!!! the packing instruction for TU have to be set as unter-packvorschrift otherwise the LU quantity won't appear after creating a material receipt */
    cy.selectInListField('Included_HU_PI_ID', packingInstructionsNameForTU, true);
    cy.writeIntoStringField('Qty', 10, true, null, true);
    cy.pressDoneButton();
  });

  it('Create category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create product for the purchase order', function() {
    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(productCategoryName, productCategoryName, priceListName, productName1, productName1, null, packingInstructionsVersionForTU);
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

  describe('Create a purchase order and Material Receipts', function() {
    it('Create a purchase order and visit Material Receipt Candidates', function() {
      new PurchaseOrder()
        .setBPartner(vendorName)
        .setPriceSystem(priceSystemName)
        .setPoReference('test')
        .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(5))
        .apply();
      cy.completeDocument();
    });

    it('Visit referenced Material Receipt Candidates', function() {
      cy.openReferencedDocuments('M_ReceiptSchedule');
      cy.expectNumberOfRows(2);
    });

    it('Create Material Receipt', function() {
      cy.selectNthRow(0).click();
      cy.executeQuickAction('WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingDefaults');
      cy.selectNthRow(0, true);
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
    it('Partial material withdrawal in handling unit editor', function() {
      cy.visitWindow('540189');
      toggleNotFrequentFilters();
      selectNotFrequentFilterWidget('default');
      cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
      cy.writeIntoLookupListField('M_Locator_ID', warehouseName, warehouseName, false, false, null, true);
      applyFilters();
    });
    it('Select first row - related to LU quantity and extract 1 from there', function() {
      cy.selectNthRow(0).click();
      cy.executeQuickAction('WEBUI_M_HU_MoveTUsToDirectWarehouse', false, true);
      cy.writeIntoStringField('QtyTU', 1, true, null, true);
      cy.pressStartButton();

      clearNotFrequentFilters();

      toggleNotFrequentFilters();
      selectNotFrequentFilterWidget('default');
      cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
      cy.writeIntoLookupListField('M_Locator_ID', warehouseName, warehouseName, false, false, null, true);
      applyFilters();

      cy.selectNthRow(0)
        .find('.Quantity')
        .should('contain', '40');
    });
  });
});
