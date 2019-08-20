import { Product, ProductCategory, ProductPrice } from '../../support/utils/product';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { BPartner } from '../../support/utils/bpartner';
import { BPartnerLocation } from '../../support/utils/bpartner_ui';
import { humanReadableNow } from '../../support/utils/utils';

describe('Change Product Price', function() {
  const date = humanReadableNow();
  const productName = `ProductName ${date}`;
  const productCategoryName = `ProductCategory ${date}`;
  const bpName = `Customer ${date}`;

  const priceList = `Testpreise Kunden (Deutschland)_Germany - Deutschland_EUR_2015-01-01`;
  const taxCatergory = `Regular Tax Rate 19% (Germany)`;
  let salesOrderRecordID;
  let productRecordID;

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
      .setListPriceAmount(`0`)
      .setStandardPriceAmount(`0`)
      .setLimitPriceAmount(`0`);

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
    new BPartner({ name: bpName })
      .setCustomer(true)
      .setPaymentTerm('Immediatlely')
      .setCustomerPricingSystem(`Testpreisliste Kunden`)
      .addLocation(new BPartnerLocation('Address1').setCity('Cologne').setCountry('Deutschland'))
      .apply();
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
    cy.selectNthRow(0);
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
