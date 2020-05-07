/// <reference types="Cypress" />

import { BPartner, BPartnerLocation, BPartnerContact } from '../../support/utils/bpartner';
import { DiscountSchema } from '../../support/utils/discountschema';

describe('purchase order Test', function() {
    const timestamp = new Date().getTime() // used in the document names, for ordering
    const poReference = `${timestamp}`;
    const contactLastName = `2ndary ${timestamp}`;

    const vendorName = `Vendor ${timestamp}`
    const discountSchemaName = `DiscountSchema ${timestamp}`


    it('Create a vendor, then a purchase order', function() {

        new DiscountSchema(discountSchemaName)
            .setValidFrom('01/01/2019{enter}')
            .apply();

        new BPartner
            .builder(vendorName)
                .setVendor(true)
                .setVendorPricingSystem('Testpreisliste Lieferanten')
                .setVendorDiscountSchema(discountSchemaName)
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
