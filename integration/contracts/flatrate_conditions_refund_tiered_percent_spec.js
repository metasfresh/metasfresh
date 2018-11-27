/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundPercentConditions } from '../../support/utils/contract';

describe('Create refund flatrate conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    it('Create tiered percent-based refund conditions', function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `${timestamp} Refund contract transisiton (Cypress Test)`;
        createAndCompleteTransition(transitionName, null, null);

        const conditionsName = `${timestamp} tiered percent-based refund conditions (Cypress Test)`;
        createAndCompleteRefundPercentConditions(conditionsName, transitionName, 'T'/*Tiered / Gestaffelte Rückvergütung*/);
    });
});
