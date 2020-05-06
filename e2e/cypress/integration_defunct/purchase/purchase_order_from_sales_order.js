import { BPartner } from '../../support/utils/bpartner';
import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { DocumentStatusKey } from '../../support/utils/constants';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';

describe('Create Purchase order from sales order', function() {
  let packingMaterialName;
  let packingInstructionsName;
  let productName;
  let productCategoryName;
  let discountSchemaName;
  let purchasePriceSystem;
  let purchasePriceList;
  let purchasePriceListVersion;
  let salesPriceSystem;
  let salesPriceList;
  let salesPriceListVersion;
  let vendorName;
  let customerName;
  let soQty;

  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_from_sales_order.json').then(f => {
      packingMaterialName = appendHumanReadableNow(f['packingMaterialName']);
      packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      purchasePriceSystem = appendHumanReadableNow(f['purchasePriceSystem']);
      purchasePriceList = appendHumanReadableNow(f['purchasePriceList']);
      purchasePriceListVersion = appendHumanReadableNow(f['purchasePriceListVersion']);
      salesPriceSystem = appendHumanReadableNow(f['salesPriceSystem']);
      salesPriceList = appendHumanReadableNow(f['salesPriceList']);
      salesPriceListVersion = appendHumanReadableNow(f['salesPriceListVersion']);
      vendorName = appendHumanReadableNow(f['vendorName']);
      customerName = appendHumanReadableNow(f['customerName']);
      soQty = f['soQty'];
    });
  });

  it('Create price and product entities', function() {
    Builder.createBasicPriceEntities(purchasePriceSystem, purchasePriceListVersion, purchasePriceList, false);
    Builder.createBasicPriceEntities(salesPriceSystem, salesPriceListVersion, salesPriceList, true);

    /** Create product for packing material which has both a purchase price list and a sales price list */
    let productPricePM1;
    let productPricePM2;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(packingMaterialName)
        .setProductCategory('24_Gebinde')
        .addProductPrice(productPricePM1)
        .addProductPrice(productPricePM2)
        .apply();
    });
  });

  it('Create packing related entities', function() {
    Builder.createPackingMaterial(packingMaterialName, packingInstructionsName);
  });

  it('Create product category, product, vendor and customer entities', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
    /**Create vendor to use in product - Business partner tab - current vendor */
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendorPricingSystem(purchasePriceSystem)
        .setVendorDiscountSchema(discountSchemaName)
        .apply();
    });
    /**Create customer for sales order */
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName })
        .setCustomerPricingSystem(salesPriceSystem)
        .setCustomerDiscountSchema(discountSchemaName)
        .apply();
    });

    let productPrice1;
    let productPrice2;
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName)
        .addProductPrice(productPrice1)
        .addProductPrice(productPrice2)
        .addCUTUAllocation(packingInstructionsName)
        .setBusinessPartner(vendorName)
        .apply();
    });
  });
  it('Create a sales order', function() {
    new SalesOrder()
      .setBPartner(customerName)
      .setPriceSystem(salesPriceSystem)
      .addLine(new SalesOrderLine().setProduct(productName).setQuantity(soQty))
      .apply();
    cy.completeDocument();
  });

  it('Create and check the Purchase Order', function() {
    cy.executeHeaderActionWithDialog('C_Order_CreatePOFromSOs');
    cy.pressStartButton();

    cy.openReferencedDocuments('AD_RelationType_ID-540164');
    cy.expectNumberOfRows(1);
    cy.selectNthRow(0).dblclick();

    cy.selectTab('C_OrderLine');
    cy.expectNumberOfRows(1);
    cy.selectNthRow(0);
    cy.openAdvancedEdit();

    cy.getStringFieldValue('M_Product_ID', true).should('contain', productName);
    cy.getStringFieldValue('PriceEntered', true).should('contain', '1.23');
    cy.getStringFieldValue('QtyEnteredTU', true).should('equals', soQty.toString());
    cy.getStringFieldValue('C_BPartner_ID', true).should('contains', vendorName);
    cy.pressDoneButton();

    cy.expectDocumentStatus(DocumentStatusKey.Drafted);
  });
});
