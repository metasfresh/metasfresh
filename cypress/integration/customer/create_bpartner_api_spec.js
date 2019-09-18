import { BPartner } from '../../support/utils/bpartner';
import { appendHumanReadableNow } from '../../support/utils/utils';

describe('Create new BPartner via API', function() {
  let customerName;

  // test
  let bpartnerID = null;

  it('Read the fixture', function() {
    cy.fixture('customer/create_bpartner_api_spec.json').then(f => {
      customerName = appendHumanReadableNow(f['customerName']);
    });
  });

  it('Create BPartner via API', function() {
    cy.fixture('sales/simple_customer.json').then(customerJson => {
      const bpartner = new BPartner({ ...customerJson, name: customerName });

      bpartner.setVendor(true).setVendorPaymentTerm(customerJson['paymentTerm']);

      bpartner.apply().then(bpartner => {
        bpartnerID = bpartner.id;
      });
    });
  });

  it('Check new Business Partner', function() {
    cy.visitWindow(123, bpartnerID);
  });
});
