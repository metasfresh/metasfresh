/// <reference types="Cypress" />

import { BPartner, BPartnerLocation, BPartnerContact } from '../support/utils/bPartner';

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
      .vendor(true)
      .location(new BPartnerLocation
        .builder('Address1')
        .city('Cologne')
        .country('Deutschland')
        .build())
      .contact(new BPartnerContact
        .builder()
        .firstName('Default')
        .lastName('Contact')
        .defaultContact(true)
        .build())
      .contact(new BPartnerContact
        .builder()
        .firstName('Secondary')
        .lastName('Contact')
        .build())
      .build()
      .apply();
  });
});
