/// <reference types="Cypress" />

import { BPartner, BPartnerLocation, BPartnerContact } from '../support/utils/bpartner';

describe('purchase order Test', function() {
    before(function() {
        // login before each test
        cy.loginByForm();
    });

    const timestamp = new Date().getTime(); // used in the document names, for ordering
    const poReference = `${timestamp} (Cypress Test)`;
    const contactLastName = `2ndary ${timestamp} (Cypress Test)`;

    const vendorName = `${timestamp} (Cypress Test)`; // "Test Lieferant"


    it('Create a vendor, then a purchase order', function() {

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
                    .setLastName(contactLastName)
                    .build())
                .build()
                .apply();

            cy.visit('/window/181/NEW');
            
            cy.writeIntoLookupListField('C_BPartner_ID', vendorName, vendorName);

            // AD_User should be pre-filled with 'default contact';
            cy.writeIntoLookupListField('AD_User_ID', contactLastName, contactLastName);
            
            cy.writeIntoStringField('POReference', poReference);
            cy.clickOnCheckBox('IsDropShip');
    });

});
