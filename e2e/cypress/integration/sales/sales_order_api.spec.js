import { SalesOrder } from '../../support/utils/sales_order_api';
import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';

const date = humanReadableNow();
const customer1Name = `Customer1-${date}`;

describe('New sales order test', function () {
  const salesOrder1Name = `SalesOrder1-${date}`;
  let salesOrder1ID = null;

  before(function () {
    cy.visitWindow(143);
    cy.fixture('sales/simple_customer.json').then((customerJson) => {
      new BPartner({ ...customerJson, name: customer1Name }).clearContacts().setBank(undefined).apply();
    });

    cy.fixture('sales/sales_order.json').then((orderJson) => {
      new SalesOrder({ ...orderJson, reference: salesOrder1Name })
        .setBPartner(customer1Name)
        .apply()
        .then((salesOrder) => {
          salesOrder1ID = salesOrder.id;
        });
    });
  });

  it('Go to sales order', function () {
    cy.visitWindow(143, salesOrder1ID);
  });
});
