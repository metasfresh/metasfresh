import { BPartner } from '../../support/utils/bpartner';

describe('purchase - simple vendor spec', function() {
  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const vendorName = `Vendor ${timestamp}`;

  it('Create a vendor with two contacts', function() {
    cy.fixture('purchase/simple_vendor.json').then(vendorJson => {
      Object.assign(new BPartner(), vendorJson)
        .setName(vendorName)
        .apply();
    });
  });
});
