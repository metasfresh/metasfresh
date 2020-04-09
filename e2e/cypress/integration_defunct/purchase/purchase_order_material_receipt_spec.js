/// <reference types="Cypress" />

import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';
import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';

describe('purchase order Test', function() {
    const timestamp = new Date().getTime() // used in the document names, for ordering
    const poReference = `${timestamp}`;

    const vendorName = `Vendor ${timestamp}`
    const discountSchemaName = `DiscountSchema ${timestamp}`


    it('Create a vendor, then a purchase order', function() {

        cy.log(`Create discount schema with name=${discountSchemaName}`)
        new DiscountSchema(discountSchemaName)
            .setValidFrom('01/01/2019{enter}')
            .addDiscountBreak(new DiscountBreak()
                 .setBreakValue('0')
                .setBreakDiscount('0'))
            .apply();
        cy.screenshot();

        cy.log(`Create vendor with name=${vendorName}`)
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
                .build()
                .apply();
        cy.screenshot();

        cy.log(`Create purchase order with poReference=${poReference}`)
        new PurchaseOrder
            .builder()
            .setBPartner(vendorName)
            .setPoReference(poReference)
            .addLine(new PurchaseOrderLine
                .builder()
                .setProduct('Convenience')
                .setTuQuantity('10')
                .build())
            .setDocAction('Complete')
            .setDocStatus('Completed')
            .build()
            .apply();
        cy.screenshot();

        cy.log('Zoom to the material receipt window')
        cy.wait(5000) // give the system a change to create those receipt schedules
        cy.get('body').type('{alt}6') // open referenced-records-sidelist
        cy.selectReference('M_ReceiptSchedule').click()
        cy.screenshot();
    });
});
