import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Builder } from '../../support/utils/builder';
import { humanReadableNow } from '../../support/utils/utils';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import {
  applyFilters,
  selectNotFrequentFilterWidget,
  toggleNotFrequentFilters,
  toggleFrequentFilters,
} from '../../support/functions';

describe('Create Sales order', function() {
  const date = humanReadableNow();
  const customer1 = `CustomerTest1 ${date}`;
  const productName1 = `ProductTest1 ${date}`;
  const productName2 = `ProductTest2 ${date}`;
  const productCategoryName = `ProductCategoryName ${date}`;
  const discountSchemaName = `DiscountSchemaTest ${date}`;
  const priceSystemName = `PriceSystem ${date}`;
  const priceListName = `PriceList ${date}`;
  const priceListVersionName = `PriceListVersion ${date}`;
  const productType = 'Item';

  before(function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
    Builder.createBasicProductEntities(
      productCategoryName,
      productCategoryName,
      priceListName,
      productName1,
      productName1,
      productType
    );
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customer1 }).setCustomerDiscountSchema(discountSchemaName);
      bpartner.apply();
    });
    // cy.fixture('sales/simple_customer.json').then(customerJson => {
    //   const bpartner = new BPartner({ ...customerJson, name: customer2 }).setCustomerDiscountSchema(discountSchemaName);
    //   bpartner.apply();
    // });
  });
  it('Create the first sales order', function() {
    new SalesOrder()
      .setBPartner(customer1)
      .setPriceSystem(priceSystemName)
      .addLine(new SalesOrderLine().setProduct(productName1).setQuantity(1))
      .apply();
    cy.completeDocument();
  });
  //   it('Create the second sales order', function() {
  //     new SalesOrder()
  //       .setBPartner(customer2)
  //       .setPriceSystem(priceSystemName)
  //       .addLine(new SalesOrderLine().setProduct(productName2).setQuantity(1))
  //       .apply();
  //     cy.completeDocument();
  //   });
  it('Open Picking terminal', function() {
    cy.visitWindow('540345');
    toggleNotFrequentFilters();
    selectNotFrequentFilterWidget('default');
    cy.writeIntoStringField('M_Product_ID', productName1, false, null, true);
    cy.writeIntoStringField('C_BPartner_Customer_ID', customer1, false, null, true);
    applyFilters();

    toggleFrequentFilters();
    // selectFrequentFilterWidget('Filter by: Preparation Date');
  });
});
