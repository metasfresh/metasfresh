/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundAmountConditions } from '../../support/utils/contract';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
import { DiscountSchema, DiscountBreak } from '../../support/utils/discountschema';

describe('Create accumulated amount-based (AA) refund conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm()
    });
    
    it('Create accumulated amount-based refund conditions', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `Transition (AA) ${timestamp}`;
        createAndCompleteTransition(transitionName, null, null);
        cy.screenshot();

        const conditionsName = `Conditions (AA) ${timestamp}`;
        createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'A'/*Accumulated / Gesamtrückvergütung*/);
        cy.screenshot();

        const discountSchemaName = `DiscountSchema (AA) ${timestamp}`;
        new DiscountSchema
            .builder(discountSchemaName)
            .addDiscountBreak(new DiscountBreak
                .builder()
                .setBreakValue(0)
                .setBreakDiscount(0)
                .build())
            .build()
            .apply()
        cy.screenshot();

        const bPartnerName = `Vendor (AA) ${timestamp}`
        new BPartner
            .builder(bPartnerName)
            .setVendor(true)
            .setVendorDiscountSchema(discountSchemaName)
            .addLocation(new BPartnerLocation
                .builder('Address1')
                .setCity('Cologne')
                .setCountry('Deutschland')
                .build())
            .build()
            .apply()

        cy.executeHeaderActionWithDialog('C_Flatrate_Term_Create_For_BPartners')
            .selectInListField('C_Flatrate_Conditions_ID', conditionsName, conditionsName)
            .writeIntoStringField('StartDate', '01/01/2019{enter}')
            .pressStartButton();
        cy.screenshot();
    });
});

