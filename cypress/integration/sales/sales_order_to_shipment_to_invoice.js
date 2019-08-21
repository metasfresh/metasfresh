import { BPartner } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';
import { Builder } from '../../support/utils/builder';
import { humanReadableNow } from '../../support/utils/utils';
import { SalesOrder, SalesOrderLine } from '../../support/utils/sales_order';
import { RewriteURL } from '../../support/utils/constants';

describe('Create Sales order', function() {
  const date = humanReadableNow();
  const customer = `CustomerTest ${date}`;
  const productName = `ProductTest ${date}`;
  const productValue = `sales_order_test ${date}`;
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
      productName,
      productValue,
      productType
    );
    cy.fixture('discount/discountschema.json').then(discountSchemaJson => {
      Object.assign(new DiscountSchema(), discountSchemaJson)
        .setName(discountSchemaName)
        .apply();
    });
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customer }).setCustomerDiscountSchema(discountSchemaName);

      bpartner.apply();
    });

    cy.readAllNotifications();
  });
  it('Create a sales order', function() {
    new SalesOrder()
      .setBPartner(customer)
      .setPriceSystem(priceSystemName)
      .addLine(new SalesOrderLine().setProduct(productName).setQuantity(1))
      .apply();
    cy.completeDocument();
  });
  it('Go to Shipment disposition', function() {
    cy.openReferencedDocuments('M_ShipmentSchedule');
    cy.selectNthRow(0).click();
  });
  it('Generate shipments', function() {
    cy.executeQuickAction('M_ShipmentSchedule_EnqueueSelection');
    cy.pressStartButton();
    cy.waitUntilProcessIsFinished();
  });
  it('Open notifications', function() {
    cy.openInboxNotificationWithText(customer);
  });
  it('Billing - Invoice disposition', function() {
    // wait until current window is "Shipment"
    cy.url().should('matches', RewriteURL.ExactSingleView(169));

    cy.openReferencedDocuments('C_Invoice_Candidate');
    cy.selectNthRow(0).click();
  });
  it('Generate invoices on billing candidates', function() {
    cy.executeQuickAction('C_Invoice_Candidate_EnqueueSelectionForInvoicing', false, true);
    cy.pressStartButton();
    cy.openInboxNotificationWithText(customer);
  });
});
