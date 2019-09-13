import { BPartner } from '../../support/utils/bpartner';
import { ProductCategory, ProductPrice, Product } from '../../support/utils/product';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { DocumentStatusKey } from '../../support/utils/constants';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';

let productForPackingMaterial;
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
let productType;
let vendorName;
let customerName;
let soQty;

let productPricePM1;
let productPricePM2;
let productPrice1;
let productPrice2;
describe('Create Purchase order from sales order', function() {
  it('Read the fixture', function() {
    cy.fixture('purchase/purchase_order_from_sales_order.json').then(f => {
      productForPackingMaterial = appendHumanReadableNow(f['productForPackingMaterial']);
      packingInstructionsName = appendHumanReadableNow(f['packingInstructionsName']);
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      purchasePriceSystem = appendHumanReadableNow(f['purchasePriceSystem']);
      purchasePriceList = appendHumanReadableNow(f['purchasePriceList']);
      purchasePriceListVersion = appendHumanReadableNow(f['purchasePriceListVersion']);
      productType = f['productType'];
      vendorName = appendHumanReadableNow(f['vendorName']);
      customerName = appendHumanReadableNow(f['customerName']);
      salesPriceSystem = appendHumanReadableNow(f['salesPriceSystem']);
      salesPriceList = appendHumanReadableNow(f['salesPriceList']);
      salesPriceListVersion = appendHumanReadableNow(f['salesPriceListVersion']);
      soQty = f['soQty'];
    });
  });

  it('Create price entities', function() {
    /**purchase price list */
    Builder.createBasicPriceEntities(purchasePriceSystem, purchasePriceListVersion, purchasePriceList, false);
    /**sales price list */
    Builder.createBasicPriceEntities(salesPriceSystem, salesPriceListVersion, salesPriceList, true);
    /**Create product for packing material which has both a purchase price list and a sales price list */
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPricePM2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });
  });
  it('Create product entities to be used in purchase order', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productForPackingMaterial)
        .setProductType(productType)
        .setProductCategory('24_Gebinde')
        .addProductPrice(productPricePM1)
        .addProductPrice(productPricePM2)
        .apply();
    });
  });
  it('Create packing related entities to be used in purchase order', function() {
    Builder.createPackingMaterial(productForPackingMaterial, packingInstructionsName);
  });
  it('Create product category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });
  it('Create vendor to use in product - Business partner tab - current vendor', function() {
    cy.fixture('sales/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName })
        .setVendorPricingSystem(purchasePriceSystem)
        .setVendorDiscountSchema(discountSchemaName)
        .apply();
    });
  });
  it('Create customer for sales order', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customerName })
        .setCustomerPricingSystem(salesPriceSystem)
        .setCustomerDiscountSchema(discountSchemaName)
        .apply();
    });
  });
  it('Create product prices', function() {
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice1 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(purchasePriceList);
    });
    cy.fixture('product/product_price.json').then(productPriceJson => {
      productPrice2 = Object.assign(new ProductPrice(), productPriceJson).setPriceList(salesPriceList);
    });
  });
  it('Create product', function() {
    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductType(productType)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice1)
        .addProductPrice(productPrice2)
        .addCUTUAllocation(packingInstructionsName)
        .setBusinessPartner(vendorName)
        .apply();
    });
  });
  it('Create a sales order', function() {
    cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
      new SalesOrder()
        .setBPartner(customerName)
        .setPriceSystem(salesPriceSystem)
        .addLine(new SalesOrderLine().setProduct(productName).setQuantity(1))
        .apply();
    });
    // cy.selectInListField('M_PricingSystem_ID', salesPriceSystem, false, null, true);
    cy.completeDocument();
  });

  it('Create purchase order from sales order', function() {
    cy.executeHeaderActionWithDialog('C_Order_CreatePOFromSOs');
    cy.pressStartButton();
    cy.waitUntilProcessIsFinished();
  });
  it('Go to purchase order and check product name and price, vendor, quantity and status', function() {
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
