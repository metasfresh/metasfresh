/// <reference types="Cypress" />

import { createAndCompleteTransition, createAndCompleteRefundAmountConditions } from '../../support/utils/contract';

describe('Create refund flatrate conditions', function() {
    before(function() {
        // login before each test and open the flatrate conditions window
        cy.loginByForm();
    });
    
    it(`Create and complete condtions and transition record`, function () {
        const timestamp = new Date().getTime(); // used in the document names, for ordering

        const transitionName = `${timestamp} Refund contract transisiton (Cypress Test)`;
        createAndCompleteTransition(transitionName, null, null);

        const conditionsName = `${timestamp} Refund contract (Cypress Test)`;
        createAndCompleteRefundAmountConditions(conditionsName, transitionName, 'T'/*Tiered / Gestaffelte Rückvergütung*/);
    });
});
