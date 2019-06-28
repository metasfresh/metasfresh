import { BPartner } from '../../support/utils/bpartner_api';

describe('Create new BPartner', function() {
  const timestamp = new Date().getTime();
  const customerName = `Sales Order Test ${timestamp}`;
  let bpartnerID = null;

  before(function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customerName });

      bpartner.apply().then(id => {
        bpartnerID = id;
      });
    });
  });

  describe('Create a new sales order', function() {
    it('Check new Business Partner', function() {
      cy.visit(`/window/123/${bpartnerID}`);
    });
  });
});
