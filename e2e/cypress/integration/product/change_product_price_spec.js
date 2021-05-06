import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Change Product Price', function() {
  let productName;
  let productCategoryName;
  let bpName;

  let priceList;
  let taxCatergory;
  let amount;
  let paymentTerm;
  let salesOrderRecordID;
  let productRecordID;
  let customerPricingSystem;

  it('Read fixture and prepare the names', function() {
    cy.fixture('product/change_product_price_spec.json').then(f => {
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      bpName = appendHumanReadableNow(f['bpName']);

      priceList = f['priceList'];
      taxCatergory = f['taxCatergory'];
      amount = f['amount'];
      paymentTerm = f['paymentTerm'];
      customerPricingSystem = f['customerPricingSystem'];
    });
  });

  it('Create a Product Category', function() {
    cy.fixture('product/simple_productCategory.json').then(productCategoryJson => {
      Object.assign(new ProductCategory(), productCategoryJson)
        .setName(productCategoryName)
        .apply();
    });
  });

  it('Create a Product and Price', function() {
    const productPrice = new ProductPrice()
      .setPriceList(priceList)
      .setTaxCategory(taxCatergory)
      .setListPriceAmount(amount)
      .setStandardPriceAmount(amount)
      .setLimitPriceAmount(amount);

    cy.fixture('product/simple_product.json').then(productJson => {
      Object.assign(new Product(), productJson)
        .setName(productName)
        .setProductCategory(productCategoryName + '_' + productCategoryName)
        .addProductPrice(productPrice)
        .setSold(false)
        .apply();
    });

    cy.getCurrentWindowRecordId().then(recordId => {
      productRecordID = recordId;
    });
  });

  it('Create Business Partner', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: bpName })
        .setPaymentTerm(paymentTerm)
        .setCustomerPricingSystem(customerPricingSystem)
        .apply();
    });
  });

  it('Create a Sales Order', function() {
    const salesOrderLine = new SalesOrderLine().setProduct(productName).setQuantity(`1`);

    cy.fixture('sales/sales_order.json').then(salesOrderJson => {
      Object.assign(new SalesOrder(), salesOrderJson)
        .setBPartner(bpName)
        .addLine(salesOrderLine)
        .apply();
    });

    cy.getCurrentWindowRecordId().then(recordId => {
      salesOrderRecordID = recordId;
    });
  });

  it(`Change Price and use it`, function() {
    cy.visitWindow(`140`, productRecordID);
    cy.selectTab(`M_ProductPrice`);
    cy.get(`[data-cy=cell-M_PriceList_Version_ID]`).click();
    cy.get(`[data-cy=cell-M_PriceList_Version_ID]`)
      .parent()
      .should('have.class', 'row-selected');
    cy.openAdvancedEdit();
    cy.writeIntoStringField('PriceStd', 1, true, null, true);
    cy.pressDoneButton();

    cy.visitWindow(`143`, salesOrderRecordID);
    cy.selectTab('C_OrderLine');
    cy.pressAddNewButton();
    cy.writeIntoLookupListField('M_Product_ID', productName, productName, false, true);
    cy.writeIntoStringField('QtyEntered', 1, true, null, true);
    cy.pressDoneButton();
  });
});
