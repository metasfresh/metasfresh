import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { ProductCategory } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';

describe('Create Purchase order - complete - reactivate - complete', function() {
  let productForPackingMaterial;
  let packingInstructionsName;
  let productName1;
  let productCategoryName;
  let discountSchemaName;
  let priceSystemName;
  let priceListName;
  let priceListVersionName;
  let vendorName;
  let poQty;

  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_complete_reactivate_complete.json').then(f => {
      productForPackingMaterial = appendHumanReadableNow(f['productForPackingMaterial']);
      packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
      productName1 = appendHumanReadableNow(f['productName1']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
      vendorName = appendHumanReadableNow(f['vendorName']);
      poQty = f['poQty'];
    });
  });

  it('Create price and product entities', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, false);
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    Builder.createProductWithPriceUsingExistingCategory(priceListName, productForPackingMaterial, productForPackingMaterial, null, '24_Gebinde');
  });

  it('Create packing entities', function() {
    Builder.createPackingMaterial(productForPackingMaterial, packingInstructionsName);
  });

  it('Create product entities', function() {
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

  it('Create a purchase order', function() {
    new PurchaseOrder()
      .setBPartner(vendorName)
      .setPriceSystem(priceSystemName)
      .setPoReference('test')
      .addLine(new PurchaseOrderLine().setProduct(productName1).setQuantity(poQty))
      .apply();
    cy.completeDocument();
  });

  it('Reactivate the purchase order', function() {
    cy.reactivateDocument();
  });

  it('Complete the purchase order again', function() {
    cy.completeDocument();

    cy.selectTab('C_OrderLine');
    cy.expectNumberOfRows(2);
    cy.selectNthRow(0);
    cy.openAdvancedEdit();

    cy.getStringFieldValue('M_Product_ID', true).should('contain', productName1);
    cy.getStringFieldValue('PriceEntered', true).should('contain', '1.23');
    cy.getStringFieldValue('QtyEnteredTU', true).should('equals', poQty.toString());
    cy.pressDoneButton();
  });
});
