import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';

describe('Create new BPartner', function() {
  const date = humanReadableNow();
  const customerName = `Sales Order Test ${date}`;
  let bpartnerID = null;

  before(function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customerName });

      bpartner
        .setVendor(true)
        .setVendorPaymentTerm('30 days net')
        .setPaymentTerm('30 days net');

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  describe('Create a new sales order', function() {
    it('Check new Business Partner', function() {
      cy.visit(`/window/123/${bpartnerID}`);
    });
  });
});
