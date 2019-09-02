/** todo The test is done but is failing due to https://github.com/metasfresh/me03/issues/2381.
 * After this issue will be fixed, the test will pass.
 *  It will need to be moved from integration/defunct dir. */
import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { applyFilters, selectNotFrequentFilterWidget, selectFrequentFilterWidget, toggleNotFrequentFilters, toggleFrequentFilters } from '../../support/functions';

let customer1;
let productName1;
let productCategoryName;
let discountSchemaName;
let priceSystemName;
let priceListName;
let priceListVersionName;
let productType;

it('Read the fixture', function() {
  cy.fixture('sales/open_picking_terminal_and_filter.json').then(f => {
    customer1 = appendHumanReadableNow(f['customer1']);
    productName1 = appendHumanReadableNow(f['productName1']);
    productCategoryName = appendHumanReadableNow(f['productCategoryName']);
    discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);

    priceSystemName = appendHumanReadableNow(f['priceSystemName']);
    priceListName = appendHumanReadableNow(f['priceListName']);
    priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
    productType = f['productType'];
  });
});

it('Create price, product and bpartner', function() {
  Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
  Builder.createBasicProductEntities(productCategoryName, productCategoryName, priceListName, productName1, productName1, productType);
  cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
    Object.assign(new DiscountSchema(), discountSchemaJson)
      .setName(discountSchemaName)
      .apply();
  });
  cy.fixture('sales/simple_customer.json').then(customerJson => {
    const bpartner = new BPartner({ ...customerJson, name: customer1 }).setCustomerDiscountSchema(discountSchemaName);
    bpartner.apply();
  });
});
it('Create the sales order', function() {
  new SalesOrder()
    .setBPartner(customer1)
    .setPriceSystem(priceSystemName)
    .addLine(new SalesOrderLine().setProduct(productName1).setQuantity(1))
    .apply();
  cy.completeDocument();
});
it('Open Picking terminal', function() {
  cy.visitWindow('540345');
  toggleFrequentFilters();
  selectFrequentFilterWidget(true);
  cy.get('.ranges')
    .find('li')
    .first()
    .should('contain', 'Today')
    .click();
  applyFilters();
  /**Even though the date selected is today, the filter label will show yesterday's date due to: https://github.com/metasfresh/me03/issues/2381
   * that's the reason why the test is failing currently
   */

  toggleNotFrequentFilters();
  selectNotFrequentFilterWidget('default');
  cy.writeIntoLookupListField('M_Product_ID', productName1, productName1, false, false, null, true);
  cy.writeIntoLookupListField('C_BPartner_Customer_ID', customer1, customer1, false, false, null, true);
  applyFilters();

  cy.expectNumberOfRows(2);
});
