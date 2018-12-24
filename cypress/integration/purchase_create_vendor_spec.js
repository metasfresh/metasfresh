/// <reference types="Cypress" />

import { BPartner, BPartnerLocation, BPartnerContact } from '../support/utils/bpartner';

describe('purchase order Test', function() {
  before(function() {
    // login before each test
    cy.loginByForm();
  });

  const timestamp = new Date().getTime(); // used in the document names, for ordering
  const vendorName = `${timestamp} (Cypress Test)`;

  it('Create a vendor with two contacts', function() {
    new BPartner
      .builder(vendorName)
      .setVendor(true)
      .setVendorPricingSystem("Testpreisliste Lieferanten")
      .setVendorDiscountSchema("STandard")
      .addLocation(new BPartnerLocation
        .builder('Address1')
        .setCity('Cologne')
        .setCountry('Deutschland')
        .build())
      .addContact(new BPartnerContact
        .builder()
        .setFirstName('Default')
        .setLastName('Contact')
        .setDefaultContact(true)
        .build())
      .addContact(new BPartnerContact
        .builder()
        .setFirstName('Secondary')
        .setLastName('Contact')
        .build())
      .build()
      .apply();
  });
});
