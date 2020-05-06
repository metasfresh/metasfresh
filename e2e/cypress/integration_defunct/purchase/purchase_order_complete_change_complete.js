import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { PackingMaterial } from '../../support/utils/packing_material';
import { PackingInstructions } from '../../support/utils/packing_instructions';
import { PackingInstructionsVersion } from '../../support/utils/packing_instructions_version';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow, getLanguageSpecific } from '../../support/utils/utils';
import { DocumentStatusKey } from '../../support/utils/constants';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';

describe('Create Purchase order - complete - change - complete', function() {
  let productForPackingMaterial;
  let packingInstructionsName;
  let productName1;
  let productCategoryName;
  let discountSchemaName;
  let priceSystemName;
  let priceListName;
  let priceListVersionName;
  let vendorName;

  let initialPoQty;
  let newPoQtyEntered;
  let newPoQtyEnteredTU;
  let newPoPrice;

  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_complete_change_complete.json').then(f => {
      productForPackingMaterial = appendHumanReadableNow(f['productForPackingMaterial']);
      packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
      productName1 = appendHumanReadableNow(f['productName1']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      vendorName = appendHumanReadableNow(f['vendorName']);

      initialPoQty = f['initialPoQty'];
      newPoQtyEntered = f['newPoQtyEntered'];
      newPoQtyEnteredTU = f['newPoQtyEnteredTU'];
      newPoPrice = f['newPoPrice'];
    });
  });

  it('Create price and product entities to be used in purchase order', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterial, productForPackingMaterial, null, '24_Gebinde');
    cy.fixture('product/packing_material.json').then(packingMaterialJson => {
      Object.assign(new PackingMaterial(), packingMaterialJson)
        .setName(productForPackingMaterial)
        .setProduct(productForPackingMaterial)
        .apply();
    });
  });
  it('Create packing related items to be used in purchase order', function() {
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
  it('Create product to be used in purchase order', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });

    Builder.createProductWithPriceAndCUTUAllocationUsingExistingCategory(productCategoryName, productCategoryName, priceListName, productName1, productName1, null, packingInstructionsName);
  });
  it('Create vendor', function() {
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendorPricingSystem(priceSystemName)
        .setVendorDiscountSchema(discountSchemaName)
        .apply();
    });
    cy.readAllNotifications();
  });
  it('Create a purchase order and complete it', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .setPoReference('test')
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(initialPoQty))
      .apply();
    cy.completeDocument();
    // cy.waitUntilProcessIsFinished();
  });
  /**Reactivate purchase order */
  it('Reactivate the purchase order', function() {
    cy.reactivateDocument();
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      cy.get('.tag.tag-default').contains(getLanguageSpecific(miscDictionary, DocumentStatusKey.InProgress));
    });

    cy.log('change Quantity TU');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.writeIntoStringField('QtyEnteredTU', newPoQtyEnteredTU);
    cy.pressDoneButton();
    cy.selectTab('C_OrderLine');

    cy.log('change Quantity');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.writeIntoStringField('QtyEntered', newPoQtyEntered);
    cy.pressDoneButton();
    cy.selectTab('C_OrderLine');

    cy.log('change Price');
    cy.selectNthRow(0);
    cy.openAdvancedEdit();
    cy.writeIntoStringField('PriceEntered', newPoPrice);
    cy.pressDoneButton();
    cy.completeDocument();
  });
});
