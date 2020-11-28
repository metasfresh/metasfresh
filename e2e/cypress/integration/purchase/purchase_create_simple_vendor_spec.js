import { BPartner } from '../../support/utils/bpartner';
import { humanReadableNow } from '../../support/utils/utils';

describe('purchase - simple vendor spec', function() {
  const date = humanReadableNow();
  const vendorName = `Vendor ${date}`;

  it('Create a vendor with two contacts', function() {
    cy.fixture('purchase/simple_vendor.json').then(vendorJson => {
      new BPartner({ ...vendorJson, name: vendorName }).clearLocations().apply();
    });
  });
});
