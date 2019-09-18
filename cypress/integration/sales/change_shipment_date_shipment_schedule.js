import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Bank } from '../../support/utils/bank';
import { Builder } from '../../support/utils/builder';
import { appendHumanReadableNow } from '../../support/utils/utils';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';

describe('Create Sales order', function() {
  let customer;
  let productName;
  let productCategoryName;
  let discountSchemaName;
  let priceSystemName;
  let priceListName;
  let priceListVersionName;
  let productQty;

  it('Read the fixture', function() {
    cy.fixture('sales/change_shipment_date_shipment_schedule.json').then(f => {
      customer = appendHumanReadableNow(f['customer']);
      productQty = appendHumanReadableNow(f['productQty']);
      productName = appendHumanReadableNow(f['productName']);
      productCategoryName = appendHumanReadableNow(f['productCategoryName']);
      discountSchemaName = appendHumanReadableNow(f['discountSchemaName']);
      priceSystemName = appendHumanReadableNow(f['priceSystemName']);
      priceListName = appendHumanReadableNow(f['priceListName']);
      priceListVersionName = appendHumanReadableNow(f['priceListVersionName']);
    });
  });

  it('Prepare test data', function() {
    Builder.createBasicPriceEntities(priceSystemName, priceListVersionName, priceListName, true);
    Builder.createBasicProductEntities(productCategoryName, productCategoryName, priceListName, productName, productName);

    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });

    cy.fixture('finance/bank.json').then(productJson => {
      Object.assign(new Bank(), productJson).apply();
    });

    cy.fixture('sales/simple_customer.json').then(customerJson => {
      new BPartner({ ...customerJson, name: customer }).setCustomerDiscountSchema(discountSchemaName).apply();
    });

    cy.readAllNotifications();
  });

  it('Create a sales order', function() {
    new SalesOrder()
      .setBPartner(customer)
      .setPriceSystem(priceSystemName)
      .addLine(new SalesOrderLine().setProduct(productName).setQuantity(productQty))
      .apply();

    cy.completeDocument();
  });

  it('go to Shipment disposition and change the shipment date', function() {
    cy.openReferencedDocuments('M_ShipmentSchedule');
    cy.selectNthRow(0).dblclick();

    /**Change shipment date */
    cy.selectOffsetDateViaPicker('DeliveryDate_Override', 1);
    cy.selectOffsetDateViaPicker('PreparationDate_Override', 1);

    var nextDay = new Date();
    nextDay.setDate(nextDay.getDate() + 1);
    let nextDayAsString = nextDay.toLocaleDateString('en-US', { year: 'numeric', month: '2-digit', day: '2-digit' });

    cy.get('.form-field-DeliveryDate_Effective, .form-field-DeliveryDate_Override, .form-field-PreparationDate_Effective, .form-field-PreparationDate_Override')
      .find('input')
      .should(input => {
        // this will fail on localhost due to the timezone issues. only works on jenkins for now
        expect(input.val()).to.have.string(nextDayAsString);
      });
  });
});
