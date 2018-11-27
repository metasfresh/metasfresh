/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundAmountConditions } from '../../support/utils/contract';

describe('Create tiered amount-based refund conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    it('Create tiered amount-based refund conditions', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `${timestamp} tiered amount-based refund conditions (Cypress Test)`;
        createAndCompleteTransition(transitionName, null, null);

        const conditionsName = `${timestamp} tiered amount-based refund conditions (Cypress Test)`;
        createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'T'/*Tiered / Gestaffelte Rückvergütung*/);
    });
});
