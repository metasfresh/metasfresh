/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundAmountConditions } from '../../support/utils/contract';
import { BPartner, BPartnerLocation } from '../../support/utils/bpartner';
//import { PurchaseOrder, PurchaseOrderLine } from '../../support/utils/purchase_order';

describe('Create tiered amount-based refund conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    it('Create tiered amount-based refund conditions and a vendor with a respective contract', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `t5 tiered amount-based refund conditions ${timestamp}`;
        createAndCompleteTransition(transitionName, null, null);

        const conditionsName = `t5 tiered amount-based refund conditions ${timestamp}`;
        createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'T'/*Tiered / Gestaffelte Rückvergütung*/);

        const bPartnerName = `t5 tiered amount-based refund conditions ${timestamp}`;

        new BPartner
            .builder(bPartnerName)
            .vendor(true)
            .vendorDiscountSchema('STandard')
            .location(new BPartnerLocation
                .builder('Address1')
                .city('Cologne')
                .country('Deutschland')
                .build())
            .build()
            .apply();

        cy.executeHeaderAction('C_Flatrate_Term_Create_For_BPartners')
            .selectInListField('C_Flatrate_Conditions_ID', conditionsName, conditionsName)
            .writeIntoStringField('StartDate', '01/01/2018{enter}')
            .pressStartButton();
        // TODO: verify that we have a contract (maybe via selectReference)

        // new PurchaseOrder
        //     .builder()
        //         .bPartner(bPartnerName)
        //         .line(new PurchaseOrderLine
        //             .builder()
        //             .product('Convenience')
        //             .tuQuantity('2')
        //             .build())
        //         .docAction('Complete')
        //         .docStatus('Completed')
        //     .build()
        //     .apply();
    });
});
