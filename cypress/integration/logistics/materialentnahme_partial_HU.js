import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { humanReadableNow } from '../../support/utils/utils';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';
import {
  applyFilters,
  selectNotFrequentFilterWidget,
  toggleNotFrequentFilters,
  clearNotFrequentFilters,
} from '../../support/functions';

const date = humanReadableNow();
const productForPackingMaterial = `ProductPackingMaterial_${date}`;
const productForPackingMaterial1 = `ProductPackingMaterial1_${date}`;
const packingMaterialForTU = `PackingMaterialForTU_${date}`;
const packingMaterialForLU = `PackingMaterialForLU${date}`;
const packingInstructionsNameForTU = `ProductPackingInstructionsForTU_${date}`;
const packingInstructionsNameForLU = `ProductPackingInstructionsForLU_${date}`;
const productName1 = `Product1_${date}`;
const productCategoryName = `ProductCategoryName_${date}`;
const discountSchemaName = `DiscountSchema_${date}`;
const priceSystemName = `PriceSystem_${date}`;
const priceListName = `PriceList_${date}`;
const priceListVersionName = `PriceListVersion_${date}`;
const productType = 'Item';
const vendorName = `Vendor_${date}`;
const materialentnahme = 'Materialentnahmelager';
const warehouseName = 'Hauptlager_StdWarehouse_Hauptlager_0_0_0';
const packingInstructionsVersionForTU = `PackingInstructionsVersionForTU_${date}`;
const packingInstructionsVersionForLU = `PackingInstructionsVersionForLU_${date}`;

describe('Partial material withdrawal in handling unit editor with Materialentnahmelager', function() {
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
    Builder.createProductWithPriceUsingExistingCategory(
      priceListName,
      productForPackingMaterial1,
      productForPackingMaterial1,
      productType,
      '24_Gebinde'
    );
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialForTU)
        .setProduct(productForPackingMaterial)
        .apply();
    });
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(packingMaterialForLU)
        .setProduct(productForPackingMaterial1)
        .apply();
    });
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsNameForTU)
        .apply();
    });
    cy.fixture('product/packing_instructions.json').then(packingInstructionsJson => {
      Object.assign(new PackingInstructions(), packingInstructionsJson)
        .setName(packingInstructionsNameForLU)
        .apply();
    });
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsVersionForTU)
        .setPackingInstructions(packingInstructionsNameForTU)
        .setPackingMaterial(packingMaterialForTU)
        .apply();
    });
    // cy.selectTab('M_HU_PI_Item');
    // cy.selectNthRow(1)
    //   .find('.Quantity')
    //   .dblclick({ force: true })
    //   .type('150', { force: true });
    // // cy.type('enter');
    // cy.selectTab('M_HU_PI_Item');
    cy.fixture('product/packing_instructions_version.json').then(pivJson => {
      Object.assign(new PackingInstructionsVersion(), pivJson)
        .setName(packingInstructionsVersionForLU)
        .setPackingInstructions(packingInstructionsNameForLU)
        .setPackingMaterial(packingMaterialForLU)
        .setUnit('Load/Logistique Unit')
        .apply();
      cy.selectTab('M_HU_PI_Item');
      cy.pressAddNewButton();
      cy.selectInListField('ItemType', 'Unter-Packvorschrift', true);
      cy.selectInListField('Included_HU_PI_ID', packingInstructionsNameForLU, true);
      cy.writeIntoStringField('Qty', '10', true, null, true);
      cy.pressDoneButton();
    });
  });

  it('Create category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .setValue(productCategoryName)
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
      packingInstructionsVersionForTU
    );
  });

  it('Create vendor', function() {
    new BPartner({ name: vendorName })
      .setVendor(true)
      .setVendorPricingSystem(priceSystemName)
      .setVendorDiscountSchema(discountSchemaName)
      .setPaymentTerm('30 days net')
      .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
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
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(15))
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
    cy.executeQuickAction('WEBUI_M_HU_CreateReceipt_NoParams', false, true);
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

    // cy.selectNthRow(0).click();
    // cy.executeQuickAction('WEBUI_M_HU_MoveTUsToDirectWarehouse');
    // cy.writeIntoStringField('QtyTU', '1', true, null, true);
    // cy.pressStartButton();

    // clearNotFrequentFilters();

    // toggleNotFrequentFilters();
    // selectNotFrequentFilterWidget('default');
    // cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
    // cy.writeIntoLookupListField('M_Locator_ID', materialentnahme, materialentnahme, false, false, null, true);
    // applyFilters();

    // cy.expectNumberOfRows(2);
  });
});
